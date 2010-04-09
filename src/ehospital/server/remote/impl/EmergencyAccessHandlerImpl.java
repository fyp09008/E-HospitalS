package ehospital.server.remote.impl;

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

public class EmergencyAccessHandlerImpl implements EmergencyAccessHandler {
	
	public int emergencyAccess(byte[] username, byte[] emergencyUser,
			byte[] emergencyPwd, int tmpCardNum) {
		try {
			Cipher c = null;
			c = Cipher.getInstance("aes");
			//decrypt in program key
			c.init(Cipher.DECRYPT_MODE, RMIConsole.ProgramKey);
			username = c.doFinal(username);
			String strUsername = new String(username);
			
			Session s = ehospital.server.SessionList.findClient(strUsername); 
			if (s == null) {
				return 1;
			} else {
				DBManager dbm = new DBManager();
				if (dbm.connect()) {
					//decrypt in program key
					emergencyUser = c.doFinal(emergencyUser);
					//decrypt in session key
					c.init(Cipher.DECRYPT_MODE, s.getSessionKey());
					emergencyUser = c.doFinal(emergencyUser);
					String strEmergencyUser = new String(emergencyUser);
					ResultSet emergencyUserData = dbm.isUserExist(strEmergencyUser);
					if (dbm.isUserExist(strUsername) != null  && emergencyUserData != null ){
						try {
							if (!Utility.compareByte(emergencyUserData.getString(4).getBytes(), emergencyPwd)) {
								return 1;
							}
							ResultSet tmpUser = dbm.query("select * from `tmp_user` where `isTaken` = 0 AND id = " + tmpCardNum, null);
							if (tmpUser != null) {
								dbm.update("update from `tmp_user` set `isTaken` = 1 where `id` = " + tmpUser.getString(0));
							} else {
								return 4;
							}
							//record swapping in DB
							String [] n = {emergencyUserData.getString(0),emergencyUserData.getString(2),emergencyUserData.getString(3)};
							dbm.update("insert into `swapped_user` (`user_id`, `ori_pub_key`, `ori_mod`) VALUE (?,?,?)",n);
							//swap key pair
							String [] o = {emergencyUserData.getString(2),emergencyUserData.getString(3),emergencyUserData.getString(0)};
							dbm.update("update `user` set `pub_key` = ?, `mod` = ? where `id` = ?", o);
						
							
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
