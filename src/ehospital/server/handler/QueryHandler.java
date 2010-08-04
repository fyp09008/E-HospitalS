package ehospital.server.handler;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import message.QueryRequestMessage;
import ehospital.server.db.DBManager;
/**
 * obsolete class, developed in 09/10 semester 1 using socket programming.  Handle Query from client
 * @author   Gilbert mc
 */
public class QueryHandler extends Handler{

	/**
	 * @uml.property  name="query"
	 */
	private String query;
	/**
	 * @uml.property  name="rs"
	 */
	private ResultSet rs;
	/**
	 * @uml.property  name="dbman"
	 * @uml.associationEnd  
	 */
	private DBManager dbman;
	
	public QueryHandler(QueryRequestMessage msg, SecretKeySpec sks) {
		try {
			byte[] rawQuery = msg.query;
			@SuppressWarnings("unused")
			String username = msg.username;
			this.setSessionKeySpec(sks);
			dbman = new DBManager();
			this.query = getQueryString(rawQuery);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @return   the query
	 * @uml.property  name="query"
	 */
	public String getQuery() {
		return query;
	}
	
	/**
	 * @return   the rs
	 * @uml.property  name="rs"
	 */
	public ResultSet getRs() {
		return rs;
	}
	
	/**
	 * @return the encrypted result set
	 * @throws SQLException
	 * @throws IOException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public ResultSet query() throws SQLException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		dbman.connect();
		ResultSet rs = dbman.query(query);
		//dbman.disconnect();
		return rs;
	}
	
	/**
	 * @param query   the query to set
	 * @uml.property  name="query"
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	
	/**
	 * @param rs   the rs to set
	 * @uml.property  name="rs"
	 */
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	
	/**
	 * This method is used to convert the byte[] raw query
	 * to query in string
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	private String getQueryString(byte[] raw) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		String sql = new String(this.decryptAES(raw));
		return sql;
	}
}