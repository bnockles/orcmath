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

public interface Dragable extends Visible{

	
	
	/**
	 * 
	 * @param x x coord at start of drag
	 * @param y y coord at start of drag
	 * @return true if drag is allowed
	 */
	boolean setStart(int x, int y);

	/**
	 * location when drag is released
	 * @param x
	 * @param y
	 */
	void setFinish(int x, int y);

	/**
	 * location while being dragged
	 * @param x
	 * @param y
	 */
	void setHeldLocation(int x, int y);

}
