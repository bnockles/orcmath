package guiPlayer;

import java.util.List;

import guiTeacher.GUIApplication;
import guiTeacher.components.RadioButton;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.FullFunctionScreen;

public class Sampler extends GUIApplication {

	public Sampler(int width, int height) {
		super(width, height);
		setVisible(true);
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args){
		Sampler sample = new Sampler(800, 550);
		Thread go = new Thread(sample);
		go.start();
	}

	@Override
	public void initScreen() {
		SampleScreen s = new SampleScreen(getWidth(), getHeight());
		setScreen(s);
	}

	public class SampleScreen extends FullFunctionScreen{

		
		
		public SampleScreen(int width, int height) {
			super(width, height);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void initAllObjects(List<Visible> viewObjects) {
			RadioButton rb1 = new RadioButton(0, 20, 30, 30, "X", null);
			RadioButton rb2 = new RadioButton(100, 20, 30, 30, "Y", null);
			rb1.addPeer(rb2);
			rb2.addPeer(rb1);
			viewObjects.add(rb1);
			viewObjects.add(rb2);
		}
		
	}
	
}
