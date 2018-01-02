package guiPlayer;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import guiTeacher.components.Action;
import guiTeacher.components.TextLabel;
import guiTeacher.interfaces.Clickable;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.ClickableScreen;

public class CustomScreen extends ClickableScreen {

	public CustomScreen(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	private CustomLabel label;
	private int relativeY;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4880450066550035713L;


	@Override
	public void initAllObjects(List<Visible> viewObjects) {
		label = getALabel();
		viewObjects.add(label);
		label.setAction(new Action() {

			@Override
			public void act() {
				int y = label.getY();
				double yMax = label.getHeight();
				int value = (int)(255*(relativeY-y)/yMax);
				if(value < 256 && value >= 0 ){
					label.setIconColor(new Color(value,value,value));
				}
			}
		});

	}

	private CustomLabel getALabel() {
		return new LabelImplementation(50, 50);
	}

	@Override
	public void mouseMoved(MouseEvent m) {
		super.mouseMoved(m);
		label.updateString1("X coordinate "+m.getX());
		label.updateString2("Y coordinate "+m.getY());
		relativeY = m.getY();
		label.update();
	}

}
