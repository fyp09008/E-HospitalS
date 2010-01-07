/**
 * 
 */
package ehospital.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.sun.rowset.CachedRowSetImpl;

import message.*;

/**
 * @author mc Gilbert
 *
 */
public class ClientThread extends Thread {

	private Socket csocket;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private DBManager dbm;
	private SecretKeySpec sks;
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
					/*QueryRequestMessage request = (QueryRequestMessage) o;
					QueryHandler qh = new QueryHandler(request);
					qh.queryDB();
					QueryResponseMessage response = new QueryResponseMessage();
					response.ResultSet = qh.encryptRS();
					objOut.writeObject(response);
					objOut.flush();*/
				} 
				else if (o instanceof AuthRequestMessage)
				{
					AuthRequestMessage request = (AuthRequestMessage) o;
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
						this.lomsg = this.intToByteArray(i);
						re.logoutmsg = ah.encryptAES(intToByteArray(i));
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
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			}
		}
		System.out.println(this+"GG");
		ServerThread.RemoveThread(this);
		System.out.print("~>");
	}
	
	// pos: return a byte array representation of integer
	private byte[] intToByteArray (final int integer) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			dos.writeInt(integer);
			dos.flush();
			return bos.toByteArray();
		} catch(IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean CloseSocket()
	{
		try {
			this.csocket.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	public String getUsername() {
		return username;
	}
	
	public String getRemoteIP()
	{
		return this.csocket.getRemoteSocketAddress().toString();
	}
}
