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

public class Button extends TextLabel implements Clickable{

	private Action action;
	private BufferedImage hoverImage;
	private boolean hovered;
	private boolean enabled;
	protected int curveX;
	protected int curveY;
	
	public Button(int x, int y, int w, int h, String text, Color color, Action action) {
		super(x, y, w, h, text);
		setBackground(color);
		enabled = true;
		this.action = action;
		setCurve(35,25);
		setActiveBorderColor(Color.BLACK);
		update();
		
	}
	
	public Button(int x, int y, int w, int h, String text, Action action) {
		super(x, y, w, h, text);
		this.action = action;
		enabled = true;
		setCurve(35,25);
		setActiveBorderColor(Color.BLACK);
		update();

	}
	
	/**
	 * set the roundness of the curve. Default is 35,25 pixels
	 * @param pixels
	 */
	public void setCurve(int xPixels, int yPixels){
		clear();
		this.curveX = xPixels;
		this.curveY = yPixels;
		update();
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if(!enabled)hovered = false;
	}

	public BufferedImage getImage(){
		if(hovered || !enabled)return hoverImage;
		else return super.getImage();
	}
	
	public BufferedImage getHoveredImage(){
		return hoverImage;
	}
	
	public BufferedImage getNonhoveredImage(){
		return super.getImage();
	} 
	public void update(Graphics2D g){
		drawButton(g, false);
	}
	
	
	public void update(){
		hoverImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D hoverG = hoverImage.createGraphics();
		applyStyles(hoverG);
		drawButton(hoverG, true);
		super.update();
	}
	
	
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
			g.setColor(getActiveBorderColor());
			g.setStroke(new BasicStroke(getButtonOutlineSize()));
			g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, curveX, curveY);
		}
	}
	
	
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

	public void hoverAction(){
		GUIApplication.mainFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));
		setLeft(false);
	}
	
	public void unhoverAction(){
		setLeft(true);
		GUIApplication.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

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
	
	public void act(){
		if(action != null) action.act();
	}
	
	public void setAction(Action a){
		this.action = a;
	}

	public boolean isHovered() {
		return hovered;
	}
	
	
	
	
	
	
	
	
	

}
