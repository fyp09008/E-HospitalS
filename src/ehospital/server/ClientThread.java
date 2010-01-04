/**
 * 
 */
package ehospital.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.crypto.spec.SecretKeySpec;

import message.AuthRequestMessage;
import message.AuthResponseMessage;
import message.QueryRequestMessage;
import message.QueryResponseMessage;

/**
 * @author mc Gilbert
 *
 */
public class ClientThread extends Thread {

	private Socket csocket;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private String buf;
	private SecretKeySpec sessionKey;
	/**
	 * @param csocket
	 */
	public ClientThread(Socket csocket) {
		this.csocket = csocket;
		this.buf = null;
		try {
			this.objIn = new ObjectInputStream(this.csocket.getInputStream());
			this.objOut = new ObjectOutputStream(this.csocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		boolean flag = true;
		while (flag)
		{
			try {
				Object o = objIn.readObject();
				if (o instanceof QueryRequestMessage)
				{
					QueryRequestMessage request = (QueryRequestMessage) o;
					QueryHandler qh = new QueryHandler(request);
					qh.queryDB();
					QueryResponseMessage response = new QueryResponseMessage();
					response.ResultSet = qh.encryptRS();
					objOut.writeObject(response);
					objOut.flush();
				} 
				else if (o instanceof AuthRequestMessage)
				{
					AuthRequestMessage request = (AuthRequestMessage) o;
					AuthHandler ah = new AuthHandler(request);
					AuthResponseMessage re;
					if(ah.authenticate()) {
						ah.genSessionKey();
						re = new AuthResponseMessage();
						re.isAuth = true;
						this.sessionKey = new SecretKeySpec(ah.getSessionKey(), "aes");
						re.sessionKey = ah.getEncryptedSessionKey();
						
					} else {
						re = new AuthResponseMessage();
						re.isAuth = false;
					}
					objOut.writeObject(re);
					objOut.flush();
				}
				else
				{
					System.out.println("Wrong Msg Format");
					System.out.print("~>");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
