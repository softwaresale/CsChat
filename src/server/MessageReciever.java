package server;

import java.util.concurrent.ArrayBlockingQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class MessageReciever implements Runnable {

	private ArrayBlockingQueue<Message> incoming;
	private Socket sock;
	
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
