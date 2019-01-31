package server;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class User implements Serializable {

	private String username = null;
	private String firstname = null;
	private String lastname = null;
	private InetAddress addr = null;
	
	public User() {
		username = "Unknown";
		firstname = "User";
		lastname = "Unknown";
		
		// Get localhost's inetaddr
		try {
			this.addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Handle this in a more meaningful way
			e.printStackTrace();
		}
	}

	public User(String username, String firstname, String lastname) {
		super();
		// Set info
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		
		// Get localhost's inet address
		try {
			this.addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Handle this in a more meaningful way
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User otherUser = (User) obj;
			return (this.username.equals(otherUser.getUsername()));
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return String.format("%s %s <%s> [%s]", 
				this.firstname, 
				this.lastname, 
				this.username, 
				this.addr.toString());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public InetAddress getAddr() {
		return addr;
	}

	public void setAddr(InetAddress addr) {
		this.addr = addr;
	}

}
