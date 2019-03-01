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
import java.awt.Image;



public class ImageTextButton extends Button{

	Image icon;
	
	public ImageTextButton(String text, Image icon, int x, int y, int width, int height, Action action) {
		super(x, y, width, height,"    "+text, getAccentColor(),action);
		String adjustedTitle = icon == null ? text:"    "+text;
		setText(adjustedTitle);
		this.icon = icon;
		update();
	}
	
	public void update(Graphics2D g) {
		super.update(g);
		
		if(icon != null){
			g.drawImage(icon, 2, 0, null);
		}
//		GuiUtilities.centerIcon(g2, icon, getWidth()-fm.stringWidth(text), getHeight());
	}

}
