package holiday;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;

import guiTeacher.components.MovingComponent;


public class Banner extends MovingComponent {

	private int spanWidth;
	
	public Banner(int spanWidth) {
		super(spanWidth, 30, 260, 60);
		this.spanWidth = spanWidth;
		setVx(-1);
		Thread go = new Thread(this);
		go.start();
	}

	@Override
	public void drawImage(Graphics2D g) {
		g.setColor(Color.white);
		try {
			File fontFile = new File("resources/Holiday.ttf");
			//			File fontFile = new File("resources//DayRoman.ttf");
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			Font baseFont=font.deriveFont(36f);
			g.setFont(baseFont);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawString("Happy New Year!", 0, getHeight()-10);
	}

	@Override
	public void checkBehaviors() {
		if(getX()+getWidth() < 0){
			setX(spanWidth);
		}
	}

}
