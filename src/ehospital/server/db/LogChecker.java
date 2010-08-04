package ehospital.server.db;

import java.sql.*;
import java.security.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mail.mail;

/**
 * <p>Checking consistency of the log entry in the database.</p> <p>The checking is pretty dummy for the moment. The checking is done while you enter the latest log</p>
 * @author   Wilson
 */
public class LogChecker {

	  /**
	 * @uml.property  name="dbm"
	 * @uml.associationEnd  
	 */
	DBManager dbm = new DBManager();
	  
      public LogChecker(){

     }
      
   /**
     * Check the hashed value of the previous record is the same as the hashed
     * value of the record before previous plus its hashed value.
     * @return true if it is consistency
     * @throws SQLException
     */
    public boolean checking() throws SQLException{
    	  
    	  if(dbm.connect()) {
    		  Statement stmt = dbm.getConn().createStatement();
    		  ResultSet rs = stmt.executeQuery("SELECT date,user,content,hash_value FROM log");
				String date = null;
				String user = null;
			    String content = null;
			    int hash_value = 0;
				while (rs.next()) {
					date = rs.getString("date");
					user = rs.getString("user");
					content = rs.getString("content");
					hash_value = rs.getInt("hash_value");
					}
				
				String logString = date+user+content;
				
				@SuppressWarnings("unused")
				MessageDigest md = null;
				try {
					md = MessageDigest.getInstance("md5");
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			
				if (logString.hashCode()== hash_value){
					return true;} 
				else{
					System.out.println("Not equal in previous log!");
					//send alert email
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			        Date date1 = new Date();
			        String datetime = dateFormat.format(date1);
					mail mailing = new mail("Intrusion Detection in Log!", "Wrong previous log is found at "+datetime+"!");
					mailing.sendmail();
					return false;
				}
					
		  }
    	  dbm.disconnect();
    	  return false; 
      }
}
