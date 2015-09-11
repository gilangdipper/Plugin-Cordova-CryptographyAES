var cordova = require('cordova');

var cryptographyAES = {
	doEncryption : function(text, successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback,
		'cryptographyAES', // mapped to our native Java class called "CryptographyAes"
		'doEncryption', // with this action name
		[text]);
	},
	doDecryption : function(text, successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback,
		'cryptographyAES', // mapped to our native Java class called "CryptographyAes"
		'doDecryption', // with this action name
		[text]);
	}
};

module.exports = cryptographyAES;