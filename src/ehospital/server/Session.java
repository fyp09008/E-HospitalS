package ehospital.server;

import java.util.TimerTask;

public class Session extends TimerTask {
	private ClientThread ct;
	public Session(ClientThread ct) {
		this.ct = ct;
	}

	@Override
	public void run() {
		ServerThread.RemoveThread(ct);
		
	}
	
}
