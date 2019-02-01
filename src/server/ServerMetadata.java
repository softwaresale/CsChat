package server;

import java.util.ArrayList;
import java.net.InetAddress;

public class ServerMetadata {
	
	private ArrayList<User> connectedUsers;
	private InetAddress servAddr;
	
	public ServerMetadata() {
		// TODO Auto-generated constructor stub
	}

	public ServerMetadata(ArrayList<User> connectedUsers, InetAddress servAddr) {
		this.connectedUsers = connectedUsers;
		this.servAddr = servAddr;
	}

	public ArrayList<User> getConnectedUsers() {
		return connectedUsers;
	}

	public void setConnectedUsers(ArrayList<User> connectedUsers) {
		this.connectedUsers = connectedUsers;
	}

	public InetAddress getServAddr() {
		return servAddr;
	}

	public void setServAddr(InetAddress servAddr) {
		this.servAddr = servAddr;
	}

}
