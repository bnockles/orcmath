package alice2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import guiTeacher.components.Action;
import guiTeacher.components.TextLabel;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.ClickableScreen;

public class SimonScreenAlice extends ClickableScreen implements Runnable {


	private TextLabel textLabel;
	private ButtonInterfaceAlice[] buttons;
	private ProgressInterfaceAlice progress;
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

		textLabel.setText("");
		nextRound();
	}

	private void nextRound() {
		acceptingInput = false;
		roundNumber++;

		//step 3 didnt do newArrayList = new ArrayList<MoveInterfaceAlice>();
		arrayList.add(randomMove());
		setRound(); 
		setSequenceSize();
		changeText("Simon's turn");
		textLabel.setText("");
		playSequence();
		changeText("Your turn");
		acceptingInput = true;
		sequenceIndex = 0;
	}

	private void playSequence() {
		 ButtonInterfaceAlice b = null;
		 for(int i = 0; i < arrayList.size();i++)
		 {
			 if(b != null)
			 {
				 b.dim();
				 b.getButton();
				 b.highlight();
				 int sleepTime;
				 sleepTime = roundNumber +1;
				 try {
						Thread.sleep(800);
						} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}		
	
			 }
		 }
		 b.dim();
		 
		
	}

			public void changeText(String string) {
				Thread blink = new Thread(new Runnable(){
					public void run(){

						try {

							Thread.sleep(800);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});		
			}

			public int setSequenceSize() {
				return arrayList.size();
			}

			public int setRound() {
				return roundNumber;
			}

			@Override
			public void initAllObjects(List<Visible> viewObjects) {
				addButtons();
				for(ButtonInterfaceAlice b: buttons){ 
					viewObjects.add(b); 
				}
				progress = getProgress();
				textLabel = new TextLabel(130,230,300,40,"Let's play Simon!");
				arrayList = new ArrayList<MoveInterfaceAlice>();
				lastSelectedButton = -1;
				arrayList.add(randomMove());
				arrayList.add(randomMove());
				roundNumber = 0;
				viewObjects.add(progress);
				viewObjects.add(textLabel);		


				// instances of MoveInterfaceX to the ArrayList (again, change the name of sequence, if necessary)
				//but calls a method named randomMove(). Create this method now. You will write this method next.
				randomMove();
			}

			private MoveInterfaceAlice randomMove() {

				int bIndex = (int)(Math.random()*buttons.length);
				while(bIndex == lastSelectedButton){
					bIndex = (int)(Math.random()*buttons.length);
				}
				return getMove(bIndex);

				// In the randomMove() method, write a method that selects
				//a ButtonInterfaceX, at random, from the ButtonInterfaceX[] field.
				//The index of the selected Button cannot be equal to the value of the lastSelectedButton field.
				//Now, you will record this button as a "Move". (Remember, in Simon, a "move" means pressing a button.
				//The difference between a button and a move is that you can press a button of the same color multiple times, 
				//but there is only one button of that color.) Since your partner will program the Move class, you need a
				//helper method to obtain an instance, just like the helper method you made for getProgress(). Your code should look like this:
			}

			/**
	Placeholder until partner finishes implementation of MoveInterface
			 */
			private MoveInterfaceAlice getMove(int bIndex) {
				return null;
			}

			private void addButtons() {
				int numberOfButtons = 3;	

				buttons = new ButtonInterfaceAlice[numberOfButtons-1];

				Color button1 = new Color(107,244, 66);
				Color button2 = new Color(244,191,66);
				Color button3 = new Color(196,99,33);
				Color button4 = new Color(36,33,198);
				Color button5 = new Color(43,19,41);
				Color button6 = new Color(115,57,214);

				for(int i = 0; i < numberOfButtons; i++) {

					final ButtonInterfaceAlice b = getAButton();
					buttons[i] = b;

					b.setColor(Color.blue);
					b.setX(4);
					b.setY(10);

					b.setAction(new Action(){

						public void act(){

							if(acceptingInput) {

								Thread blink = new Thread(new Runnable(){
									public void run(){

										b.highlight();
										try {
											Thread.sleep(800);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										b.dim();
									}

								});
								blink.start();

							}

						}

					});
					if(b == arrayList.get(sequenceIndex).getButton()) {
						sequenceIndex++;

					}
					else {

						progress.gameOver();
					}
					if(sequenceIndex == arrayList.size()){ 
						Thread nextRound = new Thread(SimonScreenAlice.this); 
						nextRound.start(); 
					}
				}
			}

			//Placeholder until partner finishes implementation of ProgressInterface

			private ButtonInterfaceAlice getAButton() {

				return null;

			}

			private ProgressInterfaceAlice getProgress() { 
				return null; 
			}

		}
