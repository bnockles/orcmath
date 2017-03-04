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

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import guiTeacher.Utilities;
import guiTeacher.interfaces.Clickable;

public class Button extends TextLabel implements Clickable{

	private Action action;
	private BufferedImage hoverImage;
	private boolean hovered;
	private boolean enabled;
	
	public Button(int x, int y, int w, int h, String text, Color color, Action action) {
		super(x, y, w, h, text);
		setBackground(color);
		enabled = true;
		this.action = action;
		update();
		
	}
	
	public Button(int x, int y, int w, int h, String text, Action action) {
		super(x, y, w, h, text);
		this.action = action;
		enabled = true;
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
		if(hovered)return hoverImage;
		else return super.getImage();
	}
	
	public void update(Graphics2D g){
		drawButton(g, false);
	}
	
	
	public void update(){
		hoverImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		drawButton(hoverImage.createGraphics(), true);
		super.update();
	}
	
	
	public void drawButton(Graphics2D g, boolean hover){
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if(getBackground() != null){
			if(!hover)g.setColor(getBackground());
			else{
				g.setColor(Utilities.lighten(getBackground(), .4f));
//				g.setColor(getBackground());
			}
			g.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 25);
		}else{
			clear();
		}
		g.setColor(Color.BLACK);
		g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 35, 25);
		g.setColor(getForeground());
		g.setFont(getFont());
		FontMetrics fm = g.getFontMetrics();
		
		if(getText()!= null){
			g.setColor(Color.white);
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


	public boolean isHovered(int x, int y) {
		boolean b = x>getX() && x<getX()+getWidth() 
		&& y > getY() && y<getY()+getHeight();
//		if(b != hovered){
//			
//		}
		hovered = b && enabled;
		return hovered;
	}
	
	public void act(){
		action.act();
	}
	
	public void setAction(Action a){
		this.action = a;
	}

	public boolean isHovered() {
		return hovered;
	}
	
	
	
	
	
	
	
	
	

}
