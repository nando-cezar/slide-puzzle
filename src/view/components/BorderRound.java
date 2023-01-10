package view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;

public class BorderRound extends AbstractBorder {

	private static final long serialVersionUID = 1L;
	private RenderingHints antialiasing;
	private Component c;
	private Graphics g;
	private int x;
	private int y;
	private int width;
	private int height;

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		this.c = c;
		this.g = g;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.antialiasing = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.initialize();
	}

	private void initialize() {
		Graphics2D g2d = (Graphics2D) g;

		Color oldColor = c.getParent().getBackground();
		g2d.setColor(oldColor);

		Shape outer;
		Shape inner;

		int offs = 1;
		int size = offs + offs;
		float arc = .2f * offs;
		outer = new RoundRectangle2D.Float(x / 2, y / 2, width, height, offs * width, offs * height);
		inner = new RoundRectangle2D.Float(x + offs - 2, y + offs - 2, width - size + 4, height - size + 4, arc, arc);

		Path2D path = new Path2D.Float(Path2D.WIND_EVEN_ODD);
		g2d.addRenderingHints(antialiasing);
		path.append(outer, false);
		path.append(inner, false);
		g2d.fill(path);
		g2d.setColor(oldColor);
	}

}