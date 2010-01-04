package ehospital.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.crypto.spec.SecretKeySpec;

import message.QueryRequestMessage;
/**
 * 
 * @author Gilbert
 *
 */
public class QueryHandler extends Handler {

	private String query;
	private ResultSet rs;
	private SecretKeySpec sessionKeySpec;
	private Connection conn;
	
	public QueryHandler(QueryRequestMessage msg) {
		super();
		byte[] rawQuery = msg.query;
		String username = msg.username;
		// TODO query username session & sign, decrypt sign, decrypt session, create query
	}
	
	public void setSecretKeySpec() {
		// TODO query database for sessionKey
	}
	
	public void queryDB() {
		try {
			rs = this.dbm.query(query);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] encryptRS() {
		byte[] rsRaw = null;
		return null;
	}
	
	
	/**
	 * @param rs the rs to set
	 */
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	/**
	 * @return the rs
	 */
	public ResultSet getRs() {
		return rs;
	}
	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}
	
}