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

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import guiTeacher.interfaces.Visible;

/**
 * The quintessential container for Visible objects. There are two types of ComponentContainers, Screens that fill the whole window and Panes that have specified location width and height
 * @author bnockles
 *
 */
public abstract class ComponentContainer extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6466250073705673444L;
	private List<Visible> startingObjects;
	private List<Visible> viewObjects;
	private BufferedImage image;
	private BufferedImage formerImage;//used for transitions
	private int borderWidth;
	private Color borderColor;
	private Color backgroundColor;

	//used for animating transitions
	private float alpha;
	private int xScreen;
	private int yScreen;
	private int widthScreen;
	private int heightScreen;
	private int xTarget;
	private int yTarget;
	private boolean fixedSize;

	/**
	 * 
	 * @param width initial pixel width
	 * @param height initial pixel height
	 */
	public ComponentContainer(int width, int height) {
		alpha = 1.0f;
		widthScreen = width;
		heightScreen = height;
		backgroundColor = Color.white;
		startingObjects = new ArrayList<Visible>();
		create();
	}

/**
 * 
 * @param width initial pixel width
 * @param height initial pixel height
 * @param initWithObjects initial Visible objects in this ComponentContainer
 */
	public ComponentContainer(int width, int height, ArrayList<Visible> initWithObjects) {
		alpha = 1.0f;
		widthScreen = width;
		heightScreen = height;
		startingObjects = initWithObjects;
		backgroundColor = Color.white;
		create();
	}

	/**
	 * Will resize the ComponentContainer and complete refresh it
	 * @param w
	 * @param h
	 */
	public void setDimensions(int w, int h){
		widthScreen = w;
		heightScreen = h;
		startingObjects = viewObjects;
		create();
	}
	
	/** 
	 * Called by constructors only (because this method calls initObjects, which will actually wipe out temp data from UI objects)
	 */
	private void create(){
		viewObjects = new ArrayList<Visible>();
		for (Visible v: startingObjects){
			viewObjects.add(v);
		}
		initImage(widthScreen, heightScreen);
		update();
		initObjects(viewObjects);
	}
	
	
	/**
	 * 
	 * @return the background color of this ComponentContainer
	 */
	public Color getScreenBackground() {
		return backgroundColor;
	}
	
	/**
	 * 
	 * @param c the background color of this ComponentContainer
	 */
	public void setBackground(Color c){
		this.backgroundColor = c;
	}
	
	private void initImage(int width, int height) {
		if (width > 0 && height > 0){
			borderColor = Color.black;
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			formerImage = null;
		}


	}
	/**
	 * adds objects to viewObjects 
	 */
	public abstract void initObjects(List<Visible> viewObjects);

/**
 * Used for resizing Scrollable Panes
 * @return an int[] of length 2, specifying the maximum x- and y- coordinate of the most extreme Component
 */
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

	@Override
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

	/**
	 * Draws a border around this ComponentContainer if the width of the border is >0
	 * @param g
	 */
	public void drawBorder(Graphics2D g){
		if(getBorderWidth() >0){
			g.setColor(getBorderColor());
			Stroke s = g.getStroke();
			g.setStroke(new BasicStroke(getBorderWidth()));
			g.drawRect(getBorderWidth()/2, getBorderWidth()/2, getWidth()-getBorderWidth(), getHeight()-getBorderWidth());
			g.setStroke(s);
		}
	}


/**
 * 
 * @return the Color of this ComponentContainer
 */
	public Color getBorderColor() {
		return borderColor;
	}


	/**
	 * 
	 * @param borderColor the Color of this ComponentContainer
	 */
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	
	



/**
 * 
 * @return the pixel thickness of this ComponentContainer
 */
	public int getBorderWidth() {
		return borderWidth;
	}


	/**
	 * 
	 * @param borderWidth the pixel thickness of this ComponentContainer
	 */
	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}

/**
 * called by GUIApplication when performing a Transition 
 * @param t
 * @param formerScreenImage
 */
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

	/**
	 * Clears this image and calls update(Graphics2D g) 
	 */
	public void update(){
		BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = buffer.createGraphics();
		update(g);
		image.createGraphics().drawImage(buffer, 0, 0, null);
	}

	/**
	 * Updates all Components in this ComponentContainer 
	 */
	public void update(Graphics2D g2){
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getScreenBackground());
		g2.fillRect(0, 0, image.getWidth(), image.getHeight());
		g2.setColor(Color.black);
		drawObjects(g2);
		repaint();
	}

	/**
	 * Draws all Visible objects, called by update(Graphics2D)
	 * @param g
	 */
	public void drawObjects(Graphics2D g){
		//iterate through all view objects
		for(int i = 0; i < viewObjects.size(); i++){
			Visible v= viewObjects.get(i);
			if(v.isVisible()){
				if(v.getAlpha() == 1f){
					g.drawImage(v.getImage(), v.getX(), v.getY(), null);
				}else{
						float alpha = v.getAlpha();
						Composite orig = g.getComposite();
						AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
						g.setComposite(ac);
						g.drawImage(v.getImage(), v.getX(), v.getY(), null);
						g.setComposite(orig);
				}
			}
		}
	}

	/**
	 * removes a Visible Component
	 * @param v the Visible to be removed
	 */
	public void remove(Visible v){
		if(viewObjects.contains(v)){
			viewObjects.remove(v);//all other objects slide up in order
		}
	}

	/**
	 * Moves a visible to the front
	 * @param v
	 */
	public void moveToFront(Visible v){
		if(viewObjects.contains(v)){
			viewObjects.remove(v);//all other objects slide up in order
			viewObjects.add(v);
		}
	}

	/**
	 * Moves a Visible to the back
	 * @param v
	 */
	public void moveToBack(Visible v){
		if(viewObjects.contains(v)){
			viewObjects.remove(v);//all other objects slide up in order
			viewObjects.add(0, v);
		}
	}

	/**
	 * removes all Visible objects
	 */
	public void removeAll(){
		viewObjects = new ArrayList<Visible>();
	}

	/**
	 * 
	 * @return the image of this ComponentContainer 
	 */
	public BufferedImage getImage(){
		return image;
	}
	
	/**
	 * @return the width of this ComponentContainer
	 */
	public int getWidth(){
		return image.getWidth();
	}

	/**
	 * @return teh height of this ComponentContainer
	 */
	public int getHeight(){
		return image.getHeight();
	}

	/**
	 * Set to true in order to prevent Screens from adopting the size of their Window containers
	 * @param b
	 */
	public void setFixedSize(boolean b){
		fixedSize = b;	
	}

	/**
	 * 
	 * @return true if this ComponentContainer adopts the size of their Window containers
	 */
	public boolean isFixedSize(){
		return fixedSize;
	}

	/**
	 * Called indirectly by GUIApplication whenever the containing Window is resized
	 * To resize components, override this method. Default implementation has no effect
	 */
	public void  resizeComponents(){
	}
	
	/**
	 * Called by GUIApplication whenever the containing Window is resized
	 */
	public void resize(int width, int height){
		this.widthScreen = width;
		this.heightScreen = height;
		initImage(widthScreen, heightScreen);
		resizeComponents();
		update();
	}

	/**
	 * 
	 * @return the opacity of this ComponentContainer 
	 */
	public float getAlpha() {
		return alpha;
	}

	/**
	 * Set the opacity of this ComponentContainer
	 */
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	
	
}
