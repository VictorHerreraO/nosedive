const functions = require("firebase-functions");
const admin = require('firebase-admin');
const serverValue = admin.database.ServerValue;
const log = functions.logger;

/**
 * On friend added callbacks
 * 
 * Listen on /name to prevent callback being called after rating a non-following user
 */
exports.onNewFriend = functions.database.ref('/friend/{userId}/{friendId}/name').onCreate((snapshot, context) => {
    const params = context.params;
    const userId = params.userId;
    const friendId = params.friendId;
    const followNotification = {
        who: userId,
        type: "NEW_FOLLOW",
        date: serverValue.TIMESTAMP
    };

    log.info(userId, ' added: ', friendId);

    const root = snapshot.ref.root
    const followingRef = root.child('userStats').child(userId).child('following');
    const followersRef = root.child('userStats').child(friendId).child('followers');
    const notificationsRef = root.child('notification').child(friendId);

    return followingRef.set(serverValue.increment(1))
        .then(() => {
            return followersRef.set(serverValue.increment(1))
        })
        .catch(ex => {
            log.warn('unable to update ', userId, ' friend stats: ', ex);
        })
        .then(() => {
            return notificationsRef.push(followNotification);
        })
        .catch(ex => {
            log.warn('unable to push user notification', ex);
        });
});
