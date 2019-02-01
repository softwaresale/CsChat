package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/** Listends for incoming clients
 * 
 * This class listens for incoming clients and adds them
 * to the server. As each is added, a new entry is added
 * in the connections table in the Server class and a new
 * message listener is added to the message listener pool.
 * @author charlie
 */
public class ClientListener implements Runnable {

	private ServerSocket serv;
	private BlockingQueue<Runnable> outgoingSocketListeners;
	private HashMap<User, Socket> connections;
	private ArrayBlockingQueue<Message> msgqueue;
	
	/** Creates a new client listener.
	 * 
	 * This constructor should not be used outside of the server.
	 *  
	 * @param serv Socket server to accept clients from
	 * @param outgoingListeners Queue taking socket listeners to thread pool
	 * @param connections Map of server connections
	 * @param msgqueue Server message queue (messages to be sent out)
	 * @throws ServerException
	 */
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

	/** Reads a user from the first transmission over a connection
	 * 
	 * This method is called when a socket is first accepted. The first message
	 * sent over the socket is the user info.
	 * 
	 * @param sock Socket to read from
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private User readUserFromSocket(Socket sock) throws IOException, ClassNotFoundException {
		User user = null;
		ObjectInputStream istream = new ObjectInputStream(sock.getInputStream());
		user = (User) istream.readObject();
		return user;
	}
}
