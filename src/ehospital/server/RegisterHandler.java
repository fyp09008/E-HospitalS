/**
 * 
 */
package ehospital.server;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import cipher.*;

/**
 * @author mc
 *
 */
public class RegisterHandler extends Handler{
	
	private String username;
	private String role;
	private String publicKeyExp;
	private String modulus;
	private String pwdMDExp;
	private RSAHardware rsaHard;
	
	public RegisterHandler()
	{
		
	}
	
	public RegisterHandler(String role, String username) {
		this.role = role;
		this.username = username;
		rsaHard = new RSAHardware();
	}
	
	public int register()
	{
		if (dbm.isUserExist(username)) return -1;
		if (genKey() < 0) return -1;
		if (storeToDB() < 0) return -1;
		return 0;
	}
	
	private String genPassword()
	{
		String pwd = null;
		char[] c = new char[8];
		Random r = new Random();
		double p = 0;
		int choice = 0;
		for (int i = 0; i < 8; i++)
		{
			p = Math.random();
			if (p < 0.3)
				choice = 1;
			else if (p >= 0.3 && p < 0.6)
				choice = 2;
			else choice = 3;
			switch (choice)
			{
				case 1:
					c[i] = (char) (r.nextInt(122 - 97 + 1)+97);
					break;
				case 2:
					c[i] = (char) (r.nextInt(57 - 48 + 1)+48);
					break;
				case 3:
					c[i] = (char) (r.nextInt(90 - 65 + 1)+65);
					break;
				default:
					break;
			}
		}
		pwd = new String(c);
		System.out.println("Password is: "+pwd);
		return pwd;
	}
	
	private int genKey()
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		do {
			System.out.print("Please insert a java card and press enter: ");
			try {
				br.readLine();
			} catch (IOException ex) { }
		} while (rsaHard.initJavaCard() < 0);
		
		System.out.print("Generating RSA key pair...");
		
		// generate a RSA key pair
		if (rsaHard.genKey() == -1) {
			System.out.println("Error");
			return -1;
		}
		
		
		
		modulus = rsaHard.getGeneratedModulus();
		publicKeyExp = rsaHard.getGeneratedPublicKeyExp();
		String privateKeyExp = rsaHard.getGeneratedPrivateKeyExp();
		rsaHard.initJavaCard("285921800099");
		rsaHard.setPrivateKey(privateKeyExp, modulus);
		rsaHard.setPublicKey(publicKeyExp, modulus);
		String genPwd = this.genPassword();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("md5");
			pwdMDExp = this.byteArrayToString(md.digest(genPwd.getBytes()));
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Done");
		
		return 0;
	}
	
	private int storeToDB() {
		return dbm.storeUser(role, username, pwdMDExp, publicKeyExp, modulus);
	}
	
	
}
