package view;

import java.awt.*;
import javax.swing.*;

public class StatisticsView extends JPanel {
	
	private JLabel dag;
	private JLabel tijd;
	private JPanel viewpanel;
	private int uren = 0;
	private String[] dagen;
	private int x = 0;
	private JLabel verwachteinkomsten;
	private JLabel inkomsten;
	private int ticketprijs = 6;
	private int reservedticketprijs = 10;
	private int passticketprijs = 30;

	
	public StatisticsView() {
		
		dagen = new String[7];
		dagen[0] = "Maandag";
		dagen[1] = "Dinsdag";
		dagen[2] = "Woensdag";
		dagen[3] = "Donderdag";
		dagen[4] = "Vrijdag";
		dagen[5] = "Zaterdag";
		dagen[6] = "Zondag";
		
		viewpanel = new JPanel();
		viewpanel.setLayout(new GridLayout(5,5,30,10));
		tijd = new JLabel("Tijdstip: 0:00");
		viewpanel.add(tijd);
		dag = new JLabel("Dag: Maandag");
		viewpanel.add(dag);
		
		verwachteinkomsten = new JLabel("verwachte prijs: €1000");
		viewpanel.add(verwachteinkomsten);
		inkomsten = new JLabel("inkomsten: 250");
		viewpanel.add(inkomsten);
		add(viewpanel);
		setVisible(true);
	}
	
	public void plusuur() {
		if(uren > 22 ) {
			uren = -1;
		}
		uren++;
		tijd.setText("Tijdstip: " + String.valueOf(uren) + ":00");
	}
	
	public void plusdag() {
		
		if(uren==23) {
			x++;
			if(x==7) {
				x = 0;
			}
		}
		dag.setText("Dag: " + dagen[x]);
		
	}
	
	public int uur() {
		return uren;
	}
	
	public void tijdEnDag() {
		plusdag();
		plusuur();
	}

}