package net.alkhansa.cryptographyaesplugin;

//Cordova imports
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;

//Android imports
import android.content.Context;
import android.util.Log;
//import android.provider.Settings;
import android.util.Base64;

//Package Cryptografy
//import sun.misc.*;
import javax.crypto.*;
import java.security.SecureRandom;
import javax.crypto.spec.SecretKeySpec;


//JSON Imports
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class cryptographyAES extends CordovaPlugin {
 	
 	//Define some constants for the supported actions
	public static final String TAG = "Cryptography AES Plugin";
	public static final String ACTION_GET_ENCRYPTION = "doEncryption";
	public static final String ACTION_GET_DECYPTION = "doDecryption";

	public static byte[] ciphertext, plaintext, ivBytes;
	 
	/**
	* Constructor.
	*/
	public cryptographyAES() {}
	 
	/**
	* Sets the context of the Command. This can then be used to do things like
	* get file paths associated with the Activity.
	*
	* @param cordova The context of the main Activity.
	* @param webView The CordovaWebView Cordova is running in.
	*/
	 
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		Log.v(TAG,"Init CryptographyAES");
	}
	 
	public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) throws JSONException {  
		// Shows Action Cryptografy
		Log.v(TAG,"CryptographyAES received:"+ action);
		       
		try {
            //First check on the getCarrierName
			if (ACTION_GET_ENCRYPTION.equals(action)) {
				String pesan = args.getString(0);
		        String kunci = "GilangAlKhansa";
		        SecretKeySpec key = genKey(kunci);
		        String cipher = "";
		        try{
					//Enkripsi Pesan
					cipher = encrypt(key,pesan);
					//Dekripsi Pesan
					//String cipher = "o7d8Mcmvskj5pYLx6lLxWw==";
					//System.out.println("Ciphertext :"+cipher);

					// String plain = decrypt(key,cipher);
					// System.out.println("\nDecryption :"+plain);
				} catch(Exception e){
					//System.out.println("error mbokk : "+ e);
					callbackContext.error("error encrypt : "+ e.getMessage());
				}

				callbackContext.success(cipher);
				return true;
			} else {
				//Next see if it is a getCountryCode action
				if (ACTION_GET_DECYPTION.equals(action)) {
                   	callbackContext.success("sukses");
                   	return true;
               	}
			}
            //We don't have a match, so it must be an invalid action
            callbackContext.error("Invalid Action");
            return false;
        } catch (Exception e) {
            //If we get here, then something horrible has happened
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        }

		//return true;
	}

	public static String encrypt(SecretKeySpec key, String plaintext) {
		try{
			byte[] pesan = plaintext.getBytes();
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			ciphertext = cipher.doFinal(pesan);
		} catch(Exception e){
			System.out.println("error encrypt : "+ e);
		}
		String sandi = Base64.encodeToString(ciphertext, Base64.DEFAULT);
        //String sandi = new BASE64Encoder().encode(ciphertext);
        return sandi;
    }

    public static String decrypt(SecretKeySpec key, String ciphertext) {
		try{
			byte[] sandi = ciphertext.getBytes();
			Cipher cipher = Cipher.getInstance("AES");
			byte[] decodeCipher = Base64.decode(ciphertext, Base64.DEFAULT);
			//byte[] decodeCipher = new BASE64Decoder().decodeBuffer(ciphertext);
			cipher.init(Cipher.DECRYPT_MODE, key);
			plaintext = cipher.doFinal(decodeCipher);
           
		} catch(Exception e){
			//System.out.println("error mbokk : "+ e);
			String pesan = "invalid decrypt this text : " + ciphertext;
			return pesan;
		}
        String pesan = new String(plaintext);
        return pesan;
    }

    public static SecretKeySpec genKey(String key) throws Exception {
		byte[] input = key.getBytes();
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		byte[] byteseed = new byte[256];
		secureRandom.setSeed(input);
		kgen.init(256, secureRandom);
		SecretKey kunci = kgen.generateKey();
		byte[] bytes = kunci.getEncoded();
		SecretKeySpec spec = new SecretKeySpec(bytes,"AES");
		return spec;
    }
}