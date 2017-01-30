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
package screens;

import java.awt.Desktop;
import java.net.URI;
import java.util.List;

import components.Notification;
import components.UpdateNotification;
import guiTeacher.components.Action;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.FullFunctionScreen;
import main.OrcMath;

public class OrcMathScreen extends FullFunctionScreen {

	public OrcMathScreen(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initAllObjects(List<Visible> viewObjects) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Notifies user that an update is available
	 * @param mostRecentUpdate
	 */
	public void presentUpdateNotification(UpdateNotification mostRecentUpdate) {
		mostRecentUpdate.update();
		animateAdd(mostRecentUpdate);
		
	}

	/**
	 * Presents user with notification that error exists and a new version should be downloaded
	 */
	public void presentOutdatedNotification() {
		final Notification n = new Notification("Your version of OrcMath is outdated and may not work", "Update", null);
		n.setAction(new Action(){
			
			@Override
			public void act() {
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
				if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
				        try {
				            desktop.browse(new URI(UpdateNotification.UPDATE_URL));
				        } catch (Exception e) {
				            e.printStackTrace();
				        }
				    }else{
				    	OrcMath.createScreen.remove(n);
				    	OrcMath.createScreen.presentNotification("Your system does not allow linking from Java software. Go to neverbenbetter.com/orcmath for details.");
				    }
			}
		});
		n.update();
		animateAdd(n);
	}

	public void animateAdd(Visible v) {
		int destinationY = v.getY();
		v.setY(getHeight());
		addObject(v);
		Thread add = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(v.getY()>destinationY){
					v.setY(v.getY()-1);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				v.setY(destinationY);
			}
		});
		add.start();
		
	}
	
	public void animateRemoval(Visible v) {
		Thread remove = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(v.getY()<getHeight()){
					v.setY(v.getY()+1);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				remove(v);
			}
		});
		remove.start();
		
	}

	public void presentNotification(String string) {
		final Notification n = new Notification(string, "Okay", null);
		n.setAction(new Action(){
			
			@Override
			public void act() {
				animateRemoval(n);
			}
		});
		n.update();
		animateAdd(n);
	}

}
