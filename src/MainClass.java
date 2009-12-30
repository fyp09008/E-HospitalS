import java.io.IOException;
import java.net.ServerSocket;


public class MainClass {

	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ServerSocket ss;
		int port = 8899;
		ss = new ServerSocket(port);
		System.out.println("Listening port: 8899");
		while(true) {
			new DumbServerThread(ss.accept());
		}
	}

}
