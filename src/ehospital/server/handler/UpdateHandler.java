package ehospital.server.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.crypto.spec.SecretKeySpec;

import message.UpdateRequestMessage;
import ehospital.server.db.DBManager;

/**
 * obsolete class, developed in 09/10 semester 1 using socket programming, reference only. Handle update request from server program.
 * @author   mc, Gilbert
 */
public class UpdateHandler extends Handler {
	
	private String updatesql;
	/**
	 * @uml.property  name="dbm"
	 * @uml.associationEnd  
	 */
	private DBManager dbm;
	/**
	 * @uml.property  name="m"
	 * @uml.associationEnd  
	 */
	private UpdateRequestMessage m;
	private String IP;
	private boolean isGranted;
	
	public UpdateHandler(UpdateRequestMessage m, DBManager dbm, SecretKeySpec sks) throws SQLException {
		this.dbm = dbm;
		this.setSessionKeySpec(sks);
		this.m = m;
		this.updatesql = getQueryString(m.query);
		String type = new String(this.decryptAES(m.type));
		if (this.loadCryptoInfo(m.username))
		{
			String sql = null;
			if (type.equalsIgnoreCase("insert"))
			{
				sql = "SELECT `Add` FROM privilege, user WHERE user.Role=privilege.Role AND user.username='"+m.username+"'; ";
			}
			else if (type.equalsIgnoreCase("update") || type.equalsIgnoreCase("delete"))
			{
				sql = "SELECT `Write` FROM privilege, user WHERE user.Role=privilege.Role AND user.username='"+m.username+"'; ";
			}
			ResultSet rs = dbm.query(sql);
			rs.next();
			isGranted = rs.getBoolean(1);
		}
	}
	
	/**
	 * 
	 * @return true if update successful
	 */
	public boolean update()
	{
		try {
			if (isGranted)
			{
				dbm.update(this.updatesql);
				return true;
			}
			else
			{
				@SuppressWarnings("unused")
				String Subj = "Intrusion Detected!";
				String msg = "Time: "+Calendar.getInstance().getTime().toString()+"\r\n";
				msg += "User: "+m.username+"\r\n";
				msg += "From: "+this.IP+"\r\n";
				msg += "Action: "+ new String(this.decryptAES(m.type))+"\r\n";
				ResultSet rs = dbm.query("SELECT user.Role, `Read`, `Write`, `Add` FROM privilege, user WHERE user.Role=privilege.Role AND user.username='"+m.username+"';" );
				rs.next();
				msg += "Role: "+rs.getString(1)+"\r\n";
				msg += "Privilege(Read|Write|Add): "+rs.getString(2)+"|"+rs.getString(3)+"|"+rs.getString(4)+"\r\n";
				
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Fail to Update the database, abort.");
			System.err.println("Error: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Decrypt query in ciphertext.
	 * @param cipher
	 * @return decrypted plaintext query String.
	 */
	private String getQueryString(byte[] cipher)
	{
		return new String(this.decryptAES(cipher));
	}

	/**
	 * @param  ip
	 * @uml.property  name="iP"
	 */
	public void setIP(String ip) {
		IP = ip;
	}
	
}
