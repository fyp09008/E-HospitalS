package ehospital.server.db;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.security.*;

/**
 * Provide Logging service for the whole system.
 * @author wilson
 *
 */
public class Logger {
	
	private Logger() {
	}
	
	/**
	 * Log event into the data logger.
	 * @param user1
	 * @param content
	 * @return 0 if success, -1 if fails 
	 */
	@SuppressWarnings("null")
	public static int log(String user1, String content) {
		DBManager dbm = new DBManager();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String datetime = dateFormat.format(date);

		try {
			if(dbm.connect()) {
				Statement s = dbm.getConn().createStatement();
				ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM log");
				r.next();
				int count = r.getInt("rowcount") ;
				r.close() ;
				
				if (count == 0){
					Statement stmt = dbm.getConn().createStatement();
					String q = "INSERT INTO log (id,date,user,content,hash_value) VALUES (null,'"+datetime+"', '"+user1+"', '"+content+"','"+"abcdefg".hashCode()+"');";				
					stmt.executeUpdate(q);
					dbm.disconnect();		
				}
				else if (count ==1){
					Statement stmt = dbm.getConn().createStatement();
			    	@SuppressWarnings("unused")
					MessageDigest md = null;
				    try {
					    md = MessageDigest.getInstance("md5");
				      } catch (NoSuchAlgorithmException e) {
					  e.printStackTrace();
				      }
				     String logString = datetime+user1+content;
			         String q = "INSERT INTO log (id,date,user,content,hash_value) VALUES (null,'"+datetime+"', '"+user1+"', '"+content+"','"+logString.hashCode()+"');";				
				     stmt.executeUpdate(q);
				     dbm.disconnect();	
				}
				else if (count > 1){
					LogChecker cl = new LogChecker();
					
				     if (cl.checking()){
				    	 
					    Statement stmt = dbm.getConn().createStatement();
				    	@SuppressWarnings("unused")
						MessageDigest md = null;
					    try {
						    md = MessageDigest.getInstance("md5");
					      } catch (NoSuchAlgorithmException e) {
						  e.printStackTrace();
					      }
					     String logString = datetime+user1+content;
				         String q = "INSERT INTO log (id,date,user,content,hash_value) VALUES (null,'"+datetime+"', '"+user1+"', '"+content+"','"+logString.hashCode()+"');";				
					     stmt.executeUpdate(q);
					     
					     
				       }  
				     }
				 dbm.disconnect();	
			     }
		   } catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} 
		return 0;
	}
}

