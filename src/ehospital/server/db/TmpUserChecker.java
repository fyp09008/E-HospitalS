package ehospital.server.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;

public class TmpUserChecker extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		DBManager dbm = new DBManager();
		if (dbm.connect()) {
			try {
				ResultSet rs = dbm.query("select `user_id`,`ori_pub_key`,`ori_mod` from `swapped_user` where`start_date` < SUBDATE(NOW(), INTERVAL 1 DAY) AND isValid = 1");
				if (rs == null) {
					do {
						dbm.update("update `user` set `pub_key`="+rs.getString(1)+",`mod` = "+ rs.getString(2) + "where id = "+rs.getString(0));
						dbm.update("update `swapped_user` set isValid = 0 where `user_id` = "+rs.getString(0));
					} while (rs.next());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
