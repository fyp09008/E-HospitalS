package ehospital.server.remote.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import cipher.RSASoftware;
import ehospital.server.Session;
import ehospital.server.SessionList;
import ehospital.server.Utility;
import ehospital.server.db.DBManager;

public class AuthHandlerImpl extends UnicastRemoteObject implements remote.obj.AuthHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 40327953895437380L;
	
	private DBManager dbm;
	
	public AuthHandlerImpl() throws RemoteException{
		dbm = new DBManager();
	}

	public SecretKeySpec genSessionKey() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			return new SecretKeySpec(keyGen.generateKey().getEncoded(), "aes");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public byte[] authenticate(String username, byte[] HEPwd)
			throws RemoteException {
		dbm = new DBManager();
		ResultSet user = dbm.isUserExist(username);
		if (user != null) {
			try {
				String pwdFromDB = dbm.getRs().getString(2);
				String pwdReceived = Utility.byteArrayToString(HEPwd);
				if (pwdFromDB.equals(pwdReceived)) {
					SecretKeySpec sks = this.genSessionKey();
					byte[] s = sks.getEncoded();
					//add session
					String exp = user.getString(3);
					String mod = user.getString(4);
					Session session = new Session(username, sks, exp, mod);
					ehospital.server.SessionList.clientList.add(session);
					RSASoftware rsa = new RSASoftware();
					rsa.setPublicKey(exp, mod);
					return rsa.encrypt(s, s.length);
				} 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public byte[] getEncryptedSessionKey(String username) {
		Session s = SessionList.findClient(username);
		byte[] sessionKey = s.getSessionKey().getEncoded();
		RSASoftware rsa = new RSASoftware();
		rsa.setPublicKey(s.getExp(), s.getMod());
		return rsa.encrypt(sessionKey, sessionKey.length);
	}

	public ResultSet getPrivilege(String username) {					
			try {
				this.dbm.connect();
				return this.dbm.query("SELECT uid, `Read`, `Write`, `Add` FROM privilege, user WHERE user.Role=privilege.Role AND user.username='"+username+"'; ");
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			
	}

}
