package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.*;

public class RevenueBarView extends JPanel {
	
	private int vandaag = 0;
	private int dag2 = 0;
	private int dag3 = 0;
	private int dag4 = 0;
	private int dag5 = 0;
	private int scale = 1;
	private JLabel vandaaglabel;
	private JLabel dag2label;
	private JLabel dag3label;
	private JLabel dag4label;
	private JLabel dag5label;
	private JPanel revenuebarpanel;
	
	public RevenueBarView() {
		this.setOpaque(false);
		
		revenuebarpanel = new JPanel();
		revenuebarpanel.setLayout(new GridLayout(45,80));
		
		vandaaglabel = new JLabel("Omzet vandaag: 0");
		revenuebarpanel.add(vandaaglabel);
		
		dag2label = new JLabel("Omzet gisteren: 0");
		revenuebarpanel.add(dag2label);
		dag3label = new JLabel("Omzet eergisteren: 0");
		revenuebarpanel.add(dag3label);
		dag4label = new JLabel("Omzet 3 dagen geleden: 0");
		revenuebarpanel.add(dag4label);
		dag5label = new JLabel("Omzet 4 dagen geleden: 0");
		revenuebarpanel.add(dag5label);
		
		add(revenuebarpanel);
		setVisible(true);
	}
	
	public void paint(Graphics g) {
		super.paintChildren(g);
		
		g.setColor(Color.GRAY);
		g.fillRect(50,700-dag5/scale, 30,dag5/scale);
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(dag5), 48,695-dag5/scale);
		g.setColor(Color.GRAY);
		g.fillRect(100,700-dag4/scale,30,dag4/scale);
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(dag4), 98,695-dag4/scale);
		g.setColor(Color.GRAY);
		g.fillRect(150,700-dag3/scale,30,dag3/scale);
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(dag3), 148,695-dag3/scale);
		g.setColor(Color.GRAY);
		g.fillRect(200,700-dag2/scale,30,dag2/scale);
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(dag2), 198,695-dag2/scale);
		g.setColor(Color.ORANGE);
		g.fillRect(250,700-vandaag/scale,30,vandaag/scale);
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(vandaag), 248,695-vandaag/scale);
		g.setColor(Color.BLACK);
		g.drawLine(30,700,280,700);
		g.setColor(Color.BLACK);
		g.drawLine(30,100,30,700);
	}
	
	public void repaint(int vandaag, int dag2, int dag3, int dag4, int dag5){
		this.vandaag = vandaag;
		vandaaglabel.setText("Omzet vandaag: " + vandaag);
		this.dag2 = dag2;
		dag2label.setText("Omzet gisteren: " + dag2);
		this.dag3 = dag3;
		dag3label.setText("Omzet eergisteren: " + dag3);
		this.dag4 = dag4;
		dag4label.setText("Omzet 3 dagen geleden: " + dag4);
		this.dag5 = dag5;
		dag5label.setText("Omzet 4 dagen geleden: " + dag5);

		int [] arr = new int[5];
		arr[0] = vandaag;
		arr[1] = dag2;
		arr[2] = dag3;
		arr[3] = dag4;
		arr[4] = dag5;
		
		int max=0;
		for (int i=0; i<5; i++){
		    if ( max < arr[i] ) {
		        max = arr[i];
		    }
		}
		
		scale = max/500;
		if(scale < 20) {
			scale = 20;
		}
				
		this.repaint();
	}
	
	
	
}