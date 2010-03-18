package ehospital.server.remote.impl;

import java.rmi.RemoteException;

import ehospital.server.Session;

import remote.obj.DisconnHandler;

public class DisconnHandlerImpl extends ehospital.server.handler.DisconnHandler implements DisconnHandler {

	public boolean logout(String username, byte[] msgCipher) throws RemoteException {
		
		Session s = ehospital.server.SessionList.findClient(username);
		if (s == null) {
			return false;
		}
		this.loadCryptoInfo(username);
		byte[] decMsg = this.getRsa().unsign(msgCipher, msgCipher.length);
		if (this.compareByte(decMsg, s.getLomsg())) {
			ehospital.server.SessionList.deleteSession(username);
			return true;
		}
		return false;
	}

}
