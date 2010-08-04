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
 * Holding the session information in the server. 
 * @author   Gilbert
 */
public class Session extends TimerTask {
	//5 minutes
	private static int TIMEOUT = 300000;
	
	/**
	 * @uml.property  name="username"
	 */
	private String username;
	/**
	 * @uml.property  name="sessionKey"
	 */
	private SecretKeySpec sessionKey;
	/**
	 * @uml.property  name="lomsg"
	 */
	private byte[] lomsg;
	/**
	 * @uml.property  name="exp"
	 */
	private String exp;
	/**
	 * @uml.property  name="mod"
	 */
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

	/**
	 * Get user name.
	 * @return   user name
	 * @uml.property  name="username"
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Get session key.
	 * @return   session key
	 * @uml.property  name="sessionKey"
	 */
	public SecretKeySpec getSessionKey() {
		return sessionKey;
	}

	/**
	 * Get exponent
	 * @return   exponent
	 * @uml.property  name="exp"
	 */
	public String getExp() {
		return exp;
	}

	/**
	 * Set exponent.
	 * @param  exp
	 * @uml.property  name="exp"
	 */
	public void setExp(String exp) {
		this.exp = exp;
	}

	/**
	 * Get modulus.
	 * @return   modulus
	 * @uml.property  name="mod"
	 */
	public String getMod() {
		return mod;
	}

	/**
	 * Set modulus.
	 * @param  mod
	 * @uml.property  name="mod"
	 */
	public void setMod(String mod) {
		this.mod = mod;
	}
	
	/**
	 * Get logout message
	 * @return   logout message
	 * @uml.property  name="lomsg"
	 */
	public byte[] getLomsg() {
		return lomsg;
	}

	/**
	 * Set logout message.
	 * @param  lomsg
	 * @uml.property  name="lomsg"
	 */
	public void setLomsg(byte[] lomsg) {
		this.lomsg = lomsg;
	}

	/**
	 * set session key
	 * @param  sessionKey
	 * @uml.property  name="sessionKey"
	 */
	public void setSessionKey(SecretKeySpec sessionKey) {
		this.sessionKey = sessionKey;
	}
}