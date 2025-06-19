package frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import user.User;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
/**
 * @author ozanemrearikan
 */
public class RegistrationLoginFrame extends JFrame {
	
	/************** Pledge of Honor ******************************************
	I hereby certify that I have completed this programming project on my own
	without any help from anyone else. The effort in the project thus belongs
	completely to me. I did not search for a solution, or I did not consult any
	program written by others or did not copy any program from other sources. I
	read and followed the guidelines provided in the project description.
	READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
	SIGNATURE: Ozan Emre ARIKAN, 83993
	*************************************************************************/

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrationLoginFrame frame = new RegistrationLoginFrame();
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
	public RegistrationLoginFrame() {
		setTitle("OEA Games");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 200, 40, 200, 0 };
		gbl_contentPane.rowHeights = new int[] { 60, 40, 60, 40, 60, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblNewLabel_1 = new JLabel("Username:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Password:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.fill = GridBagConstraints.BOTH;
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 2;
		contentPane.add(passwordField, gbc_passwordField);

		
		JButton btnRegister = new JButton("Register");
		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
		gbc_btnRegister.fill = GridBagConstraints.BOTH;
		gbc_btnRegister.insets = new Insets(0, 0, 0, 5);
		gbc_btnRegister.gridx = 0;
		gbc_btnRegister.gridy = 4;
		contentPane.add(btnRegister, gbc_btnRegister);

		JButton btnNewButton_1 = new JButton("Login");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 4;
		contentPane.add(btnNewButton_1, gbc_btnNewButton_1);

		btnRegister.addActionListener(new RegistrationListener());
		btnNewButton_1.addActionListener(new LoginButtonListener());

	}

	// After creation of registration frame, beginning with login page action
	private class RegistrationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose(); // For closing this frame
			EventQueue.invokeLater(() -> {
				RegistratoryFrame registrationFrame = new RegistratoryFrame();
				registrationFrame.setVisible(true); // To open the register frame
			});
		}
	}

	// Login button must work too
	private class LoginButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Path directoryPath = Paths.get("src/user");
			File directory = directoryPath.toFile();
			if (!directory.exists()) {
				directory.mkdirs(); // Will create directory if does not exist
			}

			String username = textField.getText();
			String password = new String(passwordField.getPassword());
			
			try {
				if (lAuthentification(username, password)) {
					if (username != null || password != null) {
						User user = getUser(username);
						MainFrame mainMenu = new MainFrame(user);
						dispose();
						mainMenu.setVisible(true);
					}
				}
			}catch (IOException e1) {
				e1.printStackTrace();
			}
		

	}}


	// Needed to control match between username and password
	/**
	 * This method controls if username and password match and it returns a boolean value.
	 * @param username
	 * @param password
	 * @return true/false
	 * @throws IOException
	 */
	private boolean lAuthentification(String username, String password) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/user/users.txt"));
		for (String line : lines) {
			String[] part = line.split(";");
			if (part[0].trim().equals(username) && part[1].trim().equals(password)) {
				return true;
			}
		}
		return false;
	}
	
	// Now there is a need for a method to get user data from .txt file
	/**
	 * This method gets entered user name from text file.
	 * @param username
	 * @return user
	 * @throws IOException
	 */
	private User getUser(String username) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/user/users.txt"));
		for (String line : lines) {
			String[] part = line.split(";");
			if (part[0].trim().equals(username)) {
				String userName = part[0].trim();
				String password = part[1].trim();
				String name = part[2].trim();
				String surName = part[3].trim();
				String email = part[4].trim();
				int age = Integer.parseInt(part[5].trim());
				
				User user = new User(name, surName, userName, age, email, password);
				return user;
			}
		}
		return null;
	}
	

}

