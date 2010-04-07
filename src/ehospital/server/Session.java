package ehospital.server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.spec.SecretKeySpec;

import remote.obj.ClientCallback;

public class Session extends TimerTask {
	
	private static int TIMEOUT = 1000;
	
	private String username;
	private SecretKeySpec sessionKey;
	private byte[] lomsg;
	private String exp;
	private String mod;

	public Session(String username, SecretKeySpec sessionKey, String exp, String mod) {
		this.username = username;
		this.sessionKey = sessionKey;
		int i = new Random().nextInt();
		this.lomsg = Utility.intToByteArray(i);
		this.mod = mod;
		this.exp = exp;
		new Timer().schedule(this, Session.TIMEOUT);
	}
	
	@Override
	public void run() {
		try {
			
			Registry r = LocateRegistry.getRegistry(11111);
			ClientCallback ccb = (ClientCallback)r.lookup("ClientCallback");
			ccb.timeout();
			SessionList.deleteSession(username);
			this.cancel();
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		
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

	public void setSessionKey(SecretKeySpec sessionKey) {
		this.sessionKey = sessionKey;
	}
}