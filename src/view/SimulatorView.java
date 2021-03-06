package view;


import java.awt.*;

import controller.*;
import model.*;

import javax.swing.*;

public class SimulatorView extends JPanel {
    private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;
    private JFrame screen;
    private Controller controller;
    private StatisticsView statics;
    private JTabbedPane tabs;
    private JPanel tabspanel;
    private PieChartView2 piechart;
    private BarView barchart;
    private RevenueBarView revenuebarchart;
    private LineChartView linechart;
    private QueueView queueview;
    private int extendedview = 0;
    private String tabTitle;

    /**
     * Creeert de simulatie
     * @param numberOfFloors
     * @param numberOfRows
     * @param numberOfPlaces
     * @param simulator
     */
    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces, Simulator simulator) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        statics = new StatisticsView();
        controller = new Controller(simulator);
        
        carParkView = new CarParkView();
        piechart = new PieChartView2();

        barchart = new BarView();
        revenuebarchart = new RevenueBarView();
        linechart = new LineChartView();
        linechart.dataNul();
        queueview = new QueueView();

        tabspanel = new JPanel();
        tabspanel.setPreferredSize(new Dimension(650, 750));
        tabs = new JTabbedPane();
		tabs.setPreferredSize(new Dimension(320, 750));
		tabs.addTab("PieChart", piechart);
		tabs.addTab("BarView", barchart);
		tabs.addTab("Omzet", revenuebarchart);
		tabs.addTab("LineChart", linechart);
		tabs.add("Wachtrijen", queueview);
        
        screen=new JFrame("Project Parkeergarage");
        Container contentPane = screen.getContentPane();
        contentPane.add(carParkView, BorderLayout.CENTER);
        contentPane.add(statics, BorderLayout.WEST);
        screen.getContentPane().add(controller, BorderLayout.SOUTH);
        screen.getContentPane().add(tabs, BorderLayout.EAST);
        screen.pack();
        screen.setSize(1400, 820);
        screen.setVisible(true);
		screen.setResizable(false);
		screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		

        updateView();
        
    }
    
    
    public StatisticsView statics() {
    	return statics;
    }
    
    public void updateView() {
        carParkView.updateView();
    }
    
	public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfOpenSpots(){
    	return numberOfOpenSpots;
    }
    
    public void updatePieChart(int res, int abo, int rand, int empty, Double revenue){
    	piechart.repaint( res,  abo,  rand,  empty, revenue);
    }
    
    public void updateBarChart(int res, int abo, int rand, int empty) {
    	barchart.repaint( res,  abo,  rand,  empty);
    }
    
    public void updateRevenueBarChart(int vandaag, int dag2, int dag3, int dag4, int dag5) {
    	revenuebarchart.repaint( vandaag, dag2, dag3, dag4, dag5);
    }
    
    public void updateLineChart(int[] uren) {
    	linechart.repaint(uren);
    }
    
    public void updateQueueView(int gewonewacht, int specialewacht, int maxgewone, int maxspeciale) {
    	queueview.repaint(gewonewacht, specialewacht, maxgewone, maxspeciale);
    }
    
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }

    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < 2; floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                } 
            }
        }
        return null;
    }
    
    public Location getReservationLocation() {
    	 for (int floor = 2; floor < getNumberOfFloors(); floor++) {
             for (int row = 0; row < getNumberOfRows(); row++) {
                 for (int place = 0; place < getNumberOfPlaces(); place++) {
                     Location location = new Location(floor, row, place);
                     if (getCarAt(location) == null) {
                         return location;
                     }
                 } 
             }
         }
         return null;
    	
    }

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    public void tick() {
    	
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }
    
    private class CarParkView extends JPanel {
        
        private Dimension size;
        private Image carParkImage;    
    
        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView() {
            size = new Dimension(0, 0);
        }
    
        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }
    
        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }
    
            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
    
        public void updateView() {
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = createImage(size.width, size.height);
            }
            Graphics graphics = carParkImage.getGraphics();
            for(int floor = 0; floor < getNumberOfFloors(); floor++) {
                for(int row = 0; row < getNumberOfRows(); row++) {
                    for(int place = 0; place < getNumberOfPlaces(); place++) {
                    	
                        Location location = new Location(floor, row, place);
                        Car car = getCarAt(location);
                        Color color = car == null ? Color.white : car.getColor();
                        drawPlace(graphics, location, color);
                    }
                }
            }
            repaint();
        }
        
        
    
        /**
         * Paint a place on this car park view in a given color.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
    }

	public void switchView() {
		if(extendedview == 0) {
			int index = tabs.getSelectedIndex();
			tabTitle = tabs.getTitleAt(index);
			
			if(tabTitle == "PieChart") {
				tabs.remove(piechart);
				tabspanel.add(piechart, BorderLayout.WEST);
			}
			if(tabTitle == "BarView") {
				tabs.remove(barchart);
				tabspanel.add(barchart, BorderLayout.WEST);
			}
			if(tabTitle == "Omzet") {
				tabs.remove(revenuebarchart);
				tabspanel.add(revenuebarchart, BorderLayout.WEST);
			}
			if(tabTitle == "LineChart") {
				tabs.remove(linechart);
				tabspanel.add(linechart, BorderLayout.WEST);
			}
			if(tabTitle == "Wachtrijen") {
				tabs.remove(queueview);
				tabspanel.add(queueview, BorderLayout.WEST);
			}
			tabspanel.add(tabs, BorderLayout.EAST);
			screen.getContentPane().remove(tabs);
			screen.getContentPane().add(tabspanel, BorderLayout.EAST);
			screen.setSize(1700, 820);
			extendedview = 1;
		}
		else {
			if(tabTitle == "PieChart") {
				tabs.addTab(tabTitle, piechart);
				tabspanel.remove(piechart);
			}
			if(tabTitle == "BarView") {
				tabs.addTab(tabTitle, barchart);
				tabspanel.remove(barchart);
			}
			if(tabTitle == "Omzet") {
				tabs.addTab(tabTitle, revenuebarchart);
				tabspanel.remove(revenuebarchart);
			}
			if(tabTitle == "LineChart") {
				tabs.addTab(tabTitle, linechart);
				tabspanel.remove(linechart);
			}
			if(tabTitle == "Wachtrijen") {
				tabs.addTab(tabTitle, queueview);
				tabspanel.remove(queueview);
			}
			tabspanel.remove(tabs);
			screen.getContentPane().remove(tabspanel);
			screen.getContentPane().add(tabs, BorderLayout.EAST);
			screen.setSize(1400, 820);
			extendedview = 0;
		}
	}

}