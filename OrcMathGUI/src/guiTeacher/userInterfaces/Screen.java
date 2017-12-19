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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import guiTeacher.interfaces.Visible;

public abstract class Screen extends ComponentContainer{

	
	
	public Screen(int width, int height) {
		super(width, height);
	}

	public Screen(int width, int height, ArrayList<Visible> initWithObjects) {
		super(width,height,initWithObjects);
		
	}
	

	
	public void update(Graphics2D g){
		BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = buffer.createGraphics();
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
		drawBackground(g2);
		g2.setColor(Color.black);
		drawObjects(g2);
		g.drawImage(buffer, 0, 0, null);
	}


	protected void drawBackground(Graphics2D g2) {
		g2.setColor(getScreenBackground());
		g2.fillRect(0, 0, getImage().getWidth(), getImage().getHeight());
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
