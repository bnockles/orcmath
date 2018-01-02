package danielComponent;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import guiTeacher.components.Action;
import guiTeacher.components.Button;

public class CustButton extends Button {

	private String string1;
	private String string2;
	private Color color;
	

	public CustButton(int x, int y) {
		super(x, y, 100, 100, "", null);
	}
	
	
	public void drawButton(Graphics2D g, boolean hover)
	{
		string1 = "test";
		string2 = "asd";
		
		g.setColor(Color.black);
		
		g.drawString(string1, 10, 10);
		g.drawString(string2, 10, 20);
		
		g.drawRect(20, 40, 10, 10);
		g.fillRect(20, 40, 10, 10);
	}
	
	public void updateString1(String string)
	{
		string1 = string;
	}

	public void updateString2(String string)
	{
		string2 = string;
	}

	public void setIconColor(Color color1)
	{
		color = color1;
	}

}
