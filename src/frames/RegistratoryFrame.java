package frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import user.InvalidUserException;
import user.Validation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
/**
 * @author ozanemrearikan
 */
public class RegistratoryFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField regName;
	private JTextField regSName;
	private JTextField regAge;
	private JTextField regEmail;
	private JTextField regUName;
	private JButton btnToInLog;
	private JButton btnRegistratory;
	private JPasswordField passwordField;
	private JLabel statusLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistratoryFrame frame = new RegistratoryFrame();
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
	public RegistratoryFrame() {
		setTitle("Register Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 309);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 210, 210, 0 };
		gbl_contentPane.rowHeights = new int[] { 34, 34, 34, 34, 34, 34, 34, 34, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblNewLabel_1 = new JLabel("Name:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);

		regName = new JTextField();
		GridBagConstraints gbc_regName = new GridBagConstraints();
		gbc_regName.fill = GridBagConstraints.BOTH;
		gbc_regName.insets = new Insets(0, 0, 5, 0);
		gbc_regName.gridx = 1;
		gbc_regName.gridy = 0;
		contentPane.add(regName, gbc_regName);
		regName.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Surname:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);

		regSName = new JTextField();
		GridBagConstraints gbc_regSName = new GridBagConstraints();
		gbc_regSName.fill = GridBagConstraints.BOTH;
		gbc_regSName.insets = new Insets(0, 0, 5, 0);
		gbc_regSName.gridx = 1;
		gbc_regSName.gridy = 1;
		contentPane.add(regSName, gbc_regSName);
		regSName.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Age:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 2;
		contentPane.add(lblNewLabel_3, gbc_lblNewLabel_3);

		regAge = new JTextField();
		GridBagConstraints gbc_regAge = new GridBagConstraints();
		gbc_regAge.fill = GridBagConstraints.BOTH;
		gbc_regAge.insets = new Insets(0, 0, 5, 0);
		gbc_regAge.gridx = 1;
		gbc_regAge.gridy = 2;
		contentPane.add(regAge, gbc_regAge);
		regAge.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Email:");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 3;
		contentPane.add(lblNewLabel_4, gbc_lblNewLabel_4);

		regEmail = new JTextField();
		GridBagConstraints gbc_regEmail = new GridBagConstraints();
		gbc_regEmail.fill = GridBagConstraints.BOTH;
		gbc_regEmail.insets = new Insets(0, 0, 5, 0);
		gbc_regEmail.gridx = 1;
		gbc_regEmail.gridy = 3;
		contentPane.add(regEmail, gbc_regEmail);
		regEmail.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Username:");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 4;
		contentPane.add(lblNewLabel_5, gbc_lblNewLabel_5);

		regUName = new JTextField();
		GridBagConstraints gbc_regUName = new GridBagConstraints();
		gbc_regUName.fill = GridBagConstraints.BOTH;
		gbc_regUName.insets = new Insets(0, 0, 5, 0);
		gbc_regUName.gridx = 1;
		gbc_regUName.gridy = 4;
		contentPane.add(regUName, gbc_regUName);
		regUName.setColumns(10);

		JLabel lblNewLabel = new JLabel("Password:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 5;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.fill = GridBagConstraints.BOTH;
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 5;
		contentPane.add(passwordField, gbc_passwordField);

		btnToInLog = new JButton("Back to Login Page");
		GridBagConstraints gbc_btnToInLog = new GridBagConstraints();
		gbc_btnToInLog.fill = GridBagConstraints.BOTH;
		gbc_btnToInLog.insets = new Insets(0, 0, 5, 5);
		gbc_btnToInLog.gridx = 0;
		gbc_btnToInLog.gridy = 6;
		contentPane.add(btnToInLog, gbc_btnToInLog);

		btnRegistratory = new JButton("Register");
		GridBagConstraints gbc_btnRegistratory = new GridBagConstraints();
		gbc_btnRegistratory.fill = GridBagConstraints.BOTH;
		gbc_btnRegistratory.insets = new Insets(0, 0, 5, 0);
		gbc_btnRegistratory.gridx = 1;
		gbc_btnRegistratory.gridy = 6;
		contentPane.add(btnRegistratory, gbc_btnRegistratory);
		btnRegistratory.addActionListener(new RegisterButtonListener());

		statusLabel = new JLabel("");
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.gridwidth = 2;
		gbc_statusLabel.fill = GridBagConstraints.BOTH;
		gbc_statusLabel.insets = new Insets(0, 0, 0, 5);
		gbc_statusLabel.gridx = 0;
		gbc_statusLabel.gridy = 7;
		contentPane.add(statusLabel, gbc_statusLabel);

		btnToInLog.addActionListener(new BackToLoginButtonListener());
	}

	// To save user data.
	// Will create .txt file under user package,
	// & will take user data from there.
	private static void saveUserData(String username, String password, String name, String surname, String email,
			int age) throws IOException {
		String data = username + ";" + password + ";" + name + ";" + surname + ";" + email + ";" + age;
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/user/users.txt", true)))) {
			out.println(data);
		}
	}

	// ActionListener here,
	private class RegisterButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Path directoryPath = Paths.get("src/user");
			File directory = directoryPath.toFile();
			if (!directory.exists()) {
				directory.mkdirs(); // Create the directory if it doesn't exist
			}

			String username = regUName.getText();
			String password = new String(passwordField.getPassword());
			String name = regName.getText();
			String surname = regSName.getText();
			int age = Integer.parseInt(regAge.getText());
			String email = regEmail.getText();

			try {
				if (leControleUser(username, email)) {
					statusLabel.setText("This user is already registered!");
				} else {
					try {
						Validation.passwordValidation(password);
						Validation.usernameValidation(username);
						Validation.ageValidation(age);
						Validation.emailValidation(email);
						saveUserData(username, password, name, surname, email, age);
						statusLabel.setText("Registration is successful!");
					}

					catch (InvalidUserException | IOException e1) {
						statusLabel.setText(e1.getMessage());
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * This method takes username and email and controls if this user is already registered.
	 * @param username
	 * @param email
	 * @return	true/false
	 * @throws IOException
	 */
	private boolean leControleUser(String username, String email) throws IOException {
		Path filePath = Paths.get("src/user/users.txt");
		if (!Files.exists(filePath)) {
			Files.createDirectories(filePath.getParent());
			Files.createFile(filePath);
		}

		List<String> lines = Files.readAllLines(Paths.get("src/user/users.txt"));
		for (String line : lines) {
			String[] part = line.split(";");
			if (part.length >= 6) {
				if (part[0].trim().equals(username) || part[4].trim().equals(email)) {
					return true;
				}
			}
		}
		return false;
	}

	// Now time to turn back to login page:
	private class BackToLoginButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose(); // For closing this frame
			EventQueue.invokeLater(() -> {
				RegistrationLoginFrame loginFrame = new RegistrationLoginFrame();
				loginFrame.setVisible(true); // To open the login frame
			});
		}
	}
	
	// ActionListeners, which I thought were opened with classes in the first days when I learned to use GUI, 
	// Those gradually become simpler in next classes.
}
