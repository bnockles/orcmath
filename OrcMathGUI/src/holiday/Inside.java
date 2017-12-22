package holiday;

import java.util.List;

import guiTeacher.components.*;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.FullFunctionScreen;

public class Inside extends FullFunctionScreen{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1945798853158764086L;

	public Inside(int width, int height) {
		super(width,height);
	}

	@Override
	public void initAllObjects(List<Visible> viewObjects) {
		Banner b = new Banner(getWidth());
		viewObjects.add(b);
	}

}
