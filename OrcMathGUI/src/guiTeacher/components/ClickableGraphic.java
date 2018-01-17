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

import guiTeacher.interfaces.Clickable;

/**
 * A Graphic that can be clicked
 * @author bnockles
 *
 */
public class ClickableGraphic extends Graphic implements Clickable {

	private Action action;
	
	/**
	 * 
	 * @param x x-coordinate within context of parent ComponentContainer
	 * @param y y-coordinate within context of parent ComponentContainer
	 * @param w pixel width (preserves aspect ratio)
	 * @param h pixel height (preserves aspect ratio)
	 * @param imageLocation Location within specified source folder (for example: resources/image.png)
	 */
	public ClickableGraphic(int x, int y, int w, int h, String imageLocation) {
		super(x, y, w, h, imageLocation);
	}

	/**
	 * 
	 * @param x x-coordinate within context of parent ComponentContainer
	 * @param y y-coordinate within context of parent ComponentContainer
	 * @scale percentage scale of original image. Will set width and height based on this scale
	 * @param imageLocation Location within specified source folder (for example: resources/image.png)
	 */
	public ClickableGraphic(int x, int y, double scale, String imageLocation) {
		super(x, y, scale, imageLocation);
	}

	/**
	 * Set the action associated with clicking this Graphic
	 */
	public void setAction(Action a){
		this.action = a;
	}
	
	/**
	 * 
	 * @param x x-coordinate within context of parent ComponentContainer
	 * @param y y-coordinate within context of parent ComponentContainer
	 * @param imageLocation Location within specified source folder (for example: resources/image.png) Width and height will match original
	 */
	public ClickableGraphic(int x, int y, String imageLocation) {
		super(x, y, imageLocation);
	}

	public boolean isHovered(int x, int y) {
		return x > getX() && x < getX() + getWidth() && y > getY() && y < getY() + getHeight();
	}

	public void act() {
		if(action != null)action.act();
	}
	
	public void hoverAction(){
		//most Components don't do anything on hover
	}

}
