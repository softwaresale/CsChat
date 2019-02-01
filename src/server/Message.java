package server;

import java.io.Serializable;
import java.util.ArrayList;

/** Models a message
 * 
 * All transmissons sent are through Messages. Peer to
 * peer messages as well as server-specfic commands are
 * sent via messages. Each message has a payload, sender,
 * and addressees. THe payload is whatever data is being
 * sent. The Sender is a user that is composing the message.
 * The addressees are users that will recieve the message.
 * @author charlie
 */
public class Message implements Serializable {

	/** Describes the actual data being sent
	 * This may need to be converted into bytes if the payload
	 * becomes something other than string
	 */
	private String payload;
	
	/** The user that sent the message
	 * Messages are addressed from one sender and are sent
	 * to multiple recipients
	 */
	private User sender;
	
	/** List of users that should recieve the message
	 * List of users to recieve this message.
	 */
	private ArrayList<User> addressees;
	
	private ServerMetadata metadata;
	
	/** Creates an empty message
	 * This has blank information and is sent to nobody.
	 */
	public Message() {
		payload = "";
		sender = null;
		addressees = new ArrayList<User>();
	}

	public Message(String payload, User sender, ArrayList<User> addressees) {
		super();
		this.payload = payload;
		this.sender = sender;
		this.addressees = addressees;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Message) {
			Message otherMsg = (Message) obj;
			return (this.payload.equals(otherMsg.getPayload()));
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return String.format("%s: %s", this.sender.getUsername(), this.payload);
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public ArrayList<User> getAddressees() {
		return addressees;
	}

	public void setAddressees(ArrayList<User> addressees) {
		this.addressees = addressees;
	}

	public ServerMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(ServerMetadata metadata) {
		this.metadata = metadata;
	}
}
