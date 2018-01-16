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
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import guiTeacher.interfaces.Visible;

public class Graphic implements Visible {

	private BufferedImage image;
	private boolean loadedImages;
	private float alpha;
	private int x;
	private int y;
	private boolean visible;
	private String address;

	public Graphic(int x, int y, int w, int h, String imageLocation){	
		this.x = x;
		this.y = y;
		this.alpha = 1.0f;
		visible = true;
		loadedImages = false;
		this.address = imageLocation;
		loadImages(imageLocation, w, h);
	}

	public Graphic(int x, int y, double scale, String imageLocation){	
		this.x = x;
		this.y = y;
		this.alpha = 1.0f;
		visible = true;
		loadedImages = false;
		this.address = imageLocation;
		loadImages(imageLocation, scale);
	}

	public Graphic(int x, int y, String imageLocation){
		this.x = x;
		this.y = y;
		visible = true;
		this.alpha = 1.0f;
		loadedImages = false;
		this.address = imageLocation;
		loadImages(imageLocation, 0,0);
	}
	
	public Graphic(int x, int y, BufferedImage image){
		this.x = x;
		this.y = y;
		this.alpha = 1.0f;
		visible = true;
		loadedImages = true;
		this.image = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = this.image.createGraphics();
		g.drawImage(image, 0, 0, null);
	}
	
	public Graphic(int x, int y, BufferedImage image, double scale){
		this.x = x;
		this.y = y;
		this.alpha = 1.0f;
		visible = true;
		loadedImages = true;
		
		AffineTransform scaleT = new AffineTransform();
		scaleT.scale(scale, scale);
		AffineTransformOp scaleOp = new AffineTransformOp(scaleT, AffineTransformOp.TYPE_BILINEAR);
		this.image = scaleOp.filter(image,new BufferedImage((int)(image.getWidth()*scale), (int)(image.getHeight()*scale), BufferedImage.TYPE_INT_ARGB));
		
		loadedImages = true;
		
		

	}
	
	

	public Graphic(int x, int y, int w, int h, BufferedImage icon) {
		this.x = x;
		this.y = y;
		this.alpha = 1.0f;
		visible = true;
		loadedImages = true;
		AffineTransform scale = new AffineTransform();
		
		//make it fit to the smaller of the two
		double scaleWidth = w/(double)icon.getWidth();
		double scaleHeight = h/(double)icon.getHeight();
		double smallerOfTwo = (scaleWidth < scaleHeight)? scaleWidth : scaleHeight;
		scale.scale(smallerOfTwo, smallerOfTwo);
		AffineTransformOp scaleOp = new AffineTransformOp(scale, AffineTransformOp.TYPE_BILINEAR);
		image = scaleOp.filter(icon,new BufferedImage((int)(icon.getWidth()*smallerOfTwo), (int)(icon.getHeight()*smallerOfTwo), BufferedImage.TYPE_INT_ARGB));
	}
	
	
	/**
	 * resizes the graphic to the specified size. Note that if this Graphic was constructed with an image address, 
	 * the methods will scale the original image at that address. If this Graphic was constructed from an image, it will scale
	 * itself, which may result in loss of resolution when going from small to large 
	 * @param w
	 * @param h
	 */
	public void resize(int w, int h){
		if(address != null){
			loadImages(address, w,h);
		}else{
			BufferedImage orig = image;
			AffineTransform scale = new AffineTransform();
			
			//make it fit to the smaller of the two
			double scaleWidth = w/(double)orig.getWidth();
			double scaleHeight = h/(double)orig.getHeight();
			double smallerOfTwo = (scaleWidth < scaleHeight)? scaleWidth : scaleHeight;
			scale.scale(smallerOfTwo, smallerOfTwo);
			AffineTransformOp scaleOp = new AffineTransformOp(scale, AffineTransformOp.TYPE_BILINEAR);
			image = scaleOp.filter(orig,new BufferedImage((int)(orig.getWidth()*smallerOfTwo), (int)(orig.getHeight()*smallerOfTwo), BufferedImage.TYPE_INT_ARGB));
		}
	}

	private void loadImages(String imageLocation, double scale) {
		try{
			//get the full-size image
			ImageIcon icon = new ImageIcon(imageLocation);
	
			int newWidth = (int) (icon.getIconWidth() * scale);
			int newHeight = (int) (icon.getIconHeight() * scale);
			
			image = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(icon.getImage(), 0, 0, null);
			
			AffineTransform scaleT = new AffineTransform();
			scaleT.scale(scale, scale);
			AffineTransformOp scaleOp = new AffineTransformOp(scaleT, AffineTransformOp.TYPE_BILINEAR);
			image = scaleOp.filter(image,new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB));
			
			loadedImages = true;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void loadImages(String imageLocation, int w, int h) {
		try{
			//get the full-size image
			ImageIcon icon = new ImageIcon(imageLocation);

			//use image size of original
			if(w==0 && h == 0){
				image = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = image.createGraphics();
				g.drawImage(icon.getImage(), 0, 0, null);
			}else{
				//make a full-size image
				image = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = image.createGraphics();
				g.drawImage(icon.getImage(), 0, 0, null);
				
				//scale the image to size
				AffineTransform scale = new AffineTransform();
				
				//make it fit to the smaller of the two
				double scaleWidth = w/(double)icon.getIconWidth();
				double scaleHeight = h/(double)icon.getIconHeight();
				double smallerOfTwo = (scaleWidth < scaleHeight)? scaleWidth : scaleHeight;
				
//				scale.scale(w/(double)icon.getIconWidth(), h/(double)icon.getIconHeight());
				scale.scale(smallerOfTwo, smallerOfTwo);
				AffineTransformOp scaleOp = new AffineTransformOp(scale, AffineTransformOp.TYPE_BILINEAR);
				image = scaleOp.filter(image,new BufferedImage((int)(image.getWidth()*smallerOfTwo), (int)(image.getHeight()*smallerOfTwo), BufferedImage.TYPE_INT_ARGB));
//				g.drawImage(icon.getImage(), 0, 0, w, h, 0,0,icon.getIconWidth(), icon.getIconHeight(), null);
			}
			loadedImages = true;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void move(int newX, int newY, int durationMS){
		Visible.move(this, newX, newY, durationMS);
	}
	

	public BufferedImage getImage() {
		return image;
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return image.getWidth();
	}

	public int getHeight() {
		return image.getHeight();
	}

	public void update() {
		//does nothing, since image never changes
	}

	public boolean isAnimated() {
		return false;
	}
	
	public boolean isVisible() {
		return visible;
	}


	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public void unhoverAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hoverAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getAlpha() {
		return alpha;
	}

	@Override
	public void setAlpha(float f) {
		this.alpha = f;
	}


}
