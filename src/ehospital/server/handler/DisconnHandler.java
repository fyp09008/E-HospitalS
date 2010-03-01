/**
 * 
 */
package ehospital.server.handler;

import javax.crypto.spec.SecretKeySpec;


import message.DisconnRequestMessage;

/**
 * @author mc
 * To check if the disconnect request is issued by the appropriate user
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
	
	private boolean compareByte(byte[] b1, byte[] b2)
	{
		if (b1.length != b2.length)
			return false;
		
		for (int i = 0; i < b1.length; ++i)
			if (b1[i] != b2[i])
				return false;
		
		return true;
	}

	public void setSks(SecretKeySpec sks) {
		this.setSessionKeySpec(sks);
	}

}
