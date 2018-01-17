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

import guiTeacher.components.Action;

/**
 * A Visible object that can be clicked on. Note that a Clickable has no effect if it is not in at least a Clickable Screen
 * @author bnockles
 *
 */
public interface Clickable extends Visible {

	/**
	 * Defines the bounds within the object can be clicked
	 * @param x x-coordinates of the mouse within context of immediate parent container
	 * @param y y-coordinates of the mouse within context of immediate parent container
	 * @return true if this Clickable is hovered by mouse
	 */
	public boolean isHovered(int x, int y);
	/**
	 * The action that is executed when this object is clicked
	 */
	public void act();
	/**
	 * The action that is executed when this object is hovered
	 */
	public void hoverAction();
	/**
	 * Change the action associated with this Clickable
	 * @param a an Action
	 */
	public void setAction(Action a);
	
}
