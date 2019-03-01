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
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class AnimatedComponent extends MovingComponent{

	private ArrayList<BufferedImage> frame; //the images that can be displayed
	private ArrayList<Integer> times; //the time each image is displayed
	private long displayTime; //the time when the last image switched
	private int currentFrame; //the frame that is currently being displayed
	private boolean repeat;

	public static final int REFRESH_RATE = 20;

	public AnimatedComponent(int x, int y, int w, int h) {
		super(x, y, w, h);
		frame = new ArrayList<BufferedImage>();
		times = new ArrayList<Integer>();
		currentFrame = 0;
		repeat = true;
	}

	public void setRepeat(boolean b){
		repeat = b;
	}

	public boolean isAnimated(){
		return true;
	}
	
	public void addSequence(String originalImgageAddress, ArrayList<Integer> times, int x, int y, int w, int h,
			int n) {
		BufferedImage originalImgage;
		try {
			originalImgage = ImageIO.read(new File(originalImgageAddress));
			for(int i = 0; i < n; i++){
				addFrame(originalImgage.getSubimage(x+w*i, y, w, h),times.get(i));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Adds n subimages to the animation. each sub image has width w, and height h,
	 * the sub-images are taken-from the iamges at the given address in a sequence from left
	 * to right, beginning at coordinates x,y. Each frame will be shown for "time" ms
	 * @param originalImgageAddress
	 * @param time
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param n
	 */
	public void addSequence(String originalImgageAddress, int time, int x, int y, int w, int h,
			int n) {
		BufferedImage originalImgage;
		try {
			originalImgage = ImageIO.read(new File(originalImgageAddress));
			for(int i = 0; i < n; i++){
				addFrame(originalImgage.getSubimage(x+w*i, y, w, h),time);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public void addFrame(BufferedImage image, Integer time){
		frame.add(image);
		this.times.add(time);
	}



	public void drawImage(Graphics2D g) {
		long currentTime = System.currentTimeMillis();//gets time now
		//check if it's time to change the frame
		//and make sure that there are images in the frame list
		if(frame != null && frame.size() > 0 && frame.size() == times.size() && currentTime - displayTime > times.get(currentFrame)){
			displayTime = currentTime;

			//increase the currentFrameIndex but don't exceed size()
			currentFrame = (currentFrame+1)%frame.size();
			//end animation if not on repeat
			if(currentFrame == 0 && !repeat){
				setRunning(false);
				return;
			}
			//clear the previous image
			clear();
			BufferedImage newFrame = frame.get(currentFrame);
			g.drawImage(newFrame, 0,0,getWidth(),getHeight(),0,0,newFrame.getWidth(),newFrame.getHeight(),null);
		}
	}

	@Override
	public void checkBehaviors() {
		// TODO Auto-generated method stub
		
	}

}
