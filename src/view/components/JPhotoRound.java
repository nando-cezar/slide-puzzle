package view.components;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class JPhotoRound extends JLabel {

	private static final long serialVersionUID = 1L;
	private String path;
	private int size;
	private BorderRound borderRound;

	public JPhotoRound(String path, int size) {
		this.path = path;
		this.size = size;
		this.borderRound = new BorderRound();
		this.initialize();
	}

	private void initialize() {
		this.setText("");
		this.setIcon(new ImageIcon(imageResized(path)));
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setBorder(borderRound);
		this.setOpaque(false);
	}

	private Image imageResized(String path) {

		Image image = null;
		try {
			BufferedImage resized = ImageIO.read(new File(path));
			image = resized.getScaledInstance(size, size, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public void setPath(String path) {
		if(path != null) {
			this.path = path;
			initialize();
		}
	}

	public String getPath() {
		return (path != null) ? path : "";
	}
	

}