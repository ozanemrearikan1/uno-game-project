package frames;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;

import user.User;
/**
 * @author ozanemrearikan
 */
public class MainFrame extends JFrame {

	private static User currentUser;

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame(currentUser);
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
	public MainFrame(User currentUser) {
		
		// Stats creating at first
		appendToStats();
		
		setTitle("Main Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1366, 768);
		getContentPane().setLayout(new BorderLayout(0, 0));


		// For a good game aesthetic, I add a background:
		PanelForBackground backgroundPanel = new PanelForBackground();
		setContentPane(backgroundPanel);
		backgroundPanel.setLayout(new BorderLayout(0, 0));

		// Header and logos of game and my so-called game studio:
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setOpaque(false);
		JLabel unoLogo = new JLabel(resizeIcon("src/images/UNO-GAME-6-05-2024.png", 300, 100));
		JLabel oeaLogo = new JLabel(resizeIcon("src/images/OEA-Games-6-05-2024.png", 300, 100));
		headerPanel.add(unoLogo, BorderLayout.WEST);
		headerPanel.add(oeaLogo, BorderLayout.EAST);
		backgroundPanel.add(headerPanel, BorderLayout.NORTH);

		// Time to prepare center menu:
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));  
		centerPanel.setOpaque(false);

		// Welcome label I've added to user to feel its presence :) in game:
		JLabel welcomeLabel = new JLabel("Welcome " + currentUser.getUsername(), SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(welcomeLabel);

		// We need spacing
		centerPanel.add(Box.createVerticalStrut(20));

		// Game buttons here:
		JButton newGameButton = new JButton("New Game");
		newGameButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(newGameButton);
		newGameButton.addActionListener(e -> openPlayerNumberPage(currentUser));

		JButton loadGameButton = new JButton("Load Game");
		loadGameButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(loadGameButton);
		loadGameButton.addActionListener(e -> loadGamePage(currentUser));

		

		backgroundPanel.add(centerPanel, BorderLayout.CENTER);
		
		JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Padding around the edges
        containerPanel.setOpaque(false);


		// Leaderboard is needed for the project thus:
		JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BoxLayout(leaderboardPanel, BoxLayout.Y_AXIS));
        leaderboardPanel.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(leaderboardPanel);
        scrollPane.setPreferredSize(new Dimension(800, 150));
        containerPanel.add(scrollPane, BorderLayout.CENTER);

        JLabel leaderboardLabel = new JLabel("Leaderboard", SwingConstants.CENTER);
        leaderboardLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        leaderboardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardPanel.add(leaderboardLabel);
        
        loadLeaderboard(currentUser, leaderboardPanel);
        backgroundPanel.add(containerPanel, BorderLayout.SOUTH);
        
	}

	// For easily resizing icons
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

	// After long-time we turn back here to add GameFrame access! Yuppi!
	// But now NumberOfBotsFrame acces time :)
	private void openPlayerNumberPage(User user) {
		this.dispose();
		NumberOfBotsFrame playerNumberPage = new NumberOfBotsFrame(user);
		playerNumberPage.setVisible(true);
	}

	// Getters & Setters
	public User getCurrentUser() {
		return currentUser;
	}

	private void loadGamePage(User user) {
		this.dispose();
		LoadGameFrame loadGameFrame = new LoadGameFrame(user);
		loadGameFrame.setVisible(true);
	}

	

	// I will focus on statistics of user, let's do this!
	
	/**
	 * The method appends new user's statistics if there is a new registered people in users text file.
	 * If there is no stats.txt, the method will create.
	 * New user will be detected with adding all user registered in "HashSet". 
	 * And all users registered but not found in the "HashSet", will be added to new "LinkedList" 
	 * At the end, the "LinkedList" will be appended.
	 */
	// This is to control and create a new stats.txt:
	private void appendToStats() {
		// Stats.txt text file will be appended and if there is not it will be created!
		File stats = new File("src/user/stats.txt");
		try {
			if (!stats.exists()) {
				stats.createNewFile();
			}
			
			Set<String> users = new HashSet<>();
			try (BufferedReader reader = new BufferedReader(new FileReader(stats))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    users.add(parts[0]);
                }
            }
			
			// This will control users.txt if there is new users
			File usersFile = new File("src/user/users.txt");
			List<String> usersJustRegistered = new LinkedList<>();
			try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                	String[] parts = line.split(";");
                    String username = parts[0];
                    if (!users.contains(username)) {
                        usersJustRegistered.add(username + ",0,0,0,0");
                    }
                }
            }
			
			// This will append new users
			if (!usersJustRegistered.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(stats, true))) {
                    for (String newUser : usersJustRegistered) {
                        writer.write(newUser);
                        writer.newLine();
                    }
                }
            }
			
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The method takes user and panel parameters and loads leader board accordingly at bottom.
	 * Statistics text file is read and sorting Collection method is used.
	 * Collection takes StatsComparator() which can be found in same package.
	 * Also UserStats class facilitates data manipulations and the method adds buttons for each registered human user!
	 * @param user
	 * @param leaderboardPanel
	 */
	private void loadLeaderboard(User user, JPanel leaderboardPanel) {
        List<UserStats> userStatsList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/user/stats.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                int totalScore = Integer.parseInt(parts[4]);
                userStatsList.add(new UserStats(username, totalScore));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(userStatsList, new StatsComparator());

        for (UserStats userStats : userStatsList) {
            JButton userButton = new JButton(userStats.getUsername() + " - Total Score: " + userStats.getTotalScore());
            userButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            userButton.addActionListener(e -> openStats(user,userStats.getUsername()));
            leaderboardPanel.add(userButton);
        }
    }
	
	/**
	 * The method takes user to return back to MainFrame (main menu) with user information 
	 * and takes "String userName" for searched statistics of user specifically.
	 * The method opens statistics of wanted user. 
	 * @param User user
	 * @param String userName
	 */
	private void openStats(User user, String userName) {
		this.dispose();
		StatsOfUserFrame statsUser = new StatsOfUserFrame(user, userName);
		statsUser.setVisible(true);
	}
	
	


}
