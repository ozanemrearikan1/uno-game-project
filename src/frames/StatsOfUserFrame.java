package frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import user.User;
/**
 * @author ozanemrearikan
 */
public class StatsOfUserFrame extends JFrame {

	private static User currentUser;
	private static String playerUserName;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StatsOfUserFrame frame = new StatsOfUserFrame(currentUser, playerUserName);
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
	 * This constructor takes user information and user username wanted to observe.
	 * This user info will be transferred to other frames related.
	 * This username string will be used in searching in text file.
	 * @param User currentUser
	 * @param String playerUserName
	 */
	public StatsOfUserFrame(User currentUser, String playerUserName) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Add background panel
		PanelForBackground backgroundPanel = new PanelForBackground();
		backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
		contentPane.add(backgroundPanel, BorderLayout.CENTER);
		
		backgroundPanel.add(Box.createVerticalStrut(100));

		JLabel usernameLabel = new JLabel("Username: " + playerUserName);
		usernameLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		usernameLabel.setAlignmentX(CENTER_ALIGNMENT);
		backgroundPanel.add(usernameLabel);

		JLabel gamesPlayedLabel = new JLabel("Total Games Played: ");
		gamesPlayedLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		gamesPlayedLabel.setAlignmentX(CENTER_ALIGNMENT);
		backgroundPanel.add(gamesPlayedLabel);

		JLabel winsLabel = new JLabel("Number of Wins: ");
		winsLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		winsLabel.setAlignmentX(CENTER_ALIGNMENT);
		backgroundPanel.add(winsLabel);

		JLabel lossesLabel = new JLabel("Number of Losses: ");
		lossesLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lossesLabel.setAlignmentX(CENTER_ALIGNMENT);
		backgroundPanel.add(lossesLabel);

		JLabel totalScoreLabel = new JLabel("Total Score: ");
		totalScoreLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		totalScoreLabel.setAlignmentX(CENTER_ALIGNMENT);
		backgroundPanel.add(totalScoreLabel);

		JLabel averageScoreLabel = new JLabel("Average Score: ");
		averageScoreLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		averageScoreLabel.setAlignmentX(CENTER_ALIGNMENT);
		backgroundPanel.add(averageScoreLabel);
		
		JLabel winLossRatioLabel = new JLabel("Win/Loss Ratio: ");
		winLossRatioLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		winLossRatioLabel.setAlignmentX(CENTER_ALIGNMENT);
		backgroundPanel.add(winLossRatioLabel);

		String statsFilePath = "src/user/stats.txt";
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(statsFilePath)))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith(playerUserName)) {
					String[] stats = line.split(",");
					gamesPlayedLabel.setText("Total Games Played: " + stats[1]);
					winsLabel.setText("Number of Wins: " + stats[2]);
					lossesLabel.setText("Number of Losses: " + stats[3]);
					totalScoreLabel.setText("Total Score: " + stats[4]);
					double averageScore = (Integer.parseInt(stats[1]) == 0 ? 0 : Double.parseDouble(stats[4]) / Integer.parseInt(stats[1]));
					averageScoreLabel.setText(String.format(Locale.US,"Average Score: %.2f", averageScore));
					double winLossRatio = (Integer.parseInt(stats[1]) == 0 ? 0 : Double.parseDouble(stats[2]) / Integer.parseInt(stats[3]));
					winLossRatioLabel.setText(String.format(Locale.US,"Win/Loss Ratio: %.2f", winLossRatio));
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		backgroundPanel.add(Box.createVerticalStrut(20));
		
		JButton backButton = new JButton("Back to Main Menu");
		backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> backToMainFrame(currentUser));
        backgroundPanel.add(backButton, BorderLayout.SOUTH);
	}
	
	private void backToMainFrame(User user) {
        this.dispose();
        MainFrame mainFrame = new MainFrame(user);
        mainFrame.setVisible(true);
    }


}
