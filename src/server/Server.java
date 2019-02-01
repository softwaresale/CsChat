package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/** The actual server class
 * 
 * This class is really more of a wrapper of server functionality.
 * It should never be instanced outside of its main class entry
 * point.
 * @author charlie
 */
public class Server {

	private ServerSocket serv;
	private ArrayBlockingQueue<Message> msgqueue;
	private HashMap<User, Socket> connections;
	private Thread outThread;
	private Thread listenerThread;
	private ThreadPoolExecutor messageListener;
	private BlockingQueue<Runnable> incomingSocketListeners;
	
	/** Sets up all server threads and data structures.
	 * 
	 * Note: the constructor does not initiate any functionality.
	 * It merely sets everything up.
	 * @throws IOException
	 */
	public Server() throws IOException {
		// Bind server socket to random port
		serv = new ServerSocket(8888);
		// Construct outgoing and incoming message queues
		msgqueue = new ArrayBlockingQueue<Message>(15);
		// Construct queue of incoming socket listeners
		incomingSocketListeners = new ArrayBlockingQueue<Runnable>(10);
		// Map of connections with Sockets
		connections = new HashMap<User, Socket>();
		
		// Create socket listener thread
		listenerThread = new Thread(new ClientListener(serv, this.incomingSocketListeners, connections, msgqueue));
		
		// Create message listener executor
		messageListener = new ThreadPoolExecutor(15, 25, 100, null, incomingSocketListeners);
		
		// Create monitors
		outThread = new Thread(new OutQueueMonitor(msgqueue, connections));
	}
	
	/** Starts the server
	 * This starts the many server threads.
	 */
	public void start() {
		listenerThread.start();
		outThread.start();
	}
	
	/** Stops the server
	 * 
	 * This is not very graceful, so be warned. It will take some
	 * workshopping I assume.
	 */
	public void stop() {
		try {
			outThread.join();
			listenerThread.join();
			messageListener.shutdown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
