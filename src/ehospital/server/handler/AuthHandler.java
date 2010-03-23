package ehospital.server.handler;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import cipher.RSAHardware;
import ehospital.server.db.DBManager;

import message.AuthRequestMessage;

/**
 * 
 * @author Gilbert
 *
 */
public class AuthHandler extends Handler{

	private String username;
	private byte[] pwdMDExp;
	public AuthHandler(AuthRequestMessage msg, ehospital.server.db.DBManager dbm) {
		super();
		this.dbm = dbm;
		this.username = msg.getUsername();
		byte[] pwdMDCipher = msg.getPassword();
		if (this.loadCryptoInfo(username)) {
			this.pwdMDExp = this.getRsa().unsign(pwdMDCipher, pwdMDCipher.length);
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
		ResultSet user = dbm.isUserExist(this.username);
		if (user != null) {
			try {
				String pwdFromDB = user.getString(2);
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

	public ResultSet getPrivilege() throws SQLException {
		// TODO Auto-generated method stub
		this.dbm.connect();
		return this.dbm.query("SELECT uid, `Read`, `Write`, `Add` FROM privilege, user WHERE user.Role=privilege.Role AND user.username='"+this.username+"'; ");
	}
	
}
