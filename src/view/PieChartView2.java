package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PieChartView2 extends JPanel {

	private int res; // reserved
	private int abo; // abonoment
	private int rand; //random auto's
	private int empty; // empty spots
	private float floatyRes;
	private float floatyAbo;
	private float floatyRand;
	private float floatyEmpty;
	private Double revenue = .0;
	private JPanel thePanel;
	private JLabel Rand;
	private JLabel Res;
	private JLabel Abo;
	private JLabel Empty;
	private JLabel space;
	private JLabel Revenue;
	private Graphics g;
	
	private static DecimalFormat df2 = new DecimalFormat(".##");
	
	public PieChartView2() {
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(320, 750));
		thePanel = new JPanel();
		thePanel.setLayout(new GridLayout(45,80));
	
		Rand = new JLabel("Normale auto's:    " + rand);
		thePanel.add(Rand);
		
		Res = new JLabel("Reservatie auto's:  " + res);
		thePanel.add(Res);
		
		Abo = new JLabel("Abonee auto's:      " + abo);
		thePanel.add(Abo);
		
		Empty = new JLabel("Lege plekken:        " + empty);
		
		thePanel.add(Empty);
		
		space = new JLabel("");
		space.setPreferredSize(new Dimension(225,10));
		thePanel.add(space);
		
		Revenue = new JLabel("Totale omzet: \u20ac" + revenue);
		thePanel.add(Revenue);
		
		add(thePanel);
		setVisible(true);
		

	}

	public void paint(Graphics g) {

		super.paintChildren(g);

		g.setColor(Color.WHITE);
		g.fillOval(50, 140, 200, 200);

		g.setColor(Color.WHITE);// empty
		g.fillArc(50, 140, 200, 200, 90, (int) -floatyEmpty);
		g.setColor(Color.GREEN);// res
		g.fillArc(50, 140, 200, 200, 90, (int) -floatyRes);
		g.setColor(Color.BLUE); // abo
		g.fillArc(50, 140, 200, 200, 90, (int) -floatyAbo);
		g.setColor(Color.RED); // rand

		g.fillArc(50, 140, 200, 200, 90, (int) -floatyRand);
		
		g.setColor(Color.BLACK);
		g.drawOval(50, 140,200,200);



	}

	public void repaint(int res, int abo, int rand, int empty, Double revenue){
		this.res = res;
		this.floatyRes = (rand+abo+res)/1.5f;
		Res.setText("Reservatie auto's:  " + res);
		this.abo = abo;
		this.floatyAbo = (rand+abo)/1.5f;
		Abo.setText("Abonee auto's:      " + abo);
		this.rand = rand;
		this.floatyRand = rand/1.5f;
		Rand.setText("Normale auto's:    " + rand);
		this.empty = empty;
		this.floatyEmpty = (rand+abo+res+empty)/1.5f;
		Empty.setText("Lege plekken:        " + empty);
		this.revenue = revenue;
		Revenue.setText("Totale omzet: \u20ac" + df2.format(revenue));
		this.repaint();
	}

}