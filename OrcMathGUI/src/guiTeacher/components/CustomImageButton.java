package guiTeacher.components;


import java.awt.Graphics2D;

import guiTeacher.interfaces.DrawInstructions;

/**
 * A Button with a customizable appearance. The constructor takes a "DrawInstructions" object as a parameter. Use this object to specify how the Buttons should be drawn.
 * @author bnockles
 *
 */

public class CustomImageButton extends Button {

	DrawInstructions toDraw;
	
	/**
	 * 
	 * @param x x-coordinate within container
	 * @param y y-coordinate within container
	 * @param w width of the Button
	 * @param h height of the Button
	 * @param toDraw defines how the button should look (both ehen hovered (highlighed) and not
	 * @param action
	 */
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
