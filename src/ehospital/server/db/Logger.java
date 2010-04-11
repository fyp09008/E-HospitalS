package ehospital.server.db;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wilson
 *
 */
public class Logger {
	
	private Logger() {
	}
	
	/**
	 * log event into the data logger
	 * @param user1
	 * @param content
	 * @return 0 if success, -1 if fails 
	 */
	public static int log(String user1, String content) {
		DBManager dbm = new DBManager();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String datetime = dateFormat.format(date);

		try {
			if(dbm.connect()) {
				Statement stmt = dbm.getConn().createStatement();
				String q = "INSERT INTO log (id,date,user,content) VALUES ( null, '"+datetime+"', '"+user1+"', '"+content+"');";
				
				stmt.executeUpdate(q);
				dbm.disconnect();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} 
		return 0;
	}
}
