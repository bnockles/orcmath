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
package guiTeacher;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import guiTeacher.userInterfaces.ComponentContainer;
import guiTeacher.userInterfaces.Screen;
import guiTeacher.userInterfaces.Transition;

public abstract class GUIApplication extends JFrame implements Runnable, ComponentListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 390738816689963935L;
	public static JFrame mainFrame;
	private Screen currentScreen;
	private boolean scaleWithWindow; 
	



	public GUIApplication(int width, int height){
		super();
		scaleWithWindow = true;
		setBounds(20, 20, width, height);
		initScreen();
		setUndecorated(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame = this;
		addComponentListener(this);
	}



	public abstract void initScreen();

	public void setScreen(Screen screen) {
		removeListeners();
		currentScreen = screen;
		if (!screen.isFixedSize() && (screen.getWidth() != this.getWidth() || screen.getHeight() != this.getHeight())){
			resize(getWidth(), getHeight());
		}
		setContentPane(currentScreen);
		addListeners();
	}

	public void setScreen(Screen screen, Transition t) {
		removeListeners();
		ComponentContainer oldScreen = currentScreen;
		screen.transitionWith(t, oldScreen.getImage());
		setContentPane(screen);
		currentScreen = screen;
		addListeners();
		
	}


	private void removeListeners(){
		if(currentScreen != null){
			if(currentScreen.getMouseListener() != null) removeMouseListener(currentScreen.getMouseListener());
			if(currentScreen.getMouseMotionListener() != null) removeMouseMotionListener(currentScreen.getMouseMotionListener());
			if(currentScreen.getKeyListener() != null) removeKeyListener(currentScreen.getKeyListener());
			if(currentScreen.getMouseWheelListener() != null) removeMouseWheelListener(currentScreen.getMouseWheelListener());
		}
	}

	private void addListeners(){
		if(currentScreen != null){
			if(currentScreen.getMouseListener() != null)addMouseListener(currentScreen.getMouseListener());
			if(currentScreen.getMouseMotionListener() != null) addMouseMotionListener(currentScreen.getMouseMotionListener());
			if(currentScreen.getKeyListener() != null) addKeyListener(currentScreen.getKeyListener());
			if(currentScreen.getMouseWheelListener() != null) addMouseWheelListener(currentScreen.getMouseWheelListener());
		}
	}


	public void run() {
		long updateTime;
		long timeAfterUpdate;
		while(true){
			updateTime = System.currentTimeMillis();
			currentScreen.update();
			repaint();
			timeAfterUpdate = 30-(System.currentTimeMillis()-updateTime);
			try {
				if(timeAfterUpdate > 0)
				Thread.sleep(timeAfterUpdate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public void resize(int w, int h){
		if (!currentScreen.isFixedSize()){
			currentScreen.resize(w, h);
		}
	}

	/**
	 * On resize, every component is simply recreated, as initObjects is ultimately recalled.
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		resize(getWidth(), getHeight());
	}



	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
