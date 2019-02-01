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

public class Server {

	private ServerSocket serv;
	private ArrayBlockingQueue<Message> msgqueue;
	private HashMap<User, Socket> connections;
	private Thread outThread;
	private Thread listenerThread;
	private OutQueueMonitor outQueueMonitor;
	private ThreadPoolExecutor messageListener;
	private BlockingQueue<Runnable> incomingSocketListeners;
	
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
		outQueueMonitor = new OutQueueMonitor(msgqueue, connections);
		outThread = new Thread(outQueueMonitor);
	}
	
	public void start() {
		listenerThread.start();
		// Execute outqueue monitor
		outThread.start();
	}
	
	public void stop() {
		try {
			outThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
