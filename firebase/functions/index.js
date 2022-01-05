const functions = require("firebase-functions");

/**
 * On user rated callbacks
 */
exports.onNewRating = functions.database.ref('/rating/{userId}/{ratingId}').onCreate((snapshot, context) => {
    const params = context.params;
    const userId = params.userId;
    const rating = snapshot.val();
    const raterId = rating.who;

    functions.logger.log(raterId, ' rated: ', userId);

    const root = snapshot.ref.root
    const statsRef = root.child('userStats').child(userId);
    const friendRef = root.child('friend').child(raterId).child(userId);

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
                    sumFloat = +stats.scoreSum || 0;
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
        .then(() => {
            // Update friend last rated date
            const ratingDate = rating.date;

            if (typeof ratingDate !== 'number' || typeof raterId !== 'string') {
                throw new Error('illegal [rating.date] or [rated.who]')
            }

            return friendRef.child('lastRated').set(ratingDate);
        })
        .catch(ex => {
            functions.logger.warn('unable to update ', userId, ' rating stats: ', ex);
        });
});


/**
 * On friend added callbacks
 */
exports.onNewFriend = functions.database.ref('/friend/{userId}/{friendId}').onCreate((snapshot, context) => {
    const params = context.params;
    const userId = params.userId;
    const friendId = params.friendId;

    functions.logger.log(userId, ' added: ', friendId);

    const root = snapshot.ref.root
    const followingRef = root.child('userStats').child(userId).child('following');
    const followersRef = root.child('userStats').child(friendId).child('followers');

    const incrementCount = function(snap, ref) {
        var count = snap.val() || 0;

        if (typeof count !== 'number' || count <= 0) {
            count = 0;
        }

        functions.logger.log('count is = ', count);

        return ref.set(count + 1);
    }

    return followingRef.once('value')
        .then(snap => {
            // Update following count
            return incrementCount(snap, followingRef);
        })
        .then(() => {
            return followersRef.once('value');
        })
        .then((snap) => {
            // Update followers count
            return incrementCount(snap, followersRef);
        })
        .catch(ex => {
            functions.logger.warn('unable to update ', userId, ' friend stats: ', ex);
        })
});
