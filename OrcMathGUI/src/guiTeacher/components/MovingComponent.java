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

import java.awt.Graphics2D;

public abstract class MovingComponent extends Component implements Runnable{
	private long moveTime; //time when the image last moved
	private double vx; //the horizontal velocity
	private double vy; //the vertical velocity
	private double posx; //the actual x-coordinate of the object
	private double posy; //the actual y-coordinate of the object
	private boolean running;

	public static final int REFRESH_RATE = 20;

	public MovingComponent(int x, int y, int w, int h) {
		super(x, y, w, h);
		posx = x;
		posy = y;
		vx = 0;
		vy = 0;
	}


	public boolean isAnimated(){
		return true;
	}
	

	public void run(){
		posx= getX();
		posy= getY();
		running = true;
		moveTime = System.currentTimeMillis();
		while(running){

			try {
				Thread.sleep(REFRESH_RATE);
				checkBehaviors();
				update();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}


	@Override
	public void update(Graphics2D g) {
		long currentTime = System.currentTimeMillis();//gets time now
		int difference = (int) (currentTime - moveTime);//checks how long it has been since last update
		//updates only if amount of time is greater than frame rate
		if(difference >= REFRESH_RATE){
			//update displayTime, since an update is ocurring
			moveTime = currentTime;
			//calculate what the new position should be.
			posx += vx*(double)difference/REFRESH_RATE;
			posy += vy*(double)difference/REFRESH_RATE;
			//note: for very low velocities, position might not move by much. Therefore,
			//rounding to an int may not change
			setX((int)(posx));
			setY((int)(posy));

		}
		drawImage(g);
	}

	public void setX(int x){
		super.setX(x);
		posx = x;
	}
	
	public void setY(int y){
		super.setY(y);
		posy = y;
	}

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public boolean isRunning(){
		return running;
	}
	
	public void setRunning(boolean b){
		running = b;
	}
	
	/** 
	 * for demonstration purposes only. Make abstract
	 * @param g
	 */
	public abstract void drawImage(Graphics2D g);
	
	/**
	 * For demonstration purposes only. Make abstract
	 */
	public abstract void checkBehaviors();
//		if(posy+vy > 300){
//			posy = 300;
//			vy=-vy;
//		}
//		else if(posy+vy < 20){
//			posy = 20;
//			vy=-vy;
//		}
//		
//		if(posx+vx > 300){
//			posx = 300;
//			vx=-vx;
//		}
//		else if(posx+vx < 20){
//			posx = 20;
//			vx=-vx;
//		}
//		
//	}
}
