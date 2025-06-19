package frames;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
/**
 * @author ozanemrearikan
 * with extending JPanel and overriding paintComponent() method
 * 
 */
public class PanelForBackground extends JPanel {
	private Image background;

	public PanelForBackground() {
		try {
			File path = new File("src/images/blurred_uno_cards.png");
			background = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
	}

}
