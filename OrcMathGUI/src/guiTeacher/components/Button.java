/*******************************************************************************
 * Copyright (c) 2016-2017 Benjamin Nockles
 *
 * This file is part of OrcMath.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License 
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package guiTeacher.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import guiTeacher.GUIApplication;
import guiTeacher.Utilities;
import guiTeacher.interfaces.Clickable;

/**
 * A Clickable Component that has two images: a normal image and a hovered image. If a button is enabled, its appearance changes when it is hovered as a visual confirmation that it can be clicked
 * @author bnockles
 *
 */
public class Button extends TextLabel implements Clickable{

	private Action action;
	private BufferedImage hoverImage;
	private boolean hovered;
	private boolean enabled;
	protected int curveX;
	protected int curveY;
	protected Color outlineColorActive;
	protected Color outlineColorInactive;
	protected int outlineSize;
	
	/**
	 * 
	 * @param x - the x-coordinate of the Button, within the context of the ComponentContainer
	 * @param y - the y-coordinate of the Button, within the context of the ComponentContainer
	 * @param w - the pixel width of this Button
	 * @param h - the pixel height of this Button
	 * @param text - Text that will appear in the center of this Button
	 * @param color - the background color of this button
	 * @param action - the action associated with clicking this Button. May be null
	 */
	public Button(int x, int y, int w, int h, String text, Color color, Action action) {
		super(x, y, w, h, text);
		setBackground(color);
		this.action = action;
		setDefaults();
		update();
		
	}
	
	/**
	 * 
	 * @param x - the x-coordinate of the Button, within the context of the ComponentContainer
	 * @param y - the y-coordinate of the Button, within the context of the ComponentContainer
	 * @param w - the pixel width of this Button
	 * @param h - the pixel height of this Button
	 * @param text - Text that will appear in the center of this Button
	 * @param action - the action associated with clicking this Button. May be null.
	 */
	public Button(int x, int y, int w, int h, String text, Action action) {
		super(x, y, w, h, text);
		this.action = action;
		setDefaults();
		update();

	}
	
	protected void setDefaults(){
		enabled = true;
		setCurve(35,25);
		outlineColorActive = getActiveBorderColor();
		outlineColorInactive = getInactiveBorderColor();
		outlineSize = getButtonOutlineSize();
	}
	
	/**
	 * set the roundness of the curve. Default is 35,25 pixels
	 * @param xPixels the horizontal width of the curve
	 * @param yPixels the vertical height of the curve
	 */
	public void setCurve(int xPixels, int yPixels){
		clear();
		this.curveX = xPixels;
		this.curveY = yPixels;
		update();
	}
	
	/**
	 * @return true if this Button is enabled (can be clicked and hovered)
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
/**
 * set 'enabled' for this Button. When a Button is not enabled, it is visible but will not change when hovered and will not act when hovered.
 * @param enabled
 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if(!enabled)hovered = false;
	}

	/**
	 * sets the active border color of this Button. Use setActiveBorderColor to set the border color of ALL Buttons
	 * @param c
	 */
	public void setCustomActiveBorderColor(Color c){
		outlineColorActive = c;
	}
	
	/**
	 * sets the inactive border color of this Button. Use setInactiveBorderColor to set the border color of ALL Buttons
	 * @param c
	 */
	public void setCustomInactiveBorderColor(Color c){
		outlineColorInactive = c;
	}
	
	/**
	 * Sets the thickness of this Button's border
	 * @param size
	 */
	public void setCustomOutlineSize(int size){
		this.outlineSize = size;
	}
	
	/**
	 * @return either the hoverImage or image, depending on the state of this Button
	 */
	public BufferedImage getImage(){
		if(hovered || !enabled)return hoverImage;
		else return super.getImage();
	}
	
	/**
	 * 
	 * @return the hoverImage, regardless of the current state of this Button (i.e. even if the Button is not hovered right now)
	 */
	public BufferedImage getHoveredImage(){
		return hoverImage;
	}
	
	/**
	 * 
	 * @return the non-hovered image, regardless of the current state of this Button (i.e. even if the Button is currently hovered)
	 */
	public BufferedImage getNonhoveredImage(){
		return super.getImage();
	} 
	
	/**
	 * Called automatically by update()
	 * For most subclasses of Button, override drawButton() instead of this method
	 */
	public void update(Graphics2D g){
		drawButton(g, false);
	}
	
	/**
	 * Updates both the image and hover image of this Button
	 */
	public void update(){
		hoverImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D hoverG = hoverImage.createGraphics();
		applyStyles(hoverG);
		drawButton(hoverG, true);
		super.update();
	}
	
	/**
	 * called by drawButton. This method draws the background of the Button and includes the background color and outline of the Button.
	 * @param g
	 * @param hover
	 */
	public void drawShape(Graphics2D g, boolean hover){
		if(getBackground() != null){
			if(!hover)g.setColor(getBackground());
			else{
				g.setColor(Utilities.lighten(getBackground(), .4f));
//				g.setColor(getBackground());
			}
			g.fillRoundRect(0, 0, getWidth(), getHeight(), curveX, curveY);
		}else{
			clear();
		}
		if (isButtonOutline()){
			if(enabled)
				g.setColor(outlineColorActive);
			else 
				g.setColor(outlineColorInactive);
			g.setStroke(new BasicStroke(outlineSize));
			g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, curveX, curveY);
		}
	}
	
	/**
	 * Called automatically by update. This method replaces the update(Graphics 2D) method and draw the Button.
	 * The hover parameter is used to distinguish between the Button when it is hovered and the Button when it is not being hovered
	 * @param g
	 * @param hover
	 */
	public void drawButton(Graphics2D g, boolean hover){
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		drawShape(g, hover);
		g.setColor(getForeground());
		g.setFont(getFont());
		FontMetrics fm = g.getFontMetrics();
		
		if(getText()!= null){
			g.setColor(getForeground());
			String t = getText();
			//just in case text is too wide, cut off
			int cutoff = t.length();
			while(cutoff > 0 && fm.stringWidth(t) > getWidth()){
				cutoff --;
				t = t.substring(0,cutoff); 
			}
			g.drawString(t, (getWidth()-fm.stringWidth(t))/2, 
					(getHeight()+fm.getHeight()-fm.getDescent())/2);
		}
	}

	/**
	 * default implementation of hoverAction sets the cursor to the hand image
	 */
	public void hoverAction(){
		GUIApplication.mainFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));
		setLeft(false);
	}
	
	/**
	 * default implementation of unhoverAction return the cursor to the default pointer
	 */
	public void unhoverAction(){
		setLeft(true);
		GUIApplication.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Called automatically by ComponentContainers that listen for MouseMotion. This method checks whether the x, y coordinates of the mouse are within the bounds of this Button. To check the current status of the button without having to update its hver status, use isHovered
	 *@param x - the x coordinate of the mouse, relative to this ComponentContainer
	 *@param y - the y coordinate of the mouse, relative to this ComponentContainer
	 */
	public boolean isHovered(int x, int y) {
		boolean b = x>getX() && x<getX()+getWidth() 
		&& y > getY() && y<getY()+getHeight();
//		if(b != hovered){
//			
//		}
		hovered = b && enabled;
		if(hovered){
			hoverAction();
		}else if (!hasLeft()){
			unhoverAction();
		}
		return hovered;
	}
	
	/**
	 * called automatically when this Button is clicked
	 */
	public void act(){
		if(action != null) action.act();
	}
	
	/**
	 * Set the action for this Button. The act() method of the action is called automatically whenever this Button is clicked
	 */
	public void setAction(Action a){
		this.action = a;
	}

	/**
	 * @return true if this Button is currently hovered. To update the status of the Button, us isHovered(int x, int y)
	 */
	public boolean isHovered() {
		return hovered;
	}
	
	
	
	
	
	
	
	
	

}
