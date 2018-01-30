package view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PieChartView2 extends JPanel {

	private int res; // reserved
	private int abo; // abonoment
	private int rand; //random auto's
	private int empty; // empty spots
	private int revenue;
	private JPanel thePanel;
	private JLabel Rand;
	private JLabel Res;
	private JLabel Abo;
	private JLabel Empty;
	private JLabel space;
	private JLabel Revenue;
	private Graphics g;
	
	public PieChartView2() {
<<<<<<< HEAD
		this.setSize(250,250);
		this.setOpaque(false);
		this.setVisible(true);
=======

		thePanel = new JPanel();
		thePanel.setLayout(new GridLayout(45,80));
	
		Rand = new JLabel("Random auto's:  " + rand);
		thePanel.add(Rand);
		
		Res = new JLabel("Reserved autos:  " + res);
		thePanel.add(Res);
		
		Abo = new JLabel("Abonee autos:    " + abo);
		thePanel.add(Abo);
		
		Empty = new JLabel("Lege plekken:     " + empty);
		thePanel.add(Empty);
		
		space = new JLabel("");
		thePanel.add(space);
>>>>>>> 4d614b03aab05486557e6b5297f1865a8f4e23aa
		
		Revenue = new JLabel("Total revenue: " + revenue);
		thePanel.add(Revenue);
		
		add(thePanel);
		setVisible(true);
		

	}

	public void paint(Graphics g) {
		super.paintChildren(g);
		g.setColor(Color.BLACK);
		g.drawOval(45, 140,200,200);
		g.setColor(Color.WHITE);
		g.fillOval(45, 140, 200, 200);

		g.setColor(Color.WHITE);// empty
		g.fillArc(45, 140, 200, 200, 90, -(rand+abo+res+empty));
		g.setColor(Color.GREEN);// res
		g.fillArc(45, 140, 200, 200, 90, -(rand+abo+res));
		g.setColor(Color.BLUE); // abo
		g.fillArc(45, 140, 200, 200, 90, -(rand+abo));
		g.setColor(Color.RED); // rand
		g.fillArc(45, 140, 200, 200, 90, -rand);
	}

	public void repaint(int res, int abo, int rand, int empty, int revenue){
		this.res = res;
		Res.setText("Random auto's:  " + rand);
		this.abo = abo;
		Abo.setText("Reserved autos:  " + res);
		this.rand = rand;
		Rand.setText("Abonee autos:    " + abo);
		this.empty = empty;
		Empty.setText("Lege plekken:     " + empty);
		this.revenue = revenue;
		Revenue.setText("Total revenue: " + revenue);
		this.repaint();
	}

}