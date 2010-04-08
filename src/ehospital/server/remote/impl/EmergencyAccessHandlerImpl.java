package ehospital.server.remote.impl;

import ehospital.server.db.DBManager;
import remote.obj.EmergencyAccessHandler;

public class EmergencyAccessHandlerImpl implements EmergencyAccessHandler {

	public int emergencyAccess(String username, String emergencyUser,
			byte[] matchMsg) {
		if (ehospital.server.SessionList.findClient(username) == null) {
			return 1;
		} else {
			DBManager dbm = new DBManager();
		}
		return 0;
	}

}
