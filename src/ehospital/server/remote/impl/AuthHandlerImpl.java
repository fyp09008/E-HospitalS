package ehospital.server.remote.impl;

import java.rmi.RemoteException;
import java.sql.SQLException;

import ehospital.server.Session;
import ehospital.server.db.DBManager;

public class AuthHandlerImpl extends ehospital.server.handler.AuthHandler implements remote.obj.AuthHandler{

	public AuthHandlerImpl(String username, String mdPwd, DBManager dbm) {
		super(username, mdPwd, dbm);
		
	}

	public AuthHandlerImpl() {
		super(null,null,new DBManager());
	}

	public byte[] authenticate(String username, byte[] HEPwd)
			throws RemoteException {
		this.dbm = new DBManager();
		this.loadCryptoInfo(username);
		if (dbm.isUserExist(username)) {
			try {
				String pwdFromDB = dbm.getRs().getString(2);
				String pwdReceived = this.byteArrayToString(HEPwd);
				if (pwdFromDB.equals(pwdReceived)) {
					this.genSessionKey();
					byte[] s = this.getSessionKeySpec().getEncoded();
					//add session
					Session session = new Session(username, getSessionKeySpec());
					ehospital.server.SessionList.clientList.add(session);
					
					return this.getRsa().encrypt(s, s.length);
				} 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
