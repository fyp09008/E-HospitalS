package ehospital.server.db;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.security.*;

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
	@SuppressWarnings("null")
	public static int log(String user1, String content) {
		DBManager dbm = new DBManager();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String datetime = dateFormat.format(date);

		try {
			if(dbm.connect()) {
				/*test counting*/
				Statement s = dbm.getConn().createStatement();
				ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM log");
				r.next();
				int count = r.getInt("rowcount") ;
				r.close() ;
				System.out.println("log has " + count + " row(s).");
				
			    /*test counting*/
				
				if (count == 0){
					Statement stmt = dbm.getConn().createStatement();
					//MessageDigest md = null;
					//try {
					//	md = MessageDigest.getInstance("md5");
					//} catch (NoSuchAlgorithmException e) {
					//	// TODO Auto-generated catch block
					//	e.printStackTrace();
					//}
					//byte[] hashed_value = md.digest("A".getBytes()); //first log hash value
					String q = "INSERT INTO log (id,date,user,content,hash_value) VALUES (null,'"+datetime+"', '"+user1+"', '"+content+"','"+"abcdefg".hashCode()+"');";				
					stmt.executeUpdate(q);
					dbm.disconnect();		
				}
				else if (count ==1){
					Statement stmt = dbm.getConn().createStatement();
			    	MessageDigest md = null;
				    try {
					    md = MessageDigest.getInstance("md5");
				      } catch (NoSuchAlgorithmException e) {
					 // TODO Auto-generated catch block
					  e.printStackTrace();
				      }
				     String logString = datetime+user1+content;
				    // byte[] hashed_value = md.digest(logString.getBytes());
			         String q = "INSERT INTO log (id,date,user,content,hash_value) VALUES (null,'"+datetime+"', '"+user1+"', '"+content+"','"+logString.hashCode()+"');";				
				     stmt.executeUpdate(q);
				     dbm.disconnect();	
				}
				else if (count > 1){
					check_log cl = new check_log();
					
				     if (cl.checking()){
				    	 
					    Statement stmt = dbm.getConn().createStatement();
				    	MessageDigest md = null;
					    try {
						    md = MessageDigest.getInstance("md5");
					      } catch (NoSuchAlgorithmException e) {
						 // TODO Auto-generated catch block
						  e.printStackTrace();
					      }
					     String logString = datetime+user1+content;
					    // byte[] hashed_value = md.digest(logString.getBytes());
				         String q = "INSERT INTO log (id,date,user,content,hash_value) VALUES (null,'"+datetime+"', '"+user1+"', '"+content+"','"+logString.hashCode()+"');";				
					     stmt.executeUpdate(q);
					     //hashlog hash_value = new hashlog();
					    // ResultSet rs = stmt.executeQuery("SELECT id FROM log");
					    // String id = null;
					    // while (rs.next()) {
						//    id = rs.getString("id");
						//  }
					   //  System.out.println(id);
					     
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

