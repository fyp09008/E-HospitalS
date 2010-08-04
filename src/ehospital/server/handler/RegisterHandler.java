package ehospital.server.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Random;

import cipher.RSAHardware;

/**
 * Register User into the database. Remnant from semester 1 version. 
 * @author   mc, Gilbert
 */
public class RegisterHandler extends Handler{
	
	private String username;
	private String role;
	private String publicKeyExp;
	private String modulus;
	private String pwdMDExp;
	/**
	 * @uml.property  name="rsaHard"
	 * @uml.associationEnd  
	 */
	private RSAHardware rsaHard;
	
	/**
	 * Default constructor. does Nothing
	 */
	public RegisterHandler()
	{
		
	}
	
	/**
	 * Constructor that intake a role and a username
	 * @param role
	 * @param username
	 */
	public RegisterHandler(String role, String username) {
		this.role = role;
		this.username = username;
		rsaHard = new RSAHardware();
	}
	
	/**
	 * Register a user
	 * @return 0 if success, -1 if failed
	 */
	public int register()
	{
		if (dbm.isUserExist(username) != null) return -1;
		if (genKey() < 0) return -1;
		if (storeToDB() < 0) return -1;
		return 0;
	}
	
	/**
	 * Return a randomly generated password with 8 characters
	 * @return password
	 */
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
	
	/**
	 * generate key and put it into the Java Card
	 * @return 0 if success, -1 if failed
	 */
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
			e.printStackTrace();
		}
		
		
		System.out.println("Done");
		
		return 0;
	}
	
	/**
	 * store the new user into the database
	 * @return 0 if success
	 */
	private int storeToDB() {
		return dbm.storeUser(role, username, pwdMDExp, publicKeyExp, modulus);
	}
	
	/**
	 * register a temp user and store the keys into JAVA Card
	 * @return 0 if success, 1 if the card fails, 2 if SQL error, 4 if database not available 
	 */
	public int regTmpUser() {
		rsaHard = new RSAHardware(); 
		if (genKey() < 0) return 1;
		String [] str = {rsaHard.getGeneratedPublicKeyExp(),rsaHard.getGeneratedModulus()};
		if (dbm.connect()) {
			try {
				dbm.update("insert into `tmp_user` ( `pub_key`, `mod` ) VALUE (?,?)", str);
			} catch (SQLException e) {
				e.printStackTrace();
				return 2;
			}
		} else {
			return 4;
		}
		return 0;
	}
	
	
}
