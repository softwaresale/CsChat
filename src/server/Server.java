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

public class Server {

	private ServerSocket serv;
	private ArrayBlockingQueue inqueue;
	private ArrayBlockingQueue outqueue;
	private HashMap<User, Socket> connections;
	
	public Server() throws IOException {
		// Bind server socket to random port
		serv = new ServerSocket(8888);
		// Construct outgoing and incoming message queues
		inqueue = new ArrayBlockingQueue<Message>(10);
		outqueue = new ArrayBlockingQueue<Message>(15);
		// Map of connections with Sockets
		connections = new HashMap<User, Socket>();
	}

	public ArrayList<Socket> getAddressedSockets(Message msg) {
		ArrayList<Socket> socks = new ArrayList<Socket>();
		for (Entry<User, Socket> conn : connections.entrySet()) {
			if (msg.getAddressees().contains(conn.getKey()))
				socks.add(conn.getValue());
		}
		return socks;
	}
}
