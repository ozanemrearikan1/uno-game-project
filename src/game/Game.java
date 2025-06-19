package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import game.Card.Couleur;
import game.Card.Valeur;
import user.User;
/**
 * 
 * @author ozanemrearikan
 */
public class Game {
	
	/************** Pledge of Honor ******************************************
	I hereby certify that I have completed this programming project on my own
	without any help from anyone else. The effort in the project thus belongs
	completely to me. I did not search for a solution, or I did not consult any
	program written by others or did not copy any program from other sources. I
	read and followed the guidelines provided in the project description.
	READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
	SIGNATURE: Ozan Emre ARIKAN, 83993
	*************************************************************************/
	
	private List<Player> players;
	private Player currentPlayer;
	private Deck deck = new Deck();
	public Card currentCardOnTop;
	private boolean isClockwise = true; // True means that the game is clockwise.
	private boolean declarationDUno = false; // When player clicked on GameFrame, it will become true!
	
	/**
	 * Constructor takes user which is human and numOfPlayers to create bots.
	 * @param user
	 * @param numOfPlayers
	 */
	public Game(User user, int numOfPlayers) {
		this.players = new ArrayList<>();
		players.add(new HumanPlayer(user, this));
		for (int i = 1; i <= numOfPlayers; i++) {
			String botName = "Bot" + i;
			players.add(new AIPlayer(botName, this));
		}
		getDeck().createDeck();
		giveCards();
		getDeck().sendToPile(getDeck().drawCard());
		currentCardOnTop = getDeck().getPile().get(getDeck().getPile().size()-1);
		this.currentPlayer = players.get(0); // We've added human user and bots, game starts with human user. 
	}
	
	

	// Since I start with Player classes, let's finish this "isPlayable" method first!
	public boolean isPlayable(Card card) {
		if (card.getColour() == Couleur.Wild || currentCardOnTop.getColour() == Couleur.Wild) {
			return true;
		}
		return card.getColour().equals(currentCardOnTop.getColour())  || card.getValour().equals(currentCardOnTop.getValour());
	}
	
	// Continue with giving cards to players:
	private void giveCards() {
		for (Player player : players) {
			for (int i = 0; i < 7; i++) {
				player.drawCard(deck);
			}
		}
	}
	
	// So far, we've prepared cards, player classes, users & given cards to players.
	
	// I have decided that from this point, I will continue with preparing GameFrame!
	
	// Register/Login pages --> MainFrame --> Cards --> Deck --> Players --> Game, completed!

	
	// Turn back to game logic:
	public void toNextPlayer() {
		int current = players.indexOf(currentPlayer);
		int next = (current + (isClockwise ? 1 : -1)) % players.size();
		if (next < 0) {
			next += players.size(); 
		}
		setCurrentPlayer(players.get(next));
	}
	
	public void implyCardAffects(Card card) {
		if (card.getValour().equals(Valeur.Skip)) {
			toNextPlayer();
			toNextPlayer();
		} else if (card.getValour().equals(Valeur.Reverse)) {
			isClockwise = !isClockwise;
			toNextPlayer();
		} else if (card.getValour().equals(Valeur.DrawTwo)) {
			toNextPlayer();
			currentPlayer.drawCard(deck);
			currentPlayer.drawCard(deck);
		} else if (card.getValour().equals(Valeur.Wild)) {
			toNextPlayer();
		} else if (card.getValour().equals(Valeur.WildFour)) {
			toNextPlayer();
			currentPlayer.drawCard(deck);
			currentPlayer.drawCard(deck);
			currentPlayer.drawCard(deck);
			currentPlayer.drawCard(deck);
		}else {
			toNextPlayer();
		}
	}
	
	// playCard() method is one of last parts; most challenging and thought provoking part of the project:
	public void playCard(Card card, Player player) {
		if (currentPlayer instanceof HumanPlayer && currentPlayer.getHand().size() == 2 && declarationDUno == false) {
			currentPlayer.drawCard(deck);
			currentPlayer.drawCard(deck);
		}
	    currentPlayer.hand.remove(currentPlayer.getHand().indexOf(card));
	    deck.sendToPile(card);
	    currentCardOnTop = card;
	    implyCardAffects(card);
	}
	
	public Player getPreviousPlayer() {
		int current = players.indexOf(currentPlayer);
		int previous = (current - (isClockwise ? 1 : -1)) % players.size();
		if (previous < 0) {
			previous += players.size(); 
		}
		return getPlayers().get(previous);
	}
	
	public Player getTwoPreviousPlayer() {
		int current = players.indexOf(currentPlayer);
		int previous = (current - (isClockwise ? 2 : -2)) % players.size();
		if (previous < 0) {
			previous += players.size(); 
		}
		return getPlayers().get(previous);
	}
	
	public void declarerLUno() {
		declarationDUno = true;
	}
	
	// Now, before finishing the project, let's add save and load methods of game!
	public void saveGame(String saveName) throws IOException {
		try (PrintWriter writer = new PrintWriter(new FileWriter(saveName))) {
			writer.println(isClockwise);
			writer.println(declarationDUno);
			writer.println(players.indexOf(getCurrentPlayer()));
			
			for (Card card : deck.getCards()) {
                writer.println(card);
            }
            writer.println("---Deck---");
            
            for (Card card : deck.getPile()) {
                writer.println(card);
            }
            writer.println("---Pile---");
			
            for (Player player : players) {
                writer.println(player.getName());
                for (Card card : player.getHand()) {
                    writer.println(card);
                }
                writer.println("---Player---");
            }
		}
	}
	
	public static Game loadGame(String saveName, User user) throws IOException{
		String[] parts = saveName.split("_");
		String numOfTotalPlayers = parts[1];
		int numOfPlayers = Integer.valueOf(numOfTotalPlayers) - 1;
		Game game = new Game(user, numOfPlayers);
		
		try (BufferedReader reader = new BufferedReader(new FileReader(saveName))) {
			game.setClockwise(Boolean.parseBoolean(reader.readLine()));
			game.setDeclarationDUno(Boolean.parseBoolean(reader.readLine()));
			
			int currentPlayerIndex = Integer.parseInt(reader.readLine());
			
			LinkedList<Card> loadedDeck = new LinkedList<>();
	        String line;
	        while (!(line = reader.readLine()).equals("---Deck---")) {
	            loadedDeck.add(Card.convertToCard(line));
	        }
	        game.getDeck().setCards(loadedDeck);

	        LinkedList<Card> loadedPile = new LinkedList<>();
	        while (!(line = reader.readLine()).equals("---Pile---")) {
	            loadedPile.add(Card.convertToCard(line));
	        }
	        game.getDeck().setPile(loadedPile);
	        game.setCurrentCardOnTop(game.getDeck().getPile().getLast());
            
	        game.players.clear(); // Clear existing players before loading
	        for (int i = 0; i <= numOfPlayers; i++) {
	            String playerName = reader.readLine();
	            Player player;
	            if (i == 0) {
	                player = new HumanPlayer(user, game);
	            } else {
	                player = new AIPlayer(playerName, game);
	            }
	            List<Card> hand = new ArrayList<>();
	            while (!(line = reader.readLine()).equals("---Player---")) {
	                hand.add(Card.convertToCard(line));
	            }
	            player.setHand(hand);
	            game.players.add(player);
	        }
	        game.currentPlayer = game.players.get(currentPlayerIndex);
            
		}
		return game;
	}
	
	
	// Getters & Setters
	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public boolean isClockwise() {
		return isClockwise;
	}

	public void setClockwise(boolean isClockwise) {
		this.isClockwise = isClockwise;
	}

	public Card getCurrentCardOnTop() {
		return currentCardOnTop;
	}

	public void setCurrentCardOnTop(Card currentCardOnTop) {
		this.currentCardOnTop = currentCardOnTop;
	}



	public boolean isDeclarationDUno() {
		return declarationDUno;
	}



	public void setDeclarationDUno(boolean declarationDUno) {
		this.declarationDUno = declarationDUno;
	}
	
	
	
	

}
