package danielComponent;

import java.util.List;

import com.itextpdf.text.Font;
import com.itextpdf.text.Image;

import guiTeacher.components.*;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.FullFunctionScreen;

public class HolidayScreen extends FullFunctionScreen {

	private String text;
	private Button changeScreen;
	private TextArea title;
	private static final long serialVersionUID = 4954108273887121678L;
	private Graphic image;
	
	public HolidayScreen(int width, int height, String text) {
		super(width, height);
		this.text = text;
	}

	@Override
	public void initAllObjects(List<Visible> viewObjects) 
	{		
		changeScreen = new Button(550, 550, 100, 30, "Next Page", new Action() {

			public void act() {
				HolidayCardGUI.gui.setScreen(HolidayCardGUI.gui.screen2);
			}
		});		
		viewObjects.add(changeScreen);		
		
		title = new TextArea(300, 300, 300, 300, "Happy Holidays");
		viewObjects.add(title);
		
		image = new Graphic(0, 0, 700, 700, "resources\\card.jpg");
		viewObjects.add(image);
	}
}
