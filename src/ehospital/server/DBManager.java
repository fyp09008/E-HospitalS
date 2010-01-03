/**
 * 
 */
package ehospital.server;

import java.sql.*;

/**
 * @author mc
 * 
 * This class is used to manage the database
 *
 */
public class DBManager {
	
	private static final String dbdriver = "com.mysql.jdbc.Driver";
	private static final String dbstr = "jdbc:mysql://localhost/hospital_rec";
	private static final String username = "FYP09";
	private static final String password = "1234qwer";
	
	private Connection conn;

	/**
	 * Build a connection to the database using predefined param.
	 */
	public DBManager() {
		try {
			Class.forName(dbdriver);
			this.conn = DriverManager.getConnection(dbstr, username, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param query
	 * @return The result from the query
	 * @throws SQLException
	 */
	public ResultSet Query(String query) throws SQLException
	{
		ResultSet rs = conn.createStatement().executeQuery(query);
		return rs;
	}
	
	/**
	 * @param query
	 * @throws SQLException
	 */
	public void Update(String query) throws SQLException
	{
		conn.createStatement().executeUpdate(query);
	}

	/**
	 * @param id
	 * @return String array that store the public key exponent and modulus of user, return null if there is any problem
	 */
	public String [] getPubKeyAndMod(String id)
	{
		String [] buf = new String[2];
		try {
			ResultSet rs = Query("SELECT pub_key, `mod` FROM user WHERE id='"+id+"'");
			rs.next();
			buf[0] = rs.getString(1);
			buf[1] = rs.getString(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("SQLException");
			return null;
		}
		return buf;
	}
}
