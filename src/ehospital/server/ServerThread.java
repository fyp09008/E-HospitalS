package ehospital.server;

/**
 * 
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * obsolete class, developed in 09/10 semester 1 using socket programming.
 * @author mc
 *
 */
public class ServerThread extends Thread {
	
	private ServerSocket ssocket;
	private int port;
	private boolean flag;
	private ArrayList<Socket> clientls;
	private static ArrayList<ClientThread> ctls;

	/**
	 * Open server thread listening to port 8888
	 */
	public ServerThread() {
		port = 8888;
		ssocket = null;
		flag = true;
		clientls = new ArrayList<Socket>();
		ctls = new ArrayList<ClientThread>();
	}

	/**
	 * Listening to a custom port
	 * @param port
	 */
	public ServerThread(int port) {
		this.port = port;
		ssocket = null;
		flag = true;
		clientls = new ArrayList<Socket>();
		ctls = new ArrayList<ClientThread>();
	}

	@Override
	public void run() {
		try {
			ssocket = new ServerSocket(port);
			System.out.println("Listening to port: " + port);
			System.out.print("~>");
			flag = true;
			while (flag)
			{
				try {
					Socket csocket = ssocket.accept();
					ClientThread ct = new ClientThread(csocket);
					clientls.add(csocket);
					ctls.add(ct);
					ct.start();
				} catch (IOException e) {
					// TODO add Logger
					System.out.println("Server Socket Closed");
					System.out.print("~>");
					Iterator<ClientThread> i = ctls.iterator();
					ClientThread tmpct = null;
					while (i.hasNext())
					{
						tmpct = i.next();
						if (!tmpct.CloseSocket())
						{
							System.out.println("Cannot close Socket of "+tmpct.getUsername()+" from "+tmpct.getRemoteIP());
							System.out.print("~>");
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Add Logger
			System.out.println("Server cannot be started! IOException");
			e.printStackTrace();
		}
	}
	
	/**
	 * Close server socket.
	 */
	public void closeServerSocket()
	{
		try {
			flag = false;
			ssocket.close();
		} catch (IOException e) {
			// TODO Add Logger
			System.out.println("Server Socket Closed");
			System.out.print("~>");
//			Iterator<Socket> i = clientls.iterator();
			Iterator<ClientThread> i = ctls.iterator();
			ClientThread tmpct = null;
			while (i.hasNext())
			{
				tmpct = i.next();
				if (!tmpct.CloseSocket())
				{
					System.out.println("Cannot close Socket of "+tmpct.getUsername()+" from "+tmpct.getRemoteIP());
					System.out.print("~>");
				}
			}
		}
	}
	
	/**
	 * Print user list.
	 */
	public void printUserList()
	{
		Iterator<ClientThread> i = ctls.iterator();
		ClientThread tmpct = null;
		while (i.hasNext())
		{
			tmpct = i.next();
			System.out.println(tmpct.getUsername()+" from "+tmpct.getRemoteIP());
		}
		System.out.println("~>");
	}
	
	/**
	 * Remove a user from the user list.
	 * @param ct
	 */
	public static void RemoveThread(ClientThread ct)
	{
		ctls.remove(ct);
	}
}
