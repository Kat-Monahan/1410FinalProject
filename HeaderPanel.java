package final01;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Handles the image painter for our header panel
 */
public class HeaderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage image;

	public HeaderPanel() {
		try {
			URL imageurl = getClass().getResource("/images/custom2.png");
			image = ImageIO.read(imageurl);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error loading image: " + e.getMessage());

		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (image != null) {

			g.drawImage(image, 0, 0, this);
		}
	}
}
