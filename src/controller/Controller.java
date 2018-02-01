package controller;

import javax.swing.*;
import logic.*;
import java.awt.FlowLayout;
import java.awt.event.*;

public class Controller  extends AbstractController  implements ActionListener, Runnable {

	private JButton plus;
	private JButton plus100;
	private JButton start;
	private JButton stop;
	private Simulator simulator;;

	public Controller(Simulator simulator) {
		super(simulator);



		this.simulator = simulator;

		plus = new JButton("Plus 1");
		plus.addActionListener(this);
		plus100 = new JButton("Plus 100");
		plus100.addActionListener(this);
		start = new JButton("Start");
		start.addActionListener(this);
		stop = new JButton("Stop");
		stop.setEnabled(false);
		stop.addActionListener(this);


		this.setLayout(new FlowLayout());
		add(plus);
		add(plus100);
		add(start);
		add(stop);
		setVisible(true);

	}


	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==plus) {
			simulator.tick();
		}

		if(e.getSource()==plus100) {

			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					plus100.setEnabled(false);
					start.setEnabled(false);
					stop.setEnabled(true);
					plus.setEnabled(false);
					for(int i=0;i<100;i++){
						simulator.tick();
					}
					plus100.setEnabled(true);
					start.setEnabled(true);
					stop.setEnabled(false);
					plus.setEnabled(true);
				}
			});
			thread.start();
		}

		if(e.getSource()==start) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					start.setEnabled(false);
					plus100.setEnabled(false);
					plus.setEnabled(false);
					stop.setEnabled(true);
					simulator.start();
				}
			});
			t.start();
		}  
		if(e.getSource()==stop) {
			start.setEnabled(true);
			plus100.setEnabled(true);
			plus.setEnabled(true);
			stop.setEnabled(false);
			simulator.stop();
			
		}

	}



	@Override
	public void run() {

	}	
}




