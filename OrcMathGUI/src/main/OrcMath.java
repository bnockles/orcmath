/*******************************************************************************
 * Copyright (c) 2017 Benjamin Nockles
 *
 * This file is part of OrcMath.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License 
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import data.SettingsData;
import guiTeacher.GUIApplication;
import guiTeacher.components.Action;
import guiTeacher.components.Button;
import guiTeacher.components.Graphic;
import guiTeacher.components.StyledComponent;
import guiTeacher.components.TextArea;
import guiTeacher.components.TextLabel;
import screens.CreateScreen;
import screens.OrcMathScreen;
import screens.Settings;

/**
 * @author bnockles
 *
 */
public class OrcMath extends GUIApplication {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8096423404631205711L;
	public static final int SCREEN_WIDTH = 920; 
	public static final int SCREEN_HEIGHT = 700; 
	public static final Color ACCENT_COLOR = new Color(127,0,255);
	public static final Color HIGHLIGHT_COLOR = new Color(220,212,242);

	public static OrcMath app;
	public static CreateScreen createScreen;
	public static Settings settings;
	public static SettingsData sd;
	private boolean checkUpdates = true;
	private static String[] versionLog;
	private static FirstTimeWindow firstTime;


	/**
	 * @param width
	 * @param height
	 */
	public OrcMath(int width, int height) {
		super(width, height);
		setIconImage(new ImageIcon("resources/appIcon.png").getImage());
		setResizable(false);
		if(checkUpdates){
			try {
				// Create a URL for the desired page
				URL url = new URL("https://bnockles.github.io/orcmath/version.html");       

				// Read all the text returned by the server
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String str;
				String text = "";
				while ((str = in.readLine()) != null) {
					str =str+ in.readLine().toString();
					text+=str;
					System.out.println("Reading "+str);
					// str is one line of text; readLine() strips the newline character(s)
				}
				//				String body = text.substring(text.indexOf("<body>")+6,str.indexOf("</body>"));
				String body = text.replaceAll("\\<.*?>","");
				versionLog = body.split("-BREAK-");
				in.close();
				sd.checkForUpdates(versionLog);
			} catch (MalformedURLException e) {
			} catch (IOException e) {
			}
		}
	}


	@Override
	public void initScreen() {
		StyledComponent.setStaticBorderColor(new Color(51,0,102));
		StyledComponent.setTabHeight(25);
		StyledComponent.setTabColor(new Color(0,153,153));
		StyledComponent.setTabShadeColor(new Color(0,127,119));
		StyledComponent.setAccentColor(ACCENT_COLOR);
		StyledComponent.setHighlightColor(HIGHLIGHT_COLOR);
		try {
			File fontFile = new File("resources//AdventPro-Medium.ttf");
			//			File fontFile = new File("resources//DayRoman.ttf");
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			Font baseFont=font.deriveFont(16f);
			StyledComponent.setBaseFont(baseFont);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//			File fontFile = new File("resources//AdventPro-Medium.ttf");
			File fontFile = new File("resources//Baumans-Regular.ttf");
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			Font baseFont=font.deriveFont(16f);
			StyledComponent.setTabFont(baseFont);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sd = new SettingsData();
		settings = new Settings(getWidth(),getHeight());

		createScreen = new CreateScreen(getWidth(),getHeight());
		createScreen.setDirectory(new File(sd.getDirectory()));
		//		createScreen.setDirectory(new File(sd.getDirectory()));
		setScreen(createScreen);

		if(sd.presentOutdatedNotification()){
			createScreen.presentOutdatedNotification();
		}
		if(sd.getDirectory().equals("")){
			int popUpWidth = 500;
			int popUpHeight = 350;
			int popUpX = getX()+(getWidth())-popUpWidth;
			int popUpY = getY();
			firstTime = new FirstTimeWindow(popUpX,popUpY,popUpWidth,popUpHeight);
			firstTime.setAlwaysOnTop(true);
			firstTime.repaint();



		}else{
			settings.setDirectoryText(sd.getDirectory());
		}
	}

	private class FirstTimeWindow extends JFrame implements MouseListener, MouseMotionListener{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4498632691127227190L;
		private TextLabel welcome;
		private TextArea notice;
		private Button gotit;
		private int pressX;
		private int pressY;

		public FirstTimeWindow(int x, int y, int w, int h){
			super();
			setBounds(x, y,w,h);
			welcome = new TextLabel(200,10,w-20,30,"Welcome to OrcMath!");
			notice = new TextArea(10,30,w-20,h-60,"To get started, you must do the following. (You can keep these instructions open until you finish.)\n"
					+ "1) click the purple gear in the bottom left corner.\n"
					+ "2) At the top left corner of the screen, Click the link that says \"Edit directory\"\n"
					+ "3) Select a location to save the files that will be generated by this program.\n"
					+ "4) That's it! On this screen (the \"Settings\" screen), you can customize your worksheets. Click the purple arrow at the bottom-right corner to select problems and begin generating pages.");
			notice.setSize(16f);
			notice.update();
			gotit = new Button((w)/2-50,h-60,100,50,"Got It!",OrcMath.ACCENT_COLOR, new Action(){
				@Override
				public void act() {
					FirstTimeWindow.this.setVisible(false);
					FirstTimeWindow.this.dispose();
				}

			});
			addMouseListener(this);
			addMouseMotionListener(this);
			setResizable(false);
			setUndecorated(true);
			pressX = 0;
			pressY = 0;
		}


		public void paint(Graphics g){
			g.setColor(Color.white);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.black);
			g.drawImage(welcome.getImage(), welcome.getX(), welcome.getY(), null);
			g.drawImage(notice.getImage(), notice.getX(), notice.getY(), null);
			g.drawImage(gotit.getImage(), gotit.getX(), gotit.getY(), null);
		}


		@Override
		public void mouseClicked(MouseEvent e) {
			if(gotit.isHovered(e.getX(), e.getY())){
				gotit.act();
			}
		}


		@Override
		public void mousePressed(MouseEvent e) {
			pressX = e.getX();
			pressY = e.getY();
		}


		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}


		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}


		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}


		@Override
		public void mouseDragged(MouseEvent e) {
			int newx = this.getX()+e.getX()-pressX;
			int newy = this.getY()+e.getY()-pressY;
			setBounds(newx, newy, getWidth(), getHeight());
		}


		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}




	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OpeningImage open = new OpeningImage();
		open.setVisible(true);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				app = new OrcMath(SCREEN_WIDTH, SCREEN_HEIGHT);
				open.setVisible(false);
				app.setVisible(true);
				if(firstTime!=null)firstTime.setVisible(true);
				Thread go = new Thread(app);
				go.start();


			}
		});
	}




}
