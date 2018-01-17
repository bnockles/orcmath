package guiPlayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import guiTeacher.GUIApplication;
import guiTeacher.components.*;
import guiTeacher.interfaces.FileRequester;
import guiTeacher.interfaces.KeyedComponent;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.FileLoader;
import guiTeacher.userInterfaces.FullFunctionScreen;

public class Sampler extends GUIApplication implements FileRequester{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2452328323352199392L;

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

		/**
		 * 
		 */
		private static final long serialVersionUID = 258186143576427947L;
		AnimatedComponent mario;
		
		public SampleScreen(int width, int height) {
			super(width, height);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void initAllObjects(List<Visible> viewObjects) {
			//Set styles
			StyledComponent.setButtonOutline(true);
			StyledComponent.setAccentColor(Color.WHITE);
			setCustomFont();
			
			RadioButton rb1 = new RadioButton(480, 40, 30, 30, "X", null);
			RadioButton rb2 = new RadioButton(520, 40, 30, 30, "Y", null);
			rb1.addPeer(rb2);
			rb2.addPeer(rb1);
			viewObjects.add(rb1);
			viewObjects.add(rb2);
			
			String[] buttonTexts = {"1","2","3"};
			RadioButtons buttons = new RadioButtons(640, 20, 100, 45, "Buttons", buttonTexts, 0, RadioButtons.STYLE_HORIZONTAL);
			viewObjects.add(buttons);
					
			ScrollablePane scroll = new ScrollablePane(this, 20, 60, 200, 80);
			scroll.setBorderWidth(3);
			for(int i=0; i < 10; i++){
				
				scroll.addObject(new TextLabel(5,30*i,100,25,"Label "+(i+1)));
			}
			scroll.update();
			
			viewObjects.add(scroll);
			
			
			TextBox box = new TextBox(20, 160, 200, 100, "Try typing here.");
			viewObjects.add(box);
			
			TextArea display = new TextArea(250, 160, 200, 100, "Press the button below the text box on the left to update this text.");
			viewObjects.add(display);
			
			
			Button b = new Button(20,265, 200, 30,"Update Text Area", new Action() {
				
				@Override
				public void act() {
					display.setText(box.getText());
				}
			});
			viewObjects.add(b);
			
			Graphic level = new Graphic(20, 300, "resources/mariolevel.jpg");
			viewObjects.add(level);
			
			mario = new AnimatedComponent(250, 265, 29, 34);
			mario.addSequence("resources/mario.png", 150, 234, 50, 29, 34, 3);
			Thread run = new Thread(mario);
			run.start();
			viewObjects.add(mario);
			
			TextLabel label = new TextLabel(280, 270, 100, 30, "Try Dragging!");
			viewObjects.add(label);
			
			TextField tf = new TextField(250, 60, 200, 30, "", "Enter text below");
			viewObjects.add(tf);
			
			Checkbox cb = new Checkbox("Invisible Mario", 250, 95, 200, false, new Action() {
				
				boolean visible = true;
				
				@Override
				public void act() {
					visible = !visible;
					mario.setVisible(visible);;
					
				}
			}); 
			viewObjects.add(cb);
			
			
				
				FileOpenButton fileButton = new FileOpenButton(490, 70, 120, 30, null,Sampler.this);
				viewObjects.add(fileButton);
			
			addTable(viewObjects);
		}
		
		public void addTable(List<Visible> viewObjects) {
			StyledComponent.setTabColor(new Color(100,180,230));
			String[] headers = {"Title","Integer Value","Description"};
			int[] widths = {80,60,150};
			boolean[] editable = {false, true, true};
			
			TableHeader header = new TableHeader(headers, widths, editable, 30);
			SimpleTable table = new SimpleTable(SampleScreen.this, 0, 0, 300, 200, header);
			String[] cardinal = {"1st","2nd","3rd"};
			for(int i = 0; i < 10; i++){
				String c = (i < cardinal.length)? cardinal[i] : (i+1)+"th";
			
				
				String[] value = {"Title #"+i,i+"","The "+c+" object"};
				table.addRow(value);
			}
			
			table.update();
			ArrayList<Visible> contentsOfPane = new ArrayList<Visible>();
			contentsOfPane.add(table);
			ScrollableDragablePane tablePane = new ScrollableDragablePane(SampleScreen.this, contentsOfPane, 470, 110, 300, 180);
			viewObjects.add(tablePane);
		}

		public void mouseDragged(MouseEvent m) {
			super.mouseDragged(m);
			mario.setX(m.getX());
			mario.setY(m.getY());
		}
		
		
		private void setCustomFont(){
			InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("AdventPro-Medium.ttf");
			try {
				Font font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(14f);
				StyledComponent.setBaseFont(font);
			} catch (FontFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void setFile(File f) {
		List<String> lines = FileLoader.getFileAsLines(f);
		System.out.println("Loading file:");
		for (String line : lines){
			System.out.println(line);
		}
	}
	@Override
	public JFrame getWindow() {
		return this;
	}
	

	
}
