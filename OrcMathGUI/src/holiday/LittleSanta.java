package holiday;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import guiTeacher.components.AnimatedComponent;
import guiTeacher.components.Graphic;

public class LittleSanta extends AnimatedComponent{

	private Graphic falling;
	
	public LittleSanta() {
		super(65, 300, 34, 49);
		addSequence("resources/santa.gif", 110, 16, 352, 34, 49, 3);
		setVy(-1);
		//create another sequence for when Santa falls:
		falling = new Graphic(40, 300, 34, 49,"resources/falling.png");
		
		Thread go = new Thread(this);
		go.start();
	}
	
	@Override
	public BufferedImage getImage() {
		if(getVy() < 0){
			return super.getImage();
		}else{
			return falling.getImage();
		}
	}

	@Override
	public void checkBehaviors(){
		if(getY()<55){
			setVy(3);
		}else if(getY()>400){
			setVy(-1);
		}
	}


}
