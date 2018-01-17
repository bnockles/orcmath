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

import guiTeacher.Utilities;

/**
 * A Button with no outline and colored text with an underline (like an html link)
 * @author bnockles
 *
 */
public class Link extends Button {

	private int align;
	private Color linkColor;

	/**
	 * 
	 * @param x x-coordinate within container
	 * @param y y-coordinate within container
	 * @param w width of the link
	 * @param h height of the link
	 * @param text link text
	 * @param action associated Action
	 */
	public Link(int x, int y, int w, int h, String text, Action action) {
		super(x, y, w, h, text, null, action);
		linkColor = getAccentColor();
		align = StyledComponent.ALIGN_LEFT;
		update();
	}


/**
 * 
 * @return the link Color for this link (default linkColor is StyledComponent.accentColor)
 */
	public Color getLinkColor() {
		return linkColor;
	}


/**
 * 
 * @param linkColor set the linkColor for this link (otherwise, linkColor defaults to StyledComponent.accentColor 
 */
	public void setLinkColor(Color linkColor) {
		this.linkColor = linkColor;
		update();
	}



	public int getAlign() {
		return align;
	}


	public void update(Graphics2D g){
		clear();
		super.update(g);
	}

	public void drawButton(Graphics2D g, boolean hover){
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if(getLinkColor() != null){

			if(!hover)g.setColor(getLinkColor());
			else g.setColor(Utilities.lighten(getLinkColor(),.4f));
			g.setFont(getFont());
			FontMetrics fm = g.getFontMetrics();

			if(getText()!= null){
				String t = getText();
				//just in case text is too wide, cut off
				int cutoff = t.length();
				while(cutoff > 0 && fm.stringWidth(t) > getWidth()){
					cutoff --;
					t = t.substring(0,cutoff); 
				}

				int xStart = 0;
				int sWidth = fm.stringWidth(t);
				if(align == StyledComponent.ALIGN_CENTER){
					xStart = getWidth()/2-sWidth;
				}else if(align == StyledComponent.ALIGN_RIGHT){
					xStart = getWidth()-sWidth;
				}
				g.drawString(t, xStart, fm.getHeight());
				g.drawLine(xStart,fm.getHeight()+1,xStart+sWidth,fm.getHeight()+1);
			}
		}
	}

	public void setAlign(int align) {
		this.align = align;
		update();
	}

}
