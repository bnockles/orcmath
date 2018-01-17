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

import java.awt.event.KeyListener;

/**
 * Must be implemented by any Component that responds to keyed entry
 * @author bnockles
 *
 */
public interface KeyedComponent extends KeyListener, Visible{

	
	/**
	 * Defines the bounds of this Component 
	 * @param x x-coordinate of the mouse within context of immediate parent container
	 * @param y y-coordinate of the mouse within context of immediate parent container
	 * @return true if this Component is being hovered
	 */
	public boolean isHovered(int x, int y);

	/**
	 * Called when a FocusController gives focus to this Component
	 * @param b
	 */
	public void setFocus(boolean b);

}
