package examgame;

import java.awt.Color;
import java.util.List;

import guiTeacher.components.Action;
import guiTeacher.components.Button;
import guiTeacher.components.TextArea;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.ClickableScreen;

public class ExamScreen extends ClickableScreen{

	static Thread counter = new Thread();
	private Button start;
	private TextArea textscore;
	private TextArea counterText;
	private int score;
	private boolean playgame = false;
	private boolean counterdone = false;
	
	public ExamScreen(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void initAllObjects(List<Visible> viewObjects) {
		score = 0;
		counterText = new TextArea(160,250,300,300,"");
		start = new Button(50,50, 300, 300, "READY",Color.RED, new Action(){ 
			public void act() {
				
				if(playgame) {
					score++;
					textscore.setText("Score" + score);
				}
				
				Thread counter = new Thread(new Runnable(){
					public void run() {
						for(int i =4; i>0; i--) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							counterText.setText(Integer.toString(i-1));
							if(i == 1) {
								counterText.setText("GO!");
								start.setText("Click Me!");
								playgame = true;
							}
						}
						for(int i = 6; i > 0; i--) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							counterText.setText(Integer.toString(i-1));
							if(i == 1) {
								start.setText("Done");
								playgame = false;
							}
						}
					}

				});
				if(!counterdone) {
					counter.start();
					counterdone = true;
				}
			}
		});
		
		textscore = new TextArea(160, 150, 300, 300, "Score: " + score);
		viewObjects.add(start);
		viewObjects.add(counterText);
		viewObjects.add(textscore);
	
	
	}
}
