package ehospital.server.db;

import java.sql.*;
import java.security.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mail.mail;

public class LogChecker {

	  DBManager dbm = new DBManager();
	  
      public LogChecker(){

     }
      
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
