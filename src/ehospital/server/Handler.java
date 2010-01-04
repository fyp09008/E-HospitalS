package ehospital.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;

import javax.crypto.spec.SecretKeySpec;

import cipher.RSASoftware;

public class Handler {

	protected DBManager dbm;

	private RSASoftware rsa;
	
	private SecretKeySpec sessionKeySpec;
	
	public Handler() {
		dbm = new DBManager();
	}
	
	public String byteArrayToString(byte[] b) {
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
	
	public byte[] stringToByteArray(String str) {
		String HEX_NUM = "0123456789abcdef";
		int CHAR_NOT_FOUND = -1;
		byte[] b = new byte[str.length()/2];
		for(int i = 0, j = 0; i< str.length(); i++) {
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
	
	public byte[] encryptRSA(byte plaintext[]) {
		byte ciphertext[] = getRsa().encrypt(plaintext, plaintext.length);
		return ciphertext;
	}
	
	public byte[] decryptRSA(byte ciphertext[]) {
		byte plaintext[] = getRsa().encrypt(ciphertext, ciphertext.length);
		return plaintext;
	}
	
	
	public boolean loadCryptoInfo(String username) {
		try {
			if(dbm.isUserExist(username) {
				ResultSet rs = dbm.query("SELECT pub_key, `mod` FROM user WHERE username='"+username+"';");
				String pubKeyExp = rs.getString(1);
				String mod = rs.getString(2);
				rsa.setPublicKey(pubKeyExp, mod);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param sessionKeySpec the sessionKeySpec to set
	 */
	protected void setSessionKeySpec(SecretKeySpec sessionKeySpec) {
		this.sessionKeySpec = sessionKeySpec;
	}

	/**
	 * @return the sessionKeySpec
	 */
	protected SecretKeySpec getSessionKeySpec() {
		return sessionKeySpec;
	}

	/**
	 * @param rsa the rsa to set
	 */
	protected void setRsa(RSASoftware rsa) {
		this.rsa = rsa;
	}

	/**
	 * @return the rsa
	 */
	protected RSASoftware getRsa() {
		return rsa;
	}

	
	 
}
