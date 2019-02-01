package server;

/** Parent middleware class
 * 
 * All middleware extends this class. It is outgoing only.
 * @author charlie
 *
 */
public abstract class AbstractMiddleware {

	public AbstractMiddleware() {
	}

	/** This method is what the message goes through
	 * @param incoming Message to be modified
	 * @return Modified message
	 */
	public abstract Message handle(Message incoming);
}
