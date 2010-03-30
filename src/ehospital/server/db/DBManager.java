/**
 * 
 */
package ehospital.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author mc
 * 
 * This class is used to manage the database
 *
 */
public class DBManager{
	
	private static final String dbdriver = "com.mysql.jdbc.Driver";
	private static final String dbstr = "jdbc:mysql://127.0.0.1/hospital_rec";
	private static final String username = "FYP09";
	private static final String password = "1234qwer";
	
	private Connection conn;
	/**
	 * Build a connection to the database using predefined param.
	 */
	public DBManager() {
	}
	
	public boolean connect() {
		try {
			Class.forName(dbdriver);
			this.setConn(DriverManager.getConnection(dbstr, username, password));
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param query
	 * @return The result from the query
	 * @throws SQLException
	 */
	public ResultSet query(String query) throws SQLException
	{
		this.connect();
		ResultSet rs = getConn().createStatement().executeQuery(query);
		return rs;
	}
	
	/**
	 * @param query Basic Query String. e.g: "SELECT * FROM hospital"
	 * @param param Array of name-val pairs of parameters
	 * @return
	 * @throws SQLException
	 */
//	public ResultSet query(String query, Param [] param) throws SQLException
//	{
//		this.connect();
//		String whereclause = null;
//		if (param != null && param.length != 0)
//		{
//			whereclause = " WHERE "+param[0].getName()+param[0].getOp()+"?";
//			for (int i = 1; i < param.length; i++)
//			{
//				whereclause += " AND "+param[i].getName()+param[i].getOp()+"?";
//			}
//		}
//		PreparedStatement prep = this.getConn().prepareStatement(whereclause == null ? query : query+" "+whereclause);
//		if (param != null && param.length != 0)
//		{
//			for (int i = 0; i < param.length; i++)
//			{
//				prep.setString(i+1, param[i].getVal());
//			}
//		}
//		ResultSet rs = prep.executeQuery();
//		return rs;
//	}
	
	/**
	 * This query method is used as follow:
	 * ResultSet rs = query("SELECT * FROM tbl1 WHERE field1=?, field2=?", param);
	 * where param is the array of parameters
	 * @param query The query with all parameters set to ?
	 * @param param The val of each parameters.  
	 * @return
	 * @throws SQLException 
	 */
	public ResultSet query(String query, Object[] param) throws SQLException
	{
		PreparedStatement prep = this.getConn().prepareStatement(query);
		if (param != null && param.length != 0)
		{
			for (int i = 0; i < param.length; i++)
			{
				// Check wt instance is the param..
				if (param[i] instanceof String)
					prep.setString(i+1, (String) param[i]);
				else if (param[i] instanceof Integer)
					prep.setInt(i+1, Integer.parseInt(param[i].toString()));
			}
		}
		return prep.executeQuery();
	}
	
	public void update(String query) throws SQLException
	{
		this.connect();
		getConn().createStatement().executeUpdate(query);
	}
	
	public void update(String query, Param [] SETparam, Param [] WHEREparam) throws SQLException
	{
		this.connect();
		String setclause = " SET ";
		String whereclause = " WHERE ";
		for (int i = 0; (SETparam != null && SETparam.length != 0) && i < SETparam.length; i++)
		{
			setclause += SETparam[i].getName()+"=?";
			setclause += i == SETparam.length - 1 ? " " : ", ";
		}
	
		for (int i = 0; (WHEREparam != null && WHEREparam.length != 0) &&  i < WHEREparam.length; i++)
		{
			whereclause += WHEREparam[i].getName()+WHEREparam[i].getOp()+"?";
			whereclause += i == WHEREparam.length - 1 ? ";" : " AND ";
		}
		if (SETparam != null && SETparam.length != 0)
		{
			query += setclause;
		}
		if (WHEREparam != null && WHEREparam.length != 0)
			query += whereclause;
		PreparedStatement prep = this.getConn().prepareStatement(query);
		for (int i = 0; (SETparam != null && SETparam.length != 0) && i < SETparam.length; i++)
		{
			prep.setString(i+1, SETparam[i].getVal());
		}
		for (int i = 0; (WHEREparam != null && WHEREparam.length != 0) &&  i < WHEREparam.length; i++)
		{
			prep.setString(i+SETparam.length+1, WHEREparam[i].getVal());
		}
		prep.executeUpdate();
	}
	
	/**
	 * Example:
	 * update("UPDATE tbl SET field1=?", param);
	 * where param is a array of parameter
	 * @param query The update or insert query
	 * @param param The parameters
	 * @throws SQLException 
	 */
	public void update(String query, Object[] param) throws SQLException
	{
		PreparedStatement prep = this.getConn().prepareStatement(query);
		if (param != null && param.length != 0)
		{
			for (int i = 0; i < param.length; i++)
			{
				// Check wt instance is the param..
				if (param[i] instanceof String)
					prep.setString(i+1, (String) param[i]);
				else if (param[i] instanceof Integer)
					prep.setInt(i+1, Integer.parseInt(param[i].toString()));
			}
		}
		prep.executeUpdate();
	}
	
	public void insert(String query, Param[] param) throws SQLException
	{
		this.connect();
		String values = " (";
		for (int i = 0; param != null && i < param.length; i++)
		{
			values += i == param.length - 1 ? " )" : ", ";
		}
		if (param != null && param.length != 0)
		{
			PreparedStatement prep = this.getConn().prepareStatement(query+values);
			prep.executeUpdate();
		}
		
	}
	
	/**
	 * @param username
	 * @return String array that store the public key exponent and modulus of user, return null if there is any problem
	 */
	public String [] getPubKeyAndMod(String username)
	{
		//TODO change to callable statement
		String [] buf = new String[2];
		try {
			this.connect();
			ResultSet rs = query("SELECT pub_key, `mod` FROM user WHERE username='"+username+"'");
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
	
	public int storeUser(String role, String username, String pwdMDExp, String publicKeyExp, String modulus) {
		try {
			//TODO change to callable statement
			if(this.connect()) {
				Statement stmt = getConn().createStatement();
				String q = "INSERT INTO user (Role, pub_key,`mod`, pwd, RegDate, username) VALUES ( '"+role+"', '"+publicKeyExp+"', '"+modulus+"', '"+pwdMDExp+"', CURDATE(),'"+username+"');";
				
				stmt.executeUpdate(q);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} 
		return 0;
	}
	
	public ResultSet isUserExist(String username) {
		//TODO change to callable statement
		try {
			if (this.connect()) {
				Statement stmt = getConn().createStatement();
				String q = "SELECT `username`,`pwd`,`pub_key`,`mod` FROM `user` WHERE `username` LIKE '"+username+"';";
				ResultSet rs =  stmt.executeQuery(q);
				if(rs.first()) {
					return rs;
				}
				
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
	}

	
	public void disconnect() {
		try {
			getConn().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @author mhchung
	 * Param class to store name-val pair of parameter
	 *
	 */
	public class Param
	{
		private String name;
		private String val;
		private String op;
		
		/**
		 * @return the op
		 */
		public String getOp() {
			return op;
		}

		/**
		 * @param op the op to set
		 */
		public void setOp(String op) {
			this.op = op;
		}

		/**
		 * @param name
		 * @param val
		 * @param op =, <, >..
		 */
		public Param(String name, String val, String op) {
			super();
			this.name = name;
			this.val = val;
			this.op = op;
		}

		/**
		 * @param name name of the parameter
		 * @param val value of the parameter
		 */
		public Param(String name, String val) {
			this.name = name;
			this.val = val;
			this.op = "=";
		}
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the val
		 */
		public String getVal() {
			return val;
		}
		/**
		 * @param val the val to set
		 */
		public void setVal(String val) {
			this.val = val;
		}
	}
	
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Connection getConn() {
		return conn;
	}
	
}
