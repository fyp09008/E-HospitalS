package ehospital.server;

import java.util.Vector;
/*
 * A Singleton Class to store session
 * Use Vector to provide synchronized
 */
public class SessionList {
	
	public static Vector<Session> clientList = new Vector<Session>();
	
	public static Session findClient(String username) {
		for (int i = 0; i < clientList.size(); i++) {
			if (clientList.get(i).getUsername().equals(username)) {
				return clientList.get(i);
			}
		}
		return null;
	}
	
	public static void deleteSession(String username) {
		for (int i = 0; i < clientList.size(); i++) {
			if (clientList.get(i).getUsername().equals(username)) {
				clientList.remove(i);
				return;
			}
		}
	}
}
