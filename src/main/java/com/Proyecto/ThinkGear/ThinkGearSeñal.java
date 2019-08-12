package com.Proyecto.ThinkGear;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class ThinkGearSeñal extends JPanel {

	private static final long serialVersionUID = 1L;
	private int strength = 0;

	
	public ThinkGearSeñal(){		
        SpringLayout spring = new SpringLayout();
        setLayout(spring);                
        setPreferredSize(new Dimension(30, 30));
	}
	private Color getColor(int bar){
		if (strength == 0 ){
			 return Color.GRAY ;
		}
		if (bar == 1 && strength <=20){
			return Color.RED;
		}else if(strength <=20) {
			return Color.GRAY ;
		}
		if (bar <= 2 && strength <=40){
			return Color.ORANGE;
		}else if(strength <=40) {
			return Color.GRAY ;
		}
		if (bar <= 3 && strength <=60){
			return Color.YELLOW;
		}else if(strength <=60) {
			return Color.GRAY ;
		}
		if (bar <= 4 && strength <=80){
			return Color.BLUE;
		}else if (strength <= 80) {
			return Color.GRAY;
		}
		if (bar <= 5 && strength <= 100) {
			return Color.GREEN;
		}	
		 return null;
	}
	 public void paint(Graphics g) {
		 g.setColor(getColor(1));
		 g.fillRect(0, 8, 4, 8);  
		 g.setColor(getColor(2));
		 g.fillRect(5, 6, 4, 10);  
		 g.setColor(getColor(3));
		 g.fillRect(10, 4, 4, 12);  
		 g.setColor(getColor(4));
		 g.fillRect(15, 2, 4, 14); 
		 g.setColor(getColor(5));
		 g.fillRect(20, 0 , 4, 16);
	 }	
	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
		paint(getGraphics());
	}
}
