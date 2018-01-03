package alice2;

import java.util.ArrayList;
import java.util.List;

import guiTeacher.components.TextLabel;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.ClickableScreen;

public class SimonScreenAlice extends ClickableScreen implements Runnable {

	
	private TextLabel textlabel;
	private ButtonInterfaceAlice[] buttonInterface;
	private ProgressInterfaceAlice progressInterface;
	private ArrayList<MoveInterfaceAlice> arrayList;
	private int roundNumber;
	private boolean acceptingInput;
	private int sequenceIndex;
	private int lastSelectedButton;
	
	
	public SimonScreenAlice(int x, int y) {
		
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initAllObjects(List<Visible> viewObjects) {
		addButtons();
		for(ButtonInterfaceAlice b: buttons){ 
		    viewObjects.add(b); 
		}
		progress = getProgress();
		label = new TextLabel(130,230,300,40,"Let's play Simon!");
		arrayList = new ArrayList<MoveInterfaceAlice>();
		//add 2 moves to start
		lastSelectedButton = -1;
		arrayList.add(randomMove());
		arrayList.add(randomMove());
		roundNumber = 0;
		viewObjects.add(progress);
		viewObjects.add(label);		
		
		lastSelectedButton = -1;
		// instances of MoveInterfaceX to the ArrayList (again, change the name of sequence, if necessary)
		//but calls a method named randomMove(). Create this method now. You will write this method next.
		randomMove();
	}

	private void randomMove() {
		// In the randomMove() method, write a method that selects
		//a ButtonInterfaceX, at random, from the ButtonInterfaceX[] field.
		//The index of the selected Button cannot be equal to the value of the lastSelectedButton field.
		//Now, you will record this button as a "Move". (Remember, in Simon, a "move" means pressing a button.
		//The difference between a button and a move is that you can press a button of the same color multiple times, 
		//but there is only one button of that color.) Since your partner will program the Move class, you need a
		//helper method to obtain an instance, just like the helper method you made for getProgress(). Your code should look like this:
		int bIndex = (int)(Math.random()*buttons.length);
	    while(bIndex == lastSelectedButton){
	        bIndex = (int)(Math.random()*buttons.length);
	    }
	    return getMove(bIndex);
	}

	/**
	Placeholder until partner finishes implementation of MoveInterface
	*/
	private MoveInterfaceBen getMove(int bIndex) {
	    return null;
	}

	private void addButtons() {
		// TODO Auto-generated method stub
		
	}

	//Placeholder until partner finishes implementation of ProgressInterface
	
	private ProgressInterfaceAlice getProgress() { 
	    // TODO Auto-generated method stub 
	    return null; 
	}
	
}
