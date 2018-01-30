package logic;

import java.awt.Color;
import java.util.Random;
import controller.*;
import view.*;

public final class Simulator {

	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	private static final String RES = "3";
		
	private static CarQueue entranceCarQueue;
	private static CarQueue entranceResQueue;
    private static CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;
    public boolean run;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    
    private int currentResCar = 0;
    private int currentAboCar = 0;
    private int currentRandCar = 0;
    private int currentEmpty = 0;

    private static int tickPause = 100;

    static int weekDayArrivals= 100; // average number of arriving cars per hour
    static int weekendArrivals = 200; // average number of arriving cars per hour
    static int weekDayPassArrivals= 50; // average number of arriving cars per hour
    static int weekendPassArrivals = 5; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute
    
    static int maxEntranceCarQueue = 100; // maximum cars in the normal car queue
    
    static int doorgeredenAutos= 0;

    public Simulator() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        entranceResQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(3, 6, 30, this);
        
    }
    
    public void start() {
    	this.run = true;
    	run();
    }
    
    public void stop() {
    	this.run = false;
    }
    
    

    public void run() {
    	do {
        //for (int i = 0; i < 10000; i++) {
            tick();
            updateViews();
            //}
    	}
    	while(this.run == true);
    }

    public void tick() {
    	simulatorView.statics().tijdEnDag();
    	advanceTime();
    	handleExit();
    	updateViews();
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	handleEntrance();
    }

    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

    }

    private void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);  	
    }
    
    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }
    
    private void updateViews(){
    	simulatorView.tick();
        // Update the car park view.
        simulatorView.updateView();
        simulatorView.updatePieChart(currentResCar,currentAboCar,currentRandCar,currentEmpty);
        simulatorView.updateBarChart(currentResCar,currentAboCar,currentRandCar,currentEmpty);
    }
    
    private void carsArriving(){
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
        numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, RES); 
    }

    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
    	while (queue.carsInQueue()>0 && 
    			simulatorView.getNumberOfOpenSpots()>0 && 
    			i<enterSpeed) {
            Car car = queue.removeCar();
            if(car instanceof ReservationCar) {
            	Location freeResLocation = simulatorView.getReservationLocation();
            	simulatorView.setCarAt(freeResLocation, car);
            	currentResCar++;	// Add reserved car location
            	currentEmpty--; 	// remove one empty spot
            }
            else if(car instanceof ParkingPassCar) {
            	Location freeResLocation = simulatorView.getFirstFreeLocation();
            	simulatorView.setCarAt(freeResLocation, car);
            	currentAboCar++;	// Add abonoment car location
            	currentEmpty--; 	// remove one empty spot
            }
            else {
            Location freeLocation = simulatorView.getFirstFreeLocation();
            simulatorView.setCarAt(freeLocation, car);
            currentRandCar++; 	// Add random car location
            currentEmpty--; 	// remove one empty spot
            }
            i++;
        }
    }
    
   
    
    public int getRes(){
    	return currentResCar;
    }
    
    public int getAbo(){
    	return currentAboCar;
    }
    
    public int getRand(){
    	return currentRandCar;
    }
    
    public int getEmpty(){
    	return currentEmpty;
    }
    
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = simulatorView.getFirstLeavingCar();
        while (car!=null) {
        	if(car instanceof ReservationCar) {
       		 currentResCar--;
       		 currentEmpty++;
            }
        	if(car instanceof ParkingPassCar) {
       		 currentAboCar--;
       		 currentEmpty++;
            }
        	if(car instanceof AdHocCar) {
       		 currentRandCar--;
       		 currentEmpty++;
            }
        	if (car.getHasToPay()){
	            car.setIsPaying(true);
	            paymentCarQueue.addCar(car);
        	}
        	else {
        		carLeavesSpot(car);
        	}
            car = simulatorView.getFirstLeavingCar();
        }
    }

    private void carsPaying(){
        // Let cars pay.
    	int i=0;
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
    	}
    }
    
    private void carsLeaving(){
        // Let cars leave.
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
    	}	
    }
    
    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        int x;
        if(simulatorView.statics().uur()==17 ||
           simulatorView.statics().uur()==18 ||	
           simulatorView.statics().uur()==19 ||
           simulatorView.statics().uur()==20 ||
           simulatorView.statics().uur()==21) {
        	x = 300;
        }
        else {
        	x = 60;
        }
         
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / x);	
    }
    
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
    	Random rand = new Random();
    	
    	switch(type) {
    	case AD_HOC: 
            for (int i = 0; i < numberOfCars; i++) {
            	int  n = rand.nextInt(maxEntranceCarQueue +1); // n = random number between 0 and the max number of normal cars in queue
            	if (n > entranceCarQueue.carsInQueue() || entranceCarQueue.carsInQueue() == 0) {
            		entranceCarQueue.addCar(new AdHocCar());
            	}
            	else {
            		doorgeredenAutos++;
            	}
            }
            break;
    	case PASS:
            for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new ParkingPassCar());
            }
            break;	
    	case RES:
    		for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new ReservationCar());
    		}
    	}
    }
    
    private void carLeavesSpot(Car car){
    	simulatorView.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }
    
    public static int getWeekDayArrivals() {
    	return weekDayArrivals;
    }
    
    public static void setWeekDayArrivals(int WDA) {
    	weekDayArrivals = WDA;
    	System.out.println("Werkdag: "+weekDayArrivals);
    }
    
    public static int getWeekendArrivals() {
    	return weekendArrivals;
    }
    
    public static void setWeekendArrivals(int WEA) {
    	weekendArrivals = WEA;
    	System.out.println("Weekend: "+weekendArrivals);
    }
    
    public static int getweekDayPassArrivals() {
    	return weekDayPassArrivals;
    }
    
    public static void setweekDayPassArrivals(int WDPA) {
    	weekDayPassArrivals = WDPA;
    	System.out.println("Werkdag (met pas): "+weekDayPassArrivals);
    }
    
    public static int getweekendPassArrivals() {
    	return weekendPassArrivals;
    }
    
    public static void setweekendPassArrivals(int WEPA) {
    	weekendPassArrivals = WEPA;
    	System.out.println("Weekend (met pas): "+weekendPassArrivals);
    }
    
    public static int getNormalCarQueue() {
    	return entranceCarQueue.carsInQueue();
    }
    
    public static int getPassCarQueue() {
    	return entrancePassQueue.carsInQueue();
    }
    
    public static int getResCarQueue() {
    	return entranceResQueue.carsInQueue();
    }
    
    public static int getTickPause() {
    	return tickPause;
    }
    
    public static void setTickPause(int TP) {
    	tickPause = TP;
    }
    
    public static int getMaxEntranceCarQueue() {
    	return maxEntranceCarQueue;
    }
    
    public static void setMaxEntranceCarQueue(int MECQ) {
    	maxEntranceCarQueue = MECQ;
    }
    
    public static int getDoorgeredenAutos() {
    	return doorgeredenAutos;
    }
}