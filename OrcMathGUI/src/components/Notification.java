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

import java.awt.Graphics2D;
import java.util.ArrayList;

import guiTeacher.components.Action;
import guiTeacher.components.Button;
import guiTeacher.components.StyledComponent;
import guiTeacher.components.TextLabel;
import guiTeacher.interfaces.Clickable;
import main.OrcMath;

public class Notification extends StyledComponent implements Clickable{

	
	
	public static final int NOTIFICATION_HEIGHT = 30;
	
	private static final int BUTTON_WIDTH = 80;
	private static final int SPACE = 3;
	private ArrayList<Button> buttons;
	private Button okay;
	private TextLabel notification;
	private Button buttonHovered;
	
	
	
	public Notification(String notificationText, String buttonText, Action actOnClick) {
		super(0,OrcMath.createScreen.getHeight()-NOTIFICATION_HEIGHT, OrcMath.createScreen.getWidth(),NOTIFICATION_HEIGHT);
	
			
			
			notification = new TextLabel(SPACE, 0, getWidth()-3*SPACE-2*BUTTON_WIDTH, getHeight(), notificationText);
			int buttonHeight = getHeight()-2*SPACE;
			okay = new Button(getWidth()-BUTTON_WIDTH-SPACE, SPACE, BUTTON_WIDTH, buttonHeight, buttonText, actOnClick);
			buttons = new ArrayList<Button>();
			buttons.add(okay);

	}
	
	public void setAction(Action a){
		okay.setAction(a);
	}
	


	

	@Override
	public void update(Graphics2D g) {
		g.setColor(getAlertColor());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(notification.getImage(), notification.getX(), notification.getY(),null);
		for(Button b: buttons){
			g.drawImage(b.getImage(), b.getX(), b.getY(), null);
		}
	}

	@Override
	public boolean isHovered(int x, int y) {
		buttonHovered = null;
		for(Button b: buttons){
			if(x>b.getX()+this.getX() && x<b.getX()+b.getWidth()+this.getX() && y > this.getY()+b.getY() && y < this.getY()+b.getY()+b.getHeight()){
				buttonHovered = b;
				break;
			}
		}
		return buttonHovered!=null;
	}

	@Override
	public void act() {
		if(buttonHovered != null){
			buttonHovered.act();
		}
	}

}
