/*******************************************************************************
 * Copyright (c) 2016-2017 Benjamin Nockles
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
package guiTeacher.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


public abstract class StyledComponent extends Component {

	public static final int ALIGN_CENTER = 0;
	public static final int ALIGN_LEFT = -1;
	public static final int ALIGN_RIGHT = 1;
	
	private static int tabHeight = 60;
	private static Font tabFont = new Font("Times Roman",Font.PLAIN,14);//also used in tables
	private static Color tabColor = new Color(150,200,255);
	private static Color tabShade = new Color(150,200,255);
	private static boolean buttonOutline = false;
	private static int buttonOutlineSize = 2;
	
	private static Font baseFont =new Font("Times Roman",Font.PLAIN,14);//also used in tables
	private static Font monoFont =new Font("Courier",Font.PLAIN,14);//also used in tables
	private static Color textColor = Color.black;
	private static Color headerColor = new Color(0,0,0);
	private static Color bodyColor = new Color(0,0,0);
	private static Color backgroundColor = new Color(255,255,255);
	private static Color inactiveBorderColor = new Color(140,140,140);
	private static Color alertColor = new Color(239,172,56);
	private static Color activeBorderColor = new Color(0,0,0);
	private static Color staticBorderColor = new Color(0,0,0);
	private static Color accentColor = new Color(150,150,150);
	private static Color highlightColor = new Color(200,200,240);
	private static int headerAlign = ALIGN_CENTER;
	private static int bodyAlign = ALIGN_LEFT;
	private static int animationTime = 600;//duration of transitions
	
	//tables
	private static int tableBorder = 1;
	
	public static void setTextColor(Color c){
		textColor = c;
	}
	
	public Color getTextColor(){
		return textColor;
	}
	
	public static void setMonoText(Font f){
		monoFont = f;
	}
	
	public Font getMonoFont(){
		return monoFont;
	}
	
	public StyledComponent(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	protected void applyStyles(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(baseFont);
	}
	
	public Font getBaseFont(){
		return baseFont;
	}
	
	public static void setBaseFont(Font f){
		baseFont = f;
	}
	
	
	
	
	public static boolean isButtonOutline() {
		return buttonOutline;
	}

	public static void setButtonOutline(boolean buttonOutline) {
		StyledComponent.buttonOutline = buttonOutline;
	}

	public Color getInactiveBorderColor() {
		return inactiveBorderColor;
	}



	public void setInactiveBorderColor(Color inactiveBorderColor) {
		StyledComponent.inactiveBorderColor = inactiveBorderColor;
	}



	public Color getActiveBorderColor() {
		return activeBorderColor;
	}



	public void setActiveBorderColor(Color activeBorderColor) {
		StyledComponent.activeBorderColor = activeBorderColor;
	}



	public Font getTabFont() {
		return tabFont;
	}
	
	public static void setTabFont(Font tabFont) {
		StyledComponent.tabFont = tabFont;
	}
	
	
	public static int getTableBorder() {
		return tableBorder;
	}

	public static Color getStaticBorderColor() {
		return staticBorderColor;
	}



	public static void setStaticBorderColor(Color staticBorderColor) {
		StyledComponent.staticBorderColor = staticBorderColor;
	}



	public static int getButtonOutlineSize() {
		return buttonOutlineSize;
	}

	public static void setButtonOutlineSize(int buttonOutlineSize) {
		StyledComponent.buttonOutlineSize = buttonOutlineSize;
	}

	public static void setTableBorder(int tableBorder) {
		StyledComponent.tableBorder = tableBorder;
	}


	public int getTabHeight() {
		return tabHeight;
	}

	public Color getTabColor() {
		return tabColor;
	}


	public Color getHeaderColor() {
		return headerColor;
	}

	public void setHeaderColor(Color headerColor) {
		this.headerColor = headerColor;
	}

	public static void setTabHeight(int height) {
		tabHeight = height;
	}

	public static void setTabColor(Color c) {
		tabColor = c;
	}

	public Color getBodyColor() {
		return bodyColor;
	}

	public void setBodyColor(Color bodyColor) {
		this.bodyColor = bodyColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public static Color getAccentColor() {
		return accentColor;
	}

	public static void setAccentColor(Color c) {
		accentColor = c;
	}

	public int getHeaderAlign() {
		return headerAlign;
	}

	public void setHeaderAlign(int headerAlign) {
		this.headerAlign = headerAlign;
	}

	public static void setHighlightColor(Color c) {
		highlightColor = c;
	}

	public Color getHighlightColor() {
		return highlightColor;
	}
	

	public int getBodyAlign() {
		return bodyAlign;
	}

	public void setBodyAlign(int bodyIsAlign) {
		this.bodyAlign = bodyIsAlign;
	}
	
	
	
	public Color getAlertColor() {
		return alertColor;
	}

	public static void setAlertColor(Color alertColor) {
		StyledComponent.alertColor = alertColor;
	}

	public int getAnimationTime(){
		return animationTime;
	}
	
	public Color getTabShade(){
		return tabShade;
	}

	public static void setTabShadeColor(Color color) {
		tabShade = color;
	}
	
	

}
