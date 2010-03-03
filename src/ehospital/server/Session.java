package ehospital.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.TimerTask;

import javax.crypto.spec.SecretKeySpec;

public class Session extends TimerTask {

	private ClientThread ct;
	
	private String username;
	private SecretKeySpec sessionKey;
	private byte[] lomsg;
	
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
	
	public byte[] getLomsg() {
		return lomsg;
	}

	public void setLomsg(byte[] lomsg) {
		this.lomsg = lomsg;
	}

	public Session(ClientThread ct) {
		this.ct = ct;
	}

	public Session(String username, SecretKeySpec sessionKey) {
		this.username = username;
		this.sessionKey = sessionKey;
		int i = new Random().nextInt();
		this.lomsg = intToByteArray(i);
	}
	@Override
	public void run() {
		if (ct != null) {
			ServerThread.RemoveThread(ct);
		} else {
			
		}
		
	}

	public String getUsername() {
		return username;
	}

	public SecretKeySpec getSessionKey() {
		return sessionKey;
	}
	
}