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
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import guiTeacher.interfaces.Clickable;
import guiTeacher.interfaces.Dragable;


public class Accordion extends StyledComponent implements Clickable, Dragable {

	protected ArrayList<AccordionTab> tabs;
	protected AccordionTab openTab;

	//mouse listening
	private int relativeX;
	private int relativeY;
	private Color tabColor;
	private Color tabShade;
	private Dragable draggedItem;

	public Accordion(int x, int y, int w) {
		super(x, y, w, 1);
		setHeaderColor(new Color(255,255,255));
		tabColor = getTabColor();
		tabShade = getTabColor();
		tabs = new ArrayList<AccordionTab>();
		openTab = null;
	}

	public boolean isAnimated(){
		return true;
	}

	public AccordionTab addTab(String tabHeader, ScrollablePane content){
		AccordionTab t = new AccordionTab(this, tabHeader, content);
		tabs.add(t);
		return t;
	}

	@Override
	public boolean isHovered(int x, int y) {
		if (x >= getX() && x <= getX()+ getWidth() && y >= getY() && y <= getY()+getHeight()){
			relativeX = x-getX();
			relativeY = y-getY();
			return true;
		}
		return false;
	}

	public void hoverAction(){
		if(openTab != null && openTab.getSubwindow().isHovered(relativeX, relativeY-getTabHeight()*(1+tabs.indexOf(openTab)))){
			openTab.getSubwindow().hoverAction();
		}
	}

	public int getHeight(){
		//		int openTabHeight = 0;
		//		if(openTab != null){
		//			openTabHeight = openTab.getHeight()-getTabHeight();
		//		}
		//		System.out.println("the height of this accordion is "+(getTabHeight()*tabs.size()+openTabHeight)+" and the height of the image is "+getImage().getHeight());
		//		return getTabHeight()*tabs.size()+openTabHeight;
		int th = 0;
		for(AccordionTab t: tabs){
			th+=t.getHeight();
		}
		return th;
	}

	@Override
	public void act() {
		int indexOfOpenTab = 0;
		int heightOfOpenTabContents = 0;
		if(openTab != null){
			indexOfOpenTab = tabs.indexOf(openTab);
			heightOfOpenTabContents = openTab.getHeight()-getTabHeight();
		}
		//virtualTabIndex represents which tab would be clicked if all tabs were closed
		int virtualTabIndex = relativeY/getTabHeight();//location of click tab, supposing all tabs are closed
		virtualTabIndex = (virtualTabIndex < tabs.size())? virtualTabIndex : tabs.size()-1;
		AccordionTab virtualTab =tabs.get(virtualTabIndex);
		//Clicked tab represents the actual clicked tab, taking into account open tabs
		AccordionTab clickedTab = virtualTab;
		if(virtualTabIndex > indexOfOpenTab){
			int yUnderOpenTab = relativeY - getTabHeight()*(indexOfOpenTab+1);
			if(yUnderOpenTab > heightOfOpenTabContents){
				int tabClick = indexOfOpenTab+1+(yUnderOpenTab-heightOfOpenTabContents)/getTabHeight();
				tabClick = (tabClick < tabs.size())?tabClick:tabs.size()-1;
				clickedTab = tabs.get(tabClick);				
			}else{
				clickedTab = openTab;
			}
		}
		//		System.out.println("Accordion.java virtual tab index is "+virtualTabIndex+"\nthe actually clicked tabe is at index "+tabs.indexOf(clickedTab)+"\nopenTab is "+indexOfOpenTab+" with height "+heightOfOpenTab+"\nvirtual tab is "+virtualTabIndex);
		if(openTab != null && clickedTab == openTab){
			clickedTab.act(relativeX,relativeY-getTabHeight()*(indexOfOpenTab+1));
		}else{
			openTab(clickedTab);
		}
	}

	public void openTab(AccordionTab tab){
		if(openTab != null)openTab.close();
		tab.open();
		openTab = tab;

	}

	/**
	 * Accordion image is dynamic (height changes based on open tabs)
	 * so when update is called, a new image is always created
	 */

	private int lastWidth = 0;
	private int lastHeight = 0;
	public void update(){
		if(getWidth() != lastWidth || getHeight() != lastHeight){
			lastWidth = getWidth();
			lastHeight = getHeight();
			BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			update(buffer.createGraphics());
			resize().drawImage(buffer, 0, 0, null);
		}else{
			super.update();
		}
	}

	@Override
	public void update(Graphics2D g) {
		int tabY= 0;
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		for(AccordionTab ac: tabs){
			ac.update();
			g.drawImage(ac.getImage(), 0, tabY, null);
			tabY+=ac.getHeight();
		}
	}

	@Override
	public void setAction(Action a) {
		// TODO Auto-generated method stub

	}

	public void setTabShade(Color shade){
		tabShade = shade;
	}

	private int yInTab(){
		if(openTab != null){
			return relativeY-getTabHeight()*(1+tabs.indexOf(openTab));			
		}else{
			return 0;
		}
	}
	
	private int xInTab(){
		if(openTab != null){
			return relativeX;			
		}else{
			return 0;
		}
	}
	
	public boolean setStart(int x, int y) {
		boolean hoverOverDragable = false;

		if(openTab != null && openTab.getContent() instanceof Dragable){
			Dragable item = (Dragable)openTab.getContent();
			if(item.setStart(xInTab(),yInTab())){
				draggedItem = item;
				hoverOverDragable = true;
			}
		}


		return hoverOverDragable;
	}

	@Override
	public void setFinish(int x, int y) {
		if(draggedItem != null)draggedItem.setFinish(xInTab(),yInTab() );
		draggedItem = null;
	}

	@Override
	public void setHeldLocation(int x, int y) {
		if(draggedItem != null)draggedItem.setHeldLocation(xInTab(),yInTab());

	}



}
