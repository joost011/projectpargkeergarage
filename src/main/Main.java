package main;

import java.awt.BorderLayout;


import javax.swing.*;

import controller.*;
import logic.*;
import view.*;

public class Main {
	private Simulator simulator;
	//protected JFrame screen;
	//private SimulatorView simulatorview;
	private Controller controller;
	
	public Main() {
		simulator=new Simulator();
		//controller=new Controller(simulator);
		//simulatorview = new SimulatorView(3, 6, 30);
	/*	screen=new JFrame("Model View Controller/Dynamic Model with thread");
		screen.setSize(750, 500);
		screen.setResizable(false);
		screen.setLayout(null);
		screen.getContentPane().add(simulatorview);
		screen.getContentPane().add(controller); 
		//simulatorview.setBounds(10, 10, 200, 200);
		controller.setBounds(100, 410, 450, 30);
		screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		screen.setVisible(true); */ 
	}
}
