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
import guiTeacher.components.TextLabel;
import guiTeacher.interfaces.DrawInstructions;
import guiTeacher.interfaces.FocusController;
import guiTeacher.interfaces.Visible;
import main.OrcMath;

public class LaTeXReference extends FullFunctionPane {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8299483315862465532L;
	private static final int _WIDTH = 400;
	private static final int _HEIGHT = 40;
	private CustomImageButton dismiss;

	public LaTeXReference(FocusController focusController, int x, int y, int w, int h) {
		super(focusController, x, y, w, h);
	}

	@Override
	public void initAllObjects(List<Visible> v){
		String[] codes1 = {"\\\\","\\,","\\text{plain text}","\\frac{a}{b}","sqrt{x}","x_1,\\,y_2","x^2","\\log","\\cos","\\sin","\\tan","\\to","\\left(\\frac{tall}{paren}\\right)"};
		String[] images1 = {"newline.png","space.png","text.png","frac.png","sqrt.png","sub.png","exp.png","log.png","cos.png","sin.png","tan.png","to.png","tallParen.png"};	
		int y =1;
		y = addSection(v, y, "BASIC", codes1, images1);
		
		String[] codes2 = {"\\overline{AB}","\\overrightarrow{AB}","\\overleftrightarrow{AB}","\\angle","\\45^\\circ","\\theta","\\cong","\\sim","\\perp","\\triangle"};
		String[] images2 = {"segment.png","ray.png","line.png","angle.png","degrees.png","theta.png","cong.png","sim.png","perp.png","triangle.png"};
		y = addSection(v, y, "GEOMETRY", codes2, images2);
		String[] codes3 = {"\\frac{\\partial u}{\\partial t}","\\lim_{x \\to +\\infty}","\\int_a^b f(x)\\,dx","\\sum_{i=1}^{2n}"};
		String[] images3 = {"dudt.png","lim.png","integral.png","sum.png"};
		y = addSection(v, y, "CALCULUS", codes3, images3);

		
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
	
	private int addSection(List<Visible> v, int y, String name, String[] codes, String[] images){
		v.add(new Section(y*_HEIGHT, name));
		y++;
		for(int i = 0; i < codes.length; i++){
			v.add(new LaTeXRef(y, codes[i], new Graphic(0, 0,.7, "resources/latex/"+images[i])));
			y++;
		}
		return y;
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
	
	private class Section extends TextLabel{

		public Section(int y, String text) {
			super(0, y, _WIDTH, 26, text);
			setSize(18);
			setCustomAlign(TextLabel.ALIGN_CENTER);
			update();
		}
		
		public void update(Graphics2D g){
			super.update(g);
			g.setStroke(new BasicStroke(2));
			int h = g.getFontMetrics().getHeight()+2;
			g.drawLine(20, h, getWidth()-20, h);
		}
		
	}
	private class LaTeXRef extends TextField{

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
