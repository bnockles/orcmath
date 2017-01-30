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
import java.util.List;

import guiTeacher.interfaces.Clickable;
import guiTeacher.interfaces.FocusController;
import guiTeacher.interfaces.Scrollable;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.ComponentContainer;

public class ScrollablePane extends ComponentContainer implements Clickable, Scrollable{

	/**
	 * margin at end of scroll pane
	 */
	public static final int X_MARGIN =5;
	public static final int Y_MARGIN =5;
	
	
	protected ArrayList<Clickable> clickables;
	protected BufferedImage contentImage;
	private boolean active;
	private boolean visible;
	
	private int x;
	private int y;
	private final FocusController parentScreen;
	private Component containingComponent;//some components like Accordion contain ScrollapblePanes
//	private Scrollable content;
	private int contentX;
	private int contentY;
	private int maxY;
	protected int xRelative;
	protected int yRelative;
	
	public ScrollablePane(FocusController focusController, int x, int y, int w, int h) {
		super(w, h);
		this.x = x;
		this.y = y;
		this.parentScreen=focusController;
		visible = true;
		containingComponent = null;
		setUpContentImage();
		update();		
	}
	
	public ScrollablePane(FocusController focusController, ArrayList<Visible> initWithObjects, int x, int y, int w, int h) {
		super(w, h,initWithObjects);
		this.x = x;
		this.y = y;
		this.parentScreen=focusController;
		containingComponent = null;
		visible = true;
		setUpContentImage();
		update();
	}
	
	public ScrollablePane(FocusController focusController, Component container, ArrayList<Visible> initWithObjects, int x, int y, int w, int h) {
		super(w, h,initWithObjects);
		this.x = x;
		this.y = y;
		this.parentScreen=focusController;
		containingComponent = container;
		setUpContentImage();
		update();
	}
	
	/**
	 * calculates maximum x and y value, given all components
	 */
	public void setUpContentImage(){
		contentX = 0;
		contentY=0;
		int[] maxXAndY = calculateMaxXY();
		maxY = maxXAndY[1];
		setContentImage(maxXAndY[0],maxXAndY[1]);
		
	}
	
	
	
	public void setContentImage(int w, int h) {
		contentImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	public void initObjects(List<Visible> viewObjects) {
		initAllObjects(viewObjects);
		clickables = new ArrayList<Clickable>();
		for(Visible v: viewObjects){
			if(v instanceof Clickable){
				clickables.add((Clickable)v);
			}
		}

	}
	
	/**
	 * override by subclasses to add objects manually
	 * @param viewObjects
	 */
	public void initAllObjects(List<Visible> viewObjects){
	
	}

	@Override
	public boolean isHovered(int x, int y) {
		boolean hov =x > getX() && y > getY() && x<getX()+getWidth() && y < getY()+getHeight();
		if(hov){
			xRelative = x - getX();
			yRelative = y - getY();
		}
		return hov;
	}

	public void addObject(Visible v){
		super.addObject(v);
		setUpContentImage();
	}
	
	public void remove(Visible v){
		super.remove(v);
		setUpContentImage();
	}
	
	public void removeAll(){
		super.removeAll();
		setUpContentImage();
	}
	
	public void hoverAction(){
		parentScreen.moveScrollFocus(this);	
		for(Clickable c:clickables){
			if(c.isHovered(xRelative+contentX, yRelative+contentY)){
				c.hoverAction();
//				don't break because sometime objects have tasks after hovering is complete
			}
		}
	}
	
	//x and y are relative to the pane
	public void act() {
		for(Clickable c: clickables){
			if(c.isHovered(xRelative+contentX, yRelative+contentY)){
				c.act();
				update();
				break;
			}
		}
	}
	
	public BufferedImage getContentImage(){
		return contentImage;
	}

	@Override
	public void update(Graphics2D g) {
		if(contentImage != null) {
			Graphics2D gContent  = contentImage.createGraphics();
			gContent.setColor(Color.WHITE);
			gContent.fillRect(0, 0, contentImage.getWidth(), contentImage.getHeight());
			super.update(gContent);
			g.drawImage(contentImage, 0, 0, getWidth(), getHeight(), contentX, contentY, contentX+getWidth(), contentY+getHeight(), null);
		}
	}

	public void setFocus(boolean b) {
		active = b;
	}
	
	public void scrollY(int clicks){
		contentY+=clicks;
		if(contentY<0)contentY=0;
		if(contentY+getHeight()>contentImage.getHeight())contentY=(contentImage.getHeight()-getHeight()>0)? contentImage.getHeight()-getHeight(): 0;
		update();
		if(containingComponent != null)containingComponent.update();
	}

	public int getScrollX() {
		return contentX;
	}

	public int getScrollY() {
		return contentY;
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setX(int x) {
		this.x = x;
		
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public boolean isAnimated() {
		return false;
	}

	@Override
	public void setAction(Action a) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isVisible() {
		return visible;
	}


	public void setVisible(boolean visible) {
		this.visible = visible;
	}


	
	
}
