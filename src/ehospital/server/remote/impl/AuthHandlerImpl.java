package ehospital.server.remote.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.sun.rowset.CachedRowSetImpl;

import cipher.RSASoftware;
import ehospital.server.Console;
import ehospital.server.Session;
import ehospital.server.SessionList;
import ehospital.server.Utility;
import ehospital.server.db.DBManager;
import ehospital.server.db.Logger;

public class AuthHandlerImpl extends UnicastRemoteObject implements remote.obj.AuthHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 40327953895437380L;
	
	private DBManager dbm;
	
	/**
	 * Default constructor. Initialize DBManager.
	 * @throws RemoteException
	 */
	public AuthHandlerImpl() throws RemoteException {
		dbm = new DBManager();
	}

	/**
	 * Generate and return a session key
	 * @return Session Key
	 */
	public SecretKeySpec genSessionKey() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			return new SecretKeySpec(keyGen.generateKey().getEncoded(), "aes");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see remote.obj.AuthHandler#authenticate(byte[], byte[])
	 */
	public byte[] authenticate(byte[] usernameIn, byte[] HEPwdIn)
			throws RemoteException {
		String clientIP = Utility.getClientHost();
		String username = new String(Utility.decryptProgKey(usernameIn));
		System.out.println(username);
		byte[] HEPwd = Utility.decryptProgKey(HEPwdIn);
		dbm = new DBManager();
		ResultSet user = dbm.isUserExist(username);
		if (user != null && HEPwd != null) {
			try {
				String pwdFromDB = user.getString(2);
				RSASoftware rsa = new RSASoftware();
				String exp = user.getString(3);
				String mod = user.getString(4);

				rsa.setPublicKey(exp, mod);
				
				HEPwd = rsa.unsign(HEPwd, HEPwd.length);
				
				String pwdReceived = Utility.byteArrayToString(HEPwd);
				System.out.println(pwdFromDB);
				System.out.println(pwdReceived);
				if (pwdFromDB.equals(pwdReceived)) {
					Session sessionExist1 = ehospital.server.SessionList.findClient(username); 
					if (sessionExist1 != null) {
						ehospital.server.SessionList.deleteSession(username);
					}
					SecretKeySpec sks = this.genSessionKey();
					byte[] s = sks.getEncoded();
					
					//add session
					System.out.println("add new session");
					
					Session sessionExist = ehospital.server.SessionList.findClient(username); 
					if (sessionExist == null) {
						Session session = new Session(username, sks, exp, mod,clientIP);
						ehospital.server.SessionList.clientList.add(session);
					} else {
						sessionExist.setSessionKey(sks);
					}
					Logger.log(clientIP, username+" successfully authenticate.");
					return  Utility.encryptProgKey(rsa.encrypt(s, s.length));
				} 
			} catch (SQLException e) {
				Logger.log(clientIP, " failed to authenticate due to: \n"+e.getMessage());
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see remote.obj.AuthHandler#getEncryptedSessionKey(byte[])
	 */
	public byte[] getEncryptedSessionKey(byte[] usernameIn) {
		String username = (String)Console.decrypt(usernameIn);
		Session s = SessionList.findClient(username);
		byte[] sessionKey = s.getSessionKey().getEncoded();
		RSASoftware rsa = new RSASoftware();
		rsa.setPublicKey(s.getExp(), s.getMod());
		//RSA encrypt
		byte[] encSessionKey = rsa.encrypt(sessionKey, sessionKey.length);
		//sign with program key
		return (byte[]) Utility.encryptProgKey(encSessionKey);
	}

	/**
	 * serialize the object into a byte array
	 * @param obj
	 * @return byte array representation of obj
	 */
	private byte[] objToBytes(Object obj){
	      ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
	      ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush(); 
			oos.close(); 
			bos.close();
			byte [] data = bos.toByteArray();
			return data;
		} catch (NotSerializableException e){
			return null;
		}catch (IOException e) {
		} 
		return null;
	}
	
	/* (non-Javadoc)
	 * @see remote.obj.AuthHandler#getPrivilege(byte[])
	 */
	public byte[] getPrivilege(byte[] usernameIn) {	
		String clientIP = Utility.getClientHost();
			try {
				String username = new String((byte[])Utility.decryptProgKey(usernameIn));
				Session s = SessionList.findClient(username);
				Cipher c;
				c = Cipher.getInstance("aes");
				c.init(Cipher.ENCRYPT_MODE, s.getSessionKey());
				this.dbm.connect();
				ResultSet rs = this.dbm.query("SELECT uid, `Read`, `Write`, `Add` FROM privilege, user WHERE user.Role=privilege.Role AND user.username='"+username+"'; ");
				CachedRowSetImpl crs = new CachedRowSetImpl();
				crs.populate(rs);
				return (byte[]) Utility.encryptProgKey(c.doFinal(this.objToBytes(crs)));

				} catch (NoSuchAlgorithmException e) {
					Logger.log(clientIP, " failed to get privilege due to: \n"
							+e.getMessage());
				} catch (NoSuchPaddingException e) {

					Logger.log(clientIP, " failed to get privilege due to: \n"
							+e.getMessage());
				} catch (InvalidKeyException e) {

					Logger.log(clientIP, " failed to get privilege due to: \n"
							+e.getMessage());
				} catch (IllegalBlockSizeException e) {

					Logger.log(clientIP, " failed to get privilege due to: \n"
							+e.getMessage());
				} catch (BadPaddingException e) {

					Logger.log(clientIP, " failed to get privilege due to: \n"
							+e.getMessage());
				} catch (SQLException e) {

					Logger.log(clientIP, " failed to get privilege due to: \n"
							+e.getMessage());
				}
			return null;
			
	}

	/* (non-Javadoc)
	 * @see remote.obj.AuthHandler#getLoMsg(byte[])
	 */
	public byte[] getLoMsg(byte[] usernameIn) {
		String clientIP = Utility.getClientHost();
		String username = new String((byte[])Utility.decryptProgKey(usernameIn));
		Session s = SessionList.findClient(username);
		//System.out.println("get session ok in get log out msg");
		if (s != null) {
			try {
				byte[] lomsg = s.getLomsg();
				Cipher c = Cipher.getInstance("aes");
				c.init(Cipher.ENCRYPT_MODE, s.getSessionKey());
				return  Utility.encryptProgKey(c.doFinal(lomsg));
			} catch (InvalidKeyException e) {
				Logger.log(clientIP, " failed to get logout message due to: \n"
						+e.getMessage());
			} catch (IllegalBlockSizeException e) {
				Logger.log(clientIP, " failed to get logout message due to: \n"
						+e.getMessage());
			} catch (BadPaddingException e) {
				Logger.log(clientIP, " failed to get logout message due to: \n"
						+e.getMessage());
			} catch (NoSuchAlgorithmException e) {
				Logger.log(clientIP, " failed to get logout message due to: \n"
						+e.getMessage());
			} catch (NoSuchPaddingException e) {
				Logger.log(clientIP, " failed to get logout message due to: \n"
						+e.getMessage());
			}
		}
		Logger.log(clientIP, " failed to get logout message due to: \n"+"no such session.");
		return null;
	}
	
	/* (non-Javadoc)
	 * @see remote.obj.AuthHandler#logout(byte[], byte[])
	 */
	public byte[] logout(byte[] usernameIn, byte[] lomsg) throws RemoteException {
		String clientIP = Utility.getClientHost();
		String username = new String((byte[])Utility.decryptProgKey(usernameIn));
		Session s = ehospital.server.SessionList.findClient(username);
		Cipher c = null;
		try {
			c = Cipher.getInstance("aes");
			c.init(Cipher.DECRYPT_MODE, s.getSessionKey());
		} catch (NoSuchAlgorithmException e) {
			Logger.log(clientIP, username+" failed to logout due to: \n"+e.getMessage());
		} catch (NoSuchPaddingException e) {
			Logger.log(clientIP, username+" failed to logout due to: \n"+e.getMessage());
		} catch (InvalidKeyException e) {
			Logger.log(clientIP, username+" failed to logout due to: \n"+e.getMessage());
		}
		if (s == null || lomsg == null) {
			Boolean b = new Boolean(false);
			Logger.log(clientIP, username+" failed to logout due to: " +
									"no such session or no logout message.");
			return Utility.encryptProgKey(Utility.objToBytes(b));
		}
		RSASoftware rsa = new RSASoftware();
		rsa.setPublicKey(s.getExp(), s.getMod());
		byte[] decMsg = null;
		try {
			lomsg = c.doFinal(Utility.decryptProgKey(lomsg));
			decMsg = rsa.unsign(lomsg, lomsg.length);
		} catch (IllegalBlockSizeException e) {
			 
		} catch (BadPaddingException e) {
			
		}

		if (Utility.compareByte(decMsg, s.getLomsg())) {
			ehospital.server.SessionList.deleteSession(username);
			Boolean b = new Boolean(true);
			Logger.log(clientIP, username+" successfully logout.");
			return Utility.encryptProgKey(Utility.objToBytes(b));
		}
		
		s.getTimer().cancel();
		Boolean b = new Boolean(false);
		return Utility.encryptProgKey(Utility.objToBytes(b));
	}
	
	/* (non-Javadoc)
	 * @see remote.obj.AuthHandler#unplugCard(byte[])
	 */
	public void unplugCard(byte[] usernameIn) throws RemoteException{
		String username = new String((byte[])Utility.decryptProgKey(usernameIn));
		Session s = ehospital.server.SessionList.findClient(username);
		if ( s != null){
			s.getTimer().cancel();
			ehospital.server.SessionList.deleteSession(username);
		}
	}


	/* (non-Javadoc)
	 * @see remote.obj.AuthHandler#changePassword(byte[], byte[], byte[])
	 */
	public byte[] changePassword(byte[] usernameIn, byte[] hashedOld,
			byte[] hashedNew) throws RemoteException {
		String clientIP = Utility.getClientHost();
		String username = new String((byte[])Utility.decryptProgKey(usernameIn));
		byte[] oldPW = Utility.decryptProgKey(hashedOld);
		byte[] newPW = Utility.decryptProgKey(hashedNew);
		dbm = new DBManager();
		ResultSet user = dbm.isUserExist(username);
		if (user != null && oldPW!= null) {
			try {
				String pwdFromDB = user.getString(2);
				RSASoftware rsa = new RSASoftware();
				String exp = user.getString(3);
				String mod = user.getString(4);
				
				rsa.setPublicKey(exp, mod);
				
				oldPW = rsa.unsign(oldPW, oldPW.length);
				newPW = rsa.unsign(newPW, newPW.length);
				String pwdReceived = Utility.byteArrayToString(oldPW);
				if (pwdFromDB.equals(pwdReceived)) {
					DBManager dbm = new DBManager();
					dbm.connect();
					String query = "UPDATE user SET pwd = ? WHERE username = ?";
					String param[] = {Utility.byteArrayToString(newPW),username};
					dbm.update(query,param);
					Boolean b = new Boolean(true);
					Logger.log(clientIP, username+" successfully change his/her password.");
					return Utility.encryptProgKey(Utility.objToBytes(b));
				}else{
					Logger.log(clientIP, username+" failed to change his/her password due to incorrect password");
					Boolean b = new Boolean(false);
					return Utility.encryptProgKey(Utility.objToBytes(b));
				}
			}catch (Exception e){
				Boolean b = new Boolean(false);
				Logger.log(clientIP, username+" failed to change his/her password due to :\n" + e.getMessage());
				return Utility.encryptProgKey(Utility.objToBytes(b));
			}
		}
		Boolean b = new Boolean(false);
		Logger.log(clientIP, username+" failed to change his/her password due to incorrect password");
		return Utility.encryptProgKey(Utility.objToBytes(b));
	}

}
