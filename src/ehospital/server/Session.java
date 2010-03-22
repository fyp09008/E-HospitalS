package ehospital.server;

import java.util.Random;
import java.util.TimerTask;

import javax.crypto.spec.SecretKeySpec;

public class Session extends TimerTask {
	
	private String username;
	private SecretKeySpec sessionKey;
	private byte[] lomsg;
	
	
	
	public byte[] getLomsg() {
		return lomsg;
	}

	public void setLomsg(byte[] lomsg) {
		this.lomsg = lomsg;
	}

	public Session() {
		
	}

	public Session(String username, SecretKeySpec sessionKey) {
		this.username = username;
		this.sessionKey = sessionKey;
		int i = new Random().nextInt();
		this.lomsg = Utility.intToByteArray(i);
	}
	@Override
	public void run() {
		
	}

	public String getUsername() {
		return username;
	}

	public SecretKeySpec getSessionKey() {
		return sessionKey;
	}
	
}