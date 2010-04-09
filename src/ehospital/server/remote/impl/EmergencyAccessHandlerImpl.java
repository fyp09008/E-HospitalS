package ehospital.server.remote.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import ehospital.server.Utility;
import ehospital.server.db.DBManager;
import remote.obj.EmergencyAccessHandler;

public class EmergencyAccessHandlerImpl implements EmergencyAccessHandler {
	
	public int emergencyAccess(String username, String emergencyUser,
			byte[] emergencyPwd, int tmpCardNum) {
		if (ehospital.server.SessionList.findClient(username) == null) {
			return 1;
		} else {
			DBManager dbm = new DBManager();
			if (dbm.connect()) {
				ResultSet emergencyUserData = dbm.isUserExist(emergencyUser);
				if (dbm.isUserExist(username) != null  && emergencyUserData != null ){
					try {
						if (!Utility.compareByte(emergencyUserData.getString(4).getBytes(), emergencyPwd)) {
							return 1;
						}
						ResultSet tmpUser = dbm.query("select * from `tmp_user` where `isTaken` = 0 AND id = " + tmpCardNum, null);
						dbm.update("update from `tmp_user` set `isTaken` = 1 where `id` = " + tmpUser.getString(0));
						String [] n = {emergencyUserData.getString(0),emergencyUserData.getString(2),emergencyUserData.getString(3)};
						dbm.update("insert into `swapped_user` (`user_id`, `ori_pub_key`, `ori_mod`) VALUE (?,?,?)",n);
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
	}

	@Override
	public int emergencyAccess(byte[] username, byte[] emergencyUser,
			byte[] emergencyPwd, int tmpCardNum) {
		// TODO Auto-generated method stub
		return 0;
	}

}
