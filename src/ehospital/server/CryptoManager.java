package ehospital.server;

import java.security.KeyPairGenerator;
import java.security.PublicKey;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class CryptoManager {

	private SecretKeySpec sessionKey;
	private KeyGenerator keyGen;
	private KeyPairGenerator keyPairGen;
	private PublicKey pub;
}

