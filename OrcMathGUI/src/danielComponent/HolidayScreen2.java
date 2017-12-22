package danielComponent;

import java.util.List;

import guiTeacher.components.Action;
import guiTeacher.components.Button;
import guiTeacher.components.Graphic;
import guiTeacher.components.TextArea;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.FullFunctionScreen;

public class HolidayScreen2 extends FullFunctionScreen {

	private static final long serialVersionUID = -4303618909765520178L;

	public TextArea title;
	public Button back;
	public Graphic inside;
	
	public HolidayScreen2(int width, int height) {
		super(width, height);
		}

	@Override
	public void initAllObjects(List<Visible> viewObjects) {
		title = new TextArea(300, 300, 300, 300, "Merry Christmas");
		viewObjects.add(title);

		back = new Button(550, 550, 100, 30, "Back", new Action() 
		{			
			public void act() 
			{
				HolidayCardGUI.gui.setScreen(HolidayCardGUI.gui.screen1);
			}
		});
		viewObjects.add(back);
		
		inside = new Graphic(20,45,700,700, "resources\\inside.jpg");
		viewObjects.add(inside);
	}

}
