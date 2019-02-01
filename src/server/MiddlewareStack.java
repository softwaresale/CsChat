package server;

import java.util.Stack;

/** Stack of middlware to be run
 * 
 * Middleware to be run is added to this stack. It is a FILO
 * data structure, so everything has to go through it. Push
 * and pop middleware.
 * @author charlie
 *
 */
public class MiddlewareStack extends Stack<AbstractMiddleware> {

	public MiddlewareStack() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/** Executes the stack on a message
	 * @param incoming Message to be modified
	 * @return Final message
	 */
	public Message execute(Message incoming) {
		Message outgoing = incoming;
		for (AbstractMiddleware mid : this)
			outgoing = mid.handle(outgoing);
		
		return outgoing;
	}
}
