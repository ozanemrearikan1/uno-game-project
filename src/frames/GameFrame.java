package frames;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import game.AIPlayer;
import game.Card;
import game.Card.Couleur;
import game.Game;
import game.HumanPlayer;
import game.LogGame;
import game.Player;
import user.User;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
/**
 * @author ozanemrearikan
 */
public class GameFrame extends JFrame {
	
	/************** Pledge of Honor ******************************************
	I hereby certify that I have completed this programming project on my own
	without any help from anyone else. The effort in the project thus belongs
	completely to me. I did not search for a solution, or I did not consult any
	program written by others or did not copy any program from other sources. I
	read and followed the guidelines provided in the project description.
	READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
	SIGNATURE: Ozan Emre ARIKAN, 83993
	*************************************************************************/

	// Labels:
	JLabel topCardLabel;
	JLabel deckCountLabel;
	JLabel pileCountLabel;
	JLabel turnLabel;
	JLabel directionLabel;
	JLabel sessionNameLabel;
	JPanel bottomPanel;
	JPanel leftPanel;
	JPanel centralPanel;
	JTextArea logArea;

	private static User currentUser;

	private static Game game;
	private boolean isItPaused = false;
	private boolean didBotSaidUno = false;
	private Timer timer;
	private LogGame log;

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameFrame frame = new GameFrame(game, currentUser);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	/**
	 * GameFrame takes game and user as its parameters. 
	 * @param game
	 * @param currentUser
	 */
	public GameFrame(Game game, User currentUser) {
		GameFrame.game = game;

		setTitle("UNO® Game by OEA Games");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);

		// Log area located at top:
		logArea = new JTextArea(5, 20);  
		logArea.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(logArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JPanel logPanel = new JPanel(new BorderLayout());
		logPanel.add(logScrollPane, BorderLayout.CENTER);
		logPanel.setPreferredSize(new Dimension(400, 100)); 
		logPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(logPanel, BorderLayout.NORTH); 

		log = new LogGame("src/game/loggings.txt", logArea);
		log.logMessage("-----NEW GAME-----");
		log.logMessage("Please do not forget to declare UNO");
		log.logMessage("before discarding your second to last card!");
		log.logMessage("Enjoy!");
		

		// Left Panel
		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		// Player names printed at left
		for (Player player : game.getPlayers()) {
			JLabel playerLabel = new JLabel(player.getName() + " - Cards: " + player.getHand().size());
			leftPanel.add(playerLabel);
		}

		// Turn also shown like that, clockwise means from up to bottom:
		turnLabel = new JLabel("Turn: " + game.getCurrentPlayer().getName());
		directionLabel = new JLabel("Direction: " + (game.isClockwise() ? "Clockwise" : "Counterclockwise"));
		sessionNameLabel = new JLabel("Session Name: " + game.getPlayers().get(0).getName() + " & friends");
		leftPanel.add(turnLabel);
		leftPanel.add(directionLabel);
		leftPanel.add(sessionNameLabel);

		add(leftPanel, BorderLayout.WEST);

		// Right Panel
		JPanel rightPanel = new JPanel(new GridLayout(7, 1));
		JButton btnForMainMenu = new JButton("Back to Main Menu");
		JButton btnResume = new JButton("Resume");
		JButton btnPause = new JButton("Pause");
		JButton btnSave = new JButton("Save Game");
		JButton btnToDrawCard = new JButton("Draw Card");
		JButton btnDeclarationDUno = new JButton("UNO!");
		JButton btnPointOutBots = new JButton("Point Out!");

		rightPanel.add(btnForMainMenu);
		rightPanel.add(btnPause);
		rightPanel.add(btnResume);
		rightPanel.add(btnSave);
		rightPanel.add(btnToDrawCard);
		rightPanel.add(btnDeclarationDUno);
		rightPanel.add(btnPointOutBots);

		// Button action listeners for right panel:
		btnToDrawCard.addActionListener(e -> {
			if (game.getCurrentPlayer() instanceof HumanPlayer) {
				Card card = game.getDeck().drawCard();
				if (card != null) {
					game.getCurrentPlayer().getHand().add(card);
					logNow(game.getCurrentPlayer().getName() + " draws " + card.toString());
					if (game.getPreviousPlayer().getHand().size() == 0) {
						logNow(game.getPreviousPlayer().getName() + " has won the game! " + game.getPreviousPlayer().getName() + " is champion!");
					}
					refreshScreen();

				} else {
					JOptionPane.showMessageDialog(this, "There is not any card in the deck!", "Empty Deck", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Now, it is not your turn!", "Please Wait!", JOptionPane.WARNING_MESSAGE);
			}
		});

		// Pause buttons
		btnPause.addActionListener(e -> {
			isItPaused = true;
			if (timer != null) {
				timer.stop();  
			}
		});

		btnResume.addActionListener(e -> {
			isItPaused = false;
			if (game.getCurrentPlayer() instanceof AIPlayer) {
				aiBotsTurn();  
			}
		});

		btnForMainMenu.addActionListener(e -> {
			this.dispose();
			MainFrame mainFrame = new MainFrame(currentUser);
			mainFrame.setVisible(true);
		});
		
		// Declaration:
		btnDeclarationDUno.addActionListener(e -> {
            if (game.getCurrentPlayer() instanceof HumanPlayer && game.getCurrentPlayer().getHand().size() == 2) {
                game.declarerLUno();
                logNow(game.getCurrentPlayer().getName() + " declares UNO!");
            } else {
                JOptionPane.showMessageDialog(this, "You cannot declare UNO now!", "Please Wait!", JOptionPane.WARNING_MESSAGE);
            }
        });
		
		// Save game button finally! 
		btnSave.addActionListener(e -> {
            try {
            	String fileDirect = nextSaveName();
                game.saveGame(fileDirect);
                logNow("You saved the game successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                logNow("Failed to save the game!");
            }
        });
		
		btnPointOutBots.addActionListener(e -> {
			if (game.getPreviousPlayer().getHand().size() == 1 && didBotSaidUno == false) {
				game.getPreviousPlayer().drawCard(game.getDeck());
				game.getPreviousPlayer().drawCard(game.getDeck());
			}else if (game.getPreviousPlayer().getHand().size() == 1){
				JOptionPane.showMessageDialog(this, "Previous player declared UNO!", "Invalid Move", JOptionPane.WARNING_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(this, "Invalid Move! There is no situation to declare UNO.", "Invalid Move!", JOptionPane.WARNING_MESSAGE);
			}
		});

		add(rightPanel, BorderLayout.EAST);

		// Human user's cards at bottom panel:
		bottomPanel = new JPanel(new FlowLayout());
		Player humanPlayer = game.getPlayers().get(0);  // First player at list always human one.
		
		// Now we need to add image path of cards, also cards at center will be added after this stage:
		for (Card card : humanPlayer.getHand()) {
			JButton cardButton = new JButton(resizeIcon(card.getImageDirection(), 60, 93));
			bottomPanel.add(cardButton);

			cardButton.addActionListener(e -> {
				if (game.getCurrentPlayer() == humanPlayer && game.isPlayable(card)) {
					if (card.getValour() == Card.Valeur.Wild || card.getValour() == Card.Valeur.WildFour) {
						Couleur colour = selectionPopUp();
                        card.setColour(colour);
		                game.setCurrentCardOnTop(card);
		                logNow(game.getCurrentPlayer().getName() + " changed game color to " + colour + "!");
					}
					logNow(game.getCurrentPlayer().getName() + " played " + card.toString());
					game.playCard(card, humanPlayer);

					if (game.getCurrentCardOnTop().toString().contains("DrawTwo")) {
						logNow(game.getCurrentPlayer().getName() + " draws two cards!");
					}

					if (game.getCurrentCardOnTop().toString().contains("Skip")) {
						logNow(game.getPreviousPlayer().getName() + " is skipped!");
					}

					if (game.getCurrentCardOnTop().toString().contains("Reverse")) {
						logNow(game.getPreviousPlayer().getName() + " changed the game direction!");
					}

					if (game.getCurrentCardOnTop().toString().contains("-Wild") && !(game.getCurrentCardOnTop().toString().contains("-WildFour"))) {
						logNow(game.getPreviousPlayer().getName() + " played a wild card!");
					}
					
					if (game.getCurrentCardOnTop().toString().contains("-WildFour")) {
						logNow(game.getCurrentPlayer().getName() + " draws four cards!");
						
					}

					if (game.getPreviousPlayer().getHand().size() == 1) {
						logNow(game.getPreviousPlayer().getName() + " declares UNO!");
					}


					refreshScreen();
					aiBotsTurn();
				} else {
					JOptionPane.showMessageDialog(this, "This is an invalid move!", "Invalid Move", JOptionPane.ERROR_MESSAGE);
					if (game.getCurrentPlayer() == humanPlayer) {
						logNow(game.getCurrentPlayer().getName() + " cannot play " + card.toString());
					}
				}
			});
		}

		add(bottomPanel, BorderLayout.SOUTH);


		// At center we'll have our pile with currentCardOnTop
		// And pile with remaining card numbers and ofc icons!
		centralPanel = new JPanel();
		centralPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20)); 
		Card currentTopCard = game.getCurrentCardOnTop();
		ImageIcon topCardIcon = resizeIcon(currentTopCard.getImageDirection(), 100, 140);
		topCardLabel = new JLabel(topCardIcon);
		pileCountLabel = new JLabel("In pile, there are " + game.getDeck().getPile().size() + " cards", SwingConstants.CENTER);
		pileCountLabel.setHorizontalTextPosition(SwingConstants.CENTER); 
		pileCountLabel.setVerticalTextPosition(SwingConstants.BOTTOM); 
		centralPanel.add(topCardLabel);
		centralPanel.add(pileCountLabel);

		// Label for the deck count with the icon:
		ImageIcon deckIcon = resizeIcon("cardimages/unocards/card_back.png", 100, 140); 
		deckCountLabel = new JLabel("Deck: " + game.getDeck().getCards().size() + " cards left", SwingConstants.CENTER);
		deckCountLabel.setIcon(deckIcon);
		deckCountLabel.setHorizontalTextPosition(SwingConstants.CENTER); 
		deckCountLabel.setVerticalTextPosition(SwingConstants.BOTTOM); 
		centralPanel.add(deckCountLabel);

		add(centralPanel, BorderLayout.CENTER);





	}

	// We've used in MainFrame, it will help here as well!
	/**
	 * This method is used for resizing icons which is really useful for card and logo images.
	 * It takes path and uses BufferedImage and ImageIO classes as well as getScaledInstance() method of java.awt.Image class.
	 * The method creates and returns scaled version of the image as ImageIcon 
	 * @param path
	 * @param width
	 * @param height
	 * @return ImageIcon
	 */
	private ImageIcon resizeIcon(String path, int width, int height) {
		try {
			BufferedImage originalImage = ImageIO.read(new File(path));
			Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			return new ImageIcon(resizedImage);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method, which is of vital importance in the functioning of the game, first clears the middle.
	 * It then adds the cards from the pile and deck back in their renewed form, along with their remaining numbers.
	 * Revalidation of method ensures proper recalculation of layout and repaint of screen is important for refreshing screen.
	 * The method is affiliated to refreshScreen() method for its functionality.
	 */
	private void miseAJourCentralPanel() {
		centralPanel.removeAll();
		centralPanel = new JPanel();
		centralPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20)); 
		Card currentTopCard = game.getCurrentCardOnTop();
		ImageIcon topCardIcon = resizeIcon(currentTopCard.getImageDirection(), 100, 140);
		topCardLabel = new JLabel(topCardIcon);
		pileCountLabel = new JLabel("In pile, there are " + game.getDeck().getPile().size() + " cards", SwingConstants.CENTER);
		pileCountLabel.setHorizontalTextPosition(SwingConstants.CENTER); 
		pileCountLabel.setVerticalTextPosition(SwingConstants.BOTTOM); 
		centralPanel.add(topCardLabel);
		centralPanel.add(pileCountLabel);

		// Label for the deck count with the icon:
		ImageIcon deckIcon = resizeIcon("cardimages/unocards/card_back.png", 100, 140); 
		deckCountLabel = new JLabel("Deck: " + game.getDeck().getCards().size() + " cards left", SwingConstants.CENTER);
		deckCountLabel.setIcon(deckIcon);
		deckCountLabel.setHorizontalTextPosition(SwingConstants.CENTER); 
		deckCountLabel.setVerticalTextPosition(SwingConstants.BOTTOM); 
		centralPanel.add(deckCountLabel);

		add(centralPanel, BorderLayout.CENTER);

		centralPanel.revalidate();
		centralPanel.repaint();
	}
	
	/**
	 * This method, which is of vital importance in the functioning of the game, first clears the left panel.
	 * Later, renew turn and direction labels and add session name again.
	 * Revalidation of method ensures proper recalculation of layout and repaint of screen is important for refreshing screen.
	 * The method is affiliated to refreshScreen() method for its functionality.
	 */
	private void updateTurnLabel() {
		leftPanel.removeAll();
		turnLabel.setText("Turn: " + game.getCurrentPlayer().getName());
		directionLabel.setText("Direction: " + (game.isClockwise() ? "Clockwise" : "Counterclockwise"));
		sessionNameLabel.setText("Session Name: " + game.getPlayers().get(0).getName() + " & friends");

		for (Player player : game.getPlayers()) {
			JLabel playerLabel = new JLabel(player.getName() + " - Cards: " + player.getHand().size());
			leftPanel.add(playerLabel);
		}
		leftPanel.add(turnLabel);
		leftPanel.add(directionLabel);
		leftPanel.add(sessionNameLabel);

		leftPanel.revalidate(); 
		leftPanel.repaint();
	}
	
	/**
	 * This method, which is of vital importance in the functioning of the game, refresh bottom panel after every discard of card by human user.
	 * Revalidation of method ensures proper recalculation of layout and repaint of screen is important for refreshing screen.
	 * The method is affiliated to refreshScreen() method for its functionality.
	 * This method includes log scenarios for all possible situations in the game as possible.
	 * Moreover, the method control "instanceof" situations for proper game progress. 
	 * If a card is not playable, JOptionPane opens. The method controls it. 
	 */
	private void updateHandOfUser() {
		Player humanPlayer = game.getPlayers().get(0);
		bottomPanel.removeAll();
		for (Card card : humanPlayer.getHand()) {
			JButton cardButton = new JButton(resizeIcon(card.getImageDirection(), 60, 93));
			bottomPanel.add(cardButton);
			// Adding here as well:
			cardButton.addActionListener(e -> {
				if (game.getCurrentPlayer() == humanPlayer && game.isPlayable(card)) {
					logNow(game.getCurrentPlayer().getName() + " played " + card.toString());
					if (card.getValour() == Card.Valeur.Wild || card.getValour() == Card.Valeur.WildFour) {
						Couleur colour = selectionPopUp();
                        card.setColour(colour);
                        game.setCurrentCardOnTop(card);
                        logNow(game.getCurrentPlayer().getName() + " changed game color to " + colour + "!");
					}
					game.playCard(card, humanPlayer);
					refreshScreen();
					if (game.getPreviousPlayer().getHand().size() == 0) {
						logNow(game.getPreviousPlayer().getName() + " has won the game! " + game.getPreviousPlayer().getName() + " is champion!");
						log.closeLog();
						timer.stop();
						if (game.getPreviousPlayer() instanceof HumanPlayer) {
							additionDesPoints(true);
						}else {
							additionDesPoints(false);
						}
					}

					if (game.getCurrentCardOnTop().toString().contains("DrawTwo")) {
						logNow(game.getCurrentPlayer().getName() + " draws two cards!");
					}

					if (game.getCurrentCardOnTop().toString().contains("Skip")) {
						logNow(game.getPreviousPlayer().getName() + " is skipped!");
					}

					if (game.getCurrentCardOnTop().toString().contains("Reverse")) {
						logNow(game.getPreviousPlayer().getName() + " changed the game direction!");
					}

					if (game.getCurrentCardOnTop().toString().contains("-Wild") && !(game.getCurrentCardOnTop().toString().contains("-WildFour"))) {
						logNow(game.getPreviousPlayer().getName() + " played a wild card!");
					}
					
					if (game.getCurrentCardOnTop().toString().contains("-WildFour")) {
						logNow(game.getCurrentPlayer().getName() + " draws four cards!");
					}

					if (game.getPreviousPlayer().getHand().size() == 1) {
						logNow(game.getPreviousPlayer().getName() + " declares UNO!");
					}

					if (game.getDeck().getCards().size() == 0) {
						logNow("There is not any card in the deck thus game is over. Now, let's calculate the scores:");
						determinerGagnantParPoints();
						log.closeLog();
						timer.stop();
					}

					if (game.getCurrentPlayer() instanceof AIPlayer && game.getPreviousPlayer().getHand().size() != 0) {
						aiBotsTurn();
					}
				} else {
					JOptionPane.showMessageDialog(this, "You cannot play this card!", "Invalid Move", JOptionPane.ERROR_MESSAGE);
					if (game.getCurrentPlayer() == humanPlayer) {
						logNow(game.getCurrentPlayer().getName() + " cannot play " + card.toString());
					}
				}
			});
		}

		bottomPanel.revalidate(); 
		bottomPanel.repaint();
	}
	
	/** 
	 * The method refresh the screen with provided methods in it. 
	 * The method must be runned on EDT, Event Dispatch Thread since this is a turn based game.
	 * The method includes a log which alerts human user.
	 */
	public void refreshScreen() {
		SwingUtilities.invokeLater(() -> {
			updateHandOfUser(); 
			updateTurnLabel(); 
			miseAJourCentralPanel(); 

			if (game.getCurrentPlayer() instanceof HumanPlayer && !(game.getPreviousPlayer().getHand().size()==0)) {
				logNow("Now, it is " + game.getCurrentPlayer().getName() + "'s turn!");
			}

		});


	}
	
	/**
	 * The method starts with timer, which is Java Swing property
	 * It has vital importance since bots must have delay to make the human user feel a real gaming experience.
	 * I put 1.3 second delay for bots and all logs for possible game situations have been put.
	 * If player is human, timer stops and human user takes their turn.
	 * If game is finished, calculations will be made and statistics will be renewed.
	 */
	private void aiBotsTurn() {
		if (timer != null) {
			timer.stop();  
		}
		timer = new Timer(1300, event -> {
			if (game.getCurrentPlayer() instanceof AIPlayer && !isItPaused) {
				game.getCurrentPlayer().playCard();
				if (game.getCurrentCardOnTop().toString().contains("Skip")) {
					logNow(game.getTwoPreviousPlayer().getName() + " played " + game.getCurrentCardOnTop().toString());
					refreshScreen();
				}else {
					logNow(game.getPreviousPlayer().getName() + " played " + game.getCurrentCardOnTop().toString());
					refreshScreen();
				}
				

				if (game.getPreviousPlayer().getHand().size() == 0) {
					logNow(game.getPreviousPlayer().getName() + " has won the game! " + game.getPreviousPlayer().getName() + " is champion!");
					log.closeLog();
					timer.stop();
					if (game.getPreviousPlayer() instanceof HumanPlayer) {
						additionDesPoints(true);
					}else {
						additionDesPoints(false);
					}
				}


				if (game.getCurrentCardOnTop().toString().contains("DrawTwo")) {
					logNow(game.getCurrentPlayer().getName() + " draws two cards!");
				}

				if (game.getCurrentCardOnTop().toString().contains("Skip")) {
					logNow(game.getPreviousPlayer().getName() + " is skipped!");
				}

				if (game.getCurrentCardOnTop().toString().contains("Reverse")) {
					logNow(game.getPreviousPlayer().getName() + " changed the game direction!");
					refreshScreen();
				}

				if (game.getCurrentCardOnTop().toString().contains("-Wild") && !(game.getCurrentCardOnTop().toString().contains("-WildFour"))) {
					Couleur nouvelleCouleur = chooseColor();
					game.currentCardOnTop.setColour(nouvelleCouleur);
					logNow(game.getPreviousPlayer().getName() + " changed game color to " + nouvelleCouleur.toString().toLowerCase() + "!");
				}
				
				if (game.getCurrentCardOnTop().toString().contains("-WildFour")) {
					Couleur nouvelleCouleur = chooseColor();
					game.currentCardOnTop.setColour(nouvelleCouleur);
					logNow(game.getPreviousPlayer().getName() + " changed game color to " + nouvelleCouleur.toString().toLowerCase() + "!");
					logNow(game.getCurrentPlayer().getName() + " draws four cards!");
				}

				if (game.getPreviousPlayer().getHand().size() == 1 && !(game.getPreviousPlayer() instanceof AIPlayer)) {
					logNow(game.getPreviousPlayer().getName() + " declares UNO!");
					didBotSaidUno = true;
				}
				
				if (game.getDeck().getCards().size() == 0) {
					logNow("There is not any card in the deck thus game is over. Now, let's calculate the scores:");
					determinerGagnantParPoints();
					log.closeLog();
					timer.stop();
				}

				if (!(game.getCurrentPlayer() instanceof AIPlayer)) {
					timer.stop();
				}
			} else {
				timer.stop();
			}
		});
		timer.setRepeats(true);
		timer.start();
	}

	/**
	 * The method helps game to log every action both in text and in log area.
	 * @param message
	 */
	private void logNow(String message) {
		log.logMessage(message);
		logArea.setCaretPosition(logArea.getDocument().getLength());
	}

	// Needed to have a function for wild card colour choice:
	/**
	 * Random choice of colour between UNO card colors if Bots discard wild card.
	 * @return a color between yellow, green, red, and blue in Card.Couleur form.
	 */
	private Couleur chooseColor() {
		Couleur[] colors = Couleur.values();
		SecureRandom random = new SecureRandom();
		int index = random.nextInt(colors.length - 1);
		return colors[index];
	}
	
	/**
	 * Now, a method for if human discards a wild card.
	 * @return a color between yellow, green, red, and blue in Card.Couleur form.
	 */
	private Couleur selectionPopUp() {
		Couleur[] allColors = Couleur.values();
        Couleur[] options = new Couleur[allColors.length - 1];
        int index = 0;
        for (Couleur color : allColors) {
            if (color != Couleur.Wild) {
                options[index++] = color;
            }
        }
        int choice = JOptionPane.showOptionDialog(this, "What color do you want to change for game?", "Color Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        return options[choice];
	}
	
	/** 
	 * This method creates a save which include number of players in game to facilitate loading game,
	 * if there is a save with same name, both will be there and saved.
	 * @return String savename
	 */
	private String nextSaveName() {
	    int i = 1;
	    while (new File("saves/" + game.getPlayers().get(0).getName() + "_" + game.getPlayers().size() + "_" + i + ".txt").exists()) {
	        i++;
	    }
	    return "saves/" + game.getPlayers().get(0).getName() + "_" + game.getPlayers().size() + "_" + i + ".txt";
	}
	
	// Now, we need a method for end of game statistics updates
	/**
	 * The method first opens stats.txt and creates empty list.
	 * The list includes lines which will be added at the end in text file.
	 * First, method finds human user logged in and changes the scores accordingly to human user's winning status of game.
	 * BufferedReader and FileReader search in text file with split method.
	 * Outputs are updated played games, wins & losses, and total score.
	 * Method contains a writer as well as reader.
	 * @param didHumanWin // update stats accordingly to this boolean
	 * @param totalScore // total score of one game
	 */
	private void updateStats(boolean didHumanWin, int totalScore) {
		File stats = new File("src/user/stats.txt");
		List<String> newStats = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(stats))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts[0].equals(game.getPlayers().get(0).getName())) {
					int gamesPlayed = Integer.parseInt(parts[1]) + 1;
					int wins = Integer.parseInt(parts[2]) + (didHumanWin ? 1 : 0);
					int losses = Integer.parseInt(parts[3]) + (didHumanWin ? 0 : 1);
					int totalScoreAccumulated = Integer.parseInt(parts[4]) + totalScore;

					newStats.add(game.getPlayers().get(0).getName() + "," + gamesPlayed + "," + wins + "," + losses + "," + totalScoreAccumulated);
				}else {
					newStats.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Complete update of text file to prevent from any problem:
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(stats))) {
			for (String newStat : newStats) {
				writer.write(newStat);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * Controls whether human user did win and if yes, all total score in the game will be added to human user.
	 * Total score is all remaining cards at hand of other players.
	 * Recalls updatestats() method at the end.
	 * @param didHumanWin
	 */
	// At the end there is a calculation; if human player wins, total score will be added to human user's stats:
	private void additionDesPoints(boolean didHumanWin) {
		int totalScore = 0;
		if (didHumanWin) {
			for (Player player : game.getPlayers()) {
				totalScore += player.calculateHandScore();
			}
		}
		updateStats(didHumanWin, totalScore);
	}
	
	/**
	 * This method is needed when the deck is finished when any player did not finish their hand.
	 * The method finds player which has lowest score at their hand.
	 * The lowest card point indicates the winner.
	 * If this player is human one, additionDesPoints() is recalled with boolean true as parameter.
	 */
	// I do not prefer to shuffle the pile again into deck thus we have to calculate and determine winner by,
	// Finding the player which has minimum points of card at hand and takes all the points on table!
	private void determinerGagnantParPoints() {
        Player winner = null;
        int temp = 3333333; // Mersin 4ever :)

        for (Player player : game.getPlayers()) {
            int score = player.calculateHandScore();
            if (score < temp) {
                temp = score;
                winner = player;
            }
        }

        if (winner != null) {
            logNow(winner.getName() + " has won the game with the lowest score: " + temp + "!");
            boolean didHumanWin = winner instanceof HumanPlayer;
            additionDesPoints(didHumanWin);
        }
    }



}
