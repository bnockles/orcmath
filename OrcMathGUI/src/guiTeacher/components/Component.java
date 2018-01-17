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
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import guiTeacher.interfaces.Visible;

public abstract class Component implements Visible {

	private int x;
	private int y;
	private int w;
	private int h;
	private float alpha;
	private BufferedImage image;
	private BufferedImage buffer;
	private Color foreground;
	private Color background;
	private Visible containingComponent;
	private boolean visible;
	private Action hoverAction;
	private boolean left;//a boolean to keep track of when a pointer leaves this object
	
	public Component(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.alpha = 1.0f;
		foreground = Color.black;
		visible = true;
		background = null;
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	}
	
	
	public boolean hasLeft() {
		return left;
	}


	public void setLeft(boolean left) {
		this.left = left;
	}


	public BufferedImage getImage() {
		return image;
	}
	
	public void setContainer(Visible container){
		containingComponent = container;
	}

	public void clear(){
		image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
//		return image.createGraphics();
	}
	
	
	/**
	 * 
	 * @return the foreground Color for this Component. Foreground color is used most often for text Components
	 */
	public Color getForeground() {
		return foreground;
	}

	/**
	 * 
	 * @param foreground the foreground Color for this Component. Foreground color is used most often for text Components. Default is black
	 */
	public void setForeground(Color foreground) {
		this.foreground = foreground;
		update();
	}

	
	/**
	 * 
	 * @return the custom backgroundColor for this Component
	 */
	public Color getBackground() {
		return background;
	}

	/**
	 * Use this method to set a custom background color for this component. Components default to null
	 * @param background 
	 */
	public void setBackground(Color background) {
		this.background = background;
	}

	/**
	 * get the pixel distance from the left side of the container
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x-coordinate of this component (within context of container)
	 */
	public void setX(int x){
		this.x = x;
	}
	
	/**
	 * @param y the pixel distance from the top side of the container
	 */
	public void setY(int y){
		this.y = y;
	}
	
	/**
	 * 
	 * get the pixel distance from the top side of the container
	 *
	 */
	public int getY() {
		return y;
	}

	/**
	 * the width of this Component, in pixels
	 */
	public int getWidth() {
		return w;
	}

	/**
	 * the height of this Component, in pixels
	 */
	public int getHeight() {
		return h;
	}

	
	public Graphics2D resize(){
		clear();
		return image.createGraphics();
	}
	
	/**
	 * Updates the image representing this component
	 */
	public void update(){
		BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = buffer.createGraphics();
		applyStyles(g);
		update(g);
		image.createGraphics().drawImage(buffer, 0, 0, null);
		if(containingComponent != null)containingComponent.update();
	}
	
	protected void applyStyles(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	/**
	 * Called automatically by update()
	 * Override this method to define the appearance of this Component. Note that the image is restricted with in the bounds from 0 to getWidth() and 0 to getHeight()
	 * @param g
	 */
	public abstract void update(Graphics2D g);

	public boolean isAnimated() {
		return false;
	}

	
	
	public Visible getContainingComponent() {
		return containingComponent;
	}


	public void setContainingComponent(Visible containingComponent) {
		this.containingComponent = containingComponent;
	}


	public void hoverAction(){
		doHoverAction();
	}
	
	public void doHoverAction(){
		if(hoverAction!=null)hoverAction.act();
	}
	
	/**
	 * Set the action that will occur automatically when this Component is hovered by the mouse
	 * @param a
	 */
	public void setHoverAction(Action a){
		this.hoverAction = a;
	}


	public boolean isVisible() {
		return visible;
	}

/**
 * sets the visible value of this Component. A Component that is invisible is also inactive
 */
	public void setVisible(boolean visible) {
		update();
		this.visible = visible;
	}
	
	@Override
	public void unhoverAction() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public float getAlpha() {
		return alpha;
	}

/**
 * Set the transparency of this Component: 0 = completely transparent
 * 1.0 = completely opaque
 */
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	/**
	 * Call this method to animate a movement from existing position to new position
	 * @param newX the new x-coordinate
	 * @param newY the new y-coordinate
	 * @param durationMS the length of time during which the animation runs
	 */
	public void move(int newX, int newY, int durationMS){
		Visible.move(this, newX, newY, durationMS);
	}
	
	public void setDimensions(int width, int height) {
		this.w = width;
		this.h = height;
		clear();
		update();
	}
	
}
