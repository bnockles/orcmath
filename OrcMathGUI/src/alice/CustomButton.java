package alice;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import guiPlayer.CustomLabel;
import guiTeacher.components.Button;
import guiTeacher.components.Action;


public class CustomButton extends Button implements CustomLabel{
	
	

	public CustomButton(int x, int y) {
		
		super(x,y, 200,100, " ", null);
	}

	@Override
	public void updateString1(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateString2(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIconColor(Color color) {
		// TODO Auto-generated method stub
		
	}
	
	public void drawButton(Graphics2D g, boolean hover){
		g.setColor(Color.black);
		g.drawString("spicy", 10, 19);
		g.drawString("salmon", 10, 19);

	}
	
	

}
