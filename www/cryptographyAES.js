var cordova = require('cordova');

var cryptographyAES = {
	doEncryption : function(text, key, successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback,
		'cryptographyAES', // mapped to our native Java class called "CryptographyAes"
		'doEncryption', // with this action name
		[text, key]);
	},
	doDecryption : function(text, key, successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback,
		'cryptographyAES', // mapped to our native Java class called "CryptographyAes"
		'doDecryption', // with this action name
		[text, key]);
	}
};

module.exports = cryptographyAES;