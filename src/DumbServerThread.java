import java.io.*;
import java.net.*;

import javax.crypto.Cipher;

import cipher.RSASoftware;

import message.*;


public class DumbServerThread extends Thread {
	
	private String username = "qwer";
	private String pwd = "1234";
	private Socket socket = null;
	private RSASoftware rsa;
		
	public DumbServerThread(Socket s) {
		this.socket = s;
		this.start();
	}
	
	public RSASoftware loadCryptoInfo(RSASoftware rsa) {
		try {
			//TODO use Database
			FileReader fr = new FileReader("pubkey.txt");
			BufferedReader in = new BufferedReader(fr);
			String pubKeyExp = in.readLine();
			String mod = in.readLine();
			rsa.setPublicKey(pubKeyExp, mod);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsa;
	}
	
	public String genSessionKey() {
		//TODO generate real AES Key
		return "123456";
	}
	
	public byte[] encrypt(String str) {
		//TODO change back to byteArray
		byte plaintext[] = str.getBytes();
		byte ciphertext[] = rsa.encrypt(plaintext, plaintext.length);
		return ciphertext;
	}
	
	public void run() {
		
		try {
		    System.out.println("Someone calling...");
		    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		    
		    RequestMessage authReqMsg = (RequestMessage)in.readObject();
		    String rePwd = authReqMsg.password;
		    		    
		    ResponseMessage reMes = new ResponseMessage();
		    if (rePwd.equals(pwd)) {
		    	reMes.isAuth = true;
		    	ResponseMessage reSesMes = new ResponseMessage();
		    	String sKey = genSessionKey();
		    	
		    	//TODO use Java Card
		    	rsa = new RSASoftware();
		    	rsa = loadCryptoInfo(rsa);
		    	
		    	byte[] bsKey = encrypt(sKey);
		    	reSesMes.sessionKey = bsKey;
		    	
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
	
}
