package controller;

import javax.swing.*;
import logic.*;
import java.awt.FlowLayout;
import java.awt.event.*;

public class Controller  extends AbstractController  implements ActionListener, Runnable {
	
	private JButton plus;
	private JButton start;
	private JButton stop;
	private Simulator simulator;
	private JButton auto;

	public Controller(Simulator simulator) {
		super(simulator);
		
		
		
		this.simulator = simulator;
		
		plus = new JButton("Plus 1");
		plus.addActionListener(this);
		start = new JButton("Start");
		start.addActionListener(this);
		stop = new JButton("Stop");
		stop.addActionListener(this);
		auto = new JButton("check");
		auto.addActionListener(this);
		
		
		this.setLayout(new FlowLayout());
		add(plus);
		add(start);
		add(stop);
		add(auto);
		setVisible(true);
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
			if(e.getSource()==plus) {
				simulator.tick();
			}
				
			if(e.getSource()==start) {
				Thread t = new Thread(new Runnable() {
			         @Override
			         public void run() {
			              simulator.start();
			         }
			});
				t.start();
			}  
			if(e.getSource()==stop) {
				simulator.stop();
			}
			if(e.getSource()==auto) {
				//simulator.auto();
			}
			
			}
		


	@Override
	public void run() {
		
	}	
}

	

