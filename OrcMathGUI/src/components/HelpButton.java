/*******************************************************************************
 * Copyright (c) 2017 Benjamin Nockles
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
package components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.net.URI;

import guiTeacher.Utilities;
import guiTeacher.components.Action;
import guiTeacher.components.Button;
import guiTeacher.components.StyledComponent;
import main.OrcMath;

public class HelpButton extends Button {

	public static final String HELP_URL = "http://www.neverbenbetter.com/orcmath-documentation";
	public static final int HEIGHT = 40;
	
	public HelpButton(int x, int y) {
		super(x, y, HEIGHT, HEIGHT, "", BackToCreateButton.BACK_COLOR, new Action(){

			@Override
			public void act() {
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
				if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
					try {
						desktop.browse(new URI(HELP_URL));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					OrcMath.createScreen.presentNotification("Your system does not allow linking from Java software. Go to neverbenbetter.com/orcmath-documentation for help.");
				}

			};
		
		});
	}


	

	
	public void drawButton(Graphics2D g, boolean hover){
		int border =2;

		g.setStroke(new BasicStroke(border));
		g.setColor(StyledComponent.getAccentColor());
//		g.fillOval(1, 1, getWidth()-2, getHeight()-2);
		g.setColor(getTabColor());
		Font f = getTabFont();
		Font baseFont=f.deriveFont(23f);
		g.setFont(baseFont);
		
		FontMetrics fm = g.getFontMetrics();
//		g.drawString("?", (getWidth()-fm.stringWidth("?"))/2, getHeight()-fm.getDescent()-2);
		drawQuestionMark(g, 0, 0, hover);
//		g.drawOval(1, 1, getWidth()-2, getHeight()-2);
	}
	
	private void drawQuestionMark(Graphics2D g, int x, int y, boolean hover){
		int mid = 12;
		int[] xs = {x,x+1,x+3,x+6, x+10, x+14,x+17,x+19, x+20,
				x+18, x+16, x+15,x+14, x+14, 
				x + 7, x + 7, x+9, x+ 11, x+ 13, x+ 10, x+ 7, x+5, x+4};
		int[] ys = {y+mid,y+mid - 5,y+mid-9,y+2, y, y+2,y+mid-9,y+mid - 5, y + mid, 
				y+mid+3,y+mid+5, y+mid+8, y+mid+10, y + mid +12,
				y + mid +12, y + mid +8, y+mid+4, y + mid+2, y + mid -2,  y + mid -4, y + mid - 3, y+mid-1, y+mid};
		

		Polygon mark = new Polygon(xs,ys,xs.length);
		if(!hover)g.setColor(StyledComponent.getAccentColor());
		else g.setColor(Utilities.lighten(StyledComponent.getAccentColor(),.4f));
		g.fill(mark);
		g.fillOval((x+7), (y+mid+15), 8, 8);
		g.setColor(StyledComponent.getStaticBorderColor());
		g.draw(mark);
		g.drawOval((x+7), (y+mid+15), 8, 8);
	}

	
}
