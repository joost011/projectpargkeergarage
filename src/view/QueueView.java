package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.*;

public class QueueView extends JPanel {
	
	private int gewonewacht; // Aantal auto's in de gewone wachtrij
	private int specialewacht; // Aantal auto's in de speciale wachtrij
	private int maxgewone = 100;
	private int maxspeciale = 100;
	private int beginpunt;
	private JLabel gewonelabel;
	private JLabel specialelabel;
	private JPanel queuepanel;
	
	public QueueView() {
		this.setOpaque(false);
		
		queuepanel = new JPanel();
		queuepanel.setLayout(new GridLayout(45,80)); 
		
		gewonelabel = new JLabel("Gewone wachtrij");
		queuepanel.add(gewonelabel);
		specialelabel = new JLabel("Speciale wachtrij");
		queuepanel.add(specialelabel);
		
		add(queuepanel);
		setVisible(true);
	}
	
	public void paint(Graphics g) {
		super.paintChildren(g);
		
		g.setColor(Color.BLACK);
		g.drawLine(30,700,280,700);
		g.drawLine(30,100,280,100);
		g.drawLine(30,100,30,700);
		g.drawLine(280,100,280,700);
		g.drawLine(155,100,155,700);
		
		g.drawString(String.valueOf(gewonewacht)+" / "+String.valueOf(maxgewone), 75,98);
		g.drawString(String.valueOf(specialewacht)+" / "+String.valueOf(maxspeciale), 200,98);
		
		//Gewone auto's tekenen
		g.setColor(Color.RED);
		beginpunt = 102;
		int aantalAutosPerHelft = maxgewone / 2;
		if(aantalAutosPerHelft == 0) {aantalAutosPerHelft = 1;}
		int ruimteVoorAutos = 598 - maxgewone;
		int hoogteAutos = ruimteVoorAutos / aantalAutosPerHelft;
		
		for (int i = 0; i < gewonewacht; i++) {
			if((i % 2) == 0 || i == 0) {
				g.fillRect(40,beginpunt, 50,hoogteAutos);
			}
			else {
				g.fillRect(95,beginpunt, 50,hoogteAutos);
				beginpunt = beginpunt + hoogteAutos +2;
			}
		}
		
		//Speciale auto's tekenen
		g.setColor(Color.CYAN);
		beginpunt = 102;
		int aantalSAutosPerHelft = maxspeciale / 2;
		if(aantalAutosPerHelft == 0) {aantalSAutosPerHelft = 1;}
		int ruimteVoorSAutos = 598 - maxspeciale;
		int hoogteSAutos = ruimteVoorSAutos / aantalSAutosPerHelft;
		
		for (int i = 0; i < specialewacht; i++) {
			if((i % 2) == 0 || i == 0) {
				g.fillRect(165,beginpunt, 50,hoogteSAutos);
			}
			else {
				g.fillRect(220,beginpunt, 50,hoogteSAutos);
				beginpunt = beginpunt + hoogteSAutos +2;
			}
		}
		
		g.setColor(Color.RED);
		g.fillRect(85, 9, 10, 10);
		
		g.setColor(Color.CYAN);
		g.fillRect(85, 25, 10, 10);
		
	}
	
	public void repaint(int gewonewacht, int specialewacht, int maxgewone, int maxspeciale){
		this.gewonewacht = gewonewacht;
		this.specialewacht = specialewacht;
		this.maxgewone = maxgewone;
		this.maxspeciale = maxspeciale;
		
		this.repaint();
	}
	
	
	
}