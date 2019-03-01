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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import guiTeacher.interfaces.Clickable;
import guiTeacher.interfaces.Dragable;
import guiTeacher.interfaces.FocusController;
import guiTeacher.interfaces.KeyedComponent;
import guiTeacher.interfaces.Scrollable;
import guiTeacher.interfaces.Visible;

public abstract class FullFunctionScreen extends ClickableScreen implements KeyListener, FocusController, MouseWheelListener {

	private ArrayList<KeyedComponent>keyedComponents;
	private KeyedComponent activeKeyedComponent;
	private Scrollable activeScrollPane;

	public FullFunctionScreen(int width, int height) {
		super(width, height);
		if(keyedComponents.size() == 1){
			activeKeyedComponent = keyedComponents.get(0);
			activeKeyedComponent.setFocus(true);
		}
		else activeKeyedComponent = null;
		activeScrollPane = null;
	}

	@Override
	public void initObjects(List<Visible> viewObjects) {
		super.initObjects(viewObjects);
		keyedComponents = new ArrayList<KeyedComponent>();
		for(Visible v: viewObjects){
			if(v instanceof KeyedComponent){
				keyedComponents.add((KeyedComponent)v);
			}
		}

	}

	public void addObject(Visible v){
		super.addObject(v);
		if(v instanceof KeyedComponent){
			keyedComponents.add((KeyedComponent)v);
		}
	}



	public void remove(Visible v){
		super.remove(v);
		keyedComponents.remove(v);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(activeKeyedComponent != null){
			activeKeyedComponent.keyTyped(e);
		}
	}

	public void mouseClicked(MouseEvent m) {
		super.mouseClicked(m);
		for(KeyedComponent k: keyedComponents){
			if(k.isVisible() && k.isHovered(m.getX(), m.getY()) && k != activeKeyedComponent){
				moveFocus(k);
				break;
			}
		}
	}

	public void moveFocus(KeyedComponent k){
		if(k.isVisible()){
			if(activeKeyedComponent!=null)activeKeyedComponent.setFocus(false);
			k.setFocus(true);
			activeKeyedComponent = k;
		}
	}

	public KeyedComponent getFocusedComponent(){
		return activeKeyedComponent;
	}


	public void moveScrollFocus(Scrollable sp){
		if(sp.isVisible() && sp != activeScrollPane){
			if(activeScrollPane!=null)activeScrollPane.setFocus(false);
			sp.setFocus(true);
			activeScrollPane = sp;			
		}
	}

	@Override
	public void mouseDragged(MouseEvent m) {
		for(KeyedComponent k: keyedComponents){
			if(k.isVisible() && k.isHovered(m.getX(), m.getY()) && k != activeKeyedComponent){
				moveFocus(k);
				break;
			}
		}
		super.mouseDragged(m);
	}

	public void mousePressed(MouseEvent m) {
		super.mousePressed(m);
		if(activeScrollPane != null ){
			activeScrollPane.press();
		}
	}

	public void mouseReleased(MouseEvent m) {
		super.mouseReleased(m);
		if(activeScrollPane != null ){
			activeScrollPane.release();
		}
	}

	public Scrollable getScrollComponent(){
		return activeScrollPane;
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(activeKeyedComponent != null){
			activeKeyedComponent.keyPressed(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(activeKeyedComponent != null){
			activeKeyedComponent.keyReleased(e);
		}
	}

	@Override
	public abstract void initAllObjects(List<Visible> viewObjects);


	public MouseWheelListener getMouseWheelListener(){
		return this;
	}

	public KeyListener getKeyListener(){
		return this;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(activeScrollPane != null){
			//positiv is down, negative is up
			int rotation = e.getWheelRotation();
			activeScrollPane.scrollY(rotation);
		}
	}
}
