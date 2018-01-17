package danielComponent;

import java.awt.Font;
import java.io.File;

import guiTeacher.GUIApplication;
import guiTeacher.components.StyledComponent;

public class HolidayCardGUI extends GUIApplication {

	public static HolidayCardGUI gui;
	public static HolidayScreen screen1 = new HolidayScreen(700, 700, "Text");
	public static HolidayScreen2 screen2 = new HolidayScreen2(700, 700);

	private static final long serialVersionUID = 4513904487268188576L;

	public HolidayCardGUI(int width, int height) {		
		super(width, height);
		
		setVisible(true);
	}

	@Override
	public void initScreen()
	{
		try {
			File fontFile = new File("resources//BlackletterHand.ttf");
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			Font baseFont=font.deriveFont(16f);
			StyledComponent.setTabFont(baseFont);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setScreen(screen1);
		
		
	}

	public static void main(String[] args)
	{
		gui = new HolidayCardGUI(700, 700);
		Thread go = new Thread(gui);
		go.start();
	}

}
