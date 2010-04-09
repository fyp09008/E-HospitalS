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

import ehospital.server.RMIConsole;
import ehospital.server.Session;
import ehospital.server.Utility;
import ehospital.server.db.DBManager;
import remote.obj.EmergencyAccessHandler;

public class EmergencyAccessHandlerImpl extends UnicastRemoteObject implements EmergencyAccessHandler {
	
	public EmergencyAccessHandlerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1197149669700355840L;

	public int emergencyAccess(byte[] username, byte[] emergencyUser,
			byte[] emergencyPwd, int tmpCardNum) throws RemoteException {
		System.out.println("Emergency Access Called");
		try {
			Cipher c = null;
			c = Cipher.getInstance("aes");
			//decrypt in program key
			c.init(Cipher.DECRYPT_MODE, RMIConsole.ProgramKey);
			username = c.doFinal(username);
			System.out.println("username ok");
			String strUsername = new String(username);
			
			Session s = ehospital.server.SessionList.findClient(strUsername); 
			if (s == null) {
				return 1;
			} else {
				DBManager dbm = new DBManager();
				if (dbm.connect()) {
					//decrypt in program key
					c.init(Cipher.DECRYPT_MODE, RMIConsole.ProgramKey);
					emergencyUser = c.doFinal(emergencyUser);
					emergencyPwd = c.doFinal(emergencyPwd);
					//decrypt in session key
					System.out.println("others ok");
					c.init(Cipher.DECRYPT_MODE, s.getSessionKey());
					emergencyUser = c.doFinal(emergencyUser);
					emergencyPwd = c.doFinal(emergencyPwd);
					
					System.out.println("other ok");
					String strEmergencyUser = new String(emergencyUser);
					System.out.println(strEmergencyUser);
					ResultSet emergencyUserData = dbm.isUserExist(strEmergencyUser);
					System.out.println();
					if (dbm.isUserExist(strUsername) != null  && emergencyUserData != null ){
						try {
							System.out.println(emergencyUserData.getString("pwd"));
							
							String strEmergencyPwd = Utility.byteArrayToString(emergencyPwd);
							System.out.println(strEmergencyPwd);
							if (!emergencyUserData.getString("pwd").equals(strEmergencyPwd)) {
								System.out.println("asd");
								return 1;
							}
							ResultSet tmpUser = dbm.query("select * from `tmp_user` where `isTaken` = 0 AND `id` = " + tmpCardNum, null);
							System.out.println(tmpCardNum);
							if (tmpUser.first()) {
								System.out.println(tmpUser.getString(1));
								dbm.update("update `tmp_user` set `isTaken` = 1 where `id` = " + tmpUser.getString(1));
							} else {
								System.out.println("asdff");
								return 4;
							}
							System.out.println("asd");
							//record swapping in DB
							String [] n = {emergencyUserData.getString("uid"),emergencyUserData.getString("pub_key"),emergencyUserData.getString("mod"),Integer.toString(tmpCardNum)};
							dbm.update("insert into `swapped_user` (`user_id`, `ori_pub_key`, `ori_mod`, `tmp_card_num`) VALUE (?,?,?,?)",n);
							//swap key pair
							String [] o = {tmpUser.getString("pub_key"),tmpUser.getString("mod"),emergencyUserData.getString("uid")};
							dbm.update("update `user` set `pub_key` = ?, `mod` = ? where `uid` = ?", o);
						
							
							return 0;
						} catch (SQLException e) {
							e.printStackTrace();
							return 2;
						}
					} else {
						return 1;
					}
				}
			}
			return 0;
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
			return 1024;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return 8;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			return 8;
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			return 4;
		} catch (BadPaddingException e) {
			e.printStackTrace();
			return 4;
		}
	}

}
