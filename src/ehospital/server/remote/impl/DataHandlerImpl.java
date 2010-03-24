package ehospital.server.remote.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import remote.obj.DataHandler;
import ehospital.server.Console;
import ehospital.server.Session;
import ehospital.server.Utility;
import ehospital.server.db.DBManager;

public class DataHandlerImpl extends UnicastRemoteObject implements DataHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8533375570405499715L;
	

	public DataHandlerImpl() throws RemoteException{
		
	}
	public int insert(String username, byte[] insertStmt)
			throws RemoteException {
		Session s = ehospital.server.SessionList.findClient(username);
		SecretKeySpec sks = s.getSessionKey();
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("aes");
			cipher.init(Cipher.DECRYPT_MODE, Console.ProgramKey);
			byte[] rawQuery = cipher.doFinal(insertStmt);
			cipher.init(Cipher.DECRYPT_MODE, sks);
			rawQuery = cipher.doFinal(insertStmt);
			DBManager dbm = new DBManager();
			dbm.connect();
			dbm.insert(new String(rawQuery), null);
			return 0;
		} catch (SQLException e) {
			return 1;
		} catch (NoSuchAlgorithmException e1) {
			return 2;
		} catch (NoSuchPaddingException e1) {
			return 4;
		} catch (InvalidKeyException e) {
			return 8;
		} catch (IllegalBlockSizeException e) {
			return 16;
		} catch (BadPaddingException e) {
			return 32;
		}
	}

	public byte[] query(String username, byte[] encQueryStmt)
			throws RemoteException {
		Session s = ehospital.server.SessionList.findClient(username);
		SecretKeySpec sks = s.getSessionKey();
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("aes");
			cipher.init(Cipher.DECRYPT_MODE, Console.ProgramKey);
			byte[] rawQuery = cipher.doFinal(encQueryStmt);
			cipher.init(Cipher.DECRYPT_MODE, sks);
			rawQuery = cipher.doFinal(encQueryStmt);
			DBManager dbm = new DBManager();
			dbm.connect();
			ResultSet rs;
			rs = dbm.query(new String(rawQuery));
			byte[] raw = Utility.objToBytes(rs);
			cipher.init(Cipher.ENCRYPT_MODE, sks);
			raw = cipher.doFinal(raw);
			cipher.init(Cipher.ENCRYPT_MODE, Console.ProgramKey);
			raw = cipher.doFinal(raw);
			return raw;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		
		//dbman.disconnect();
		return null;
	}

	public int update(String username, byte[] updateStmt)
			throws RemoteException {
		Session s = ehospital.server.SessionList.findClient(username);
		SecretKeySpec sks = s.getSessionKey();
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("aes");
			cipher.init(Cipher.DECRYPT_MODE, Console.ProgramKey);
			byte[] rawQuery = cipher.doFinal(updateStmt);
			cipher.init(Cipher.DECRYPT_MODE, sks);
			rawQuery = cipher.doFinal(updateStmt);
			DBManager dbm = new DBManager();
			dbm.connect();
			dbm.update(new String(rawQuery));
			return 0;
		} catch (SQLException e) {
			return 1;
		} catch (NoSuchAlgorithmException e1) {
			return 2;
		} catch (NoSuchPaddingException e1) {
			return 4;
		} catch (InvalidKeyException e) {
			return 8;
		} catch (IllegalBlockSizeException e) {
			return 16;
		} catch (BadPaddingException e) {
			return 32;
		}
		
		//dbman.disconnect();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
