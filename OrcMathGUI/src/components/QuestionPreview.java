/*******************************************************************************
 * Copyright (c) 2017 Benjamin Nockles
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
package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import guiTeacher.components.Action;
import guiTeacher.components.Button;
import guiTeacher.components.Graphic;
import guiTeacher.components.ScrollablePane;
import guiTeacher.components.StyledComponent;
import guiTeacher.components.TextColoredLabel;
import guiTeacher.components.TextLabel;
import guiTeacher.interfaces.FocusController;
import guiTeacher.interfaces.KeyedComponent;
import guiTeacher.interfaces.Scrollable;
import guiTeacher.interfaces.Visible;

public class QuestionPreview extends JFrame implements FocusController, MouseWheelListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7300624625753770096L;
	public static final int WIDTH = 530;
	public static final int HEIGHT = 360;

	private ScrollablePreview pane;
	private boolean initialized;
	private String currentlyShowingType;
	private int currentlyShowingDifficulty;
	private int originalX;
	private int originalY;

	public QuestionPreview(int x, int y) {
		super();
		this.originalX=x;
		this.originalY=y;
		setBounds(x,y,WIDTH,HEIGHT);
		setUndecorated(true);
		initialized = false;
		currentlyShowingDifficulty = 0;
		currentlyShowingType ="";
		pane = new ScrollablePreview( 0, 0, WIDTH, HEIGHT);
		addMouseWheelListener(this);
		addMouseListener(pane);
	}
	
	public QuestionPreview(int x, int y, int w, int h) {
		super();
		this.originalX=x;
		this.originalY=y;
		setBounds(x,y,w,h);
		setUndecorated(true);
		initialized = false;
		currentlyShowingDifficulty = 0;
		currentlyShowingType ="";
		pane = new ScrollablePreview( 0, 0, w, h);
		addMouseWheelListener(this);
		addMouseListener(pane);
	}

	public boolean isInitialized(){
		return initialized;
	}

	public void setImage(BufferedImage image){

			Graphic example = new Graphic(0, 0,image);

			initialized = true;
			//			System.out.println("QuestionPreview.java "+question);
			pane.addObject(example);
			pane.update();
			repaint();

		

	}
	
	public void setExample(String question, int difficulty){
		if(!question.equals(currentlyShowingType) || difficulty !=currentlyShowingDifficulty){
			Graphic example = new Graphic(0, 0,"resources/examples/difficulty"+difficulty+"/"+question.replace(":", "_")+".png");
			currentlyShowingDifficulty = difficulty;
			currentlyShowingType = question;
			initialized = true;
			//			System.out.println("QuestionPreview.java "+question);
			pane.addObject(example);
			pane.update();
			repaint();

		}

	}

	public void changeDifficulty(int newDifficulty){
		setExample(currentlyShowingType, newDifficulty);
	}

	public void paint(Graphics g){
		g.drawImage(pane.getImage(), 0, 0, null);
	}

	private class ScrollablePreview extends ScrollablePane implements MouseListener{

		private TextColoredLabel heading;
		private TextLabel difficulty;
		private int difficultyTrack;
		private final String _DIFFICULTY_TEXT = "Difficulty: ";
		private Button down;
		private Button up;

		public ScrollablePreview(int x, int y, int w, int h) {
			super(QuestionPreview.this, x, y, w, h);
			heading = new TextColoredLabel(0, 0, w, 23, "Example", new Color(76,0,153), Color.white);
			heading.setSize(12);
			heading.setAlign(StyledComponent.ALIGN_CENTER);

			int yL = h-30;
			int xL = 155;
			int buttonW = 25;
			int space = 5;
			int textW = 100;
			int height = 23;
			difficultyTrack = 1;

			difficulty = new TextLabel(xL+buttonW+space,yL,textW,height,_DIFFICULTY_TEXT+difficultyTrack);
			difficulty.setCustomAlign(StyledComponent.ALIGN_CENTER);
			down = new Button(xL, yL, buttonW, height, "-", new Color(204,153,255), new Action() {

				@Override
				public void act() {
					if(difficultyTrack >1 ){
						difficultyTrack --;
						difficulty.setText(_DIFFICULTY_TEXT+difficultyTrack);
					}
				}
			});
			
			up = new Button(xL+buttonW+2*space+textW, yL, buttonW, height, "+", new Color(153,255,204),new Action() {

				@Override
				public void act() {
					if(difficultyTrack <4 ){
						difficultyTrack ++;
						difficulty.setText(_DIFFICULTY_TEXT+difficultyTrack);
					}
				}
			});

		}

		@Override
		public void update(Graphics2D g) {
			BufferedImage contentImage = getContentImage();

			if(contentImage != null && heading != null) {
				double scale = (double)contentImage.getWidth()/QuestionPreview.WIDTH; 
				//				if(scale >1.0)scale = 1.0;
				int contentX = getScrollX();
				int contentY = getScrollY();
				Graphics2D gContent  = contentImage.createGraphics();
				gContent.setColor(Color.WHITE);
				gContent.fillRect(0, 0, contentImage.getWidth(), contentImage.getHeight());
				drawObjects(gContent);
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, (int)(QuestionPreview.this.getBounds().getWidth()), (int)(QuestionPreview.this.getBounds().getHeight()));
				g.drawImage(contentImage, 0, heading.getHeight(), getWidth(), getHeight()- heading.getHeight(), contentX, contentY, (int)(contentX+getWidth()*scale), (int)(contentY+getHeight()*scale) -heading.getHeight(), null);
				g.drawImage(heading.getImage(),0,0,null);
				g.drawImage(difficulty.getImage(),difficulty.getX(),difficulty.getY(),null);
				g.drawImage(down.getImage(),down.getX(),down.getY(),null);
				g.drawImage(up.getImage(),up.getX(),up.getY(),null);
			}
		}

		public void addObject(Visible v){
			removeAll();
			super.addObject(v);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if(down.isHovered(e.getX(), e.getY())){
				down.act();
				QuestionPreview.this.changeDifficulty(difficultyTrack);
			}
			if(up.isHovered(e.getX(), e.getY())){
				up.act();
				QuestionPreview.this.changeDifficulty(difficultyTrack);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int clicks = e.getWheelRotation();
		pane.scrollY(clicks);
		repaint();
	}

	@Override
	public void moveFocus(KeyedComponent k) {
		// TODO Auto-generated method stub

	}

	@Override
	public KeyedComponent getFocusedComponent() {
		return null;
	}

	@Override
	public void moveScrollFocus(Scrollable sp) {
		// TODO Auto-generated method stub

	}

	@Override
	public Scrollable getScrollComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getOriginalX() {
		return originalX;
	}


	public int getOriginalY() {
		return originalY;
	}


}
