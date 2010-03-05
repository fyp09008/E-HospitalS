package ehospital.server.handler;

import cipher.RSASoftware;

public class ServerAuthHandler extends Handler {
	
	private byte[] fingerprint = {(byte)0x16,(byte)0x27,(byte)0xac,(byte)0xa5,
			(byte)0x76,(byte)0x28,(byte)0x2d,(byte)0x36,
			(byte)0x63,(byte)0x1b,(byte)0x56,(byte)0x4d,(byte)0xeb,(byte)0xdf,(byte)0xa6,(byte)0x48};
	private final String pri = "8a68d95af9c3be531e0547410906e56143dc702f5defdbbc4f50185bded0f78ce51ebf8e1f1adbebd67644093aeac49ae64787df5f71385cb9dff480cb70ecbaa95d88797bd7c1abd9903745abe8e3202c053d2d9295ce382447444d6b1f98e5d0a45fd3548e7f1902d5e0aa82ef4c21cf5761705828222b78836a3b7f33090d";
	private final String mod = "a89877a7e5150456b696d40a9a35ac5ce72cf331ed6463bb05a658a98962739a244d770e78f70e0dd1c07404e2e77aaf9dba6ff3ee21a38a5555c1cbd28a2f7fed603b25a9cf8a6ff1a330503c882b300d855a9c315aa7eec4fca5ee3e7ca351b7e086309de90d2ad4183a606352b052b0c990856df7b3a106f76a48ea004a19";
	
	public ServerAuthHandler() {
		
	}
	
	private byte[] signServerPri(byte[] plaintext) {
		RSASoftware rsa = new RSASoftware();
		rsa.setPrivateKey(pri, mod);
		byte[] ciphertext = rsa.sign(plaintext, plaintext.length);
		return ciphertext;
	}
	
	public byte[] getSignedFingerprint() {
		return this.signServerPri(this.fingerprint);
	}
}
