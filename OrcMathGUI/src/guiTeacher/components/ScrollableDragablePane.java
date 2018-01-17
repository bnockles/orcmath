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

import java.util.ArrayList;

import guiTeacher.interfaces.Clickable;
import guiTeacher.interfaces.Dragable;
import guiTeacher.interfaces.FocusController;
import guiTeacher.interfaces.Visible;

public class ScrollableDragablePane extends ScrollablePane implements Dragable{

	protected Dragable dragItem;
	
	/**
	 * 
	 * @param focusController - the containing FocusController (usually a Screen)
	 * @param x - the x-coordinate within the container
	 * @param y - the y-coordinate within the container
	 * @param w - the width of the Pane
	 * @param h - the height of the Pane
	 */
	public ScrollableDragablePane(FocusController focusController, int x, int y, int w, int h) {
		super(focusController, x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param focusController - the containing FocusController (usually a Screen)
	 * @param initWithObjects - any objects this Pane is instantiated with
	 * @param x - the x-coordinate within the container
	 * @param y - the y-coordinate within the container
	 * @param w - the width of the Pane
	 * @param h - the height of the Pane
	 */
	public ScrollableDragablePane(FocusController focusController, ArrayList<Visible> initWithObjects, int x, int y,
			int w, int h) {
		super(focusController, initWithObjects, x, y, w, h);
		// TODO Auto-generated constructor stub
	}

//	public ScrollableDragablePane(FocusController focusController, Component container,
//			ArrayList<Visible> initWithObjects, int x, int y, int w, int h) {
//		super(focusController, container, initWithObjects, x, y, w, h);
//		// TODO Auto-generated constructor stub
//	}
	
	@Override
	public boolean setStart(int x, int y) {
		for(Clickable c: clickables){
			if(c.isHovered(xRelative, yRelative)){
				if(c instanceof Dragable){
					Dragable item = (Dragable)c;
					if(item.setStart(x,y)){
						dragItem = item;

					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void setFinish(int x, int y) {
		if(dragItem!=null){
			dragItem.setFinish(x, y);
			dragItem = null;
		}
	}

	@Override
	public void setHeldLocation(int x, int y) {
		if(dragItem != null)dragItem.setHeldLocation(x,y);
		
	}


}
