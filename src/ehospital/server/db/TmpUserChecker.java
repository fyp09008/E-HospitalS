package ehospital.server.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;

public class TmpUserChecker extends TimerTask {

	@Override
	public void run() {
		DBManager dbm = new DBManager();
		if (dbm.connect()) {
			try {
				//query from `swapped_user`
				ResultSet rs = dbm.query("select `user_id`,`ori_pub_key`,`ori_mod`,`tmp_card_num` from `swapped_user` where`start_date` < SUBDATE(NOW(), INTERVAL 1 DAY) AND isValid = 1");
				if (rs == null) {
					do {
						//rollback key_pair
						dbm.update("update `user` set `pub_key`="+rs.getString(1)+",`mod` = "+ rs.getString(2) + "where id = "+rs.getString(0));
						//disable and log tmp user
						dbm.update("update `swapped_user` set isValid = 0 where `user_id` = "+rs.getString(0));
						//free temp user
						dbm.update("update `tmp_user` set isTaken = 0 where `id` = "+rs.getString(3));
					} while (rs.next());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
