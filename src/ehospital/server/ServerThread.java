/**
 * 
 */
package ehospital.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author mc
 *
 */
public class ServerThread extends Thread {
	
	private ServerSocket ssocket;
	private int port;
	private boolean flag;
	private ArrayList<Socket> clientls;

	/**
	 * 
	 */
	public ServerThread() {
		// TODO Auto-generated constructor stub
		port = 8888;
		ssocket = null;
		flag = true;
		clientls = new ArrayList<Socket>();
	}

	/**
	 * @param port
	 */
	public ServerThread(int port) {
		this.port = port;
		ssocket = null;
		flag = true;
		clientls = new ArrayList<Socket>();
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
					ct.start();
				} catch (IOException e) {
					// TODO add Logger
					System.out.println("Server Socket Closed");
					System.out.print("~>");
					Iterator<Socket> i = clientls.iterator();
					
					while (i.hasNext())
					{
						try {
							i.next().close();
						} catch (IOException e1) {
							// TODO add Logger
							System.out.println("ClientSocket closed unexpectedly");
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

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void closeServerSocket()
	{
		try {
			flag = false;
			ssocket.close();
		} catch (IOException e) {
			// TODO Add Logger
			System.out.println("Server Socket Closed");
			Iterator<Socket> i = clientls.iterator();
			
			while (i.hasNext())
			{
				try {
					i.next().close();
				} catch (IOException e1) {
					// TODO add Logger
					System.out.println("ClientSocket closed unexpectedly");
				}
			}
		}
		
	}
}
