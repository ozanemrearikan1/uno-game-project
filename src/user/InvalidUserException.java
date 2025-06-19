package user;
/**
 * @author ozanemrearikan
 * using extension of java "Exception"
 */
public class InvalidUserException extends Exception {

	// For might-be problems during registration
	public InvalidUserException(String lemessage) {
		super(lemessage);
	}

}
