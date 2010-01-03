package ehospital.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import message.AuthRequestMessage;

import cipher.RSASoftware;
/**
 * 
 * @author Gilbert
 *
 */
public class AuthHandler {

	private String username;
	private byte[] mdPwd;
	private byte[] sessionKey;
	private RSASoftware rsa;
	
	public AuthHandler(AuthRequestMessage msg) {
		
		this.mdPwd = msg.getPassword();
		this.username = msg.getUsername();
		
	}
	
	public boolean isUserExist(String str) {
		//TODO use database
		if(str.equals(username)) {
			return true;
		} else {
			return false;
		}
	}
	
	public RSASoftware loadCryptoInfo(RSASoftware rsa) {
		try {
			//TODO use Database
			FileReader fr = new FileReader("pubkey.txt");
			BufferedReader in = new BufferedReader(fr);
			String pubKeyExp = in.readLine();
			String mod = in.readLine();
			rsa.setPublicKey(pubKeyExp, mod);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsa;
	}
	
	public void genSessionKey() {
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			this.sessionKey = keyGen.generateKey().getEncoded();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] encrypt(byte plaintext[]) {
		byte ciphertext[] = rsa.encrypt(plaintext, plaintext.length);
		return ciphertext;
	}
	
	public boolean authenticate() {
		
		if (isUserExist(this.username)) {
			String tmpPwd = "1234";
			MessageDigest md;
			try {
				md = MessageDigest.getInstance("md5");
				byte[] pwd = md.digest(tmpPwd.getBytes());
			    if (MessageDigest.isEqual(pwd, mdPwd)) {
			    	genSessionKey();
			    	rsa = new RSASoftware();
			    	rsa = loadCryptoInfo(rsa);
			    	return true;
			    }
			} catch (NoSuchAlgorithmException e) {
				// TODO Add Logger
				e.printStackTrace();
			}
		   
		}
		return false;
	}

	public byte[] getEncryptedSessionKey() {
		byte[] bsKey = encrypt(getSessionKey());
		return bsKey;
	}

	/**
	 * @return the sessionKey
	 */
	public byte[] getSessionKey() {
		return sessionKey;
	}
	
}
