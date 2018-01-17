package guiTeacher.interfaces;

import java.awt.Graphics2D;

/**
 * Used by CustomImageButtons to define the appearance of the buttons
 * @author bnockles
 *
 */
public interface DrawInstructions {

	
	void draw(Graphics2D g, boolean highlight);
}
