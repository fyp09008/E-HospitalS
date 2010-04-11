package ehospital.server;

import java.util.Vector;
/*
 * A Singleton Class to store session
 * Use Vector to provide synchronized
 * @author Gilbert
 */
public class SessionList {
	
	public static Vector<Session> clientList = new Vector<Session>();
	
	/**
	 * Find a client from the session
	 * @param username
	 * @return a client with username as user name
	 */
	public static Session findClient(String username) {
		for (int i = 0; i < clientList.size(); i++) {
			if (clientList.get(i).getUsername().equals(username)) {
				return clientList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * remove a session of a client
	 * @param username
	 */
	public static void deleteSession(String username) {
		for (int i = 0; i < clientList.size(); i++) {
			if (clientList.get(i).getUsername().equals(username)) {
				clientList.remove(i);
				return;
			}
		}
	}
}
