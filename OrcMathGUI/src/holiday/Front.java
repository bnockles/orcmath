package holiday;

import java.util.List;

import guiTeacher.components.*;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.FullFunctionScreen;

public class Front extends FullFunctionScreen {

	
	
	public Front(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initAllObjects(List<Visible> viewObjects) {
		viewObjects.add(new Graphic(0, 0, getWidth(),getHeight(),"resources/winterscape.jpg"));
		Button open = new Button((getWidth()-100)/2,getHeight()-40,100,30,"Open",new Action() {
			
			@Override
			public void act() {
				HolidayCard.card.setScreen(HolidayCard.inside);
			}
		});
		for(int i = 0; i < 28; i++){
			viewObjects.add(new Snowflake(getWidth(), getHeight()));
		}
		viewObjects.add(open);
	}

}
