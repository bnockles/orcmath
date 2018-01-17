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

import javax.swing.JFrame;

/**
 * This class illustrates transitions between Screens in a GUI Application. There are 3 types of standard Transitions:
 * ENTER_LEFT
 * ENTER_RIGHT
 * REVEAL_RIGHT 
 * @author bnockles
 *
 */
public class Transition {

	public static final int ENTER_LEFT = 0;
	public static final int ENTER_RIGHT = 1;
	public static final int REVEAL_RIGHT= 2;
	
	private int type;
	private JFrame frame;
	private  int xScreen;
	private  int yScreen;
	private  int changeX;
	private  int changeY;
	private  long time;

	/**
	 * 
	 * @param frame the GUIApplication performing the Transition
	 * @param type the type of Transition being performed (Transition.ENTER_LEFT, _RIGHT, or REVEAL_RIGHT)
	 * @param time the duration of the animation
	 */
	public Transition(JFrame frame, int type, long time) {
		
		this.frame = frame;
		this.time = time;
		this.type = type;
		switch(type){
		case(ENTER_LEFT):
			xScreen = -frame.getWidth();
		yScreen = 0;
		changeX = (int) ((frame.getWidth())/time);
		if(changeX <=0)changeX = 1;
		break;
		case(ENTER_RIGHT):
			xScreen = frame.getWidth();
		yScreen = 0;
		changeX = (int) -((frame.getWidth())/time);
		if(changeX >=0)changeX = -1;
		break;
		case(REVEAL_RIGHT):
			xScreen = frame.getWidth();
		yScreen = 0;
		changeX = (int) -((frame.getWidth())/time);
		if(changeX >=0)changeX = -1;
		break;
		default:
			xScreen = -frame.getWidth();
			yScreen = 0;
			changeX = (int) (frame.getWidth()/time);
			if(changeX <=0)changeX = 1;
		}
	}
	
	public long getTime(){
		return time;
	}

	public void step(long timePassed) {
		xScreen += changeX*timePassed;
		yScreen += changeY*timePassed;
		time -= timePassed;
	}

	public int getxScreen() {
		return xScreen;
	}

	public int getyScreen() {
		return yScreen;
	}

	public int getWidthScreen() {
		if(type == REVEAL_RIGHT){
			return frame.getWidth()-xScreen;
		}else return frame.getWidth();
	}

	public int getHeightScreen() {
		return frame.getHeight();
	}

	public int getxTarget() {
		if(type == REVEAL_RIGHT){
			return xScreen;
		}else return 0;
	}
	
	public int getyTarget() {
		return 0;
	}

	
	

}
