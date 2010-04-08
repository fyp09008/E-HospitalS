package ehospital.server.remote.impl;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import cipher.ProKeyGen;

import remote.obj.ProgramAuthHandler;

public class ProgramAuthHandlerImpl extends UnicastRemoteObject implements ProgramAuthHandler {

	public ProgramAuthHandlerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1082838666030596768L;

	
	public int authProgram(byte[] num, byte[] hashValue) throws RemoteException {
		try {
			ProKeyGen pkg = new ProKeyGen(num, "common.jar");
//			System.out.println(new String(hashValue));
//			System.out.println(new String(pkg.getProgramKey().getEncoded()));
			if (ehospital.server.Utility.compareByte(pkg.getProgramKey().getEncoded(), hashValue)) {
				return 0;
			} else {
				return 1;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 32;
		}
		
	}


}
