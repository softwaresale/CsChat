package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/** Monitors the message queue
 * 
 * This class monitors the server message queue and sends
 * outgoing messages via message dispatchers. This is meant
 * to be used only in the server.
 * @author charlie
 *
 */
public class OutQueueMonitor implements Runnable {

	private ArrayBlockingQueue<Message> outgoing;
	private HashMap<User, Socket> connections;
	private boolean keepGoing;
	private ThreadPoolExecutor threadPool;
	private BlockingQueue<Runnable> dispatchQueue;
	
	private OutQueueMonitor() {
	}
	
	/** Constructs a new monitor
	 * @param outgoing Server message queue
	 * @param conns Map of server connections
	 */
	public OutQueueMonitor(ArrayBlockingQueue<Message> outgoing, HashMap<User, Socket> conns) {
		this.outgoing = outgoing;
		this.connections = conns;
		keepGoing = true;
		this.dispatchQueue = new ArrayBlockingQueue<Runnable>(15);
		this.threadPool = new ThreadPoolExecutor(10, 15, 100, null, this.dispatchQueue);
	}

	@Override
	public void run() {
		while (keepGoing) {
			if (!outgoing.isEmpty()) {
				try {
					Message nextMessage = outgoing.take();
					ArrayList<Socket> recipients = this.getAddressedSockets(nextMessage);
					this.dispatchQueue.add(new MessageDispatcher(nextMessage, recipients));
				} catch (InterruptedException e) {
					keepGoing = false;
					e.printStackTrace();
				}
			}
		}
	}
	

	/** Gets sockets associated with addressees
	 * 
	 * Gets a list of sockets to send a message to
	 * @param msg Message to get addressees from
	 * @return ArrayList<Socket> List of sockets
	 */
	public ArrayList<Socket> getAddressedSockets(Message msg) {
		ArrayList<Socket> socks = new ArrayList<Socket>();
		for (Entry<User, Socket> conn : connections.entrySet()) {
			if (msg.getAddressees().contains(conn.getKey()))
				socks.add(conn.getValue());
		}
		return socks;
	}
}
