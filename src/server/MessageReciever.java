package server;

import java.util.concurrent.ArrayBlockingQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/** Reads a message from a user and puts it in the message queue
 * 
 * This is used only by the server and should only be run in a threadpool.
 * @author charlie
 *
 */
public class MessageReciever implements Runnable {

	private ArrayBlockingQueue<Message> incoming;
	private Socket sock;
	
	/** Constructs a new reciever
	 * @param incoming Server message queue
	 * @param sock Socket to read data from
	 */
	public MessageReciever(ArrayBlockingQueue<Message> incoming, Socket sock) {
		this.incoming = incoming;
		this.sock = sock;
	}

	@Override
	public void run() {
		while (!sock.isClosed()) {
			try {
				ObjectInputStream istream = new ObjectInputStream(sock.getInputStream());
				Message incomingMsg = (Message) istream.readObject();
				incoming.add(incomingMsg);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				break;
			}
		}
	}

}
