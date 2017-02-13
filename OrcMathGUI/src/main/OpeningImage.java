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
package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import guiTeacher.components.Graphic;

public class OpeningImage extends JFrame {


	private static final String imageLocation = "resources/Opening.png";
	
	private ImageIcon icon;
	
	public OpeningImage() {
		super();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		icon = new ImageIcon(imageLocation);
		int imageWidth = icon.getIconWidth();
		int imageHeight = icon.getIconHeight();
	
		setBounds((width-imageWidth)/2,(height-imageHeight)/2,imageWidth,imageHeight);
		setUndecorated(true);
	}
	
	public void paint(Graphics g){


		g.drawImage(icon.getImage(), 0, 0, null);
	}

}
