package guiTeacher.components;


import java.awt.Graphics2D;

import guiTeacher.interfaces.DrawInstructions;

public class CustomImageButton extends Button {

	DrawInstructions toDraw;
	
	public CustomImageButton(int x, int y, int w, int h, DrawInstructions toDraw, Action action) {
		super(x, y, w, h, "", action);
		this.toDraw = toDraw;
		update();
	}

	@Override
	public void drawButton(Graphics2D g, boolean hovered){
		if(toDraw != null) toDraw.draw(g, hovered);
	}
}
