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
	private JPanel thePanel;
	private JLabel label1;

	public PieChartView2() {

		thePanel = new JPanel();
		thePanel.setLayout(new GridLayout(5,5,30,10));
	
		label1 = new JLabel("Image and Text", JLabel.CENTER);
		//Set the position of the text, relative to the icon:
		label1.setVerticalTextPosition(JLabel.BOTTOM);
		label1.setHorizontalTextPosition(JLabel.CENTER);
		thePanel.add(label1);
		
		add(thePanel);
		setVisible(true);

	}

	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawOval(45, 80,200,200);
		g.setColor(Color.WHITE);
		g.fillOval(45, 80, 200, 200);

		g.setColor(Color.WHITE);// empty
		g.fillArc(45, 80, 200, 200, 90, -(rand+abo+res+empty));
		g.setColor(Color.GREEN);// res
		g.fillArc(45, 80, 200, 200, 90, -(rand+abo+res));
		g.setColor(Color.BLUE); // abo
		g.fillArc(45, 80, 200, 200, 90, -(rand+abo));
		g.setColor(Color.RED); // rand
		g.fillArc(45, 80, 200, 200, 90, -rand);
	}

	public void repaint(int res, int abo, int rand, int empty){
		this.res = res;
		this.abo = abo;
		this.rand = rand;
		this.empty = empty;
		this.repaint();
	}

}