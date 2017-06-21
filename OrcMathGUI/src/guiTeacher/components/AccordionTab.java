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

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;

import guiTeacher.Utilities;

public class AccordionTab extends Component implements Runnable{

	private String header;
	private Accordion parent;
	private ScrollablePane content;
	private boolean open;


	//animation
	private long displayTime; //the time when the last image switched
	private double completion; //the frame that is currently being displayed
	private int animationTime;
	private boolean running;

	public static final int REFRESH_RATE = 20;

	/**
	 * Note: content is automatically scaled to fit inside of the width of the Accordion.
	 * @param parent
	 * @param description
	 * @param content
	 */
	public AccordionTab(Accordion parent, String description, ScrollablePane content) {
		super(0, 0, parent.getWidth(), (int)((parent.getWidth()/(double)content.getWidth())*content.getHeight()));
		this.parent = parent;
		this.open = false;
		this.content = content;
		this.header = description;
		this.animationTime = parent.getAnimationTime();
		completion = 0.0;
		running = false;
		update();
	}

	public Accordion getParent(){
		return parent;
	}
	
	public void run(){

		running = true;
		displayTime = System.currentTimeMillis();
		while(running){

			try {
				Thread.sleep(REFRESH_RATE);
				int difference = (int) (System.currentTimeMillis() - displayTime);
				if(open)completion += (((double)REFRESH_RATE/animationTime) * ((double)difference/REFRESH_RATE));
				else completion -= (((double)REFRESH_RATE/animationTime) * ((double)difference/REFRESH_RATE));
				if(completion >1.0)completion = 1.0;
				if(completion <0.0)completion = 0.0;
				clear();
				update();
				parent.update();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (completion >= 1.0 || completion <=0){
				running = false;
			}
			
			displayTime = System.currentTimeMillis();
		}
	}

	/**
	 * Called by parent Accordion when this tab is clicked
	 * relativeX and relativeY are relative to this tab's top-left of content.
	 * They are NOT scaled
	 * @param relativeX
	 * @param relativeY
	 */
	public void act(int relativeX, int relativeY) {
		if(!running && relativeX >= 0 && relativeX <= getWidth() && relativeY >0 && relativeY < getHeight()){
			if(content.isHovered(relativeX, relativeY)){
				content.act();
				
			}
		}
		
	}

	public ScrollablePane getContent(){
		return content;
	}


	@Override
	public void update(Graphics2D g) {
		//only update after parent has been assigned
		if(parent != null){
			g.setColor(parent.getTabColor());
			GradientPaint tTob = new GradientPaint(0,0, parent.getTabColor(), 0,parent.getTabHeight(), parent.getTabShade());
			
			Paint current = g.getPaint();
			g.setPaint(tTob);
			g.fillRect(0, 0, getWidth(), parent.getTabHeight());
			g.setPaint(current);
			g.setColor(parent.getHeaderColor());
			Utilities.drawText(g, header, 5,getWidth(),parent.getTabHeight(),parent.getHeaderAlign());
			int heightNow = getHeight();
			if(running){
				g.drawImage(content.getImage(),0, parent.getTabHeight(), getWidth(), heightNow, 0,content.getHeight()-(int)(completion*content.getHeight()), content.getWidth(), content.getHeight(), null);
			}else if(open){
				g.drawImage(content.getImage(),0, parent.getTabHeight(), getWidth(), heightNow, 0, 0, content.getWidth(), content.getHeight(), null);				
			}
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, getWidth()-1, heightNow-1);
		}
	}

	public ScrollablePane getSubwindow() {
		return content;
	}

	public boolean isOpen() {
		return open;
	}


	public void close() {
		if(open && !running){
			open = false;
			Thread animate = new Thread(this);
			animate.start();
		}
	}

	public void switchToThisTab(){
		parent.openTab(this);
	}

	public void open() {
		if(!open && !running){
			open = true;
			Thread animate = new Thread(this);
			animate.start();	
		}
	}

	public int getHeight(){

			return parent.getTabHeight()+(int)(completion*content.getHeight());
		
	}


}
