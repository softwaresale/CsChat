package middleware;

import java.util.ArrayList;
import java.util.HashMap;
import java.net.Socket;
import java.net.InetAddress;
import server.AbstractMiddleware;
import server.Message;
import server.User;
import server.ServerMetadata;

/** Middleware that adds server metadata to outgoing messages
 * @author charlie
 *
 */
public class MetadataAdder extends AbstractMiddleware {

	private HashMap<User, Socket> connections;
	private InetAddress servAddr;
	
	/** Adds server info to middleware
	 * @param connections Map of server connections
	 * @param servAddr Address of server
	 */
	public MetadataAdder(HashMap<User, Socket> connections, InetAddress servAddr) {
		this.connections = connections;
		this.servAddr = servAddr;
	}

	@Override
	public Message handle(Message incoming) {
		
		Message outgoing = incoming;
		ServerMetadata metadata = new ServerMetadata(new ArrayList<User>(this.connections.keySet()), servAddr);
		outgoing.setMetadata(metadata);
		return outgoing;
	}
}
