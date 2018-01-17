package holiday;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
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
		Graphic back = new Graphic(0,0,1.2,"resources/newyork.jpg");
		viewObjects.add(back);
		Banner b = new Banner(getWidth());
		viewObjects.add(b);
		
		TextArea ta = new TextArea(140, 180, getWidth()-220, 400, "HAPPY CODING IN THE NEW YEAR!");
		ta.setCustomTextColor(new Color(240,240,255));
		try {
			File fontFile = new File("resources/Snowinter.ttf");
			//			File fontFile = new File("resources//DayRoman.ttf");
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			Font baseFont=font.deriveFont(96f);
			ta.setFont(baseFont);
		} catch (Exception e) {
			e.printStackTrace();
		}
		viewObjects.add(ta);
		
		viewObjects.add(new LittleSanta());
	}
	
	

}
