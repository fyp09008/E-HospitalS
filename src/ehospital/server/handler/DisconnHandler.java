/**
 * 
 */
package ehospital.server.handler;

import javax.crypto.spec.SecretKeySpec;


import message.DisconnRequestMessage;

/**
 * obsolete class, developed in 09/10 semester 1 using socket programming.
 * To check if the disconnect request is issued by the appropriate user 
 * @author mc
 * 
 */
public class DisconnHandler extends Handler {

	private String username;
	private byte[] msg;
	private byte[] msgCipher;
	
	/**
	 * 
	 */
	public DisconnHandler() {
		// TODO Auto-generated constructor stub
	}

	public DisconnHandler(DisconnRequestMessage msg, String username, byte[] lomsg) {
		this.username = username;
		this.msgCipher = msg.getSignature();
		this.msg = lomsg;
		this.loadCryptoInfo(this.username);
	}
	
	public int validate()
	{
		this.loadCryptoInfo(this.username);
		byte[] decMsg = this.getRsa().unsign(this.msgCipher, this.msgCipher.length);
		if (this.compareByte(decMsg, this.msg))
			return 0;
		else return -1;
	}
	
	

	public void setSks(SecretKeySpec sks) {
		this.setSessionKeySpec(sks);
	}

}
