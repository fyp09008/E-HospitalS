package ehospital.server.remote.impl;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.spec.SecretKeySpec;

import ehospital.server.Console;
import ehospital.server.Session;
import ehospital.server.db.DBManager;

import message.QueryRequestMessage;

import remote.obj.DataHandler;

public class DataHandlerImpl extends ehospital.server.handler.QueryHandler implements DataHandler {

	public DataHandlerImpl(QueryRequestMessage msg, SecretKeySpec sks) {
		super(msg, sks);
		// TODO Auto-generated constructor stub
	}

	public DataHandlerImpl() {
		super(null, null);
	}
	public byte[] insert(String username, byte[] insertStmt)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] query(String username, byte[] encQueryStmt)
			throws RemoteException {
		Session s = ehospital.server.SessionList.findClient(username);
		SecretKeySpec sks = s.getSessionKey();
		this.setSessionKeySpec(Console.ProgramKey);
		byte[] rawQuery = this.decryptAES(encQueryStmt);
		this.setSessionKeySpec(sks);
		rawQuery = this.decryptAES(encQueryStmt);
		this.dbm = new DBManager();
		dbm.connect();
		ResultSet rs;
		try {
			rs = dbm.query(new String(rawQuery));
			return this.objToBytes(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//dbman.disconnect();
		return null;
	}

	public byte[] update(String username, byte[] updateStmt)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
