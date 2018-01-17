package guiTeacher.interfaces;

import java.io.File;

import javax.swing.JFrame;

/**
 * Must be implemented by Screens that use FileOpenButtons
 * @author bnockles
 *
 */
public interface FileRequester{

	/**
	 * Called after a file is selected by the FileLoader class
	 * @param f
	 */
	void setFile(File f);
	
	/**
	 * Must return the window from which the FileLoader is loading from
	 * @return
	 */
	JFrame getWindow();
}
