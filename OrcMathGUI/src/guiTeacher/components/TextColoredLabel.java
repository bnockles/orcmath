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
import java.awt.Graphics2D;

import guiTeacher.Utilities;

/**
 * A TextLabel that easily sets a Custom background color and custom foreground color 
 * @author bnockles
 *
 */
public class TextColoredLabel extends TextLabel {

	private Color customBg;
	private Color customFg;
	private int align;
	
	public TextColoredLabel(int x, int y, int w, int h, String text, Color bg, Color fg) {
		super(x, y, w, h, text);
		this.customBg = bg;
		this.customFg = fg;
		align = getHeaderAlign();
		update();
	}
	
	
	
	public int getAlign() {
		return align;
	}



	public void setAlign(int align) {
		this.align = align;
		update();
	}



	@Override
	public void update(Graphics2D g) {
		applyStyles(g);
		g.setColor(customBg);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(customFg);
		g.setFont(getFont());
		if(getText() != null) {
			
			Utilities.drawText(g, getText(), 0, getWidth(), getHeight(), getAlign());
		
		}
	}

}
