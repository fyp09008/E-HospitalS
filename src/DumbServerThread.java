import java.io.*;
import java.net.*;

import javax.crypto.Cipher;

import message.*;


public class DumbServerThread extends Thread{
	private String username = "qwer";
	private String pwd = "1234";
	private Socket socket = null;
	
	//dummy en/decrypt
	public String doFinal(String str) {
		return str;
		
	}
	public void run() {
		try {
		    System.out.println("Someone calling...");
		    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		    
		    RequestMessage authReqMsg = (RequestMessage)in.readObject();
		    String cipherUsername = authReqMsg.username;
		    String cipherPwd = authReqMsg.password;
		    
		    String reUsername = doFinal(cipherUsername);
		    String rePwd = doFinal(cipherPwd);
		    
		    ResponseMessage reMes = new ResponseMessage();
		    if (reUsername.equals(username) && rePwd.equals(pwd)) {
		    	reMes.isAuth = true;
		    	ResponseMessage reSesMes = new ResponseMessage();
		    	reSesMes.sessionKey = "123456";
		    	out.writeObject(reMes);
		    	out.writeObject(reSesMes);
		    } else {
		    	reMes.isAuth = false;	
		    	out.writeObject(reMes);
		    }
		    
		    out.close();
		    in.close();
		    socket.close();

		} catch (IOException e) {
		    e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public DumbServerThread(Socket s) {
		this.socket = s;
		this.start();
	}
}
