package frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import game.Game;
import user.User;
/**
 * @author ozanemrearikan
 */
public class LoadGameFrame extends JFrame {

	private static User currentUser;

	private static final long serialVersionUID = 1L;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoadGameFrame frame = new LoadGameFrame(currentUser);
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
	public LoadGameFrame(User currentUser) {
		
		setTitle("Load Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 768);
		getContentPane().setLayout(new BorderLayout(0, 0));


		// For a good game aesthetic, I add a background here too:
		PanelForBackground backgroundPanel = new PanelForBackground();
		setContentPane(backgroundPanel);
		backgroundPanel.setLayout(new BorderLayout(0, 0));

		
		// Time to prepare center menu:
		JPanel centerPanel = new JPanel();
	    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));  
	    centerPanel.setOpaque(false);

	    File folder = new File("saves/");
        File[] listOfFiles = folder.listFiles((dir, name) -> name.startsWith(currentUser.getUsername()) && name.endsWith(".txt"));

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                JButton loadButton = new JButton(file.getName());
                loadButton.setAlignmentX(Box.CENTER_ALIGNMENT);
                loadButton.addActionListener(e -> loadGame("saves/" + file.getName(), currentUser));
                centerPanel.add(Box.createVerticalStrut(10));
                centerPanel.add(loadButton);
            }
        }
        
	    
	    backgroundPanel.add(centerPanel, BorderLayout.CENTER);
        
        
        // Background Panel must be seen thus I have added with this way:
	    JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> backToMainFrame(currentUser));
        backgroundPanel.add(backButton, BorderLayout.SOUTH);
	
	}
	
	private void loadGame(String path, User user) {
		try {
			Game loadedGame = Game.loadGame(path, user); 
			this.dispose();
			GameFrame gameFrame = new GameFrame(loadedGame, user);
			gameFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void backToMainFrame(User user) {
        this.dispose();
        MainFrame mainFrame = new MainFrame(user);
        mainFrame.setVisible(true);
    }



}
