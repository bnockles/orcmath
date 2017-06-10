package components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

import guiTeacher.components.Action;
import guiTeacher.components.Component;
import guiTeacher.components.CustomImageButton;
import guiTeacher.components.FullFunctionPane;
import guiTeacher.components.Graphic;
import guiTeacher.components.ScrollablePane;
import guiTeacher.components.TextField;
import guiTeacher.interfaces.DrawInstructions;
import guiTeacher.interfaces.FocusController;
import guiTeacher.interfaces.Visible;
import main.OrcMath;

public class LaTeXReference extends FullFunctionPane {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8299483315862465532L;
	private CustomImageButton dismiss;

	public LaTeXReference(FocusController focusController, int x, int y, int w, int h) {
		super(focusController, x, y, w, h);
	}

	@Override
	public void initAllObjects(List<Visible> v){
		String[] codes = {"\\\\","\\text{plain text}","\\frac{a}{b}","sqrt{x}","x^2","\\left(\\frac{tall}{paren}\\right)",
				"\\overline{AB}","overleftrightarrow.png"};
		String[] images = {"newline.png","text.png","frac.png","sqrt.png","exp.png","tallParen.png","segment.png","line.png"};
		for(int i = 0; i < codes.length; i++){
			System.out.println(images[i]);
			v.add(new LaTeXRef(i+1, codes[i], new Graphic(0, 0,.8, "resources/latex/"+images[i])));
		}
		dismiss = new CustomImageButton(5, 5, 20, 20, new DrawInstructions() {
			
			@Override
			public void draw(Graphics2D g, boolean highlight) {
				g.setColor(new Color(188,133,153));
				g.fillOval(0,0,20,20);
				g.setColor(Color.white);
				g.setStroke(new BasicStroke(3f));
				g.drawLine(2, 2, 18, 18);
				g.drawLine(2, 18, 18, 2);
			}
		}, new Action() {
			
			@Override
			public void act() {
				OrcMath.createScreen.showGuide(false);;
			}
		});
	}
	
	public void drawBorder(Graphics2D g) {
		super.drawBorder(g);
		g.drawImage(dismiss.getImage(),dismiss.getX(), dismiss.getY(),null);	
	}

	public void act(){
		super.act();
		if(dismiss.isHovered(xRelative, yRelative)){
			dismiss.act();
		}
	}
	
	private class LaTeXRef extends TextField{

		private static final int _WIDTH = 400;
		private static final int _HEIGHT = 60;
		
		private Graphic image;
		
		
		public LaTeXRef(int i,String text, Graphic image) {
			super(0, i*_HEIGHT, _WIDTH, _HEIGHT, text);
			this.image = image;
			setVerticalAlign(TextField.CENTER);
			setFont(getMonoFont());
			setReadOnly(true);
			setDrawBorder(false);
		}
		
		public void update(Graphics2D g){
			super.update(g);
			if(image != null) {
				int y = DESCRIPTION_SPACE+(_HEIGHT-DESCRIPTION_SPACE-image.getHeight())/2;
				
				g.drawImage(image.getImage(), _WIDTH-image.getWidth(), y, null);
			}
		}
		
	}
}
