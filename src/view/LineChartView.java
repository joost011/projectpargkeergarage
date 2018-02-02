package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.*;

public class LineChartView extends JPanel {
	
	private int[] uren = new int[24];
	private int scale = 1;
	private JPanel linechartpanel;
	private JLabel totaleAutos;
	private JLabel linechartlabel;
	
	public LineChartView() {
		this.setOpaque(false);
		
		linechartpanel = new JPanel();
		linechartpanel.setLayout(new GridLayout(45,80));
		
		totaleAutos = new JLabel("Auto's: 0");
		linechartpanel.add(totaleAutos); 
		
		
		linechartlabel = new JLabel("Totale auto's in de laatste 24 uur:");
		linechartpanel.add(linechartlabel);
		
		add(linechartpanel);
		setVisible(true);
	}
	
	public void paint(Graphics g) {
		super.paintChildren(g);
		this.setPreferredSize(new Dimension(320, 750));
		
		g.setColor(Color.BLACK);
		g.drawLine(30,650,280,650);
		g.setColor(Color.BLACK);
		g.drawLine(30,100,30,650);
		int beginpunt = 35;
		int eindpunt = 45;
		for (int i=0; i<24; i++) {
			int iplus = 0;
			if(i < 23) {
				iplus = i+1;
			}
			else {
				iplus = i;
			}
			g.drawLine(beginpunt, 650-uren[i],  eindpunt, 650-uren[iplus]);
			if (i == 23 || i == 17 || i == 11 || i == 5 || i ==0) {
				g.drawString(String.valueOf(uren[i]), eindpunt-5,600-uren[iplus]);
			}
			beginpunt = beginpunt + 10;
			eindpunt = eindpunt +10;
		}
	}
	
	public void repaint(int[] uren){
		this.uren = uren;
		
		totaleAutos.setText("Auto's:  " + this.uren[23]);
				
		this.repaint();
	}
	
	public void dataNul() {
		for (int i=0; i<24; i++) {
			uren[i] = 0;
		}
	}
	
}