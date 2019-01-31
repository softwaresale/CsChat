package server;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MessageDispatcher implements Callable<Integer> {

	// Message to send
	private Message message;
	private ArrayList<Socket> recipients;
	
	public MessageDispatcher(Message msg, ArrayList<Socket> socks) {
		this.message = msg;
		this.recipients = socks;
	}

	@Override
	public Integer call() throws IOException {
		int sent = 0;
		for (Socket sock : recipients) {
			ObjectOutputStream ostream = new ObjectOutputStream(sock.getOutputStream());
			ostream.writeObject(message);
			sent++;
		}
		return sent;
	}

}
