package view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import logic.*;
import runner.*;

public class StatisticsView extends JPanel implements ActionListener{
	
	private JLabel snelheidlabel;
	private JLabel dag;
	private JLabel tijd;
	private JLabel empty;
	private JLabel gemiddeld;
	private JLabel werkdag;
	private JLabel weekend;
	private JLabel werkdagpas;
	private JLabel weekendpas;
	private JLabel gewonewachtrij;
	private JLabel gewonedoorgereden;
	private JLabel paswachtrij;
	private JLabel reswachtrij;
	private JLabel maxgewonewachtrijlabel;
	private JButton update;
	private JPanel viewpanel;
	private int uren = 0;
	private String[] dagen;
	private int x = 0;
<<<<<<< HEAD
	private int minute =0;
	private JLabel verwachteinkomsten;
	private JLabel inkomsten;
	private int ticketprijs = 6;
	private int reservedticketprijs = 10;
	private int passticketprijs = 30;
=======
	private JTextField snelheid;
	private JTextField weekdagaankomst;
	private JTextField weekendaankomst;
	private JTextField werkdagpasdaankomst;
	private JTextField weekendpasdaankomst;
	private JTextField maxgewonewachtrij;
	
	
>>>>>>> 4d614b03aab05486557e6b5297f1865a8f4e23aa

	
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
<<<<<<< HEAD
		viewpanel.setLayout(new GridLayout(5,5,30,10));
		tijd = new JLabel("Tijdstip: 00:00");
=======
		viewpanel.setLayout(new GridLayout(25,10));
		
		tijd = new JLabel("Tijdstip: 0:00");
>>>>>>> 4d614b03aab05486557e6b5297f1865a8f4e23aa
		viewpanel.add(tijd);
		dag = new JLabel("Dag: Maandag");
		viewpanel.add(dag);
		
<<<<<<< HEAD
		verwachteinkomsten = new JLabel("verwachte prijs: €1000");
		viewpanel.add(verwachteinkomsten);
		inkomsten = new JLabel("inkomsten: 250");
		viewpanel.add(inkomsten);
=======
		empty = new JLabel("");
		viewpanel.add(empty);
		
		snelheidlabel = new JLabel("Stappenpauze:");
		viewpanel.add(snelheidlabel);
		snelheid = new JTextField(String.valueOf(Simulator.getTickPause()));
		viewpanel.add(snelheid);
		
		gemiddeld = new JLabel("Gemiddeld aantal auto's per uur:");
		viewpanel.add(gemiddeld);
		
		werkdag = new JLabel("Werkdag");
		viewpanel.add(werkdag);
		weekdagaankomst = new JTextField(String.valueOf(Simulator.getWeekDayArrivals()));
		viewpanel.add(weekdagaankomst);
		
		weekend = new JLabel("Weekend");
		viewpanel.add(weekend);
		weekendaankomst = new JTextField(String.valueOf(Simulator.getWeekendArrivals()));
		viewpanel.add(weekendaankomst);
		
		werkdagpas = new JLabel("Werkdag (met pas)");
		viewpanel.add(werkdagpas);
		werkdagpasdaankomst = new JTextField(String.valueOf(Simulator.getweekDayPassArrivals()));
		viewpanel.add(werkdagpasdaankomst);
		
		weekendpas = new JLabel("Weekend (met pas)");
		viewpanel.add(weekendpas);
		weekendpasdaankomst = new JTextField(String.valueOf(Simulator.getweekendPassArrivals()));
		viewpanel.add(weekendpasdaankomst);
		
		update = new JButton("Update");
		update.addActionListener(this);
		viewpanel.add(update);
		
		gewonewachtrij = new JLabel("Gewone wachtrij: "+String.valueOf(Simulator.getNormalCarQueue()));
		viewpanel.add(gewonewachtrij);
		
		gewonedoorgereden = new JLabel("Doorgereden: "+String.valueOf(Simulator.getDoorgeredenAutos()));
		viewpanel.add(gewonedoorgereden);
		
		paswachtrij = new JLabel("Pas wachtrij: "+String.valueOf(Simulator.getPassCarQueue()));
		viewpanel.add(paswachtrij);
		
		reswachtrij = new JLabel("Reservering wachtrij: "+String.valueOf(Simulator.getResCarQueue()));
		viewpanel.add(reswachtrij);
		
		maxgewonewachtrijlabel = new JLabel("Maximale wachtrij (normale auto's)");
		viewpanel.add(maxgewonewachtrijlabel);
		maxgewonewachtrij = new JTextField(String.valueOf(Simulator.getMaxEntranceCarQueue()));
		viewpanel.add(maxgewonewachtrij);
		
>>>>>>> 4d614b03aab05486557e6b5297f1865a8f4e23aa
		add(viewpanel);
		setVisible(true);
	}
	
	public void plusuur() {
		if (minute > 59) {
			minute = -1;
			uren++;
		}
		if(uren > 23 ) {
			uren = 0;
			
		}
		
		minute++;
		if(minute <10 && uren<10) {
			tijd.setText("Tijdstip: 0" + String.valueOf(uren)+ ":0" + String.valueOf(minute));
		}else if(uren>9 && minute<10) {
			tijd.setText("Tijdstip: " + String.valueOf(uren)+ ":0" + String.valueOf(minute));
		}else if(uren<10 && minute>9) {
			tijd.setText("Tijdstip: 0" + String.valueOf(uren)+ ":" + String.valueOf(minute));
		}
<<<<<<< HEAD
		else
		tijd.setText("Tijdstip: " + String.valueOf(uren)+ ":" + String.valueOf(minute));
	
		
		
=======
		uren++;
		tijd.setText("Tijdstip: " + String.valueOf(uren) + ":00");
		gewonewachtrij.setText("Gewone wachtrij: "+String.valueOf(Simulator.getNormalCarQueue()));
		paswachtrij.setText("Pas wachtrij: "+String.valueOf(Simulator.getPassCarQueue()));
		reswachtrij.setText("Reservering wachtrij: "+String.valueOf(Simulator.getResCarQueue()));
		gewonedoorgereden.setText("Doorgereden: "+String.valueOf(Simulator.getDoorgeredenAutos()));
>>>>>>> 4d614b03aab05486557e6b5297f1865a8f4e23aa
	}
	
	public void plusdag() {
		
		if(uren==23 && minute == 59) {
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
	
	public void actionPerformed(java.awt.event.ActionEvent ev)
	{
		String snel = snelheid.getText();
		String weekautos = weekdagaankomst.getText();
		String weekendautos = weekendaankomst.getText();
		String werkdagpasdautos = werkdagpasdaankomst.getText();
		String weekendpasdautos = weekendpasdaankomst.getText();
		String maximaalgewonewachtrij = maxgewonewachtrij.getText();
		Simulator.setTickPause(Integer.parseInt(snel));
		Simulator.setWeekDayArrivals(Integer.parseInt(weekautos));
		Simulator.setWeekendArrivals(Integer.parseInt(weekendautos));
		Simulator.setweekDayPassArrivals(Integer.parseInt(werkdagpasdautos));
		Simulator.setweekendPassArrivals(Integer.parseInt(weekendpasdautos));
		Simulator.setMaxEntranceCarQueue(Integer.parseInt(maximaalgewonewachtrij));
	}
}