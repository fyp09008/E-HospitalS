package ehospital.server;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

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
		this.pwdMDExp = msg.getPassword();
		this.username = msg.getUsername();
		
	}
	
	public AuthHandler(String username, String mdPwd, DBManager dbm) {
		this.username = username;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("md5");
			this.pwdMDExp = md.digest(mdPwd.getBytes());
			//for testing purpose
			plaintext = this.getRsa().decrypt(this.pwdMDExp, this.pwdMDExp);
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
		if (this.getRsa().getPublicKeyExp() == null) {
			this.loadCryptoInfo(username);
		}
		//TODO add password auth
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
