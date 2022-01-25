const functions = require("firebase-functions");
const admin = require('firebase-admin');
const log = functions.logger;


exports.onNewNotification = functions.database.ref('/notification/{userId}/{notificationId}').onCreate((snapshot, context) => {
    const userId = context.params.userId;
    const value = snapshot.val();
    value["id"] = context.params.notificationId;
    const payload = JSON.stringify(value); // Prevent FirebaseMessagingError: data must only contain string values

    log.debug('new notification for user: ' + userId);

    const root = snapshot.ref.root
    const tokenRef = root.child('token').child(userId)
    const messaging = admin.messaging();

    const tokenPairs = {};

    // Read user tokens
    return tokenRef.once('value')
        .then(snap => {
            // Send notifications
            const messages = [];

            snap.forEach(child => {
                const key = child.key;
                const token = child.val();
                const tokenString = token.string;
                if (!tokenString) return;

                messages.push(messaging
                    .send({
                        data: {
                            payload: payload
                        },
                        token: tokenString
                    })
                    .catch(ex => {
                        const errCode = ex.code;
                        log.error('unable to notify token ' + key + ' code: ' + errCode);
                        if (ex.code == 'messaging/registration-token-not-registered') {
                            child.ref
                                .remove()
                                .then(() => {
                                    log.info('token removed')
                                });
                        }
                    }));
            });

            return Promise.all(messages);
        })
        .then(result => {
            // Get responses
            log.info(result.length + ' notifications sent!');
        })
        .catch(ex => {
            log.warn('unable to notify user device', ex);
        });
});
