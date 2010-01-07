/**
 * 
 */
package ehospital.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
<<<<<<< HEAD
import java.util.ArrayList;

=======
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

>>>>>>> 15afeb000fdb47bc1286d3dd24a7c944129f43a5
/**
 * @author mc
 *
 */
public class Console {
	
<<<<<<< HEAD
	//private String pri =
	//	"10001";
	//private String mod = 
	//	"94134257dd13820880b443b653e8c716d6381d5a2683f5961ef0402468f0079202ebd97ac85291bd7971f915ff10e06ed1555dcb6868fcbf60bb89e4fcbb0b20ea7abda29bfaa5a11a0109c44d689f9d188e9cbb62211e5fda65cbd37382bb46c6e72fa889ae366e0c3804509676b61fdc38c4cf05a3646b5deb02a8e4e18daf";
	
	private ArrayList<String> cmdList;
=======
	private static final String pub =
		"10001";
	private static final String pri = "8a68d95af9c3be531e0547410906e56143dc702f5defdbbc4f50185bded0f78ce51ebf8e1f1adbebd67644093aeac49ae64787df5f71385cb9dff480cb70ecbaa95d88797bd7c1abd9903745abe8e3202c053d2d9295ce382447444d6b1f98e5d0a45fd3548e7f1902d5e0aa82ef4c21cf5761705828222b78836a3b7f33090d";
	private static final String mod = "a89877a7e5150456b696d40a9a35ac5ce72cf331ed6463bb05a658a98962739a244d770e78f70e0dd1c07404e2e77aaf9dba6ff3ee21a38a5555c1cbd28a2f7fed603b25a9cf8a6ff1a330503c882b300d855a9c315aa7eec4fca5ee3e7ca351b7e086309de90d2ad4183a606352b052b0c990856df7b3a106f76a48ea004a19";
	private static final byte[] key = {-19, -11, 122, 111, -37, -13, 16, -47, -65, 78, -126, -128, -88, 54, 101, 86};
	private static final SecretKeySpec ProgramKey = new SecretKeySpec(key, "AES");
	public static ArrayList<String> cmdList;
>>>>>>> 15afeb000fdb47bc1286d3dd24a7c944129f43a5
	
	/**
	 * @author Gilbert
	 */
	public Console() {
		//init commandList
<<<<<<< HEAD
		cmdList = new ArrayList<String>();
		cmdList.add(0, "exit");
		cmdList.add(1, "startup");
		cmdList.add(2, "shoutdown");
		cmdList.add(3, "register");
		cmdList.add(4, "testauth");
		cmdList.add(5, "help");
=======
		
>>>>>>> 15afeb000fdb47bc1286d3dd24a7c944129f43a5
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
<<<<<<< HEAD
		//Console c = new Console();
=======
		cmdList = new ArrayList<String>();
		cmdList.add(0, "exit");
		cmdList.add(1, "startup");
		cmdList.add(2, "shoutdown");
		cmdList.add(3, "register");
		cmdList.add(4, "testauth");
		cmdList.add(5, "Threadchk");
		cmdList.add(6, "show client");
		cmdList.add(7, "help");
>>>>>>> 15afeb000fdb47bc1286d3dd24a7c944129f43a5
		
		String cmd = "";
		ServerThread sThread = null;
		BufferedReader cmdreader = new BufferedReader(new InputStreamReader(System.in));
		try {
<<<<<<< HEAD
=======
			for (int i = 0 ; i < Console.cmdList.size(); i++ ) {
				System.out.println(i+ ". " + Console.cmdList.get(i));
			}
>>>>>>> 15afeb000fdb47bc1286d3dd24a7c944129f43a5
			System.out.print("~>");
			while ((cmd = cmdreader.readLine()) != null)
			{
				if (cmd.equalsIgnoreCase("exit"))
				{
					System.exit(0);
				}
				else if (cmd.equalsIgnoreCase("startup"))
				{
					sThread = new ServerThread();
					sThread.start();
				}
<<<<<<< HEAD
=======
				else if (cmd.equalsIgnoreCase("startwith"))
				{
					System.out.print("port?");
					sThread = new ServerThread(Integer.parseInt(cmdreader.readLine()));
					sThread.start();
				}
				else if (cmd.equalsIgnoreCase("mail"))
				{
					
				}
				else if (cmd.equalsIgnoreCase("genkey"))
				{
					cipher.RSASoftware rsaSoft = new cipher.RSASoftware();
					rsaSoft.genKey();
					System.out.println("pub: "+rsaSoft.getPublicKeyExp());
					System.out.println("pri: "+rsaSoft.getPrivateKeyExp());
					System.out.println("mod: "+rsaSoft.getModulus());
					
				}
>>>>>>> 15afeb000fdb47bc1286d3dd24a7c944129f43a5
				else if (cmd.equalsIgnoreCase("shutdown"))
				{
					if (sThread != null)
						sThread.closeServerSocket();
					else 
						System.out.println("Server is not started yet!");
				}
				else if (cmd.equalsIgnoreCase("register"))
				{
					System.out.print("Role? ");
					cmd = cmdreader.readLine();
					System.out.print("User name? ");
					String cmd2 = cmdreader.readLine();
					RegisterHandler rh = new RegisterHandler(cmd, cmd2);
					if(rh.register() == -1) {
						System.out.println("Username Exists!");
					}
					
				}
<<<<<<< HEAD
=======
				else if (cmd.equalsIgnoreCase("Threadchk"))
				{
					System.out.println(Thread.activeCount());
				}
				else if (cmd.equalsIgnoreCase("show client"))
				{
					sThread.printUserList();
				}
>>>>>>> 15afeb000fdb47bc1286d3dd24a7c944129f43a5
				else if (cmd.equalsIgnoreCase("testauth"))	
				{
					System.out.println("Username? ");
					String username = cmdreader.readLine();
					System.out.println("Password? ");
					String pwd = cmdreader.readLine();
					DBManager dbm = new DBManager();
					ehospital.server.AuthHandler ah = new ehospital.server.AuthHandler(username,pwd,dbm);
					if (ah.authenticate()) {
						System.out.println("User found and authenticated!");
					} else {
						System.out.println("Authenticate failed");
					}
					
<<<<<<< HEAD
=======
				} else if (cmd.equalsIgnoreCase("help")) {
					for (int i = 0 ; i < Console.cmdList.size(); i++ ) {
						System.out.println(Console.cmdList.get(i));
					}
>>>>>>> 15afeb000fdb47bc1286d3dd24a7c944129f43a5
				}
				System.out.print("~>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

<<<<<<< HEAD
=======
	public static Object encrypt(Object o)
	{
		Handler h = new Handler();
		h.setSessionKeySpec(ProgramKey);
		return (Object) h.encryptAES(h.objToBytes(o));
	}
	
	public static Object decrypt(Object o)
	{
		Handler h = new Handler();
		h.setSessionKeySpec(ProgramKey);
		return (Object) h.BytesToObj(h.decryptAES((byte[]) o));
	}
>>>>>>> 15afeb000fdb47bc1286d3dd24a7c944129f43a5
}
