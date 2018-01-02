package guiPlayer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import guiTeacher.components.*;
import guiTeacher.interfaces.FocusController;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.ComponentContainer;

public class CustomPane extends Pane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7896999335068726352L;
	private TextArea label;
	private Button cancel;
	private Button okButton;
	
	private static final int _WIDTH = 250;
	private static final int _HEIGHT = 200;

	public CustomPane(FocusController focusController, int x, int y) {
		super(focusController, x, y, _WIDTH, _HEIGHT);
		// TODO Auto-generated constructor stub
	}
	

	public void update(Graphics2D g){
		//customize the background
		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.red);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		//draw the objects
		super.drawObjects(g);
	}

	public void initAllObjects(List<Visible> viewObjects){
		label = new TextArea(0,0,getWidth(),_HEIGHT - 45,"This is a sample alert message. The entire red box is one pane. All of the text and the buttons move as one unit. Click 'OK' to watch this in action");
		cancel = new Button(5,_HEIGHT - 43,65,40,"Cancel", Color.GRAY, new Action(){

			@Override
			public void act() {
				CustomPane.this.setVisible(false);
				
			}
				
		});
		okButton = new Button(_WIDTH - 67,_HEIGHT-43,65,40,"OK", Color.WHITE, new Action(){

			@Override
			public void act() {
				int finalX = (int)(Math.random()*(parentScreen.getWidth()-_WIDTH));
				int finalY = (int)(_HEIGHT+Math.random()*(parentScreen.getHeight()-2*_HEIGHT));
				move(finalX, finalY, 1000);
//				Thread mover = new Thread(new Runnable() {
//					
//					@Override
//					public void run() {
//						double changeX = (finalX - CustomPane.this.getX())/60;
//						double changeY= (finalY - CustomPane.this.getY())/60;
//						for(int i = 0; i < 60; i++){
//							CustomPane.this.setX((int)(CustomPane.this.getX()+changeX));
//							CustomPane.this.setY((int)(CustomPane.this.getY()+changeY));
//							try {
//								Thread.sleep(40);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//						
//					}
//				});
//				mover.start();
			}
				
		});
		viewObjects.add(label);
		viewObjects.add(okButton);
		viewObjects.add(cancel);
	}

}
