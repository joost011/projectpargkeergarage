package logic;

import java.util.Random;
import java.awt.*;

public class ReservationCar extends Car {
	private static final Color COLOR=Color.green;
	
    public ReservationCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 240);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
        Double stayMinutesDouble = 1.0 * stayMinutes;
        this.setHasToPayAmount(((double) Math.round(stayMinutesDouble / 60 * Simulator.getRegularPricePerHour() *100) / 100) + 5);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}