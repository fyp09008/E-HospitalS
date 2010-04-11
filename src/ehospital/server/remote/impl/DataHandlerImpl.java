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

import com.sun.rowset.CachedRowSetImpl;

import remote.obj.DataHandler;
import ehospital.server.Console;
import ehospital.server.Session;
import ehospital.server.Utility;
import ehospital.server.db.DBManager;
import ehospital.server.db.Logger;

/**
 * The implementation of data manipulation in the database
 * @author Gilbert
 *
 */
public class DataHandlerImpl extends UnicastRemoteObject implements DataHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8533375570405499715L;
	private static final int INSERT = 9000;
	private static final int UPDATE = 9001;
	private static final int SELECT = 9002;

	/**
	 * Default constructor. Does Nothing
	 * @throws RemoteException
	 */
	public DataHandlerImpl() throws RemoteException{
		
	}

	/* (non-Javadoc)
	 * @see remote.obj.DataHandler#query(java.lang.String, byte[])
	 */
	public byte[] query(String username, byte[] encQueryStmt)
			throws RemoteException {
		String clientIP = Utility.getClientHost();
		Session s = ehospital.server.SessionList.findClient(username);
		SecretKeySpec sks = s.getSessionKey();
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("aes");
			cipher.init(Cipher.DECRYPT_MODE, Console.ProgramKey);
			byte[] rawQuery = cipher.doFinal(encQueryStmt);
			cipher.init(Cipher.DECRYPT_MODE, sks);
			rawQuery = cipher.doFinal(rawQuery);
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
		} catch (Exception e) {
			Logger.log(clientIP, username+" fails to query database due to: \n"+e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see remote.obj.DataHandler#update(java.lang.String, byte[])
	 */
	public int update(String username, byte[] updateStmt)
			throws RemoteException {
		String clientIP = Utility.getClientHost();
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
			Logger.log(clientIP, username+" successfully update.");
			return 0;
		} catch (SQLException e) {
			Logger.log(clientIP, username+" fails to update database due to: \n"+e.getMessage());
			return 1;
		} catch (NoSuchAlgorithmException e) {
			Logger.log(clientIP, username+" fails to update database due to: \n"+e.getMessage());
			return 2;
		} catch (NoSuchPaddingException e) {
			Logger.log(clientIP, username+" fails to update database due to: \n"+e.getMessage());
			return 4;
		} catch (InvalidKeyException e) {
			Logger.log(clientIP, username+" fails to update database due to: \n"+e.getMessage());
			return 8;
		} catch (IllegalBlockSizeException e) {
			Logger.log(clientIP, username+" fails to update database due to: \n"+e.getMessage());
			return 16;
		} catch (BadPaddingException e) {
			Logger.log(clientIP, username+" fails to update database due to: \n"+e.getMessage());
			return 32;
		}
		
	}
	
	
	/* (non-Javadoc)
	 * @see remote.obj.DataHandler#query(java.lang.String, byte[], byte[])
	 */
	public byte[] query(String username, byte[] encQueryStmt, byte[] param)
			throws RemoteException {
		Session s = ehospital.server.SessionList.findClient(username);
		SecretKeySpec sks = s.getSessionKey();
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("aes");
			cipher.init(Cipher.DECRYPT_MODE, sks);
			byte[] rawQuery = cipher.doFinal(Utility.decryptProgKey(encQueryStmt));
			byte[] p = cipher.doFinal(Utility.decryptProgKey(param));
			DBManager dbm = new DBManager();
			dbm.connect();
			ResultSet rs = dbm.query(new String(rawQuery), (Object[]) Utility.BytesToObj(p));
			CachedRowSetImpl crs = new CachedRowSetImpl();
			crs.populate(rs);
			byte[] raw = Utility.objToBytes(crs);
			cipher.init(Cipher.ENCRYPT_MODE, sks);
			raw = cipher.doFinal(raw);
			cipher.init(Cipher.ENCRYPT_MODE, Utility.ProgramKey);
			raw = cipher.doFinal(raw);
			return raw;
		} catch (Exception e) {
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see remote.obj.DataHandler#update(java.lang.String, byte[], byte[])
	 */
	public boolean update(String username, byte[] encUpdateStmt, byte[] param)
			throws RemoteException {
		String clientIP = Utility.getClientHost();
		
		Session s = ehospital.server.SessionList.findClient(username);
		SecretKeySpec sks = s.getSessionKey();
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("aes");
			cipher.init(Cipher.DECRYPT_MODE, sks);
			byte[] rawQuery = cipher.doFinal(Utility.decryptProgKey(encUpdateStmt));
			byte[] p = cipher.doFinal(Utility.decryptProgKey(param));
			DBManager dbm = new DBManager();
			
			// Check privilege
			String q = new String(rawQuery);
			String substr = q.substring(0, 6);
			boolean valid = false;
			if (substr.equalsIgnoreCase("insert"))
				valid = chkPriv(username, INSERT);
			else if (substr.equalsIgnoreCase("update"))
				valid = chkPriv(username, UPDATE);
			
			if (valid)
			{
				dbm.update(new String(rawQuery), (Object[]) Utility.BytesToObj(p));
				Logger.log(clientIP, "update success.");
				return true;
			}
			else
			{
				String subject = "Privilege violation!";
				String[] param1 = (String[]) Utility.BytesToObj(p);
				String msg = "";
				msg += "User: "+username+"\n";
				msg += "Time: "+new java.sql.Time(System.currentTimeMillis())+"\n";
				msg += "Action: "+new String(rawQuery)+"\n";
				msg += "Param: ";
				for (int i = 0; i < param1.length; i++)
				{
					msg += param1[i]+" ";
				}
				msg += "\n";
				mail.mail m = new mail.mail(subject, msg);
				m.sendmail();
				Logger.log(clientIP, "update failed because of privilege violation.");
				return false;
			}
		} catch (Exception e) {
			Logger.log(clientIP, username+"update failed due to: \n"+e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * query the database for the privilege of a certain role of a certain operation in DB.
	 * @param username
	 * @param type
	 * @return true if the privilege is grant, false if it isn't
	 * @throws SQLException
	 */
	private static boolean chkPriv(String username, int type) throws SQLException
	{
		DBManager dbm = new DBManager();
		String[] param = {username};
		dbm.connect();
		ResultSet rs = dbm.query("SELECT `Read`, `Write`, `Add` FROM privilege LEFT JOIN user ON privilege.Role=user.Role WHERE username=?;", param);
		rs.next();
		switch (type)
		{
			case INSERT:
				return rs.getBoolean("Add");
			case UPDATE:
				return rs.getBoolean("Write");
			case SELECT:
				return rs.getBoolean("Read");
			default:
				return false;
		}
	}
	
	/**
	 * Main Class for unit testing
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBManager dbm = new DBManager();
		dbm.connect();
		String username = "mcfung";
		String[] param = {"fuxk u"};
		String q = "UPDATE allergy SET name=?";
		String substr = q.substring(0, 6);
		System.out.println(substr);
		boolean valid = false;
		if (substr.equalsIgnoreCase("insert"))
			try {
				valid = chkPriv(username, INSERT);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else if (substr.equalsIgnoreCase("update"))
			try {
				valid = chkPriv(username, UPDATE);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		if (valid)
		{
			try {
				dbm.update(q, param);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			String subject = "Privilege violation!";
			String msg = "";
			msg += "User: "+username+"\n";
			msg += "Time: "+new java.sql.Time(System.currentTimeMillis())+"\n";
			msg += "Action: "+q+"\n";
			msg += "Param: ";
			for (int i = 0; i < param.length; i++)
			{
				msg += param[i]+" ";
			}
			msg += "\n";
			mail.mail m = new mail.mail(subject, msg);
			m.sendmail();
		}
	}
	
	
	

}
