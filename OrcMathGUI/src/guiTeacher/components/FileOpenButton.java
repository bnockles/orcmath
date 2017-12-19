package guiTeacher.components;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import guiTeacher.interfaces.FileRequester;
import guiTeacher.userInterfaces.FileLoader;




public class FileOpenButton extends ImageTextButton {

	public FileOpenButton(int x, int y, int w, int h, Image img, FileRequester requester){
		super("Open",img, x, y, w, h, new Action() {
			
			@Override
			public void act() {
				new FileLoader(requester);
			}
		});
	}



	
	
}
