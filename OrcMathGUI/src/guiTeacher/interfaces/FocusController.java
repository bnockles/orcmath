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
 * A focus controller shifts the focus of the scrolling pane. (When a Pane is clicked on, the focus controller gives focus to that pane so that clicks, keyed entry and mouse wheel are handled by that Pane)
 * @author bnockles
 *
 */
public interface FocusController {

	/**
	 * Moves the focus
	 * @param k the Component that focus is to move to 
	 */
	public void moveFocus(KeyedComponent k);
	/**
	 * 
	 * @return the Component that currently has focus
	 */
	public KeyedComponent getFocusedComponent();
	/**
	 * Moves the focus of the mouse wheel
	 * @param sp the scrollable that is to have focus
	 */
	public void moveScrollFocus(Scrollable sp);
	/**
	 * 
	 * @return the Component that currently has focus of the mouse wheel
	 */
	public Scrollable getScrollComponent();
	public int getWidth();
	public int getHeight();

	
}
