const functions = require("firebase-functions");
const admin = require('firebase-admin');
const { user } = require("firebase-functions/v1/auth");
const serverValue = admin.database.ServerValue;
const log = functions.logger;

/**
 * On user rated callbacks
 */
exports.onNewRating = functions.database.ref('/rating/{userId}/{ratingId}').onCreate((snapshot, context) => {
    const params = context.params;
    const userId = params.userId;
    const rating = snapshot.val();
    const raterId = rating.who;
    const ratingNotification = {
        who: raterId,
        type: "NEW_RATING",
        date: serverValue.TIMESTAMP
    };

    log.debug(raterId, 'rated: ', userId);

    const root = snapshot.ref.root
    const statsRef = root.child('userStats').child(userId);
    const ratingsRef = statsRef.child('ratings');
    const scoreSumRef = statsRef.child('scoreSum');
    const friendRef = root.child('friend').child(raterId).child(userId);
    const notificationsRef = root.child('notification').child(userId);

    // Update ratings counter
    return ratingsRef.set(serverValue.increment(1))
        .then(() => {
            // Update scoreSum counter
            const multiplier = +rating.multiplier || 1;
            const ratingValue = rating.value;

            if (typeof ratingValue !== 'number' || ratingValue <= 0) {
                throw Error('illegal [rating.value], was ' + ratingValue);
            }

            return scoreSumRef.set(serverValue.increment(multiplier * ratingValue));
        })
        .then(() => {
            // Update friend last rated date
            const ratingDate = rating.date;

            if (typeof ratingDate !== 'number' || typeof raterId !== 'string') {
                throw new Error('illegal [rating.date] or [rated.who]')
            }

            return friendRef.child('lastRated').set(ratingDate);
        })
        .then(() => {
            // Notify rated user
            return notificationsRef.push(ratingNotification);
        })
        .catch(ex => {
            log.warn('unable to update ', userId, ' rating stats: ', ex);
        });
});
