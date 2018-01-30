package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.*;

public class BarView extends JPanel {
	
	private int res; // reserved
	private int abo; // abonoment
	private int rand; //random auto's
	private int empty; // empty spots
	private JLabel reslabel;
	private JPanel barpanel;
	
	public BarView() {
		this.setOpaque(false);
		
		barpanel = new JPanel();
		barpanel.setLayout(new GridLayout(45,80));
		
		reslabel = new JLabel("Reservation cars: 0");
		barpanel.add(reslabel);
		
		add(barpanel);
		setVisible(true);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(70,300-rand, 30,rand);
		g.setColor(Color.BLUE);
		g.fillRect(130,300-abo,30,abo);
		g.setColor(Color.GREEN);
		g.fillRect(190,300-res,30,res);
		g.setColor(Color.BLACK);
		g.drawLine(30,300,250,300);
		g.setColor(Color.BLACK);
		g.drawLine(30,100,30,300);
	}
	
	public void repaint(int res, int abo, int rand, int empty){
		this.res = res;
		this.abo = abo;
		this.rand = rand;
		this.empty = empty;
		this.repaint();
	}
	
	
	
}