package alice2;

import java.awt.Color;

import guiTeacher.components.Action;
import guiTeacher.interfaces.Clickable;

public interface ButtonInterfaceAlice extends Clickable{

	void setColor(Color blue);
	void setAction(Action a);

	void highlight();
	void dim();
	ButtonAlice getButton();

}
