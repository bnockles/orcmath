package alice2;

import java.util.ArrayList;

import guiTeacher.GUIApplication;
import guiTeacher.components.TextLabel;

public class SimonGameAlice extends GUIApplication {
	
	private static SimonGameAlice game;
	private SimonGameAlice screen;

	private TextLabel textlabel;
	private ButtonInterfaceAlice[] buttonInterface;
	private ProgressInterfaceAlice progressInterface;
	private ArrayList<MoveInterfaceAlice> arrayList;
	private int roundNumber;
	private boolean acceptingInput;
	private int sequenceIndex;
	private int lastSelectedButton;
	
	public SimonGameAlice(int width, int height) {
		
		super(width,height);
		
		setVisible(true);
		// TODO Auto-generated constructor stub
	}
	//Write the main method
	//Write the initScreen method
	//Write the constructor

	@Override
	public void initScreen() {

	screen = new SimonGameAlice(getWidth(),getHeight());
	
	}
	public static void main(String args[]) {
		game = new SimonGameAlice(200,200);
		Thread runner = new Thread(game);
		runner.start();
		
		
	}
	
	

}