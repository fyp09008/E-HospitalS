/**
 * 
 */
package ehospital.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mc
 *
 */
public class Console {
	
	private String pri =
		"10001";
	private String mod = 
		"94134257dd13820880b443b653e8c716d6381d5a2683f5961ef0402468f0079202ebd97ac85291bd7971f915ff10e06ed1555dcb6868fcbf60bb89e4fcbb0b20ea7abda29bfaa5a11a0109c44d689f9d188e9cbb62211e5fda65cbd37382bb46c6e72fa889ae366e0c3804509676b61fdc38c4cf05a3646b5deb02a8e4e18daf";
	
	private ArrayList<String> cmdList;
	
	/**
	 * @author Gilbert
	 */
	public Console() {
		//init commandList
		cmdList = new ArrayList<String>();
		cmdList.add(0, "exit");
		cmdList.add(1, "startup");
		cmdList.add(2, "shoutdown");
		cmdList.add(3, "register");
		cmdList.add(4, "test");
		cmdList.add(5, "help");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Console c = new Console();
		
		String cmd = "";
		ServerThread sThread = null;
		BufferedReader cmdreader = new BufferedReader(new InputStreamReader(System.in));
		try {
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
					new Register(cmd).register();
				}
				else if (cmd.equalsIgnoreCase("test"))	
				{
					try {
						Class.forName("com.mysql.jdbc.Driver");
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hospital_rec", "FYP09", "1234qwer");
						Statement stmt = conn.createStatement();
						ResultSet rs = stmt.executeQuery("SELECT * FROM medicine;");
						rs.next();
						//System.out.println(rs.getString(0));
						System.out.println(rs.getString(1));
						System.out.println(rs.getString(2));
						rs.next();
						//System.out.println(rs.getString(0));
						System.out.println(rs.getString(1));
						System.out.println(rs.getString(2));
						/*String[][] test = ClientThread.RSparse(rs);
						for (int i = 0; i < test.length; i++)
						{
							for (int j = 0; j < test[0].length; j++)
								System.out.print(test[i][j]+" ");
							System.out.println();
						}*/
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.exit(-1);
					}
				}
				System.out.print("~>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
