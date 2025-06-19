package game;

import java.util.ArrayList;
import java.util.List;
/**
 * @author ozanemrearikan
 */
public abstract class Player {

	Game game;
	List<Card> hand = new ArrayList<>();
	String name;
	
	/**
	 * Player and game classes are interrelated. To draw and play card effortlessly please indicate a game.
	 * @param name
	 * @param game
	 */
	public Player(String name, Game game) {
		this.game = game;
		this.name = name;
	}

	
	/**
	 * This method is for bots, human user will play the game from the playCard(Card card) method in "Game" class.
	 * Bots play first playable card at their hand. If they cannot find, they draw and control one more time.
	 * If they cannot find a playable card again, game is passed to next player.
	 * If they can play, card effects will be implied and current top card will be changed.   
	 */
	public void playCard() {
		// Bot will play first card which it can.
		for (Card card : getHand()) {
			if (game.isPlayable(card)) {
				hand.remove(getHand().indexOf(card));
				game.getDeck().sendToPile(card);
				game.setCurrentCardOnTop(card);
				game.implyCardAffects(card);
				break;
			}
		}
		if (hand.size()>0 && game.getCurrentPlayer().getName().equals(this.getName())) {
			hand.add(game.getDeck().drawCard());
			for (Card card : getHand()) {
				if (game.isPlayable(card)) {
					hand.remove(getHand().indexOf(card));
					game.getDeck().sendToPile(card);
					game.setCurrentCardOnTop(card);
					game.implyCardAffects(card);
					break;
				}
			}
		}
		if(game.getCurrentPlayer().getName().equals(this.getName())){
			game.toNextPlayer();
		}

	}

	
	/**
	 * Draw card method for bots to draw a card from deck.
	 * The method adds this card to hand of bot.
	 * @param deck
	 */
	public void drawCard(Deck deck) {
		Card card = game.getDeck().drawCard();
		if (card != null) {
			hand.add(card);
		}

	}


	// It will help at the hand to finish game and calculate total scores:
	/**
	 * The method includes switch statement which controls each card at the hand of player.
	 * Score is increased by each cards's point indicated as below.
	 * Wild cards are 50 points, action cards are 20 points and number cards are the value of theirs. 
	 * @return int score // Total calculated
	 */
	public int calculateHandScore() {
		int score = 0;
		for (Card card : hand) {
			switch(card.getValour()) {
			case Zero: 
				score+= 0;
				break;
			case One: 
				score+= 1;
				break;
			case Two: 
				score+= 2;
				break;
			case Three: 
				score+= 3;
				break;
			case Four: 
				score+= 4;
				break;
			case Five: 
				score+= 5;
				break;
			case Six: 
				score+= 6;
				break;
			case Seven: 
				score+= 7;
				break;
			case Eight: 
				score+= 8;
				break;
			case Nine: 
				score+= 9;
				break;
			case DrawTwo:
			case Skip:
			case Reverse:
				score+= 20;
				break;
			case Wild:
			case WildFour:
				score+= 50;
				break;
			default:
				score+= 0;
				break; // To not face with any problem
			}
		}
		return score;
	}
	
	// Getters & Setters
	public String getName() {
		return name;
	}
	
	public List<Card> getHand() {
		return hand;
	}
	
	public void setHand(List<Card> hand) {
		this.hand = hand;
	}


}
