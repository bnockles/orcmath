package examgame;

import guiTeacher.GUIApplication;


public class ExamGame extends GUIApplication{

	public static ExamGame game;
	public static ExamScreen screen;
	
	public static void main(String[] args) {
		game = new ExamGame(800, 500);
		Thread runner = new Thread(game);
		runner.start();
		
	}
	
	public ExamGame(int width, int height) 
	{
		super(width, height);
		setVisible(true);
	}

	@Override
	public void initScreen() {
		screen = new ExamScreen(getWidth(), getHeight());
		setScreen(screen);
	}
}
