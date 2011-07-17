/**
 * 
 */
package org.apache.tapestry.finder.exceptions;

/**
 * This is a custom {@link Exception} which will be used to indicate that a user
 * login has failed, either because the a user does not exist or an invalid
 * password was provided.
 * 
 * @author sminogue
 * 
 */
public class LoginException extends Exception {

	private static final long serialVersionUID = -7381475754462239325L;

	public LoginException() {

	}

	/**
	 * @param message
	 */
	public LoginException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public LoginException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LoginException(String message, Throwable cause) {
		super(message, cause);
	}

}
