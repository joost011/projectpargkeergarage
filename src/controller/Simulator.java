package controller;


import java.util.Random;
import model.AdHocCar;
import model.Car;
import model.CarQueue;
import model.Location;
import model.ParkingPassCar;
import model.ReservationCar;
import view.*;

public final class Simulator {

	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	private static final String RES = "3";

	private static CarQueue entranceCarQueue;

	private static CarQueue entrancePassQueue;
	private CarQueue paymentCarQueue;
	private CarQueue exitCarQueue;
	private SimulatorView simulatorView;
	public boolean run;

	private int day = 0;					// Value voor het bijhouden van een dag
	private int hour = 0;					// Value voor het bijhouden van een uur
	private int minute = 0;					// Value voor het bijhouden van een minuut

	private int currentResCar = 0; 			// Hoeveelheid auto's met een reservatie
	private int currentAboCar = 0;			// Hoeveelheid auto's met een abonnement
	private int currentRandCar = 0;			// Hoeveelheid random auto's in de garage
	private int currentEmpty = 0;			// Hoeveelheid lege plekken

	private static int tickPause = 10;		// Pause tussen elke loop

	static int weekDayArrivals= 150;		// average number of arriving cars per hour
	static int weekendArrivals = 200; 		// average number of arriving cars per hour
	static int weekDayPassArrivals= 100; 	// average number of arriving cars per hour
	static int weekendPassArrivals = 80; 	// average number of arriving cars per hour

	int enterSpeed = 3; 					// number of cars that can enter per minute
	int paymentSpeed = 7; 					// number of cars that can pay per minute
	int exitSpeed = 5; 						// number of cars that can leave per minute

	static Double regularPricePerHour = 1.5; 	// regular price normal cars have to pay
	int monthlyAboPay = 0; 					// price per month cardholders have to pay
	Double resPay = 0.0; 						// price to place a reservation
	Double revenue = 0.0;						// total monies earned

	static int revenuevandaag = 0;
	static int revenuedag2 = 0;
	static int revenuedag3 = 0;
	static int revenuedag4 = 0;
	static int revenuedag5 = 0;
	static int vorigrevenue = 0;

	static int maxEntranceCarQueue = 100; 	// maximum cars in the normal car queue
	static int maxSpecialCarQueue = 100; 	// maximum cars in the normal car queue

	static int doorgeredenAutos= 0;			// Aantal doorgereden outo's
	static int doorgeredenSpecialeAutos= 0; // Aantal doorgereden speciale auto's

	private int[] autoUren = new int[24];

	public Simulator() {
		entranceCarQueue = new CarQueue();						// Start een entranceQueue
		entrancePassQueue = new CarQueue();						// Start een abonnement Queue

		paymentCarQueue = new CarQueue();						// Start een betalings Queue
		exitCarQueue = new CarQueue();							// Start een exit Queue
		simulatorView = new SimulatorView(3, 6, 30, this);		// Start een nieuwe SimulatorView
		currentEmpty = simulatorView.getNumberOfOpenSpots();	// Set de vrije locaties in de garage naar het maximale getal.
	}

	/**
	 * Start de simulatie
	 */
	public void start() {
		if(!this.run){
			this.run = true;
			run();
		}
	}

	/**
	 * Stopt de simulatie
	 */
	public void stop() {
		this.run = false;
	}


	/**
	 * Zoland dit actief is zal de simulatie zich voortzetten
	 */
	public void run() {
		do {
			//for (int i = 0; i < 10000; i++) {
			tick();
			updateViews();
			//}
		}
		while(this.run == true);
	}


	/**
	 *  Laat alle variabelen zijn werk doen
	 */
	public void tick() {
		simulatorView.statics().tijdEnDag();
		revenuevandaag = revenue.intValue() - vorigrevenue;
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


	/**
	 * Laat tijd velemalen sneller gaan dan normaal
	 */
	private void advanceTime(){
		// Advance the time by one minute.
		minute++;
		while (minute > 59) {
			minute -= 60;
			hour++;
			int totalCars = currentResCar + currentAboCar + currentRandCar;
			for (int i=0; i<24; i++) {
				if(i<23) {
					int iplus = i+1;
					autoUren[i] = autoUren[iplus];
				}
				else {
					autoUren[i] = totalCars;
				}
			}
			for (int i=0; i<24; i++) {
				if(i<23) {
					int iplus = i+1;
					autoUren[i] = autoUren[iplus];
				}
				else {
					autoUren[i] = totalCars;
				}
			}
		}
		while (hour > 23) {
			hour -= 24;
			day++;
		}
		while (day > 6) {
			day -= 7;
		}

	}

	/**
	 * Hanteert de ingang van auto's
	 */
	private void handleEntrance(){
		carsArriving();
		carsEntering(entrancePassQueue);
		carsEntering(entranceCarQueue);  	
	}

	/**
	 * Hanteert het verlaten van auto's
	 */
	private void handleExit(){
		carsReadyToLeave();
		carsPaying();
		carsLeaving();
	}

	/**
	 * Update de simulatie
	 */
	private void updateViews(){
		simulatorView.tick();																			// Activeer de tick methode
		// Update the car park view.
		simulatorView.updateView();																		// Update de simulator

		simulatorView.updateBarChart(currentResCar,currentAboCar,currentRandCar,currentEmpty);			// update BarChart view met nieuwe variabelen
		simulatorView.updatePieChart(currentResCar,currentAboCar,currentRandCar,currentEmpty, revenue); // update PieChart view met nieuwe variabelen en revenue
		simulatorView.updateRevenueBarChart(revenuevandaag, revenuedag2, revenuedag3, revenuedag4, revenuedag5);
		simulatorView.updateLineChart(autoUren);
		simulatorView.updateQueueView(entranceCarQueue.carsInQueue(), entrancePassQueue.carsInQueue(), maxEntranceCarQueue, maxSpecialCarQueue);
	}

	/**
	 * Regelt wat er moet gebeuren als auto's aan komen.
	 */
	private void carsArriving(){
		int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
		addArrivingCars(numberOfCars, AD_HOC);    									// Voegt random auto's toe
		numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
		addArrivingCars(numberOfCars, PASS);										// Voegt auto's met een abonnement toe
		numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);		
		addArrivingCars(numberOfCars, RES); 										// Voegt auto's toe met een reservatie
	}

	/**
	 * Regelt wat er moet gebeuren als mensen binnen komen
	 * @param queue
	 */
	private void carsEntering(CarQueue queue){
		int i=0;
		// Remove car from the front of the queue and assign to a parking space.
		while (queue.carsInQueue()>0 && 
				simulatorView.getNumberOfOpenSpots()>0 && 
				i<enterSpeed) {
			Car car = queue.removeCar();
			if(car instanceof ReservationCar) {
				Location freeResLocation = simulatorView.getFirstFreeLocation();
				try{																	// Om de overload te voorkomen een Try Catch
					simulatorView.setCarAt(freeResLocation, car);
					currentResCar++;													// Add reserved car location
					currentEmpty--; 													// remove one empty spot
				}
				catch(NullPointerException e){
					doorgeredenSpecialeAutos++;
				}
			}
			else if(car instanceof ParkingPassCar) {
				Location freeResLocation = simulatorView.getReservationLocation();
				try{ 																	// Om de overload te voorkomen een Try Catch
					simulatorView.setCarAt(freeResLocation, car);
					currentAboCar++;													// Add abonoment car location
					currentEmpty--; 													// remove one empty spot
				}
				catch(NullPointerException e){
					doorgeredenSpecialeAutos++;
				}
			}
			else {
				Location freeLocation = simulatorView.getFirstFreeLocation();
				try{																	// Om de overload te voorkomen een Try Catch
					simulatorView.setCarAt(freeLocation, car);
					currentRandCar++; 													// Add random car location
					currentEmpty--; 													// remove one empty spot
				}
				catch(NullPointerException e){
					doorgeredenAutos++;
				}
			}
			i++;
		}
	}


	/**
	 * Getter voor de value van reservaties
	 * @return
	 */
	public int getRes(){
		return currentResCar;
	}
	/**
	 * Getter voor de value van abonnementen
	 * @return
	 */
	public int getAbo(){
		return currentAboCar;
	}

	/**
	 * Getter voor de value van random auto's
	 * @return
	 */
	public int getRand(){
		return currentRandCar;
	}

	/**
	 * Getter voor het value van de momentele lege plekken
	 * @return
	 */
	public int getEmpty(){
		return currentEmpty;
	}

	/**
	 * Hanteert de auto's die vertrekken
	 */
	private void carsReadyToLeave(){
		// Add leaving cars to the payment queue.
		Car car = simulatorView.getFirstLeavingCar();
		while (car!=null) {
			if(car instanceof ReservationCar) {			// Als de car een instantie is van een Reservation car 
				currentResCar--;							// haal een af van de res car
				currentEmpty++;								// stop een lege plek erbij
			}
			if(car instanceof ParkingPassCar) {			// Als de car een instantie is van een ParkingPass car
				currentAboCar--;							// haal een af van de res car
				currentEmpty++;								// stop een lege plek erbij
			}
			if(car instanceof AdHocCar) {				// Als een car een instantie is van een AdHoc Car (oftewel een random auto)
				currentRandCar--;							// haal een af van de res car
				currentEmpty++;								// stop een lege plek erbij
			}
			if (car.getHasToPay()){						// Als een auto de boolean heeft dat het moet betalen
				car.setIsPaying(true);					// auto gaat betalen
				paymentCarQueue.addCar(car);			// Voeg toe aan payment queue
			}
			else {										// Als het niet zo is
				carLeavesSpot(car);						// auto verlaat de plek
			}
			car = simulatorView.getFirstLeavingCar();	// initialiseert de getFirstLeavingCar methode
		}
	}

	/**
	 * Hanteert de betaling van de auto's
	 */
	private void carsPaying(){
		// Let cars pay.
		int i=0;
		while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
			Car car = paymentCarQueue.removeCar();
			revenue+=car.getHasToPayAmount();							// voegt het betaalbedrag aan de totale opwinsten op
			carLeavesSpot(car);											// Auto verlaat de plek
			i++;
		}
	}

	/**
	 * Auto verlaat de queue
	 */
	private void carsLeaving(){
		// Let cars leave.
		int i=0;
		while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
			exitCarQueue.removeCar();
			i++;
		}	
	}

	/**
	 * Bepaalt het aantal auto's dat per uur binnen komen
	 * @param weekDay
	 * @param weekend
	 * @return
	 */
	private int getNumberOfCars(int weekDay, int weekend){
		Random random = new Random();

		// Get the average number of cars that arrive per hour.
		int averageNumberOfCarsPerHour = day < 5 ? weekDay : weekend; 	// verandert het aantal binnen komende auto's als het door de weeks is of weekend


		// Calculate the number of cars that arrive this minute.
		int x;
		if((simulatorView.statics().uur()>=14 &&			
				simulatorView.statics().uur()<=16) ||
				(simulatorView.statics().uur()>=18 &&	
				simulatorView.statics().uur()<=22)) {
			if(simulatorView.statics().dag().equals("Donderdag") ||
					simulatorView.statics().dag().equals("Vrijdag")  ||
					simulatorView.statics().dag().equals("Zaterdag") ||
					simulatorView.statics().dag().equals("Zondag")){
				x=80;
			}
			else{
				x = 125;
			}
		}
		else if (simulatorView.statics().uur()>=0 &&			
				simulatorView.statics().uur()<=7){
			x = 360;
		}
		else if (simulatorView.statics().uur()>=8 &&
				simulatorView.statics().uur()<=10 
				) {
			x = 125;
		}
		else {
			x = 200;
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
				if (n > entranceCarQueue.carsInQueue() || entranceCarQueue.carsInQueue() < maxEntranceCarQueue/10) {
					entranceCarQueue.addCar(new AdHocCar());
				}
				else {
					doorgeredenAutos++;
				}
			}
			break;
		case PASS:
			for (int i = 0; i < numberOfCars; i++) {
				int  n = rand.nextInt(maxSpecialCarQueue +1);
				if (n > entrancePassQueue.carsInQueue() || entrancePassQueue.carsInQueue() < maxSpecialCarQueue/10) {
					entrancePassQueue.addCar(new ParkingPassCar());
				}
				else {
					doorgeredenSpecialeAutos++;
				}
			}
			break;	
		case RES:
			for (int i = 0; i < numberOfCars; i++) {
				int  n = rand.nextInt(maxSpecialCarQueue +1);
				if (n > entrancePassQueue.carsInQueue() || entrancePassQueue.carsInQueue() < maxSpecialCarQueue/10) {
					entrancePassQueue.addCar(new ReservationCar());
				}
				else {
					doorgeredenSpecialeAutos++;
				}
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
	}

	public static int getWeekendArrivals() {
		return weekendArrivals;
	}

	public static void setWeekendArrivals(int WEA) {
		weekendArrivals = WEA;
	}

	public static int getweekDayPassArrivals() {
		return weekDayPassArrivals;
	}

	public static void setweekDayPassArrivals(int WDPA) {
		weekDayPassArrivals = WDPA;
	}

	public static int getweekendPassArrivals() {
		return weekendPassArrivals;
	}

	public static void setweekendPassArrivals(int WEPA) {
		weekendPassArrivals = WEPA;
	}

	public static int getNormalCarQueue() {
		return entranceCarQueue.carsInQueue();
	}

	public static int getPassCarQueue() {
		return entrancePassQueue.carsInQueue();
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

	public static int getMaxSpecialCarQueue() {
		return maxSpecialCarQueue;
	}

	public static void setMaxSpecialCarQueue(int MSCQ) {
		maxSpecialCarQueue = MSCQ;
	}

	public static int getDoorgeredenSpecialeAutos() {
		return doorgeredenSpecialeAutos;
	}

	public static void setRegularPricePerHour(Double RPPH) {
		regularPricePerHour = RPPH;
	}

	public static Double getRegularPricePerHour() {
		return regularPricePerHour;
	}

	public static void nieuweDag() {
		revenuedag5 = revenuedag4;
		revenuedag4 = revenuedag3;
		revenuedag3 = revenuedag2;
		revenuedag2 = revenuevandaag;
		vorigrevenue = vorigrevenue + revenuedag2;
		revenuevandaag = 0;
	}
	
	public void switchView() {
		simulatorView.switchView();
	}

}