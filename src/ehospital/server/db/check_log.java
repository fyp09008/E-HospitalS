package ehospital.server.db;

import java.sql.*;
import java.security.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mail.mail;

public class check_log {

	  DBManager dbm = new DBManager();
	  
      public check_log(){

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
				//System.out.println(date);
				//System.out.println(user);
				//System.out.println(content);
				//System.out.println(hash_value);
				
				String logString = date+user+content;
				
				MessageDigest md = null;
				try {
					md = MessageDigest.getInstance("md5");
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//byte[] hashed_value = md.digest(logString.getBytes());
				//byte[] hashed_value2 = md.digest(logString.getBytes());
				//System.out.println(hashed_value);
				//System.out.println(hashed_value2);
				//System.out.println(logString.hashCode());
			
				//System.out.println(logString.hashCode());
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
					
				//System.out.println(date+";"+user+";"+content+";"+hash_value);		
		  }
    	  dbm.disconnect();
    	  return false; 
      }
}
