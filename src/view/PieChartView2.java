package view;
import java.awt.Color;
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
	private Double revenue;
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
		thePanel = new JPanel();
		thePanel.setLayout(new GridLayout(45,80));
	
		Rand = new JLabel("Normale auto's:    " + rand);
		thePanel.add(Rand);
		
		Res = new JLabel("Reservatie auto's:  " + res);
		thePanel.add(Res);
		
		Abo = new JLabel("Abonee auto's:      " + abo);
		thePanel.add(Abo);
		
		Empty = new JLabel("Lege plekken:       " + empty);
		thePanel.add(Empty);
		
		space = new JLabel("");
		thePanel.add(space);
		
		Revenue = new JLabel("Total revenue: €" + revenue);
		thePanel.add(Revenue);
		
		add(thePanel);
		setVisible(true);
		

	}

	public void paint(Graphics g) {

		super.paintChildren(g);

		g.setColor(Color.WHITE);
		g.fillOval(45, 140, 200, 200);

		g.setColor(Color.WHITE);// empty
		g.fillArc(45, 140, 200, 200, 90, -(rand+abo+res+empty));
		g.setColor(Color.GREEN);// res
		g.fillArc(45, 140, 200, 200, 90, -(rand+abo+(res/2)));
		g.setColor(Color.BLUE); // abo
		g.fillArc(45, 140, 200, 200, 90, -(rand+abo));
		g.setColor(Color.RED); // rand

		g.fillArc(45, 140, 200, 200, 90, -rand);
		
		g.setColor(Color.BLACK);
		g.drawOval(45, 140,200,200);



	}

	public void repaint(int res, int abo, int rand, int empty, Double revenue){
		this.res = res;
		Res.setText("Reservatie auto's:  " + res);
		this.abo = abo;
		Abo.setText("Abonee auto's:      " + abo);
		this.rand = rand;
		Rand.setText("Normale auto's:    " + rand);
		this.empty = empty;
		Empty.setText("Lege plekken:   " + empty);
		this.revenue = revenue;
		Revenue.setText("Total revenue: €" + df2.format(revenue));
		this.repaint();
	}

}