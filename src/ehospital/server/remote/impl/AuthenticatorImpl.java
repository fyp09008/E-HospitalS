package ehospital.server.remote.impl;

import java.rmi.RemoteException;

import remoteInterface.Authenticator;

public class AuthenticatorImpl implements Authenticator {

	public boolean authenticate(String username, byte[] HEPwd)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean serverAuthenticate(byte[] serverID) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
