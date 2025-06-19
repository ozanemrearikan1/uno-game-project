package game;

import java.util.Collections;
import java.util.LinkedList;
/**
 * @author ozanemrearikan
 */
public class Deck {
	
	private LinkedList<Card> cards;
	private LinkedList<Card> pile;
	
	public Deck() {
		this.cards = new LinkedList<>();
		this.pile = new LinkedList<>();
	}
	
	/**
	 * This method will create deck for each color of each action cards and each value of number cards.
	 *  After wild cards are created. (8 cards) 
	 *  The cards in deck are shuffled at the end.
	 */
	protected void createDeck() {
		// Card will be added to deck accordingly to their colours except wilds which will be added at the end:
		for (Card.Couleur colour : Card.Couleur.values()) {
			if (colour != Card.Couleur.Wild) {
				cards.add(new Card(colour, Card.Valeur.Zero));
				// 1 zero for each colour and 2 other numbers:
				for (int i = 1; i < 10; i++) {
					Card.Valeur valour = Card.Valeur.values()[i];
					cards.add(new Card(colour, valour));
					cards.add(new Card(colour, valour));
				}
				// Action cards, of course
				cards.add(new Card(colour, Card.Valeur.DrawTwo));
				cards.add(new Card(colour, Card.Valeur.DrawTwo));
				cards.add(new Card(colour, Card.Valeur.Reverse));
				cards.add(new Card(colour, Card.Valeur.Reverse));
				cards.add(new Card(colour, Card.Valeur.Skip));
				cards.add(new Card(colour, Card.Valeur.Skip));
			}
		}
		// Wild cards added:
		cards.add(new Card(Card.Couleur.Wild, Card.Valeur.Wild));
		cards.add(new Card(Card.Couleur.Wild, Card.Valeur.Wild));
		cards.add(new Card(Card.Couleur.Wild, Card.Valeur.Wild));
		cards.add(new Card(Card.Couleur.Wild, Card.Valeur.Wild));
		cards.add(new Card(Card.Couleur.Wild, Card.Valeur.WildFour));
		cards.add(new Card(Card.Couleur.Wild, Card.Valeur.WildFour));
		cards.add(new Card(Card.Couleur.Wild, Card.Valeur.WildFour));
		cards.add(new Card(Card.Couleur.Wild, Card.Valeur.WildFour));
		
		shuffleDeck();
	}
	
	/**
	 * The method to user shuffle Collection method which takes cards "LinkedList" as its parameter.
	 */
	private void shuffleDeck() {
		// Thanks to Collections methods:
		Collections.shuffle(cards);
	}
	
	/**
	 * If deck is not empty, removeLast() "LinkedList" method removes last element of "LinkedList" and returns this last.
	 * This method will be connected to player draw card method afterwards.
	 * @return Card card
	 */
	public Card drawCard() {
		return cards.isEmpty() ? null : cards.removeLast();
	}
	
	/**
	 * The method takes the card discarded and send it to pile.
	 * Pile and deck operations are interrelated.
	 * @param card
	 */
	public void sendToPile(Card card) {
        pile.add(card);
    }
	
	
	// Getters & Setters
	public LinkedList<Card> getCards() {
		return cards;
	}

	public void setCards(LinkedList<Card> cards) {
		this.cards = cards;
	}

	public LinkedList<Card> getPile() {
		return pile;
	}

	public void setPile(LinkedList<Card> pile) {
		this.pile = pile;
	}
	
	
	
}
