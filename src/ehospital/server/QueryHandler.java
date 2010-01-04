package ehospital.server;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import message.QueryRequestMessage;
/**
 * 
 * @author Gilbert mc
 * Handle Query from client
 *
 */
public class QueryHandler {

	private String query;
	private ResultSet rs;
	private SecretKeySpec sessionKeySpec;
	private DBManager dbman;
	
	public QueryHandler(QueryRequestMessage msg, SecretKeySpec sks) {
		try {
			byte[] rawQuery = msg.query;
			String username = msg.username;
			this.sessionKeySpec = sks;
			dbman = new DBManager();
			this.query = getQueryString(rawQuery);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO query username session & sign, decrypt sign, decrypt session, create query by calling cypto man
	}
	
	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}
	
	/**
	 * @return the rs
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
	public byte[] query() throws SQLException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		dbman.connect();
		ResultSet rs = dbman.query(query);
		//dbman.disconnect();
		return encryptRS(rs);
	}
	
	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	
	/**
	 * @param rs the rs to set
	 */
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	public void setSecretKeySpec() {
		// TODO query database for sessionKey
	}
	private byte[] encryptRS(ResultSet rs) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		// TODO encrypt the resultset argument
		byte[] rsRaw = null;
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
		os.writeObject(rs);
		rsRaw = byteStream.toByteArray();
		return encryptAES(rsRaw);
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
	
	/**
	 * This method is to decrypt the ciphertext using AES with the sessionkey 
	 * @param ci ciphertext in byte[] to be decrypted
	 * @return plaintext in byte[]
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private byte [] decryptAES(byte[] ci) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		Cipher cipher = Cipher.getInstance("aes");
		cipher.init(Cipher.DECRYPT_MODE, this.sessionKeySpec);
		
		byte[] plain = cipher.doFinal(ci);
		return plain;
	}
	
	/**
	 * This method is to encrypt the plaintext using AES with the sessionkey
	 * @param plain plaintext in byte[] to be encrypted
	 * @return ciphertext in byte[]
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private byte [] encryptAES(byte[] plain) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		Cipher cipher = Cipher.getInstance("aes");
		cipher.init(Cipher.ENCRYPT_MODE, this.sessionKeySpec);
		
		byte[] ciphertext = cipher.doFinal(plain);
		return ciphertext;
	}
}