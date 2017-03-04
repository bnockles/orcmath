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

import java.awt.Desktop;
import java.awt.Graphics2D;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import guiTeacher.components.Action;
import guiTeacher.components.Button;
import guiTeacher.components.StyledComponent;
import guiTeacher.components.TextLabel;
import guiTeacher.interfaces.Clickable;
import main.OrcMath;

public class UpdateNotification extends StyledComponent implements Clickable{

	public static final String RESPONSE_CLICKED_LINK = "VIEWED";
	public static final String RESPONSE_IGNORED = "IGNORED";
	public static final String RESPONSE_NEW = "NEW RELEASE";
	
	public static final int DATE_INDEX = 2;
	public static final String UPDATE_URL = "http://www.neverbenbetter.com/orcmath";
	
	
	public static final int NOTIFICATION_HEIGHT = 30;
	
	private static final int BUTTON_WIDTH = 80;
	private static final int SPACE = 3;
	private ArrayList<Button> buttons;
	private Button ignore;
	private Button view;
	private TextLabel notification;
	private Button buttonHovered;
	
	private String response;
	private String title;
	private String date;
	private String description;
	private String url;
	private String notificationText;
	
	
	public UpdateNotification(String[] line) {
		super(0,OrcMath.SCREEN_HEIGHT-NOTIFICATION_HEIGHT, OrcMath.SCREEN_WIDTH,NOTIFICATION_HEIGHT);
		try{
			this.response= line[0];
			this.title = line[1];
			this.date = line[DATE_INDEX];
			this.description = line[3];
			
			if(line.length > 3)this.url = line[4];
			else this.url = "http://www.neverbenbetter.com/orcmath/";
			if(line.length > 4) this.notificationText=line[5];		
			else this.notificationText  = "A new update is available at orcmath.com/orcmath";
			
			
			
			notification = new TextLabel(SPACE, 0, getWidth()-3*SPACE-2*BUTTON_WIDTH, getHeight(), notificationText);
			int buttonHeight = getHeight()-2*SPACE;
			ignore = new Button(getWidth()-2*(SPACE+BUTTON_WIDTH), SPACE, BUTTON_WIDTH, buttonHeight, "Not Now", new Action() {
				
				@Override
				public void act() {
					response = RESPONSE_IGNORED;
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					date = sdf.format(new Date());
					OrcMath.sd.recordResponse(UpdateNotification.this);
					OrcMath.createScreen.animateRemoval(UpdateNotification.this);
				}
			});
			view = new Button(getWidth()-SPACE-BUTTON_WIDTH, SPACE, BUTTON_WIDTH, buttonHeight, "View", new Action() {
				
				@Override
				public void act() {
					Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
					response = RESPONSE_CLICKED_LINK;
					OrcMath.sd.recordResponse(UpdateNotification.this);
					if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
						try {
							OrcMath.createScreen.animateRemoval(UpdateNotification.this);
							desktop.browse(new URI(url));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else{
						OrcMath.createScreen.remove(notification);
						OrcMath.createScreen.presentNotification("Your system does not allow linking from Java software. Go to neverbenbetter.com/orcmath for details.");
					}

				}
			});
			buttons = new ArrayList<Button>();
			buttons.add(ignore);
			buttons.add(view);
			
		}catch(ArrayIndexOutOfBoundsException e){
			OrcMath.createScreen.presentOutdatedNotification();
		}
		

	}
	
	public String getSaveLine(){
		String a = "\",\"";
		return "\""+response+a+title+a+date+a+description+a+url+a+notificationText+"\"";
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getTitle() {
		return title;
	}

	public String getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
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

	@Override
	public void setAction(Action a) {
		// TODO Auto-generated method stub
		
	}

	
	
}
