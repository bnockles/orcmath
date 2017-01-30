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

import guiTeacher.components.Action;
import guiTeacher.components.Link;
import main.OrcMath;

public class Type extends Link {

	private QuestionPreview qp;
	
	public Type(int x, int y, int w, int h, String text, Action action, QuestionPreview qp) {
		super(x, y, w, h, text, action);
		this.qp =qp;
	}
	
	@Override
	public void hoverAction() {
		qp.setExample(getText(), 1);
		qp.setBounds(OrcMath.app.getX()+qp.getOriginalX(), OrcMath.app.getY()+qp.getOriginalY(), qp.getWidth(), qp.getHeight());
	}
	


}
