import java.io.FileWriter;
import java.io.IOException;

import cipher.RSASoftware;


public class KeyGen {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileWriter fwPubKey;
		FileWriter fwPrvKey;
		
		RSASoftware rsa = new RSASoftware();
		rsa.genKey();
		String pubKey = rsa.getPublicKeyExp();
		String mod = rsa.getModulus();
		String prvKey = rsa.getPrivateKeyExp();
		
		try {
			fwPubKey = new FileWriter("pubkey.txt");
			fwPrvKey = new FileWriter("prvkey.txt");
			fwPubKey.write(pubKey + "\n" + mod);
			fwPrvKey.write(prvKey + "\n" + mod);
			fwPubKey.close();
			fwPrvKey.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
