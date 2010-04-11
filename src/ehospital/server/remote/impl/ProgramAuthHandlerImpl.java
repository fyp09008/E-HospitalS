package ehospital.server.remote.impl;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import remote.obj.ProgramAuthHandler;
import cipher.ProKeyGen;

/**
 * This is the implementation of authenticating the client program. 
 * This method is to ensure the client program is an "authorized" version
 * that have proper data validation and other functionality.
 * @author Gilbert
 *
 */
public class ProgramAuthHandlerImpl extends UnicastRemoteObject 
									implements ProgramAuthHandler {

	/**
	 * Default Constructor, does nothing.
	 * @throws RemoteException
	 */
	public ProgramAuthHandlerImpl() throws RemoteException {
		super();
	}
	
	private static final long serialVersionUID = -1082838666030596768L;

	
	/* (non-Javadoc)
	 * @see remote.obj.ProgramAuthHandler#authProgram(byte[], byte[])
	 */
	public int authProgram(byte[] num, byte[] hashValue) throws RemoteException {
		try {
			ProKeyGen pkg = new ProKeyGen(num, "common.jar");
			if (ehospital.server.Utility.compareByte(pkg.getProgramKey().getEncoded(), hashValue)) {
				return 0;
			} else {
				return 1;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 2;
		}
		
	}


}
