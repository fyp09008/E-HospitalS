package ehospital.server;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import cipher.RSAHardware;

import message.AuthRequestMessage;

/**
 * 
 * @author Gilbert
 *
 */
public class AuthHandler extends Handler{

	private String username;
	private byte[] pwdMDExp;
	public AuthHandler(AuthRequestMessage msg, DBManager dbm) {
		super();
		this.dbm = dbm;
		this.username = msg.getUsername();
		byte[] pwdMDCipher = msg.getPassword();
		if (this.loadCryptoInfo(username)) {
			this.pwdMDExp = this.getRsa().encrypt(pwdMDCipher, pwdMDCipher.length);
		}
	}
	
	public AuthHandler(String username, String mdPwd, DBManager dbm) {
		super();
		this.username = username;
		MessageDigest md;
		try {
			this.loadCryptoInfo(username);
			md = MessageDigest.getInstance("md5");
			this.pwdMDExp = md.digest(mdPwd.getBytes());
			//for testing purpose
			RSAHardware rsaHard = new RSAHardware();
			if (rsaHard.initJavaCard("285921800099") != -1) {
				byte[] plaintext = rsaHard.sign(this.pwdMDExp, this.pwdMDExp.length);
				this.pwdMDExp = this.getRsa().unsign(plaintext, plaintext.length);
			}	
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void genSessionKey() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			this.setSessionKeySpec(new SecretKeySpec(keyGen.generateKey().getEncoded(), "aes"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public boolean authenticate() {
		this.loadCryptoInfo(username);
		if (dbm.isUserExist(this.username)) {
			try {
				String pwdFromDB = dbm.getRs().getString(2);
				String pwdReceived = this.byteArrayToString(this.pwdMDExp);
				if (pwdFromDB.equals(pwdReceived)) {
					return true;
				} 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public byte[] getEncryptedSessionKey() {
		if (this.getRsa().getPublicKeyExp() == null) {
			this.loadCryptoInfo(username);
		}
		byte[] sessionKey = this.getSessionKeySpec().getEncoded();
		return encryptRSA(sessionKey);
	}
	
}
