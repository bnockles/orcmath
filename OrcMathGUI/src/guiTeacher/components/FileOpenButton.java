package guiTeacher.components;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import guiTeacher.interfaces.FileRequester;
import guiTeacher.userInterfaces.FileLoader;



/**
 * This file creates a Button that, when clicked, will allow the user to select a file. Using this class requires a FileRequester (which is often implemented by the containing Screen)
 * @author bnockles
 *
 */
public class FileOpenButton extends ImageTextButton {

	/**
	 * 
	 * @param x x-coordinate within container
	 * @param y y-coordinate within container
	 * @param w width
	 * @param h height
	 * @param img an icon representing "Open", may be null
	 * @param requester implementation of FileRequester (often the containing Screen)
	 */
	public FileOpenButton(int x, int y, int w, int h, Image img, FileRequester requester){
		super("Open",img, x, y, w, h, new Action() {
			
			@Override
			public void act() {
				new FileLoader(requester);
			}
		});
	}



	
	
}
