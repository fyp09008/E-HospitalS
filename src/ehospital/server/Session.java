package ehospital.server;

import java.util.Random;
import java.util.TimerTask;

import javax.crypto.spec.SecretKeySpec;

public class Session extends TimerTask {
	
	private String username;
	private SecretKeySpec sessionKey;
	private byte[] lomsg;
	private String exp;
	private String mod;
	
	
	

	public Session() {
		
	}

	public Session(String username, SecretKeySpec sessionKey, String exp, String mod) {
		this.username = username;
		this.sessionKey = sessionKey;
		int i = new Random().nextInt();
		this.lomsg = Utility.intToByteArray(i);
		this.mod = mod;
		this.exp = exp;
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

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getMod() {
		return mod;
	}

	public void setMod(String mod) {
		this.mod = mod;
	}
	
	public byte[] getLomsg() {
		return lomsg;
	}

	public void setLomsg(byte[] lomsg) {
		this.lomsg = lomsg;
	}
}