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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import guiTeacher.interfaces.Visible;

public abstract class ComponentContainer extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6466250073705673444L;
	private List<Visible> viewObjects;
	private BufferedImage image;
	private BufferedImage formerImage;//used for transitions

	//used for animating transitions
	private int xScreen;
	private int yScreen;
	private int widthScreen;
	private int heightScreen;
	private int xTarget;
	private int yTarget;

	public ComponentContainer(int width, int height) {
		viewObjects = new ArrayList<Visible>();
		initImage(width, height);
		widthScreen = image.getWidth();
		heightScreen = image.getHeight();
		initObjects(viewObjects);
	}


	public ComponentContainer(int width, int height, ArrayList<Visible> initWithObjects) {
		viewObjects=initWithObjects;
		initImage(width, height);
		widthScreen = image.getWidth();
		heightScreen = image.getHeight();
		initObjects(viewObjects);
	}

	public void initImage(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		formerImage = null;
		update();

	}
	/**
	 * adds objects to viewObjects 
	 */
	public abstract void initObjects(List<Visible> viewObjects);


	public int[] calculateMaxXY(){
		int maxX = 1;
		int maxY = 1;
		for(Visible v: viewObjects){
			int tempX = v.getX()+v.getWidth();
			int tempY = v.getY()+v.getHeight();
			maxX = (tempX > maxX)?tempX : maxX;
			maxY = (tempY > maxY)?tempY : maxY;
		}
		int[] max = {maxX,maxY};
		return max;
	}



	public void addObject(Visible v){
		viewObjects.add(v);
	}

	//	public void paintComponents(Graphics g){
	//		g.drawImage(image, 0, 0, null);
	//	}
	//	
	public void paint(Graphics g){
		//		g.drawImage(image, 0, -22, null);
		int offset = -20;
		if(xScreen != 0 || yScreen != 0){
			BufferedImage transitionBuffer = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = transitionBuffer.createGraphics();
			if(formerImage != null) {
				g2.drawImage(formerImage, 0, 0, null);
				g2.drawImage(image, xScreen, yScreen+offset, xScreen+widthScreen,yScreen+heightScreen,xTarget,yTarget,xTarget+widthScreen,yTarget+heightScreen,null);
				g.drawImage(transitionBuffer, 0, offset, null);
			}
		}else{
			g.drawImage(image, 0,offset,null);			
		}
	}

	public void transitionWith(Transition t, BufferedImage formerScreenImage) {
		formerImage = formerScreenImage;
		xScreen = t.getxScreen();
		yScreen = t.getyScreen();
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


	public void update() {
		//		buffer = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		update(g);
		//		Graphics2D gi = buffer.createGraphics();
		//		gi.drawImage(buffer, getWidth(), getHeight(), null);
	}

	public void update(Graphics2D g2){
		//		BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		//		Graphics2D g2 = buffer.createGraphics();
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.white);
		g2.fillRect(0, 0, image.getWidth(), image.getHeight());
		g2.setColor(Color.black);
		drawObjects(g2);
		repaint();
		//		g.drawImage(buffer, 0, 0, null);
	}

	public void drawObjects(Graphics2D g){
		//iterate through all view objects
		for(int i = 0; i < viewObjects.size(); i++){
			Visible v= viewObjects.get(i);
			if(v.isVisible())g.drawImage(v.getImage(), v.getX(), v.getY(), null);
		}
	}

	public void remove(Visible v){
		if(viewObjects.contains(v)){
			viewObjects.remove(v);//all other objects slide up in order
		}
	}

	public void moveToFront(Visible v){
		if(viewObjects.contains(v)){
			viewObjects.remove(v);//all other objects slide up in order
			viewObjects.add(v);
		}
	}

	public void moveToBack(Visible v){
		if(viewObjects.contains(v)){
			viewObjects.remove(v);//all other objects slide up in order
			viewObjects.add(0, v);
		}
	}

	public void removeAll(){
		viewObjects = new ArrayList<Visible>();
	}

	public BufferedImage getImage(){
		return image;
	}
	public int getWidth(){
		return image.getWidth();
	}

	public int getHeight(){
		return image.getHeight();
	}

}
