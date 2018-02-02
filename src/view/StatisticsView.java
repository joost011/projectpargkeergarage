package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import logic.*;
import runner.*;

public class StatisticsView extends JPanel implements ActionListener{
	
	private JLabel snelheidlabel;
	private JLabel prijslabel;
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
	private JLabel specialedoorgereden;
	private JLabel paswachtrij;
	private JLabel maxgewonewachtrijlabel;
	private JLabel maxspecialewachtrijlabel;
	private JLabel verstrekendagenlabel;
	private JButton update;
	private JPanel viewpanel;
	private int uren = 0;
	private int minuten = 0;
	private String[] dagen;
	private int x = 0;
	private int verstrekendagen = 0;
	private int dagVoorbij = 0;
	private JTextField snelheid;
	private JTextField prijs;
	private JTextField weekdagaankomst;
	private JTextField weekendaankomst;
	private JTextField werkdagpasdaankomst;
	private JTextField weekendpasdaankomst;
	private JTextField maxgewonewachtrij;
	private JTextField maxspecialewachtrij;
	
	

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
		viewpanel.setLayout(new GridLayout(26,10));
		
		tijd = new JLabel("Tijdstip: 0:00");
		viewpanel.add(tijd);
		dag = new JLabel("Dag: Maandag");
		viewpanel.add(dag);
		
		verstrekendagenlabel = new JLabel("Verstreken dagen: 0");
		viewpanel.add(verstrekendagenlabel);
		
//		empty = new JLabel("");
//		viewpanel.add(empty);
		
		snelheidlabel = new JLabel("Stappenpauze:");
		viewpanel.add(snelheidlabel);
		snelheid = new JTextField(String.valueOf(Simulator.getTickPause()));
		viewpanel.add(snelheid);
		
		prijslabel = new JLabel("Prijs per uur:");
		viewpanel.add(prijslabel);
		prijs = new JTextField(String.valueOf(Simulator.getRegularPricePerHour()));
		viewpanel.add(prijs);
		
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
		
		specialedoorgereden = new JLabel("Doorgereden (speciaal): "+String.valueOf(Simulator.getDoorgeredenAutos()));
		viewpanel.add(specialedoorgereden);
		
		maxgewonewachtrijlabel = new JLabel("Maximale wachtrij (normale auto's)");
		viewpanel.add(maxgewonewachtrijlabel);
		maxgewonewachtrij = new JTextField(String.valueOf(Simulator.getMaxEntranceCarQueue()));
		viewpanel.add(maxgewonewachtrij);
		
		maxspecialewachtrijlabel = new JLabel("Maximale wachtrij (speciale auto's)");
		viewpanel.add(maxspecialewachtrijlabel);
		maxspecialewachtrij = new JTextField(String.valueOf(Simulator.getMaxSpecialCarQueue()));
		viewpanel.add(maxspecialewachtrij);
		
		add(viewpanel);
		setVisible(true);
	}
	
	
	
	public void plusuur() {
		if(minuten > 58) {
			minuten = -1;
			uren++;
		}
		if(uren > 23 ) {
			uren = 0;
		}
		minuten++;
		if(minuten <10 && uren<10) {
			tijd.setText("Tijdstip: 0" + String.valueOf(uren)+ ":0" + String.valueOf(minuten));
		}else if(uren>9 && minuten<10) {
			tijd.setText("Tijdstip: " + String.valueOf(uren)+ ":0" + String.valueOf(minuten));
		}else if(uren<10 && minuten>9) {
			tijd.setText("Tijdstip: 0" + String.valueOf(uren)+ ":" + String.valueOf(minuten));
		}

		else
		tijd.setText("Tijdstip: " + String.valueOf(uren)+ ":" + String.valueOf(minuten));
	
		gewonewachtrij.setText("Gewone wachtrij: "+String.valueOf(Simulator.getNormalCarQueue()));
		paswachtrij.setText("Speciale wachtrij: "+String.valueOf(Simulator.getPassCarQueue()));
		gewonedoorgereden.setText("Doorgereden: "+String.valueOf(Simulator.getDoorgeredenAutos()));
		specialedoorgereden.setText("Doorgereden (speicaal): "+String.valueOf(Simulator.getDoorgeredenSpecialeAutos()));
		verstrekendagenlabel.setText("Verstreken dagen: "+String.valueOf(verstrekendagen));
	}
	
	public void plusdag() {
		
		if(uren==23 && minuten == 59) {
			x++;
			verstrekendagen++;
			Simulator.nieuweDag();
			if(x==7) {
				x = 0;
			}
		}
		dag.setText("Dag: " + dagen[x]);
		
	}
	
	public int uur() {
		return uren;
	}
	
	public String dag(){
		return dagen[x];
	}
	
	public void tijdEnDag() {
		plusdag();
		plusuur();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==update) {
		String snel = snelheid.getText();
		String weekautos = weekdagaankomst.getText();
		String weekendautos = weekendaankomst.getText();
		String werkdagpasdautos = werkdagpasdaankomst.getText();
		String weekendpasdautos = weekendpasdaankomst.getText();
		String maximaalgewonewachtrij = maxgewonewachtrij.getText();
		String maximaalspeciaalwachtrij = maxspecialewachtrij.getText();
		String deprijs = prijs.getText();
		Double deprijsdouble = Double.parseDouble(deprijs);
		Simulator.setTickPause(Integer.parseInt(snel));
		Simulator.setWeekDayArrivals(Integer.parseInt(weekautos));
		Simulator.setWeekendArrivals(Integer.parseInt(weekendautos));
		Simulator.setweekDayPassArrivals(Integer.parseInt(werkdagpasdautos));
		Simulator.setweekendPassArrivals(Integer.parseInt(weekendpasdautos));
		Simulator.setMaxEntranceCarQueue(Integer.parseInt(maximaalgewonewachtrij));
		Simulator.setMaxSpecialCarQueue(Integer.parseInt(maximaalspeciaalwachtrij));
		Simulator.setRegularPricePerHour(deprijsdouble);
		}
	}
}