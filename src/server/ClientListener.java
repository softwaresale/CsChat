package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ClientListener implements Runnable {

	private ServerSocket serv;
	private BlockingQueue<Runnable> outgoingSocketListeners;
	private HashMap<User, Socket> connections;
	private ArrayBlockingQueue<Message> msgqueue;
	
	public ClientListener(ServerSocket serv, 
			BlockingQueue<Runnable> outgoingListeners, 
			HashMap<User, Socket> connections,
			ArrayBlockingQueue<Message> msgqueue) throws ServerException {
		this.serv = serv;
		if (!serv.isBound()) {
			throw new ServerException("ServerSocket is not bound");
		}
		this.connections = connections;
		this.outgoingSocketListeners = outgoingListeners;
		this.msgqueue = msgqueue;
	}

	@Override
	public void run() {
		while (!serv.isClosed() && serv.isBound()) {
			try {
				Socket sock = serv.accept();
				if (sock.isConnected()) {
					// Read user object
					User user = this.readUserFromSocket(sock);
					// Add connection to connections map
					this.connections.put(user, sock);
					// Add new listener to ougoingListeners
					this.outgoingSocketListeners.add(new MessageReciever(this.msgqueue, sock));
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public User readUserFromSocket(Socket sock) throws IOException, ClassNotFoundException {
		User user = null;
		ObjectInputStream istream = new ObjectInputStream(sock.getInputStream());
		user = (User) istream.readObject();
		return user;
	}
}
