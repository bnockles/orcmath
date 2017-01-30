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
package guiTeacher.userInterfaces;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import guiTeacher.interfaces.Visible;

public abstract class Screen extends ComponentContainer{

	
	
	public Screen(int width, int height) {
		super(width, height);
	}

	public Screen(int width, int height, ArrayList<Visible> initWithObjects) {
		super(width,height,initWithObjects);
		
	}



	public MouseListener getMouseListener() {
		return null;
	}
	
	public MouseMotionListener getMouseMotionListener() {
		return null;
	}
	
	public KeyListener getKeyListener() {
		return null;
	}
	
	public MouseWheelListener getMouseWheelListener(){
		return null;
	}

}
