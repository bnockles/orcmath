package alice2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import guiTeacher.components.Action;
import guiTeacher.components.Button;

public class ButtonAlice extends Button implements ButtonInterfaceAlice{
	private Color clr;
	int var = 10;

	public ButtonAlice(int x, int y, int w, int h, String text, Action action) {
		super(x, y, w, h, text, null);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void setColor(Color blue) {
		this.setBackground(blue);
	}

	@Override
	public void highlight() {
		int red = this.clr.getRed() + var;
		int green = this.clr.getGreen() + var;
		int blue = this.clr.getBlue() + var;
		this.setColor(new Color(red, green, blue));
	}

	@Override
	public void dim() {
		this.setColor(getColor());
	}
	public Color getColor() {
		return this.clr;
	}

	@Override
	public ButtonAlice getButton() {
		return this;
	}

	
	

}
