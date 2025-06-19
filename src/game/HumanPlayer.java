package game;

import user.User;
/**
 * @author ozanemrearikan
 */
public class HumanPlayer extends Player {
	
	private User user; // HumanPlayer of course is a User. From login page to this page, user info will be transferred.
	// Human player is user thus unlike other players, this player uses user parameter in their constructor.
	public HumanPlayer(User user, Game game) {
		super(user.getUsername(), game);
		this.user = user;
	}
	
	// Getters & Setters
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	// If used for human player, throws an exception.
	@Override
	public void playCard() {
		throw new UnsupportedOperationException("Method not supported");
	}
	
	

}
