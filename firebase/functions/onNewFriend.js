const functions = require("firebase-functions");

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
