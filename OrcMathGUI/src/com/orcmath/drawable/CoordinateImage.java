/*******************************************************************************
 * Copyright (c) 2012-2017 Benjamin Nockles
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
package com.orcmath.drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import com.orcmath.drawabletype.DrawableOps;
import com.orcmath.objects.Variable;


public class CoordinateImage {

	private BufferedImage image;
	private Graphics2D g;
	private int margin = 30;
	private int width;
	private int height;
	private double xMin;
	private double xMax;
	private double yMin;
	private double yMax;

	public static CoordinatePoint ORIGIN = new CoordinatePoint(0, 0);
	public static int X_COORDINATE=0;
	public static int Y_COORDINATE=1;

	//these fields determine default appearances
	int hatchMarkLength=5;
	int labelSize=20;
	Font labelFont = new Font("Times",Font.PLAIN,28);
	Font axesFont = new Font("Times",Font.PLAIN,16);


	//this constructor makes rectangular Coordinate Images with the x/yscale set so that grid is square
	public CoordinateImage(int width, int height, double min, double max, int setToThisAxis){
		this.width=width-(2*margin);
		this.height=height-(2*margin);
		if(setToThisAxis==X_COORDINATE){
			this.xMin=min;
			this.xMax=max;
			double widthTotal = max - min;
			double boxWidth = this.width/widthTotal;
			yMin = this.height/(-2*boxWidth);
			yMax = this.height/(2*boxWidth);
		}else{
			this.yMin=min;
			this.yMax=max;
			double heightTotal = max - min;
			double boxHeight= this.height/heightTotal;
			xMin = this.width/(-2*boxHeight);
			xMax = this.width/(2*boxHeight);
		}

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = image.createGraphics();
		g.translate(margin+this.width/2, margin+this.height/2);
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.black);
		g.setFont(labelFont);
	}

	public CoordinateImage(int width, int height, double xMin, double xMax, double yMin, double yMax){
		this.width=width-(2*margin);
		this.height=height-(2*margin);
		this.xMin=xMin;
		this.xMax=xMax;
		this.yMin=yMin;
		this.yMax=yMax;

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = image.createGraphics();
		
		double xDifference=xMax-xMin;
		double yDifference=yMax-yMin;
		double xMinRatio = Math.abs(xMin/xDifference);
		double yMaxRatio = Math.abs(yMax/yDifference);
		g.translate(margin+this.width*xMinRatio, margin+this.height*yMaxRatio);
//		g.translate(margin+this.width/2, margin+this.height/2);
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.black);
		g.setFont(labelFont);
	}

	//this constructor is used to make copies because the buffered image is duplicated
	public CoordinateImage(int width, int height, double xMin, double xMax, double yMin, double yMax, BufferedImage bi){
		this.width=width;
		this.height=height;
		this.xMin=xMin;
		this.xMax=xMax;
		this.yMin=yMin;
		this.yMax=yMax;

		image = bi;
		g = image.createGraphics();
		g.translate(margin+this.width/2, margin+this.height/2);
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.black);
		g.setFont(labelFont);
	}

	public void setColor(Color color){
		g.setColor(color);
	}

	public void setThickness(int thickness){
		g.setStroke(new BasicStroke(thickness));
	}

	public void drawVector(CoordinatePoint start, CoordinatePoint end){
		drawSegment(start,end);
		Circle endCircle = new Circle(end, 1);
		double angle = endCircle.getStandardPositionAngle(start, true);
		CoordinatePoint arrow1 = endCircle.getPointOnCircle(angle-25, true);
		CoordinatePoint arrow2 = endCircle.getPointOnCircle(angle+25, true);
		drawSegment(arrow1,end);
		drawSegment(arrow2,end);
	}

	public void drawLinearGraph(double[] constants){
		System.out.println("Attempting to draw graph of line with \n   m="+constants[0]+"\n   b="+constants[1]);
		if(constants[0]==DrawableOps.undefinedIdentifier){
			CoordinatePoint p1 = new CoordinatePoint(constants[1],yMin-4);
			CoordinatePoint p2 = new CoordinatePoint(constants[1],yMax+4);
			drawSegment(p1, p2);
		}else{
			CoordinatePoint p1 = new CoordinatePoint(xMin-4,constants[0]*(xMin-4)+constants[1]);
			CoordinatePoint p2 = new CoordinatePoint(xMax+4,constants[0]*(xMax+4)+constants[1]);
			drawSegment(p1, p2);
		}
	}

	public void addAxes(int xScale,int yScale, boolean includeLabels){
		Stroke stroke = g.getStroke();
		g.setStroke(new BasicStroke(2));
		int arrowWidth=5;
		double arrowLengthScale=2;
		int xMinLocation=getLocation(xMin, X_COORDINATE)-margin;
		int yMinLocation=getLocation(yMin, Y_COORDINATE)+margin;
		int xMaxLocation=getLocation(xMax, X_COORDINATE)+margin;
		int yMaxLocation=getLocation(yMax, Y_COORDINATE)-margin;
		
		int entireHeight = height+2*margin;
		int entireWidth = width+2*margin;
		g.drawLine(xMinLocation, 0, xMaxLocation, 0);
		g.drawLine(0,yMaxLocation,0, yMinLocation);
//		g.drawLine(0, entireHeight/2,0, -entireHeight/2);
		g.drawLine(xMinLocation, 0, xMinLocation+(int)(margin/arrowLengthScale), -arrowWidth);//left arrow
		g.drawLine(xMinLocation, 0, xMinLocation+(int)(margin/arrowLengthScale), arrowWidth);//left arrow

		g.drawLine(xMaxLocation, 0, xMaxLocation-(int)(margin/arrowLengthScale), arrowWidth);//right arrow
		g.drawLine(xMaxLocation, 0, xMaxLocation-(int)(margin/arrowLengthScale), -arrowWidth);//right arrow


		g.drawLine(0,yMaxLocation,-arrowWidth,yMaxLocation+(int)(margin/arrowLengthScale));//top arrow
		g.drawLine(0,yMaxLocation,+arrowWidth,yMaxLocation+(int)(margin/arrowLengthScale));//top arrow

		g.drawLine(0,yMinLocation,-arrowWidth,yMinLocation-(int)(margin/arrowLengthScale));//bottom arrow
		g.drawLine(0,yMinLocation,+arrowWidth,yMinLocation-(int)(margin/arrowLengthScale));//bottom arrow

		//add axes marks
		for(int index = xScale; index<Math.abs(xMin); index+=xScale){
			g.drawLine(getLocation((-1)*index, X_COORDINATE),hatchMarkLength,getLocation((-1)*index, X_COORDINATE),-hatchMarkLength);
		}
		for(int index = xScale; index<xMax; index+=xScale){
			g.drawLine(getLocation(index, X_COORDINATE),hatchMarkLength,getLocation(index, X_COORDINATE),-hatchMarkLength);
		}
		for(int index = yScale; index<Math.abs(yMin); index+=yScale){
			g.drawLine(-hatchMarkLength,getLocation((-1)*index, Y_COORDINATE),hatchMarkLength,getLocation((-1)*index, Y_COORDINATE));
		}
		for(int index = yScale; index<yMax; index+=yScale){
			g.drawLine(-hatchMarkLength,getLocation(index, Y_COORDINATE),hatchMarkLength,getLocation(index, Y_COORDINATE));
		}
		g.setStroke(stroke);
		if(includeLabels)addLabels(xScale,yScale);
	}

	public void addLabels(int xScale,int yScale){
		int entireHeight = height+2*margin;
		int entireWidth = width+2*margin;
		g.setFont(axesFont);
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int fontHeight = metrics.getHeight();
		for(int index = xScale; index<Math.abs(xMin); index+=xScale){
			int lineStart = metrics.stringWidth(""+((-1)*index))/2;
			g.drawString(""+((-1)*index),getLocation((-1)*index, X_COORDINATE)-lineStart,fontHeight+3);
		}
		for(int index = xScale; index<xMax; index+=xScale){
			int lineStart = metrics.stringWidth(""+index)/2;
			g.drawString(""+index,getLocation(index, X_COORDINATE)-lineStart,fontHeight+3);
		}
		for(int index = yScale; index<Math.abs(yMin); index+=yScale){
			int lineStart = metrics.stringWidth(""+((-1)*index))+3;
			g.drawString(""+((-1)*index),-hatchMarkLength-lineStart,getLocation((-1)*index, Y_COORDINATE)+fontHeight/3);
		}
		for(int index = yScale; index<yMax; index+=yScale){
			int lineStart = metrics.stringWidth(""+index)+3;
			g.drawString(""+index,-hatchMarkLength-lineStart,getLocation(index, Y_COORDINATE)+fontHeight/3);
		}
		g.setFont(labelFont);
	}

	public void addGrid(int xScale,int yScale){
		Stroke stroke = g.getStroke();
		Color color = g.getColor();
		g.setStroke(new BasicStroke(1));
		g.setColor(new Color(180,180,180));

		for(int index = xScale; index<Math.abs(xMin); index+=xScale){
			g.drawLine(getLocation((-1)*index, X_COORDINATE),getLocation(yMin, Y_COORDINATE),getLocation((-1)*index, X_COORDINATE),getLocation(yMax, Y_COORDINATE));
		}
		for(int index = xScale; index<xMax; index+=xScale){
			g.drawLine(getLocation(index, X_COORDINATE),getLocation(yMin, Y_COORDINATE),getLocation(index, X_COORDINATE),getLocation(yMax, Y_COORDINATE));
		}
		for(int index = yScale; index<Math.abs(yMin); index+=yScale){
			g.drawLine(getLocation(xMin, X_COORDINATE),getLocation((-1)*index, Y_COORDINATE),getLocation(xMax, X_COORDINATE),getLocation((-1)*index, Y_COORDINATE));
		}
		for(int index = yScale; index<yMax; index+=yScale){
			g.drawLine(getLocation(xMin, X_COORDINATE),getLocation(index, Y_COORDINATE),getLocation(xMax, X_COORDINATE),getLocation(index, Y_COORDINATE));
		}
		g.setColor(Color.black);
		g.drawRect(getLocation(xMin, X_COORDINATE),getLocation(yMax, Y_COORDINATE),this.width,this.height);
//		g.drawRect(-this.width/2,-this.height/2,this.width,this.height);
		g.setStroke(stroke);
		g.setColor(color);
	}

	public void drawPoint(CoordinatePoint point) {
		int x = getLocation(point.getxCoordinate(), X_COORDINATE);
		int y = getLocation(point.getyCoordinate(), Y_COORDINATE);
		g.fillOval(x-6, y-6, 12, 12);
	}

	public void drawOpenPoint(CoordinatePoint point) {
		Color currentColor=g.getColor();
		int x = getLocation(point.getxCoordinate(), X_COORDINATE);
		int y = getLocation(point.getyCoordinate(), Y_COORDINATE);
		g.fillOval(x-6, y-6, 12, 12);
		g.setColor(Color.white);
		g.fillOval(x-4, y-4, 8, 8);
		g.setColor(currentColor);
	}
	
	public void drawSegment(CoordinatePoint endpoint1,CoordinatePoint endpoint2){
		g.drawLine(getLocation(endpoint1.getxCoordinate(), X_COORDINATE), 
				getLocation(endpoint1.getyCoordinate(), Y_COORDINATE), 
				getLocation(endpoint2.getxCoordinate(), X_COORDINATE), 
				getLocation(endpoint2.getyCoordinate(), Y_COORDINATE));
	}
	
	public void drawDottedSegment(CoordinatePoint endpoint1,CoordinatePoint endpoint2){
		boolean visible = true;
		double distance = DrawableOps.getDistance(endpoint1, endpoint2);
		double numberOfDashes=distance/.5;
		double changeInY=endpoint2.getyCoordinate()-endpoint1.getyCoordinate();
		double changeInX=endpoint2.getxCoordinate()-endpoint1.getxCoordinate();
		double iterationChangeInY = changeInY/numberOfDashes;
		double iterationChangeInX = changeInX/numberOfDashes;
		for(int iteration =0; iteration+1<numberOfDashes; iteration+=1){
			if(visible){
				g.drawLine(getLocation(endpoint1.getxCoordinate()+iteration*iterationChangeInX, X_COORDINATE), 
						getLocation(endpoint1.getyCoordinate()+iteration*iterationChangeInY, Y_COORDINATE), 
						getLocation(endpoint1.getxCoordinate()+(iteration+1)*iterationChangeInX, X_COORDINATE), 
						getLocation(endpoint1.getyCoordinate()+(iteration+1)*iterationChangeInY, Y_COORDINATE));
			}
			visible=!visible;
		}
		
		
	}

	public void drawSegment(CoordinateSegment segment){
		g.drawLine(getLocation(segment.getEndpoint1().getxCoordinate(), X_COORDINATE), 
				getLocation(segment.getEndpoint1().getyCoordinate(), Y_COORDINATE), 
				getLocation(segment.getEndpoint2().getxCoordinate(), X_COORDINATE), 
				getLocation(segment.getEndpoint2().getyCoordinate(), Y_COORDINATE));
	}

	public void drawCircle(Circle circle) {
		CoordinatePoint center = circle.getCenter();
		double radius = circle.getRadius();
		int x = getLocation(center.getxCoordinate()-radius, X_COORDINATE);
		int y = getLocation(center.getyCoordinate()+radius, Y_COORDINATE);
		g.drawOval(x, y, (int)(radius*2*(width/(xMax-xMin))), (int)(radius*2*height/(yMax-yMin)));	
	}
	
	public void drawTriangle(Triangle t){
		drawSegment(t.getSegmentA());
		drawSegment(t.getSegmentB());
		drawSegment(t.getSegmentC());
	}
	
	public void drawOval(double centerX, double centerY, double width, double height) {
		int x = getLocation(centerX-width/2, X_COORDINATE);
		int y = getLocation(centerY-height/2, Y_COORDINATE);
		g.drawOval(x, y, (int)(width*(this.width/(xMax-xMin))), (int)(height*this.height/(yMax-yMin)));	
	}

	public void drawArc(double centerX, double centerY, double width, double height, int start, int end){
		int x = getLocation(centerX-width/2, X_COORDINATE);
		int y = getLocation(centerY-height/2, Y_COORDINATE);
		g.drawArc(x, y, (int)(width*(this.width/(xMax-xMin))), (int)(height*this.height/(yMax-yMin)),start,end);	
	}

	public void drawDottedArc(double centerX, double centerY, double width, double height, int start, int end){
		boolean visible=true;
		for(int iteration =start; iteration<end; iteration=iteration+5){
			if(visible)drawArc(centerX, centerY, width, height, iteration, 5);
			visible=!visible;
		}
	}
	
	public void drawRotationArc(Circle circle, CoordinatePoint startPoint, CoordinatePoint endPoint,boolean arrow){
		int startPointPosition = (int)(circle.getStandardPositionAngle(startPoint, true));
		int endpointPosition = (int)(circle.getStandardPositionAngle(endPoint, true));
		Circle smallCircle = new Circle(circle.getCenter(),circle.getRadius()-.5);	
		Circle largeCircle = new Circle(circle.getCenter(),circle.getRadius()+.5);
		CoordinatePoint arrowP1;
		CoordinatePoint arrowP2;
		if(endpointPosition-startPointPosition>0){
			g.drawArc(getLocation(circle.getCenter().getxCoordinate()-circle.getRadius(),X_COORDINATE),
					getLocation(circle.getCenter().getyCoordinate()+circle.getRadius(),Y_COORDINATE), 
					getLocation(2*circle.getRadius(),X_COORDINATE), 
					getLocation(-2*circle.getRadius(),Y_COORDINATE), 
					startPointPosition,
					endpointPosition-startPointPosition);
			if(arrow){
				arrowP1 = smallCircle.getPointOnCircle(endpointPosition-5, true);
				arrowP2 = largeCircle.getPointOnCircle(endpointPosition-5, true);
				drawSegment(endPoint, arrowP1);
				drawSegment(endPoint, arrowP2);
			}
		}else{
			g.drawArc(getLocation(circle.getCenter().getxCoordinate()-circle.getRadius(),X_COORDINATE),
					getLocation(circle.getCenter().getyCoordinate()+circle.getRadius(),Y_COORDINATE), 
					getLocation(2*circle.getRadius(),X_COORDINATE), 
					getLocation(-2*circle.getRadius(),Y_COORDINATE), 
					endpointPosition,
					startPointPosition-endpointPosition);
			if(arrow){
				arrowP1 = smallCircle.getPointOnCircle(startPointPosition-5, true);
				arrowP2 = largeCircle.getPointOnCircle(startPointPosition-5, true);
				drawSegment(startPoint, arrowP1);
				drawSegment(startPoint, arrowP2);
			}
		}
	}

	public BufferedImage getImage(){
		return image;
	}


	//returns the pixel value of a coordinate
	public int getLocation(double coordinate, int type){
		int location = 0;
		double min=yMin;
		double max=yMax;
		int entireSpan=-height;
		double difference=max-min;
		//		double center = max*entireSpan/(difference);	
		if (type==X_COORDINATE){
			min=xMin;
			max=xMax;
			entireSpan=width;
			difference=max-min;
			//			center = (-1)*min*entireSpan/(difference);
		}

		location = (int)(coordinate*(entireSpan/difference));//this is the number of pixels the coordinate value corresponds to
		//		if(type==X_COORDINATE){
		//			location=(int) (location+center+margin);
		//		}else{
		//			location=(int) (margin+center-location);
		//		}

		return location;
	}

	public void drawCircleLabel(String string, Circle circle, double angle, boolean inDegrees) {
		CoordinatePoint location = circle.getPointOnCircle(angle, inDegrees);
		CoordinatePoint center = circle.getCenter();
		g.setFont(labelFont);
		FontMetrics fm= g.getFontMetrics();
		int labelHeight = fm.getHeight();
//		System.out.println("Drawing label ("+string+")with height = "+labelHeight);
		if((location.getxCoordinate()-center.getxCoordinate())==0){//label is perfectly centered on top or bottom
			int lineStart = fm.stringWidth(string)/2;
			if(location.getyCoordinate()>center.getyCoordinate()){//top center		
				g.drawString(string, getLocation(location.getxCoordinate(), X_COORDINATE)-lineStart, getLocation(location.getyCoordinate(), Y_COORDINATE)-3);
			}else{//bottom center
				g.drawString(string, getLocation(location.getxCoordinate(), X_COORDINATE)-lineStart, getLocation(location.getyCoordinate(), Y_COORDINATE)+labelHeight+3);
			}
		}else if((location.getyCoordinate()-center.getyCoordinate())==0){//label is perfectly centered on left or right	
			if(location.getxCoordinate()>center.getxCoordinate()){//on right
				g.drawString(string, getLocation((location.getxCoordinate()),X_COORDINATE)+3, getLocation(location.getyCoordinate(), Y_COORDINATE)-labelHeight/2);
			}else{//on left
				g.drawString(string, getLocation((location.getxCoordinate()),X_COORDINATE)+3, getLocation(location.getyCoordinate(), Y_COORDINATE)-labelHeight/2);
			}
		}else if((location.getyCoordinate()-center.getyCoordinate())>0){//label is above
			if(location.getxCoordinate()>center.getxCoordinate()){//on right
				g.drawString(string, getLocation((location.getxCoordinate()),X_COORDINATE)+2, getLocation(location.getyCoordinate(), Y_COORDINATE));
			}else{//on left
				int lineStart = fm.stringWidth(string);
				g.drawString(string, getLocation((location.getxCoordinate()),X_COORDINATE)+-lineStart-2, getLocation(location.getyCoordinate(), Y_COORDINATE));
			}
		}
		else{//label is below
			if(location.getxCoordinate()>center.getxCoordinate()){//on right
				g.drawString(string, getLocation((location.getxCoordinate()),X_COORDINATE)+2, getLocation(location.getyCoordinate(), Y_COORDINATE)+labelHeight);
			}else{//on left
				int lineStart = fm.stringWidth(string);
				g.drawString(string, getLocation((location.getxCoordinate()),X_COORDINATE)+-lineStart-2, getLocation(location.getyCoordinate(), Y_COORDINATE)+labelHeight);
			}	
		}
	}

	public void drawCircleLabel(String string, Circle circle, CoordinatePoint point) {
		//takes a point and turns it into an angle
		double distanceFromCenter = DrawableOps.getDistance(point, circle.getCenter());
		Circle newCircle;
		double sine;
		double cosine;
		double angle1 ;
		if(distanceFromCenter==0){
			distanceFromCenter = DrawableOps.getDistance(point, ORIGIN);
			newCircle = new Circle(ORIGIN,distanceFromCenter);
			sine=point.getyCoordinate()/distanceFromCenter;
			cosine=point.getxCoordinate()/distanceFromCenter;
		}else {
			newCircle = new Circle(circle.getCenter(),distanceFromCenter);
			sine=(point.getyCoordinate()-circle.getCenter().getyCoordinate())/distanceFromCenter;
			cosine=(point.getxCoordinate()-circle.getCenter().getxCoordinate())/distanceFromCenter;
		}
		angle1 = Math.asin(sine);
		if(sine>=0&&cosine>=0){//quadrant I
			drawCircleLabel(string, newCircle, angle1, false);
		}else if(sine>=0&&cosine<0){//quadrant II
			drawCircleLabel(string, newCircle, Math.PI-angle1, false);
		}else if(sine<0&&cosine>=0){//quadrant IV
			drawCircleLabel(string, newCircle, 2*Math.PI+angle1, false);
		}
		else{//quadrant III
			drawCircleLabel(string, newCircle, Math.PI-angle1, false);
		}
	}

	public void drawAngleVertexLabel(String s, CoordinatePoint aPoint, CoordinatePoint vertex, CoordinatePoint bPoint) {
		if(s.length()>0){
			Circle aroundVertex=new Circle(vertex,.5);
			double aAngle = aroundVertex.getStandardPositionAngle(aPoint, true);
			double bAngle = aroundVertex.getStandardPositionAngle(bPoint, true);
			//			System.out.println("aPoint"+aPoint+", aAngle = "+aAngle+", bPoint"+bPoint+", bAngle ="+bAngle);
			double average= (aAngle+bAngle)/2;;
			double location;
			if(Math.abs(bAngle-aAngle)<180){
				location = average+180;
			}else{
				location=average;
			}
			if(location>360)location=location-360;
			//			System.out.println("Draw location ="+location);
			drawCircleLabel(s, aroundVertex, location,true);
		}
	}

	public void labelFigure(ArrayList<CoordinatePoint> figure){
		double centerX=0;
		double centerY=0;
		for(int index = 0; index< figure.size(); index++){
			centerX+=figure.get(index).getxCoordinate();
			centerY+=figure.get(index).getyCoordinate();
		}
		centerX=centerX/figure.size();
		centerY=centerY/figure.size();
		CoordinatePoint center = new CoordinatePoint(centerX, centerY);
		Circle circle = new Circle(center, 1);
		for(int index = 0; index< figure.size(); index++){
			drawCircleLabel(figure.get(index).getLabel(), circle, figure.get(index));
		}
	}

	public void drawArcLabel(Circle circle, int angle1, int angle2, boolean inDegrees) {
		double average = (angle1+angle2)/2;
		drawCircleLabel(""+Math.abs(angle1-angle2), circle, average, inDegrees);
	}

	public void drawLatex(String s, double xCoordinate, double yCoordinate){
		TeXFormula formula = new TeXFormula(s);
		TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, labelSize);//used to be 20
		JLabel jl = new JLabel();
		jl.setForeground(new Color(0, 0, 0));
		icon.paintIcon(jl, g, getLocation(xCoordinate, X_COORDINATE), getLocation(yCoordinate, Y_COORDINATE));	
	}

	public void drawCircleLatex(String string, Circle circle, int angle, boolean inDegrees) {
		CoordinatePoint location = circle.getPointOnCircle(angle, inDegrees);
		CoordinatePoint center = circle.getCenter();

		TeXFormula formula = new TeXFormula(string);
		TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, labelSize);//used to be 20
		int labelHeight=icon.getIconHeight();
		JLabel jl = new JLabel();
		jl.setForeground(new Color(0, 0, 0));


		if((location.getxCoordinate()-center.getxCoordinate())==0){//label is perfectly centered on top or bottom
			int lineStart =icon.getIconWidth()/2;
			if(location.getyCoordinate()>center.getyCoordinate()){//top center		
				icon.paintIcon(jl,g, getLocation(location.getxCoordinate(), X_COORDINATE)-lineStart, getLocation(location.getyCoordinate(), Y_COORDINATE)-3);
			}else{//bottom center
				icon.paintIcon(jl,g, getLocation(location.getxCoordinate(), X_COORDINATE)-lineStart, getLocation(location.getyCoordinate(), Y_COORDINATE)+labelHeight+3);
			}
		}else if((location.getyCoordinate()-center.getyCoordinate())==0){//label is perfectly centered on left or right	
			if(location.getxCoordinate()>center.getxCoordinate()){//on right
				icon.paintIcon(jl,g, getLocation((location.getxCoordinate()),X_COORDINATE)+3, getLocation(location.getyCoordinate(), Y_COORDINATE)-labelHeight/2);
			}else{//on left
				int lineStart =icon.getIconWidth();
				icon.paintIcon(jl,g, getLocation((location.getxCoordinate()),X_COORDINATE)-lineStart-3, getLocation(location.getyCoordinate(), Y_COORDINATE)-labelHeight/2);
			}
		}else if((location.getyCoordinate()-center.getyCoordinate())>0){//label is above
			if(location.getxCoordinate()>center.getxCoordinate()){//on right
				int lineStart =icon.getIconWidth()/3;
				icon.paintIcon(jl,g, getLocation((location.getxCoordinate()),X_COORDINATE)+lineStart+2, getLocation(location.getyCoordinate(), Y_COORDINATE)-labelHeight);
			}else{//on left
				int lineStart =icon.getIconWidth();
				icon.paintIcon(jl,g, getLocation((location.getxCoordinate()),X_COORDINATE)-lineStart-5, getLocation(location.getyCoordinate(), Y_COORDINATE)-labelHeight);
			}
		}
		else{//label is below
			if(location.getxCoordinate()>center.getxCoordinate()){//on right
				int lineStart =icon.getIconWidth()/3;
				icon.paintIcon(jl,g, getLocation((location.getxCoordinate()),X_COORDINATE)+2, getLocation(location.getyCoordinate(), Y_COORDINATE)+labelHeight/2);
			}else{//on left
				int lineStart =icon.getIconWidth();
				icon.paintIcon(jl,g, getLocation((location.getxCoordinate()),X_COORDINATE)-lineStart-3, getLocation(location.getyCoordinate(), Y_COORDINATE)+labelHeight/2);
			}	
		}

	}

	/* draws a LateX label along the middle of two intersecting line segments
	 * the labels can be on the inside of the angle (useful for concave parallelograms)
	 * but most often this method will be used to assure the label is on the outside of the angle so
	 * the labels can't overlap
	 */
	public void drawAngleSideLatex(String string1,String string2, CoordinatePoint p1, CoordinatePoint vertex, CoordinatePoint p2, boolean outsideAngle) {
		//creates a circle from the vertex to point1
		Circle circle = new Circle(vertex,DrawableOps.getDistance(p1, p2));
		double p2AngularPosition = circle.getStandardPositionAngle(p2, false);
		double p1AngularPosition = circle.getStandardPositionAngle(p1, false);
		double angleMeasure = p2AngularPosition-p1AngularPosition;
		//"above" means, if the position was assigned an angle at standard position, the angle of the label would
		//be greater than the angle of the segment
		boolean aboveSegment1;
		boolean aboveSegment2;
		if(angleMeasure>0 && angleMeasure<=Math.PI){
			System.out.println("Angle set 1 "+angleMeasure);
			aboveSegment1=true;
			aboveSegment2=false;
			if(vertex.getxCoordinate()<=p1.getxCoordinate()){
				aboveSegment1=false;
				if(vertex.getxCoordinate()==p1.getxCoordinate() && vertex.getyCoordinate()<p1.getyCoordinate()){
					aboveSegment1=true;
				}
			}
			if(vertex.getxCoordinate()<=p2.getxCoordinate()){
				aboveSegment2=true;
				if(vertex.getxCoordinate()==p2.getxCoordinate() && vertex.getyCoordinate()>p2.getyCoordinate()){
					aboveSegment1=false;
				}
			}
		}else if(angleMeasure>0 && angleMeasure>Math.PI){
			System.out.println("Angle set 2 "+angleMeasure);
			aboveSegment2=true;
			aboveSegment1=false;
			if(vertex.getxCoordinate()<p1.getxCoordinate()){
				aboveSegment1=true;
			}
			if(vertex.getxCoordinate()<p2.getxCoordinate()){
				aboveSegment2=false;
			}
		}else if(angleMeasure<0 && angleMeasure>=-Math.PI){
			System.out.println("Angle set 3 "+angleMeasure);
			aboveSegment2=true;
			aboveSegment1=false;
			if(vertex.getxCoordinate()<p1.getxCoordinate()){
				aboveSegment1=true;
			}
			if(vertex.getxCoordinate()<p2.getxCoordinate()){
				aboveSegment2=false;
			}
		}else{
			System.out.println("Angle set 4 "+angleMeasure);
			aboveSegment2=false;
			aboveSegment1=true;
			if(vertex.getxCoordinate()<=p1.getxCoordinate()){
				aboveSegment1=false;
			}
			if(vertex.getxCoordinate()<=p2.getxCoordinate()){
				aboveSegment2=true;
			}
		}

		drawSegmentLatex(string1, vertex, p1, true, aboveSegment1);
		drawSegmentLatex(string2, vertex, p2, true, aboveSegment2);

	}

	/*
	 * draws a LateX label along the middle of the given segment;
	 * in this method, lines with positive slope have the label underneath, while 
	 * lines with negative slope have the label above
	 */
	public void drawSegmentLatex(String string,CoordinatePoint p1, CoordinatePoint p2, boolean overwriteDefault, boolean trueForAbove) {
		//creates a segment that will align text properly
		CoordinateSegment segment = new CoordinateSegment(p1, p2);
		double[] coefs = DrawableOps.getLinearEquation(p1, p2);
		boolean top=false;
		if(coefs[0]<0){
			top=true;
			if(p2.getyCoordinate()>=p1.getyCoordinate()){
				segment = new CoordinateSegment(p2, p1);
				if(overwriteDefault) top = trueForAbove;
			}else{
				if(overwriteDefault) top = trueForAbove;
			}
		}else{		
			if(p2.getyCoordinate()<=p1.getyCoordinate()){
				segment = new CoordinateSegment(p2, p1);
				if(overwriteDefault) top = trueForAbove;
			}else{
				if(overwriteDefault) top = trueForAbove;
			}
		}

		//prepares the LaTeX
		TeXFormula formula = new TeXFormula(string);
		TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 25);//used to be 20
		JLabel jl = new JLabel();
		jl.setForeground(new Color(0, 0, 0));

		//the assigned location is where the label WOULD be, without transformation		
		double xTranslate = (segment.getEndpoint1().getxCoordinate()+segment.getEndpoint2().getxCoordinate())/2;
		double yTranslate = (segment.getEndpoint1().getyCoordinate()+segment.getEndpoint2().getyCoordinate())/2;
		Circle circle = new Circle(segment.getEndpoint1(),segment.getDecimalLength());
		double rotationAngle = circle.getStandardPositionAngle(segment.getEndpoint2(), false);
		//save the original orientation
		AffineTransform orig = g.getTransform();	

		//puts the label at a distance farther from the segment if it might overlap the labels
		int absHeight =icon.getIconHeight();
		int halfHeight =icon.getIconHeight()/2;
		int p1p2PixelDistance = (int) DrawableOps.getPixelDistance(this, p1, p2);
		if(icon.getIconWidth()>p1p2PixelDistance-15){
			absHeight =icon.getIconHeight()+halfHeight+5;
			halfHeight = icon.getIconHeight()+5;
		}

		if(rotationAngle<=Math.PI/2 || rotationAngle>=(3*Math.PI/2)){
			g.translate(getLocation(xTranslate,X_COORDINATE), getLocation(yTranslate,Y_COORDINATE));
			g.rotate(-rotationAngle);
			if(top){
				icon.paintIcon(jl,g, getLocation(0, X_COORDINATE)-icon.getIconWidth()/2, getLocation(0, Y_COORDINATE)-absHeight-1);
			}else{
				icon.paintIcon(jl,g, getLocation(0, X_COORDINATE)-icon.getIconWidth()/2, getLocation(0, Y_COORDINATE)+halfHeight+1);
			}
		}else{
			g.translate(getLocation(xTranslate,X_COORDINATE), getLocation(yTranslate,Y_COORDINATE));
			g.rotate(-rotationAngle+Math.PI);
			if(top){
				icon.paintIcon(jl,g, getLocation(0, X_COORDINATE)-icon.getIconWidth()/2, getLocation(0, Y_COORDINATE)+halfHeight+1);
			}else{
				icon.paintIcon(jl,g, getLocation(0, X_COORDINATE)-icon.getIconWidth()/2, getLocation(0, Y_COORDINATE)-absHeight-1);
			}
		}
		g.setTransform(orig);
	}

	public Graphics2D getGraphics(){
		return g;
	}

	public double getxMin() {
		return xMin;
	}

	public double getxMax() {
		return xMax;
	}

	public double getyMin() {
		return yMin;
	}

	public double getyMax() {
		return yMax;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void drawRightAngleBox(CoordinatePoint vertexA, CoordinatePoint vertexC, CoordinatePoint vertexB) {
		double[] lineToA = DrawableOps.getLinearEquation(vertexA, vertexC);
		double[] lineToB = DrawableOps.getLinearEquation(vertexB, vertexC);
		double x1 = Math.sqrt(1/(Math.pow(lineToA[0],2)+1))+vertexC.getxCoordinate();
		if((x1 < vertexA.getxCoordinate() && x1 < vertexC.getxCoordinate()) || (x1 > vertexA.getxCoordinate() && x1 > vertexC.getxCoordinate())){
			x1 = -Math.sqrt(1/(Math.pow(lineToA[0],2)+1))+vertexC.getxCoordinate();
		}
		double x2 = Math.sqrt(1/(Math.pow(lineToB[0],2)+1))+vertexC.getxCoordinate();
		if((x2 < vertexB.getxCoordinate() && x2 < vertexC.getxCoordinate()) || (x2 > vertexB.getxCoordinate() && x2 > vertexC.getxCoordinate())){
			x2 = -Math.sqrt(1/(Math.pow(lineToB[0],2)+1))+vertexC.getxCoordinate();
		}
		CoordinatePoint squareCorner1 = new CoordinatePoint(x1, lineToA[0]*x1+lineToA[1]);
		CoordinatePoint squareCorner2 = new CoordinatePoint(x2, lineToB[0]*x2+lineToB[1]);
		double[] squareEdge1 = {lineToA[0],squareCorner2.getyCoordinate()-lineToA[0]*squareCorner2.getxCoordinate()};
		double[] squareEdge2 = {lineToB[0],squareCorner1.getyCoordinate()-lineToB[0]*squareCorner1.getxCoordinate()};
		CoordinatePoint vertex = DrawableOps.getIntersectionOfLines(squareEdge1, squareEdge2);
		drawSegment(squareCorner1,vertex);
		drawSegment(squareCorner2,vertex);
		
	}

	public void drawAngleMeasure(int m, CoordinatePoint p1, CoordinatePoint vertex,
			CoordinatePoint p2) {
//		Circle c = new Circle(vertexA, .6);
//		drawCircle(c);
//		drawArcLabel(c, (int)Math.asin((vertexB.getyCoordinate()-vertexA.getyCoordinate())/(vertexB.getxCoordinate()-vertexA.getxCoordinate())), (int)Math.asin((vertexC.getyCoordinate()-vertexA.getyCoordinate())/(vertexC.getxCoordinate()-vertexA.getxCoordinate())),true);
	
		double p1x = p1.getxCoordinate();
		double p1y = p1.getyCoordinate();
		double p2x = p2.getxCoordinate();
		double p2y = p2.getyCoordinate();
		double x = vertex.getxCoordinate();
		double y = vertex.getyCoordinate();
		CoordinatePoint reflectP1 = new CoordinatePoint(x-(p1x-x), y-(p1y-y));
		CoordinatePoint reflectP2 = new CoordinatePoint(x-(p2x-x), y-(p2y-y));
		drawAngleVertexLabel(""+m+"°", reflectP1, vertex, reflectP2);
	}

	public void labelTriangleSide(Triangle triangle, String label, int sideIndex) {
		CoordinateSegment side = triangle.getSide(sideIndex);
		
		if(side.getLinearEquationCoefficients()[0] > 0 ){

				drawSegmentLatex(label, side.getEndpoint1(),side.getEndpoint2(), true, triangle.getSide((sideIndex+1)%3).getLinearEquationCoefficients()[0] < 0);

		}else{
			drawSegmentLatex(label, side.getEndpoint1(),side.getEndpoint2(), true, triangle.getSide((sideIndex+1)%3).getLinearEquationCoefficients()[0] > 0);
		}
	}




}
