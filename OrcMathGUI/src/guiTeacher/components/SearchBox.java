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
package guiTeacher.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import guiTeacher.interfaces.Clickable;
import guiTeacher.interfaces.TextComponent;
import screens.CreateScreen;


/**
 * @author bnockles
 *
 */
public class SearchBox extends TextField {

	private ArrayList<TextComponent> allItems;
	private ArrayList<TextComponent> foundItems;
	private AccordionTab resultsTab;
	
	
	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param text
	 */
	public SearchBox(int x, int y, int w, int h) {
		super(x, y, w, h, "");
//		results = new ResultsWindow();
		allItems = new ArrayList<TextComponent>();
		foundItems = new ArrayList<TextComponent>();
	}
	
	public void addToCollection(ArrayList<TextComponent> searchables){
		allItems.addAll(searchables);
	}
	
	public void addToCollection(TextComponent searchable){
		allItems.add(searchable);
	}

	public void keyTyped(KeyEvent e) {
		super.keyTyped(e);
		if(!resultsTab.isOpen())resultsTab.switchToThisTab();
		resultsTab.getContent().removeAll();
		int x = 5;
		int y = 10;
		boolean found = false;
		if(getText().length() > 2){
			for(int i = 0 ; i < allItems.size(); i++){
				TextComponent t = allItems.get(i);
				if(t.getText().toLowerCase().contains(getText().toLowerCase())){
					found = true;
					Link r = new Link(x, y, t.getWidth(), t.getHeight(), t.getText(), null);
					if(t instanceof Clickable){
						r.setAction(new Action() {
							
							@Override
							public void act() {
								((Clickable)t).act();
							}
						});
					}
					r.setLinkColor(Color.BLACK);
					y+=r.getHeight()+3;
					resultsTab.getContent().addObject(r);

				}
			}
			
		}
		
		else{
			TextLabel r = new TextLabel(x, y, 200, 22, "Type at least three letters");
			y+=r.getHeight()+3;
			resultsTab.getContent().addObject(r);
		}
		if(!found && getText().length() > 2){
			TextLabel r = new TextLabel(x, y, 200,22, "No matches found.");
			y+=r.getHeight()+3;
			resultsTab.getContent().addObject(r);
		}

		resultsTab.getContent().update();
		resultsTab.update();
		resultsTab.getParent().update();
	}
	
	

	
	public void update(Graphics2D g){
		super.update(g);
		//draws a search icon
		if(!isRunning()){
			Color grey = new Color(200,200,200);
			g.setColor(grey);
			Stroke s = g.getStroke();
			g.setStroke(new BasicStroke(3));
			int iconHeight = 18;
			int iconWidth = 16;
			int space = 9;
			//handle of mag glass
			int barHeight = getHeight() - DESCRIPTION_SPACE;
			g.drawLine(getWidth()-space-iconWidth+7, (barHeight-iconHeight)/2+DESCRIPTION_SPACE+(int)(7*(iconHeight/(double)iconWidth)), getWidth()-space, (barHeight+iconHeight)/2+DESCRIPTION_SPACE);
			g.setColor(Color.WHITE);
			g.fillOval(getWidth()-space-iconWidth, (barHeight-iconHeight)/2+DESCRIPTION_SPACE, 14, 14);
			g.setColor(grey);
			g.drawOval(getWidth()-space-iconWidth, (barHeight-iconHeight)/2+DESCRIPTION_SPACE, 14, 14);
			g.setStroke(s);
		}
		
	}

	
	public void setTarget(AccordionTab tab) {
		resultsTab = tab;
	}

}
