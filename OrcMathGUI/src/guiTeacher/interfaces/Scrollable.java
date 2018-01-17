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
package guiTeacher.interfaces;

/**
 * Must be implemented by Components that respond to Scrolling actions (mouse wheel)
 * @author bnockles
 *
 */
public interface Scrollable {

	/**
	 * Defines the bounds of this Component> Note: preserve the coordinates of parameters x,y for implementation of press() to determine relative position of press
	 * @param x x-coordinate of the mouse within context of immediate parent container
	 * @param y y-coordinate of the mouse within context of immediate parent container
	 * @return true if this Component is being hovered
	 */
	boolean isHovered(int x, int y);
	/**
	 * Called when a FocusController gives focus to this Component
	 * @param b
	 */
	void setFocus(boolean b);
	/**
	 * Called when the mouse wheel causes scrolling
	 * @param clicks can be positive or negative
	 */
	void scrollY(int clicks);
	/**
	 * called when this Component is pressed. To have the relative coordinates of the press, preserve values of isHovered, which is always called before press
	 */
	void press();
	/**
	 * called when this Component is released.
	 */
	void release();
	boolean isVisible();
}
