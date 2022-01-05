const functions = require("firebase-functions");

exports.onNewRating = functions.database.ref('/rating/{userId}/{ratingId}').onCreate((snapshot, context) => {
    const params = context.params;
    const userId = params.userId;
    const rating = snapshot.val();

    functions.logger.log('user rated with id = ', userId);

    const root = snapshot.ref.root
    const statsRef = root.child('userStats').child(userId);

    return statsRef.once('value')
        .then(snap => {
            // Update user stats counters
            const stats = snap.val();
            var ratingInt = 0;
            var sumFloat = 0.0;
            const multiplier = +rating.multiplier || 1;
            const ratingValue = rating.value;

            if (typeof ratingValue !== 'number' || ratingValue <= 0) {
                throw Error('illegal [rating.value], was ' + ratingValue);
            }

            if (stats) {
                try {
                    ratingInt = +stats.ratings || 0;
                    sumFloat = +stats.sum || 0;
                } catch (ex) { }
            }

            functions.logger.log('ratingInt is = ', ratingInt);
            functions.logger.log('sumFloat is = ', sumFloat);
            functions.logger.log('multiplier is = ', multiplier);
            functions.logger.log('ratingValue is = ', ratingValue);

            return statsRef.update({
                ratings: (ratingInt + 1),
                scoreSum: (sumFloat + (ratingValue * multiplier))
            });
        })
        .catch(ex => {
            functions.logger.warn('unable to update ', userId, ' rating stats: ', ex);
        });
});