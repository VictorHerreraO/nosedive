const onNewRating = require('./onNewRating');
const onNewFriend = require('./onNewFriend');
const serveUserPhoto = require('./serveUserPhoto');
const onNewNotification = require('./onNewNotification')

exports.onNewRating = onNewRating.onNewRating;
exports.onNewFriend = onNewFriend.onNewFriend;
exports.serveUserPhoto = serveUserPhoto.serveUserPhoto;
exports.onNewNotification = onNewNotification.onNewNotification;
