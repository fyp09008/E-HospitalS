package ehospital.server.handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import cipher.RSASoftware;

import ehospital.server.db.DBManager;

/**
 * The super class that provide utility to all other handler.
 * @author   Gilbert,mh
 */
public class Handler {

	
	
	
	/**
	 * @uml.property  name="dbm"
	 * @uml.associationEnd  
	 */
	protected DBManager dbm;

	/**
	 * @uml.property  name="rsa"
	 * @uml.associationEnd  
	 */
	private RSASoftware rsa;
	
	/**
	 * @uml.property  name="sessionKeySpec"
	 */
	private SecretKeySpec sessionKeySpec;
	
	/**
	 * Default Constructor.
	 */
	public Handler() {
		dbm = new DBManager();
		rsa = new RSASoftware();
	}
	
	/**
	 * convert a byte array into string. an element as a character.
	 * @param b a byte array
	 * @return a string 
	 */
	public String byteArrayToString(byte[] b) {
		if(b == null)
			return null;
		String HEX_NUM = "0123456789abcdef";
		char[] cStr = new char[b.length*2];
		for(int i = 0, j = 0; i< b.length; i++) {
			byte f = (byte)((b[i] & 0xF0) >> 4);
			byte g = (byte) (b[i] & 0x0F);
			cStr[j] =  HEX_NUM.charAt(f);
			j++;
			cStr[j] = HEX_NUM.charAt(g); 
			j++;
		}
		return new String(cStr);
	}
	
	/**
	 * compare 2 byte array
	 * @param b1 
	 * @param b2
	 * @return true if the two byte array is the same 
	 */
	protected boolean compareByte(byte[] b1, byte[] b2)
	{
		if (b1.length != b2.length)
			return false;
		
		for (int i = 0; i < b1.length; ++i)
			if (b1[i] != b2[i])
				return false;
		
		return true;
	}
	
	/**
	 * convert a string into byte array
	 * @param str
	 * @return byte array
	 */
	public byte[] stringToByteArray(String str) {
		String HEX_NUM = "0123456789abcdef";
		int CHAR_NOT_FOUND = -1;
		byte[] b = new byte[str.length()/2];
		for(int i = 0, j = 0; i < b.length; i++) {
			byte f = (byte)HEX_NUM.indexOf(str.charAt(j));
			if (f != CHAR_NOT_FOUND) {
				b[i] = (byte)(f << 4);
				j++;
				byte g = (byte)HEX_NUM.indexOf(str.charAt(j));
				if (g != CHAR_NOT_FOUND) {
					b[i] = (byte) (b[i] + g);
					j++;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}	
		return b;
	}
	
	/**
	 * encrypt with RSA
	 * @param plaintext
	 * @return an byte array with encrypted message
	 */
	public byte[] encryptRSA(byte plaintext[]) {
		byte ciphertext[] = getRsa().encrypt(plaintext, plaintext.length);
		return ciphertext;
	}
	
	/**
	 * decrypt with RSA
	 * @param ciphertext
	 * @return an byte array with decrypted message
	 */
	public byte[] decryptRSA(byte ciphertext[]) {
		byte plaintext[] = getRsa().encrypt(ciphertext, ciphertext.length);
		return plaintext;
	}
	
	
	/**
	 * load the crypto info.
	 * @param username
	 * @return true if all info is loaded
	 */
	public boolean loadCryptoInfo(String username) {
		try {
			if(dbm.isUserExist(username) != null) {
				dbm.connect();
				ResultSet rs = dbm.query("SELECT pub_key, `mod` FROM user WHERE username='"+username+"';");
				rs.next();
				String pubKeyExp = rs.getString(1);
				String mod = rs.getString(2);
				rsa.setPublicKey(pubKeyExp, mod);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * set session key
	 * @param sessionKeySpec   the sessionKeySpec to set
	 * @uml.property  name="sessionKeySpec"
	 */
	public void setSessionKeySpec(SecretKeySpec sessionKeySpec) {
		this.sessionKeySpec = sessionKeySpec;
	}

	/**
	 * get session key
	 * @return   the sessionKeySpec
	 * @uml.property  name="sessionKeySpec"
	 */
	public SecretKeySpec getSessionKeySpec() {
		return sessionKeySpec;
	}
	
	/**
	 * @param rsa   the rsa to set
	 * @uml.property  name="rsa"
	 */
	protected void setRsa(RSASoftware rsa) {
		this.rsa = rsa;
	}

	/**
	 * @return   the rsa
	 * @uml.property  name="rsa"
	 */
	protected RSASoftware getRsa() {
		return rsa;
	}
	
	public byte[] encryptAES(byte[] plaintext) {
		try {
			Cipher cipher = Cipher.getInstance("aes");
			cipher.init(Cipher.ENCRYPT_MODE, this.sessionKeySpec);
			
			byte[] ciphertext = cipher.doFinal(plaintext);
			return ciphertext;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public byte[] decryptAES(byte[] ciphertext) {
		try {
			Cipher cipher = Cipher.getInstance("aes");
			cipher.init(Cipher.DECRYPT_MODE, this.sessionKeySpec);
			
			byte[] plaintext = cipher.doFinal(ciphertext);
			return plaintext;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public byte[] objToBytes(Object obj){
	      ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
	      ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush(); 
			oos.close(); 
			bos.close();
			byte [] data = bos.toByteArray();
			return data;
		} catch (NotSerializableException e){
			e.printStackTrace();
			return null;
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} return null;
	}
	
	public Object BytesToObj(byte[] b){
	      ByteArrayInputStream bis = new ByteArrayInputStream(b); 
	      ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(bis);
			Object obj = ois.readObject();
			ois.close(); 
			bis.close();
			return obj;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    return null;
	}
	 
}
