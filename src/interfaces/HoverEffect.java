package interfaces;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverEffect extends MouseAdapter{
	private Color backgroundEntered;
	private Color backgroundExited;
	
	public HoverEffect(Color backgroundEntered, Color backgroundExited) {
		super();
		this.backgroundEntered = backgroundEntered;
		this.backgroundExited = backgroundExited;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		e.getComponent().setBackground(backgroundEntered);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		e.getComponent().setBackground(backgroundExited);
	}

	public Color getBackgroundEntered() {
		return backgroundEntered;
	}

	public void setBackgroundEntered(Color backgroundEntered) {
		this.backgroundEntered = backgroundEntered;
	}

	public Color getBackgroundExited() {
		return backgroundExited;
	}

	public void setBackgroundExited(Color backgroundExited) {
		this.backgroundExited = backgroundExited;
	}
}
