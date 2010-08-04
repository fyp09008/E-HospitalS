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
 * <p>Provide communication between database connector and server program.</p> <p>Our database is using MySQL. The database connector is JDBC. </p> <p>Other then Update, Insert and Create, some other commonly used functions regarding data can be found.</p>
 * @author   mc
 */
public class DBManager{
	
	private static final String dbdriver = "com.mysql.jdbc.Driver";
	private static final String dbstr = "jdbc:mysql://localhost/hospital_rec";
	private static final String username = "root";
	private static final String password = "";
	
	/**
	 * @uml.property  name="conn"
	 */
	private Connection conn;
	/**
	 * Build a connection to the database using predefined param.
	 */
	public DBManager() {
	}
	
	/**
	 * Connect to the database.
	 * @return true if the server program is successfully connected
	 */
	public boolean connect() {
		try {
			Class.forName(dbdriver);
			this.setConn(DriverManager.getConnection(dbstr, username, password));
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Query the database by normal statement.
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
	 * <p>Query the database by prepared statement. </p>
	 * <p>For more information about prepared statement, please read 
	 * <a href="http://download.oracle.com/docs/cd/E17409_01/javase/tutorial/jdbc/basics/prepared.html"
	 * >tutorial of oracle</a>, or take the course CSIS0402.</p>
	 * This query method is used as follow:<br>
	 * ResultSet rs = query("SELECT * FROM tbl1 WHERE field1=?, field2=?", param);<br>
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
	
	/**
	 * Update database with plain SQL statement.
	 * @param query
	 * @throws SQLException
	 */
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
	 * * <p>Query the database by prepared statement. </p>
	 * <p>For more information about prepared statement, please read 
	 * <a href="http://download.oracle.com/docs/cd/E17409_01/javase/tutorial/jdbc/basics/prepared.html">
	 * tutorial of oracle</a>, or take the course CSIS0402.</p>
	 * Example:<br>
	 * update("UPDATE tbl SET field1=?", param);<br>
	 * where param is the array of parameters
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
	
	
	/**
	 * Get the public key from the database according to the username.
	 * @param username
	 * @return String array that store the public key exponent and modulus of user, return null if there is any problem
	 */
	public String [] getPubKeyAndMod(String username)
	{
		String [] buf = new String[2];
		try {
			this.connect();
			ResultSet rs = query("SELECT pub_key, `mod` FROM user WHERE username='"+username+"'");
			rs.next();
			buf[0] = rs.getString(1);
			buf[1] = rs.getString(2);
		} catch (SQLException e) {
			System.out.println("SQLException");
			return null;
		}
		return buf;
	}
	
	/**
	 * Insert a new user into database.
	 * @param role
	 * @param username
	 * @param pwdMDExp
	 * @param publicKeyExp
	 * @param modulus
	 * @return 0 if success
	 */
	public int storeUser(String role, String username, String pwdMDExp, String publicKeyExp, String modulus) {
		try {
			if(this.connect()) {
				Statement stmt = getConn().createStatement();
				String q = "INSERT INTO user (Role, pub_key,`mod`, pwd, RegDate, username) VALUES ( '"+role+"', '"+publicKeyExp+"', '"+modulus+"', '"+pwdMDExp+"', CURDATE(),'"+username+"');";
				
				stmt.executeUpdate(q);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} 
		return 0;
	}
	
	/**
	 * Get a result set containing user information. 
	 * @param username
	 * @return a result set with username, password, public keym modulus and id from database
	 * 
	 */
	public ResultSet isUserExist(String username) {
		try {
			if (this.connect()) {
				Statement stmt = getConn().createStatement();
				String q = "SELECT `username`,`pwd`,`pub_key`,`mod`,`uid` FROM `user` WHERE `username` LIKE '"+username+"';";
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

	
	/**
	 * Disconnect from database server
	 */
	public void disconnect() {
		try {
			getConn().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author   mhchung  Param class to store name-val pair of parameter(Obsolete).
	 */
	public class Param
	{
		/**
		 * @uml.property  name="name"
		 */
		private String name;
		/**
		 * @uml.property  name="val"
		 */
		private String val;
		/**
		 * @uml.property  name="op"
		 */
		private String op;
		
		/**
		 * @return   the op
		 * @uml.property  name="op"
		 */
		public String getOp() {
			return op;
		}

		/**
		 * @param op   the op to set
		 * @uml.property  name="op"
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
		 * @return   the name
		 * @uml.property  name="name"
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name   the name to set
		 * @uml.property  name="name"
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return   the val
		 * @uml.property  name="val"
		 */
		public String getVal() {
			return val;
		}
		/**
		 * @param val   the val to set
		 * @uml.property  name="val"
		 */
		public void setVal(String val) {
			this.val = val;
		}
	}
	
	/**
	 * Set Connection Object.
	 * @param  conn
	 * @uml.property  name="conn"
	 */
	private void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Get Connection Object.
	 * @return   a Connection object
	 * @uml.property  name="conn"
	 */
	public Connection getConn() {
		return conn;
	}
	
}
