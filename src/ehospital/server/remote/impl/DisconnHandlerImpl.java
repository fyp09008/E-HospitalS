package ehospital.server.remote.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import cipher.RSASoftware;

import ehospital.server.Session;
import ehospital.server.Utility;

import remote.obj.DisconnHandler;

public class DisconnHandlerImpl extends UnicastRemoteObject implements DisconnHandler {

	public DisconnHandlerImpl() throws RemoteException {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 340556198638963444L;

	public boolean logout(String username, byte[] msgCipher) throws RemoteException {
		
		Session s = ehospital.server.SessionList.findClient(username);
		if (s == null) {
			return false;
		}
		RSASoftware rsa = new RSASoftware();
		rsa.setPublicKey(s.getExp(), s.getMod());
		byte[] decMsg = rsa.unsign(msgCipher, msgCipher.length);
		if (Utility.compareByte(decMsg, s.getLomsg())) {
			ehospital.server.SessionList.deleteSession(username);
			return true;
		}
		return false;
	}

}
