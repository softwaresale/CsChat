package server;

import java.util.ArrayList;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectOutputStream;

/** Sends messages
 * 
 * Runnable task that takes in a message and a list of sockets
 * and sends it to each. This is implemented in the outgoing message
 * queue monitor. It is meant to be run inside of a threadpool.
 * @author charlie
 *
 */
public class MessageDispatcher implements Runnable {

	private Message msg;
	private ArrayList<Socket> connections;
	private Integer sentCount;
	private boolean shouldCancel = false;
	
	/** Creates a new dispatcher
	 * @param msg Message to be sent
	 * @param conns List of sockets associated with each addressee
	 */
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
