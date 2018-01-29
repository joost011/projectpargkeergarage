package view;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PieChartView2 extends JPanel {
	
	private int res; // reserved
	private int abo; // abonoment
	private int rand; //random auto's
	private int empty; // empty spots
	
	public PieChartView2() {
		this.setSize(250,250);
		this.setVisible(true);
		
	}
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawOval(30, 30,200,200);
		g.setColor(Color.WHITE);
		g.fillOval(30, 30, 200, 200);

		g.setColor(Color.WHITE);// empty
		g.fillArc(30, 30, 200, 200, 90, -(rand+abo+res+empty));
		g.setColor(Color.GREEN);// res
		g.fillArc(30, 30, 200, 200, 90, -(rand+abo+res));
		g.setColor(Color.BLUE); // abo
		g.fillArc(30, 30, 200, 200, 90, -(rand+abo));
		g.setColor(Color.RED); // rand
		g.fillArc(30, 30, 200, 200, 90, -rand);
	}
	
	public void repaint(int res, int abo, int rand, int empty){
		this.res = res;
		this.abo = abo;
		this.rand = rand;
		this.empty = empty;
		this.repaint();
	}
		
}