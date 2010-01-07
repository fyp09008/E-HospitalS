/**
 * 
 */
package ehospital.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.crypto.spec.SecretKeySpec;

import mail.mail;
import message.UpdateRequestMessage;

/**
 * @author mc
 *
 */
public class UpdateHandler extends Handler {
	
	private String updatesql;
	private DBManager dbm;
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
	
	public boolean update()
	{
		try {
			if (isGranted)
			{
				dbm.Update(this.updatesql);
				return true;
			}
			else
			{
				String subj = "Instruction Detected!";
				String msg = "Time: " + Calendar.getInstance().getTime().toString() + "\r\n";
				msg += "User: " + m.username +"\r\n";
				msg += "From: " + this.IP + "\r\n";
				msg += "Action: " + new String(this.decryptAES(m.type)) + "\r\n";
				ResultSet rs = dbm.query("SELECT user.Role, `Read`, `Write`, `Add` FROM privilege, user WHERE user.Role=privilege.Role AND user.username='"+m.username+"'; ");
				rs.next();
				msg += "Role: " + rs.getString(1) + "\r\n";
				msg += "Privilege(Read|Write|Add): " + rs.getString(2) + "|" + rs.getString(3) + "|" + rs.getString(4) + "\r\n";
				new mail(subj, msg).sendmail();
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Fail to Update the database, abort.");
			System.err.println("Error: " + e.getMessage());
			return false;
		}
	}

	private String getQueryString(byte[] cipher)
	{
		return new String(this.decryptAES(cipher));
	}

	public void setIP(String ip) {
		IP = ip;
	}
	
}
