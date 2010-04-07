package ehospital.server.remote.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
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

public class AuthHandlerImpl extends UnicastRemoteObject implements remote.obj.AuthHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 40327953895437380L;
	
	private DBManager dbm;
	
	public AuthHandlerImpl() throws RemoteException {
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
	
	public byte[] authenticate(byte[] usernameIn, byte[] HEPwdIn)
			throws RemoteException {
		String username = new String(Utility.decrypt(usernameIn));
		//Session sessionExist1 = ehospital.server.SessionList.findClient(username); 
		//if (sessionExist1 == null) {
			//System.out.println("fine");
		//} else {
			//System.out.println("fuck");
		//}
		//System.out.println(username);
		byte[] HEPwd = Utility.decrypt(HEPwdIn);
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
				if (pwdFromDB.equals(pwdReceived)) {
					SecretKeySpec sks = this.genSessionKey();
					byte[] s = sks.getEncoded();
					//get client hostname
					String host = "";
					try {
						host = java.rmi.server.RemoteServer.getClientHost();
					} catch (ServerNotActiveException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Session sessionExist2 = ehospital.server.SessionList.findClient(username); 
					//if (sessionExist2 == null) {
					//	System.out.println("still fine");
					//} else {
						//System.out.println("fuck");
					//}
					//add session
					System.out.println("add new session");
					Session session = new Session(username, sks, exp, mod,host);
					Session sessionExist = ehospital.server.SessionList.findClient(username); 
					if (sessionExist == null) {
						System.out.println("no such session, create new now");
						ehospital.server.SessionList.clientList.add(session);
					} else {
						sessionExist.setSessionKey(sks);
					}
					return  Utility.encryptBytes(rsa.encrypt(s, s.length));
				} 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public byte[] getEncryptedSessionKey(byte[] usernameIn) {
		String username = (String)Console.decrypt(usernameIn);
		Session s = SessionList.findClient(username);
		byte[] sessionKey = s.getSessionKey().getEncoded();
		RSASoftware rsa = new RSASoftware();
		rsa.setPublicKey(s.getExp(), s.getMod());
		return (byte[]) Utility.encryptBytes(rsa.encrypt(sessionKey, sessionKey.length));
	}

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
			e.printStackTrace();
			return null;
		}catch (IOException e) {
			e.printStackTrace();
		} return null;
	}
	
	public byte[] getPrivilege(byte[] usernameIn) {	

			try {
				String username = new String((byte[])Utility.decrypt(usernameIn));
				//System.out.println("in getprivilege");
				Session s = SessionList.findClient(username);
				//System.out.println("in getprivilege get session ok");
				Cipher c;
				c = Cipher.getInstance("aes");
				c.init(Cipher.ENCRYPT_MODE, s.getSessionKey());
				this.dbm.connect();
				ResultSet rs = this.dbm.query("SELECT uid, `Read`, `Write`, `Add` FROM privilege, user WHERE user.Role=privilege.Role AND user.username='"+username+"'; ");
				CachedRowSetImpl crs = new CachedRowSetImpl();
				crs.populate(rs);
				return (byte[]) Utility.encryptBytes(c.doFinal(this.objToBytes(crs)));

				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			return null;
			
	}

	public byte[] getLoMsg(byte[] usernameIn) {
		String username = new String((byte[])Utility.decrypt(usernameIn));
		Session s = SessionList.findClient(username);
		//System.out.println("get session ok in get log out msg");
		if (s != null) {
			try {
				byte[] lomsg = s.getLomsg();
				Cipher c = Cipher.getInstance("aes");
				c.init(Cipher.ENCRYPT_MODE, s.getSessionKey());
				//TODO add program key
				//lomsg = c.doFinal(lomsg);
				//c.init(Cipher.ENCRYPT_MODE, ehospital.server.Console.ProgramKey);
				return  Utility.encryptBytes(c.doFinal(lomsg));
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}
	
public byte[] logout(byte[] usernameIn, byte[] lomsg) throws RemoteException {
		String username = new String((byte[])Utility.decrypt(usernameIn));
		Session s = ehospital.server.SessionList.findClient(username);
		Cipher c = null;
		try {
			c = Cipher.getInstance("aes");
			c.init(Cipher.DECRYPT_MODE, s.getSessionKey());
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (s == null || lomsg == null) {
			Boolean b = new Boolean(false);
			return (byte[])Utility.encrypt(b);
		}
		RSASoftware rsa = new RSASoftware();
		rsa.setPublicKey(s.getExp(), s.getMod());
		byte[] decMsg = null;
		try {
			lomsg = c.doFinal(Utility.decrypt(lomsg));
			decMsg = rsa.unsign(lomsg, lomsg.length);
		} catch (IllegalBlockSizeException e) {
			 
			e.printStackTrace();
		} catch (BadPaddingException e) {
			
			e.printStackTrace();
		}

		if (Utility.compareByte(decMsg, s.getLomsg())) {
			ehospital.server.SessionList.deleteSession(username);
			Boolean b = new Boolean(true);
			return (byte[])Utility.encrypt(b);
		}
		s.getTimer().cancel();
		Boolean b = new Boolean(false);
		return (byte[])Utility.encrypt(b);
	}



}
