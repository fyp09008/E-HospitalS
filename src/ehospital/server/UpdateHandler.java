/**
 * 
 */
package ehospital.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.spec.SecretKeySpec;

import message.UpdateRequestMessage;

/**
 * @author mc
 *
 */
public class UpdateHandler extends Handler {
	
	private String updatesql;
	private DBManager dbm;
	private boolean isGranted;
	
	public UpdateHandler(UpdateRequestMessage m, DBManager dbm, SecretKeySpec sks) throws SQLException {
		this.dbm = dbm;
		this.setSessionKeySpec(sks);
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
			dbm.Update(this.updatesql);
			return true;
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
	
}
