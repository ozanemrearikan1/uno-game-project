package game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
/**
 * @author ozanemrearikan
 */
public class LogGame {
	
	private PrintWriter write;
	private JTextArea logArea;
	
	/**
	 * Please add a path and logArea, this logArea will be in the frame anywhere.
	 * @param direction
	 * @param logArea
	 */
	public LogGame(String direction, JTextArea logArea) {
		this.logArea = logArea;
		try {
			write = new PrintWriter(new BufferedWriter(new FileWriter(direction, true)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Opened PrintWriter, write, prints out written message to loggings.txt and log area on GameFrame.
	 * SwingUtility invokeLater is important to refresh screen.
	 * Flush refreshes the screen. 
	 * @param message
	 */
	public void logMessage(String message) {
		write.println(message);
		write.flush();
		SwingUtilities.invokeLater(() -> logArea.append(message + "\n"));
	}
	
	/**
	 * The method closes the log safely.
	 */
	public void closeLog() {
		logMessage("---Game Over!---");
		write.close();
	}

}
