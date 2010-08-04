/**
 * 
 */
package ehospital.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import message.AuthRequestMessage;
import message.AuthResponseMessage;
import message.DisconnRequestMessage;
import message.DisconnResponseMessage;
import message.QueryRequestMessage;
import message.QueryResponseMessage;
import message.ServerAuthRequestMessage;
import message.ServerAuthResponseMessage;
import message.UpdateRequestMessage;
import message.UpdateResponseMessage;

import com.sun.rowset.CachedRowSetImpl;

import ehospital.server.db.DBManager;
import ehospital.server.handler.AuthHandler;
import ehospital.server.handler.DisconnHandler;
import ehospital.server.handler.QueryHandler;
import ehospital.server.handler.ServerAuthHandler;
import ehospital.server.handler.UpdateHandler;

/**
 * obsolete class, developed in 09/10 semester 1 using socket programming. <br>Thread class handling client requests and responses.
 * @author  Gilbert
 * @author  mc
 */
public class ClientThread extends Thread {

	private Socket csocket;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	/**
	 * @uml.property  name="dbm"
	 * @uml.associationEnd  
	 */
	private DBManager dbm;
	private SecretKeySpec sks;
	/**
	 * @uml.property  name="username"
	 */
	private String username;
	private byte[] lomsg;
	/**
	 * @param csocket
	 */
	public ClientThread(Socket csocket) {
		this.csocket = csocket;
		dbm = new DBManager();
		try {
			this.objIn = new ObjectInputStream(this.csocket.getInputStream());
			this.objOut = new ObjectOutputStream(this.csocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		boolean flag = true;
		System.out.println("Reciving connection from: "+csocket.getRemoteSocketAddress());
		System.out.print("~>");
		while (flag)
		{
			try {
				Object o = Console.decrypt(objIn.readObject());
				if (o instanceof UpdateRequestMessage)
				{
					UpdateHandler uh = new UpdateHandler((UpdateRequestMessage) o, dbm, this.sks);
					uh.setIP(this.csocket.getRemoteSocketAddress().toString());
					if (uh.update())
					{
						objOut.writeObject(Console.encrypt(new UpdateResponseMessage(true)));
						objOut.flush();
					}
					else
					{
						objOut.writeObject(Console.encrypt(new UpdateResponseMessage(false)));
						objOut.flush();
					}
				}
				else if (o instanceof QueryRequestMessage)
				{
				} 
				else if (o instanceof AuthRequestMessage)
				{
					AuthRequestMessage request = (AuthRequestMessage) o;
					@SuppressWarnings("unused")
					AuthHandler ah = new AuthHandler(request, dbm);
					QueryRequestMessage req = (QueryRequestMessage) o;
					QueryHandler qh = new QueryHandler(req, this.sks);
					ResultSet rs = qh.query();
					CachedRowSetImpl crs = new CachedRowSetImpl();
					crs.populate(rs);
					QueryResponseMessage response = new QueryResponseMessage();
					response.resultSet = qh.encryptAES(qh.objToBytes(crs));
					objOut.writeObject(Console.encrypt(response));
					objOut.flush();
				} 
				else if (o instanceof ServerAuthRequestMessage)
				{
					ServerAuthHandler sah = new ServerAuthHandler();
					byte[] fingerprint = sah.getSignedFingerprint();
					ServerAuthResponseMessage response = new ServerAuthResponseMessage(fingerprint);
					this.objOut.writeObject(Console.encrypt(response));
					AuthRequestMessage authRequest = (AuthRequestMessage)Console.decrypt(this.objIn.readObject());
					AuthHandler ah = new AuthHandler(authRequest, dbm);
					AuthResponseMessage re;
					if(ah.authenticate()) {
						re = new AuthResponseMessage();
						re.isAuth = true;
						ah.genSessionKey();
						re.sessionKey = ah.getEncryptedSessionKey();
						this.sks = ah.getSessionKeySpec();
						re.sessionKey = ah.getEncryptedSessionKey();
						ResultSet tmpPri = ah.getPrivilege();
						CachedRowSetImpl crs = new CachedRowSetImpl();
						crs.populate(tmpPri);
						byte[] b = ah.objToBytes(crs);
						re.resultSet = ah.encryptAES(b);
						//*********************
						int i = new Random().nextInt();
						this.lomsg = Utility.intToByteArray(i);
						re.logoutmsg = ah.encryptAES(Utility.intToByteArray(i));
						this.username = authRequest.getUsername();
						//*********************
					} else {
						re = new AuthResponseMessage();
						re.isAuth = false;
					}
					objOut.writeObject(Console.encrypt(re));
					objOut.flush();
				}
				else if (o instanceof DisconnRequestMessage)
				{
					DisconnHandler dh = new DisconnHandler((DisconnRequestMessage) o, this.username, this.lomsg);
					if (dh.validate() < 0)
					{
						objOut.writeObject(Console.encrypt(new DisconnResponseMessage(false)));
						objOut.flush();
					}
					else
					{
						objOut.writeObject(Console.encrypt(new DisconnResponseMessage(true)));
						objOut.flush();
						flag = false;
					}
				}
				else
				{
					System.out.println("Wrong Msg Format");
					System.out.print("~>");
				}
			} catch (IOException e) {
				e.printStackTrace();
				flag = false;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				flag = false;
			} catch (SQLException e) {
				e.printStackTrace();
				flag = false;
			} catch (InvalidKeyException e) {
				e.printStackTrace();
				flag = false;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				flag = false;
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
				flag = false;
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
				flag = false;
			} catch (BadPaddingException e) {
				e.printStackTrace();
				flag = false;
			}
		}
		System.out.println(this+"GG");
		ServerThread.RemoveThread(this);
		System.out.print("~>");
	}
	
	/**
	 * 
	 * @return true if successfully closed
	 */
	public boolean CloseSocket()
	{
		try {
			this.csocket.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Get user name
	 * @return   user name
	 * @uml.property  name="username"
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * get IP address of the client machine.
	 * @return user IP address
	 */
	public String getRemoteIP()
	{
		return this.csocket.getRemoteSocketAddress().toString();
	}
}
