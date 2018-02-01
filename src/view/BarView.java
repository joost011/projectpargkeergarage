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
	private JLabel abolabel;
	private JLabel randlabel;
	private JPanel barpanel;
	
	public BarView() {
		this.setOpaque(false);
		
		barpanel = new JPanel();
		barpanel.setLayout(new GridLayout(45,80)); 
		
		reslabel = new JLabel("Reservatie auto's: " + res);
		barpanel.add(reslabel);
		abolabel = new JLabel("Abonnement auto's: " + abo);
		barpanel.add(abolabel);
		randlabel = new JLabel("Random auto's: " + rand);
		barpanel.add(randlabel);
		
		add(barpanel);
		setVisible(true);
	}
	
	public void paint(Graphics g) {
		super.paintChildren(g);
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
		
		g.setColor(Color.BLACK);
		g.drawString("200", 6, 105);
		
		g.setColor(Color.BLACK);
		g.drawString("100", 6, 195);
		
		g.setColor(Color.BLACK);
		g.drawString("0", 20, 300);
		
		g.setColor(Color.GREEN);
		g.fillRect(65, 9, 10, 10);
		
		g.setColor(Color.BLUE);
		g.fillRect(65, 25, 10, 10);
		
		g.setColor(Color.RED);
		g.fillRect(65, 41, 10, 10);
	}
	
	public void repaint(int res, int abo, int rand, int empty){
		this.res = res;
		reslabel.setText("Reservatie auto's: " + res);
		this.abo = abo;
		abolabel.setText("Abonnement auto's: " + abo);
		this.rand = rand;
		randlabel.setText("Random auto's: " + rand);
		this.empty = empty;
		this.repaint();
	}
	
	
	
}