package server;

import java.util.ArrayList;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MessageDispatcher implements Runnable {

	private Message msg;
	private ArrayList<Socket> connections;
	private Integer sentCount;
	private boolean shouldCancel = false;
	
	public MessageDispatcher(Message msg, ArrayList<Socket> conns) {
		this.msg = msg;
		this.connections = conns;
		sentCount = 0;
	}

	@Override
	public void run() {
		for (Socket sock : connections) {
			if (shouldCancel) {
				return;
			}
			ObjectOutputStream ostream = null;
			try {
				ostream = new ObjectOutputStream(sock.getOutputStream());
				ostream.writeObject(msg);
			} catch (IOException e) {	
				//e.printStackTrace();
			} finally {
				sentCount++;
				try {
					ostream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
