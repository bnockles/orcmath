package danielComponent;

import java.util.List;

import guiTeacher.components.TextArea;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.FullFunctionScreen;

public class HolidayScreen2 extends FullFunctionScreen {

	private static final long serialVersionUID = -4303618909765520178L;

	public TextArea title;
	
	public HolidayScreen2(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initAllObjects(List<Visible> viewObjects) {
		title = new TextArea(300, 300, 300, 300, "Merry Christmas");
		viewObjects.add(title);

	}

}
