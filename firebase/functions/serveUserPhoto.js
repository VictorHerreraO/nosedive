const functions = require('firebase-functions');
const admin = require('firebase-admin');

const cacheMaxAge = 86400; // 1 day
const fallbackUrl = 'https://firebasestorage.googleapis.com/v0/b/nosedive-sandbox.appspot.com/o/userPhoto%2Fplaceholder%2Fic_launcher-playstore.png?alt=media&token=4be681c9-a01d-4d54-89f0-7e3868fe5e04';

admin.initializeApp(functions.config().firebase);

exports.serveUserPhoto = functions.https.onRequest((req, res) => {
    if (req.method !== 'GET') {
        return res.status(405).send('method ' + req.method + ' not supported!');
    }

    const uid = req.query.uid || '';
    if (!uid || ("" + uid).length == 0) {
        return res.status(404).send('uid must be a non-empty string');
    }

    return admin.database().ref('user')
        .child(uid)
        .child('photoUrl')
        .once('value')
        .then(snap => {
            const photoUrl = snap.val();
            functions.logger.log('snap.val is = ' + photoUrl);
            functions.logger.log('path is = ' + snap.ref.toString());
            res.set('Cache-Control', 'public, max-age=' + cacheMaxAge + ', s-maxage=' + cacheMaxAge);
            if (photoUrl) {
                res.redirect(photoUrl);
            } else {
                res.redirect(fallbackUrl);
            }
        });
});

