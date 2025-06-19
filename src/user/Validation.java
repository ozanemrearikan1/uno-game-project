package user;
/**
 * @author ozanemrearikan
 */
public class Validation {

	// For validation of password
	/**
	 * The method takes password wanted to put as its parameter and validates using regex.
	 * Password in input must be longer than 8 and contain at least one digit, one letter and one special character.
	 * @param password
	 * @throws InvalidUserException
	 */
	public static void passwordValidation(String password) throws InvalidUserException {
		if (password == null) {
			throw new InvalidUserException("Please enter a valid password.");
		} else if (password.length() < 8) {
			throw new InvalidUserException("Password length must be longer than 8!");
		} else if (!password.matches(".*[a-zA-Z].*")) {
			throw new InvalidUserException("Password must contain at least one letter!");
		} else if (!password.matches(".*\\d.*")) {
			throw new InvalidUserException("Password must contain at leat one digit!");
		} else if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
			throw new InvalidUserException("Password must contain at least one special character!");
		}
	}

	// For validation of email
	/**
	 * The method takes email as its parameter and validates using regex.
	 * First part of regex controls digit, underscore and letter usage until "@"
	 * There must be "@" and after that, again for web site domain control.
	 * Input must be finished with ".com".
	 * If parameter does not match, it throws an InvalidUserException.
	 * @param email
	 * @throws InvalidUserException
	 */
	public static void emailValidation(String email) throws InvalidUserException {
		if (!email.matches("\\w+@\\w+\\.com")) {
			throw new InvalidUserException("Please enter a valid email.");
		}
	}

	// For validation of username
	/**
	 * The method takes username as its parameter and validates using regex.
	 * Username can contain only letter, numbers and underscore.
	 * If parameter does not match, it throws an InvalidUserException.
	 * @param username
	 * @throws InvalidUserException
	 */
	public static void usernameValidation(String username) throws InvalidUserException {
		if (!username.matches("\\w+")) {
			throw new InvalidUserException("Username must contain only letter and number.");
		}
	}

	// For validation of age, UNO game range is: 7+
	/**
	 * This validator controls if user is from 7 to 100 years old.
	 * 7 is minimum age for UNO
	 * If you are older than 100 years old, this game must be annoying and unhealthy for you :)
	 * @param age
	 * @throws InvalidUserException
	 */
	public static void ageValidation(int age) throws InvalidUserException {
		if (age < 7) {
			throw new InvalidUserException("You must be older than 7 to play UNO.");
		} else if (age > 100) {
			throw new InvalidUserException("Too old for this game dude!");
		}
	}

	// Name & surname might be any size.

}
