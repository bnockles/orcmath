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

import data.SettingsData;
import guiTeacher.Utilities;
import guiTeacher.components.Action;
import guiTeacher.components.Button;
import guiTeacher.components.StyledComponent;
import guiTeacher.userInterfaces.Transition;
import main.OrcMath;
import screens.Settings;

public class BackToCreateButton extends Button {

	public static final int WIDTH = 40;
	public static final int HEIGHT = 35;
	public static final Color COLOR = new Color(180,230,255);
	public static final Color BACK_COLOR = new Color(180,230,255);
	public static final Color FORE_COLOR = new Color(140,200,215);

	private Polygon arrow;

	public BackToCreateButton(int x, int y) {

		super(x, y, WIDTH, HEIGHT, "", COLOR, new Action(){

			@Override
			public void act() {
				SettingsData sd = OrcMath.sd;
				Settings settings = OrcMath.settings;
				if(settings.containsEmptyFields()){
					settings.presentNotification("You cannot leave any fields empty.");
				}else{
					sd.setTeacherName(settings.getCustomTeacherName());
					sd.setOverrideVerticalSpacing(settings.getOverrideVerticalSpacing());
					sd.setVSpacing(settings.getVSpacing());		
					sd.setIncludeMainInstructions(settings.includeMainInstructions());
					sd.setIncludeHeader(settings.includeHeader());
					sd.setShuffleOrder(settings.isShuffled());
					sd.saveData();
					OrcMath.app.setScreen(OrcMath.createScreen,new Transition(OrcMath.app,Transition.REVEAL_RIGHT,700));			
				}

			}
		});

		arrow = constructArrow(WIDTH,HEIGHT, 8,Math.PI/4.5);
		update();
	}

	private Polygon constructArrow(int length, int height, int thickness, double angle){
		int xPoint = (int)((height/2)/Math.tan(angle));
		int[] xSide = {0,1,3,(int)(length-thickness/Math.sin(angle)-(thickness)/Math.tan(angle)),xPoint, xPoint-1,xPoint-1,xPoint,length};
		int[] xs = new int[xSide.length*2-1];
		for(int i=0; i < xs.length; i++){
			xs[i]=(i<xSide.length)?xSide[i]:xSide[xSide.length-1-i%xSide.length];
		}
		int yPoint = (int)(height/2-thickness/Math.cos(angle));
		int[] ySide = {thickness-3,thickness-2, thickness,thickness,yPoint,yPoint+1,height/2-1,height/2,0};
		int[] ys = new int[2*ySide.length-1];
		for(int i=0; i < ys.length; i++){
			ys[i]=(i<ySide.length)?height/2-ySide[i]: height/2 + ySide[ySide.length-1-(i%ySide.length)];
		}
		return new Polygon(xs, ys, ys.length);

	}

	public void drawButton(Graphics2D g, boolean hover){
		int border =2;

		g.setStroke(new BasicStroke(border));
		if(arrow != null) {
			if(!hover)g.setColor(StyledComponent.getAccentColor());
			else g.setColor(Utilities.lighten(StyledComponent.getAccentColor(), .4f));
			g.fill(arrow);
			g.setColor(StyledComponent.getStaticBorderColor());
			g.draw(arrow);
		}
	}

}
