/**
 * 
 */
package ehospital.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.crypto.spec.SecretKeySpec;

import ehospital.server.db.DBManager;
import ehospital.server.db.Logger;
import ehospital.server.handler.RegisterHandler;
import ehospital.server.remote.impl.AuthHandlerImpl;
import ehospital.server.remote.impl.DataHandlerImpl;
import ehospital.server.remote.impl.DisconnHandlerImpl;
/**
 * @author mc
 *
 */
public class RMIConsole {
	

	
	//private String pri =
	//	"10001";
	//private String mod = 
	//	"94134257dd13820880b443b653e8c716d6381d5a2683f5961ef0402468f0079202ebd97ac85291bd7971f915ff10e06ed1555dcb6868fcbf60bb89e4fcbb0b20ea7abda29bfaa5a11a0109c44d689f9d188e9cbb62211e5fda65cbd37382bb46c6e72fa889ae366e0c3804509676b61fdc38c4cf05a3646b5deb02a8e4e18daf";
	
	//private static final String pub =
	//	"10001";
	//private static final String pri = "8a68d95af9c3be531e0547410906e56143dc702f5defdbbc4f50185bded0f78ce51ebf8e1f1adbebd67644093aeac49ae64787df5f71385cb9dff480cb70ecbaa95d88797bd7c1abd9903745abe8e3202c053d2d9295ce382447444d6b1f98e5d0a45fd3548e7f1902d5e0aa82ef4c21cf5761705828222b78836a3b7f33090d";
	//private static final String mod = "a89877a7e5150456b696d40a9a35ac5ce72cf331ed6463bb05a658a98962739a244d770e78f70e0dd1c07404e2e77aaf9dba6ff3ee21a38a5555c1cbd28a2f7fed603b25a9cf8a6ff1a330503c882b300d855a9c315aa7eec4fca5ee3e7ca351b7e086309de90d2ad4183a606352b052b0c990856df7b3a106f76a48ea004a19";
	public static final byte[] key = {-19, -11, 122, 111, -37, -13, 16, -47, -65, 78, -126, -128, -88, 54, 101, 86};
	public static final SecretKeySpec ProgramKey = new SecretKeySpec(key, "AES");
	public static ArrayList<String> cmdList;
	public static Logger log = new Logger();
	public static Registry reg;
	/**
	 * @author Gilbert
	 */
	public RMIConsole() {
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		cmdList = new ArrayList<String>();
		cmdList.add("exit");
		cmdList.add("start");
		cmdList.add("shutdown");
		cmdList.add("register");
		cmdList.add("testauth");
		cmdList.add("threadchk");
		cmdList.add("status");
		cmdList.add("show client");
		cmdList.add("help");
		
		String cmd = "";
		BufferedReader cmdreader = new BufferedReader(new InputStreamReader(System.in));
		try {
			for (int i = 0 ; i < RMIConsole.cmdList.size(); i++ ) {
				System.out.println(i+ ". " + RMIConsole.cmdList.get(i));
			}
			System.out.print("~>");
			while ((cmd = cmdreader.readLine()) != null)
			{
				if (cmd.equalsIgnoreCase("exit"))
				{
					log.log("admin", "Server exit.");
					System.exit(0);
				}
				else if (cmd.equalsIgnoreCase("start"))
				{
					try {
						reg = LocateRegistry.createRegistry(1099);
					} catch (ExportException e) {
						reg = LocateRegistry.getRegistry(1099);
					}
					try {
			            String name = "AuthHandler";
			            remote.obj.AuthHandler engine = new AuthHandlerImpl();
			            reg.rebind(name, engine);			            	
			            
			            System.out.println("AuthenticatorImpl bound");
			            
			        } catch (Exception e) {
			            System.err.println("AuthenticatorImpl exception:");
			            e.printStackTrace();
			        }
			        
			        try {
			            String name = "DataHandler";
			            remote.obj.DataHandler engine = new DataHandlerImpl();
			            reg.rebind(name, engine);
			            System.out.println("DataHandlerImpl bound");
			            
			        } catch (Exception e) {
			            System.err.println("DataHandlerImpl exception:");
			            e.printStackTrace();
			        }
			        
			        try {
			            String name = "DisconnHandler";
			            remote.obj.DisconnHandler engine = new DisconnHandlerImpl();
			            reg.rebind(name, engine);			            	
			            
			            System.out.println("DisconnHandlerImpl bound");
			            
			        } catch (Exception e) {
			            System.err.println("DisconnHandlerImpl exception:");
			            e.printStackTrace();
			        }
			        
					log.log("admin", "Server starts");
				}
				else if (cmd.equalsIgnoreCase("genkey"))
				{
					cipher.RSASoftware rsaSoft = new cipher.RSASoftware();
					rsaSoft.genKey();			
				}
				else if (cmd.equalsIgnoreCase("shutdown")) {
					shutdown();
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
						
						log.log(cmd2, "failed to register "+cmd+"as existing user");
					}
					
				}
				else if (cmd.equalsIgnoreCase("Threadchk"))
				{
					System.out.println(Thread.activeCount());
					log.log("admin", Thread.activeCount()+"");					
				}
				else if (cmd.equalsIgnoreCase("status"))
				{
					if (reg != null) {
						String[] s = reg.list();
						if (s != null) {
							for(int i = 0; i < s.length; i++) {
								System.out.println(s[i]);
							}
						} else {
							System.out.println("Server Not Started");
						}
					} else {
						System.out.println("Server Not Started");
					}
				}
				else if (cmd.equalsIgnoreCase("show client"))
				{
					//TODO add to 
				}
				else if (cmd.equalsIgnoreCase("testauth"))	
				{
					System.out.println("Username? ");
					
					String username = cmdreader.readLine();
					System.out.println("Password? ");
					String pwd = cmdreader.readLine();
					DBManager dbm = new DBManager();
					ehospital.server.handler.AuthHandler ah = new ehospital.server.handler.AuthHandler(username,pwd,dbm);
					if (ah.authenticate()) {
						System.out.println("User found and authenticated!");
						log.log(username, "is tested authenticated by admin.");
						
					} else {
						System.out.println("Authenticate failed");
						log.log(username, "is tested failed to authenticate by admin.");
					}
					
				} else if (cmd.equalsIgnoreCase("help")) {
					for (int i = 0 ; i < RMIConsole.cmdList.size(); i++ ) {
						System.out.println(RMIConsole.cmdList.get(i));
					}
				} else if (cmd.equalsIgnoreCase("testsql"))
				{
					DBManager dbm = new DBManager();
					ehospital.server.db.DBManager.Param[] pList = new ehospital.server.db.DBManager.Param[1];
					pList[0] = dbm.new Param("name", "medicine A';");
					ehospital.server.db.DBManager.Param[] pList2 = new ehospital.server.db.DBManager.Param[1];
					pList2[0] = dbm.new Param("id", "1");
					dbm.update("Update medicine", pList, pList2);
				}
				System.out.print("~>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		shutdown();
	}

	private static void shutdown() {
		try {
			reg.unbind("AuthHandler");
			reg.unbind("DataHandler");
			reg.unbind("DisconnHandler");
			System.out.println("Server is shut down");
			log.log("admin", "Server is shut down");
		} catch (NotBoundException e) {
			System.out.println("Some of the Handlers are not bind.");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
