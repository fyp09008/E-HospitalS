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

public class DataHandlerImpl extends UnicastRemoteObject implements DataHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8533375570405499715L;
	private static final int INSERT = 9000;
	private static final int UPDATE = 9001;
	private static final int SELECT = 9002;

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
		DBManager dbm = new DBManager();
		dbm.connect();
		String username = "mcfung";
		String[] param = {"fuck u"};
		String q = "UPDATE allergy SET name=?";
		String substr = q.substring(0, 6);
		System.out.println(substr);
		boolean valid = false;
		if (substr.equalsIgnoreCase("insert"))
			try {
				System.out.println("DEBUGGGGG");
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
			System.out.println("I am HERE!");
			mail.mail m = new mail.mail(subject, msg);
			m.sendmail();
		}
	}
	
	
	public byte[] query(String username, byte[] encQueryStmt, byte[] param)
			throws RemoteException {
		Session s = ehospital.server.SessionList.findClient(username);
		SecretKeySpec sks = s.getSessionKey();
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("aes");
			cipher.init(Cipher.DECRYPT_MODE, sks);
			byte[] rawQuery = cipher.doFinal(Utility.decrypt(encQueryStmt));
			byte[] p = cipher.doFinal(Utility.decrypt(param));
			DBManager dbm = new DBManager();
			dbm.connect();
			ResultSet rs = dbm.query(new String(rawQuery), (Object[]) Utility.BytesToObj(p));
			CachedRowSetImpl crs = new CachedRowSetImpl();
			crs.populate(rs);
			byte[] raw = Utility.objToBytes(crs);
			cipher.init(Cipher.ENCRYPT_MODE, sks);
			raw = cipher.doFinal(raw);
//			raw = Utility.encryptBytes(raw);
			cipher.init(Cipher.ENCRYPT_MODE, Utility.ProgramKey);
			raw = cipher.doFinal(raw);
			//System.out.println("before return raw");
			return raw;
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean update(String username, byte[] encUpdateStmt, byte[] param)
			throws RemoteException {
		Session s = ehospital.server.SessionList.findClient(username);
		SecretKeySpec sks = s.getSessionKey();
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("aes");
			cipher.init(Cipher.DECRYPT_MODE, sks);
			byte[] rawQuery = cipher.doFinal(Utility.decrypt(encUpdateStmt));
			byte[] p = cipher.doFinal(Utility.decrypt(param));
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
				dbm.connect();
				dbm.update(new String(rawQuery), (Object[]) Utility.BytesToObj(p));
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
				return false;
			}
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
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

}
