package ehospital.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.spec.SecretKeySpec;

import remote.obj.ClientCallback;

/**
 * A session 
 * @author Gilbert
 *
 */
public class Session extends TimerTask {
	//5 minutes
	private static int TIMEOUT = 300000;
	
	private String username;
	private SecretKeySpec sessionKey;
	private byte[] lomsg;
	private String exp;
	private String mod;
	private String host;
	private Timer t;

	public Timer getTimer(){
		return t;
	}
	/**
	 * Constructor that initialize everything
	 * @param username
	 * @param sessionKey
	 * @param exp
	 * @param mod
	 * @param host
	 */
	public Session(String username, SecretKeySpec sessionKey, String exp, String mod, String host) {
		this.username = username;
		this.sessionKey = sessionKey;
		int i = new Random().nextInt();
		this.lomsg = Utility.intToByteArray(i);
		this.mod = mod;
		this.exp = exp;
		this.host = host;
		t = new Timer();
		t.schedule(this, Session.TIMEOUT);
	}
	
	@Override
	public void run() {
		try {
			Registry r = LocateRegistry.getRegistry(host,7788);
			ClientCallback ccb = (ClientCallback)r.lookup("ClientCallback");
			System.out.println(host + "session Timeout" + new Date() );
			SessionList.deleteSession(username);
			t.cancel();
			this.cancel();
			ccb.timeout();
			
		} catch (Exception e){
			SessionList.deleteSession(username);
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