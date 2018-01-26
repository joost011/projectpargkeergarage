package view;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

public class PieChartView extends JPanel {
	
	ChartPanel chartPanel;
	
	public PieChartView(String appTitle, String chartTitle){
		
		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, chartTitle);
		chartPanel = new ChartPanel(chart);
		
//		chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
//		setContentPane(chartPanel);
//		this.pack();
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		chartPanel.setVisible(true);
		
	}
	
	public ChartPanel getChart(){
		return chartPanel;
	}
	
	private PieDataset createDataset(){
		
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("value1", 5);
		result.setValue("Value2", 5);
		result.setValue("Value3", 5);
		return result;
	}
	
	private JFreeChart createChart(PieDataset dataset, String title){
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, false, false);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setStartAngle(0);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		
		return chart;
	}
	
	
	
	
}