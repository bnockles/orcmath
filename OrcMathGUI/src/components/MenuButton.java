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
import java.awt.Graphics2D;
import java.awt.Polygon;

import guiTeacher.Utilities;
import guiTeacher.components.Action;
import guiTeacher.components.Button;
import guiTeacher.components.StyledComponent;
import guiTeacher.userInterfaces.Transition;
import main.OrcMath;

public class MenuButton extends Button{

	public static final int WIDTH = 48;
	public static final int HEIGHT = 48;
	private static final int _COG_RADIUS = 24;
	private static final int _TEETH_DEPTH = 7;
	public static final Color BACK_COLOR = new Color(180,230,255);
	public static final Color FORE_COLOR = new Color(140,200,215);
	
	public MenuButton(int x, int y) {
		super(x, y, WIDTH, HEIGHT, "", new Action(){

			@Override
			public void act() {
				OrcMath.app.setScreen(OrcMath.settings,new Transition(OrcMath.app,Transition.ENTER_LEFT,700));			}
		
		});
	}
	
	public void drawButton(Graphics2D g, boolean hover){
		int border =2;
		g.setStroke(new BasicStroke(border));
//Original THREE BARS
		//		for(int i = 0; i < 3; i++){
//			g.setColor(StyledComponent.getAccentColor());
//			g.fillRoundRect(border, space+border, WIDTH-border*2, 10, 5, 5);
//			g.setColor(StyledComponent.getStaticBorderColor());
//			g.drawRoundRect(border, space+border, WIDTH-border*2, 10, 5, 5);
//			space+=HEIGHT/3;
//		}
		
		Polygon cog = makeCog(16);
		if(!hover)g.setColor(StyledComponent.getAccentColor());
		else g.setColor(Utilities.lighten(StyledComponent.getAccentColor(),.4f));
		g.fill(cog);
		g.setColor(StyledComponent.getStaticBorderColor());
		g.draw(cog);
		g.setColor(Color.white);
		g.fillOval(WIDTH/2-3,HEIGHT/2-3,6,6);
		g.setColor(StyledComponent.getStaticBorderColor());
		g.drawOval(WIDTH/2-4,HEIGHT/2-4,8,8);
	}

	private Polygon makeCog(int teeth) {
		int[] xs = new int[teeth*2];
		int[] ys = new int[teeth*2];
		
		int count = 0;
		int x = WIDTH/2;
		int y = HEIGHT/2;
		for(double i=0; i<=2*Math.PI; i=i+Math.PI/teeth){
			int r = _COG_RADIUS;
			if(count%4<2)r = _COG_RADIUS-_TEETH_DEPTH;
			xs[count] = (int)(x+r*Math.cos(i));
			ys[count] = (int)(y+r*Math.sin(i));
			count++;
		}
		return new Polygon(xs,ys,xs.length);
	}



}
