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

import javax.swing.JFrame;

import guiTeacher.userInterfaces.Screen;
import guiTeacher.userInterfaces.Transition;

public abstract class GUIApplication extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 390738816689963935L;
	private Screen currentScreen;
	private boolean scaleWithWindow; 
	//variables to describe location of incoming Screen during transitions
	private int xScreen;
	private int yScreen;
	private int widthScreen;
	private int heightScreen;
	private int xTarget;
	private int yTarget;

	//this method gets deleted once generalized
	//	public static void main(String[] args){
	//		Thread app = new Thread(new GUIApplication(500, 500));
	//		app.start();
	//	}

	public GUIApplication(int width, int height){
		super();
		scaleWithWindow = true;
		setBounds(20, 20, width, height);
		initScreen();
		setUndecorated(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		xScreen=0;
		yScreen=0;
		setVisible(true);
	}

	//this method becomes abstract once generalized
	//	public void initScreen() {
	//		TextScreen startScreen= new TextScreen("Hi everyone",getWidth(), getHeight());
	//		addMouseMotionListener(new CoordinateListener(startScreen));
	//		setScreen(startScreen);
	//		
	//	}

	public abstract void initScreen();

	public void setScreen(Screen screen) {
		removeListeners();
		xScreen = 0;
		yScreen = 0;
		widthScreen = screen.getWidth();
		heightScreen = screen.getHeight();
		xTarget = 0;
		yTarget = 0;
		currentScreen = screen;
		addListeners();
	}

	public void setScreen(Screen screen, Transition t) {
		removeListeners();
		xScreen = t.getxScreen();
		yScreen = t.getyScreen();
		widthScreen = t.getWidthScreen();
		heightScreen = t.getHeightScreen();
		xTarget = t.getxTarget();
		yTarget = t.getxTarget();
		currentScreen = screen;
		addListeners();
		Thread transition = new Thread(new Runnable() {

			@Override
			public void run() {
				long moveTime = System.currentTimeMillis();
				while(t.getTime()>0){

					try {
						Thread.sleep(20);
						t.step(20+System.currentTimeMillis()-moveTime);
						moveTime = System.currentTimeMillis();
						xScreen = t.getxScreen();
						yScreen = t.getyScreen();
						widthScreen = t.getWidthScreen();
						heightScreen = t.getHeightScreen();
						xTarget = t.getxTarget();
						yTarget = t.getyTarget();
						repaint();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				xScreen =0;
				yScreen =0;
				widthScreen=getWidth();
				heightScreen=getHeight();
				xTarget=0;
				yTarget=0;
			}
		});
		transition.start();
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

	public void paint(Graphics g){
//		g.drawImage(currentScreen.getImage(), xScreen, yScreen, widthScreen,heightScreen,xTarget,yTarget,widthScreen,heightScreen,null);
		g.drawImage(currentScreen.getImage(), xScreen, yScreen, xScreen+widthScreen,yScreen+heightScreen,xTarget,yTarget,xTarget+widthScreen,yTarget+heightScreen,null);
		//		if(scaleWithWindow){
//					g.drawImage(currentScreen.getImage(), xScreen, yScreen, getWidth(),getHeight(),null);
		//		}else{
		//
		//			g.drawImage(currentScreen.getImage(), xScreen, yScreen, null);
		//		}
	}

	public void run() {
		while(true){
			currentScreen.update();
			repaint();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
