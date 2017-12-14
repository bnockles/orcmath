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
package guiTeacher.interfaces;

import java.awt.image.BufferedImage;

public interface Visible {

	public BufferedImage getImage();
	public int getX();
	public int getY();
	public void setX(int x);
	public void setY(int y);
	public int getWidth();
	public int getHeight();
	public void update();
	public boolean isAnimated();
	public void setVisible(boolean b);
	boolean isVisible();
	float getAlpha();
	void setAlpha(float f);
	void unhoverAction();
	void hoverAction();
}
