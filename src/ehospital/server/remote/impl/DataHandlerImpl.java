package ehospital.server.remote.impl;

import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import ehospital.server.Console;
import ehospital.server.Session;
import ehospital.server.Utility;
import ehospital.server.db.DBManager;

import message.QueryRequestMessage;

import remote.obj.DataHandler;

public class DataHandlerImpl implements DataHandler {

	public DataHandlerImpl(QueryRequestMessage msg, SecretKeySpec sks) {
		
	}

	public DataHandlerImpl() {
		
	}
	public byte[] insert(String username, byte[] insertStmt)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
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
			return Utility.objToBytes(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

	public byte[] update(String username, byte[] updateStmt)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
