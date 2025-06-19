package frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import game.Game;
import user.User;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.GridLayout;
/**
 * @author ozanemrearikan
 */
public class NumberOfBotsFrame extends JFrame {

	private static User currentUser;

	private JComboBox<String> comboBox;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NumberOfBotsFrame frame = new NumberOfBotsFrame(currentUser);
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
	 * This constructor takes user information to make specified game experience.
	 * This user info will be transferred to other frames related.
	 * @param currentUser
	 */
	public NumberOfBotsFrame(User currentUser) {
		setResizable(false);
		setTitle("New Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 2, 50, 100));

		JLabel numberLabel = new JLabel("Select number of players:");
		numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(numberLabel);

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		contentPane.add(comboBox);

		JButton btnNewGame = new JButton("Create New Game");
		btnNewGame.addActionListener(e -> startGame(currentUser));
		contentPane.add(btnNewGame);

		JButton btnBackToMenu = new JButton("Back to Main Menu");
		btnBackToMenu.addActionListener(e -> turnBackToMenu(currentUser));
		contentPane.add(btnBackToMenu);
	}

	private void startGame(User user) {
		// Total players include human user. So bots are -=1 of it.
		int totalPlayers = Integer.parseInt((String) comboBox.getSelectedItem());
		int numberOfBots = totalPlayers - 1; 
		Game game = new Game(user, numberOfBots);  
		GameFrame gameFrame = new GameFrame(game, user);  
		gameFrame.setVisible(true);  
		dispose(); 
	}

	// We need to turn back to main menu maybe:
	private void turnBackToMenu(User user) {
		this.dispose();
        MainFrame mainFrame = new MainFrame(user);
        mainFrame.setVisible(true);
		
	}

}
