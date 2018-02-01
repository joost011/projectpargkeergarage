package logic;

import java.util.Random;
import java.awt.*;

public class AdHocCar extends Car {
	private static final Color COLOR=Color.red;
	
    public AdHocCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 100);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        Double stayMinutesDouble = 1.0 * stayMinutes;
        this.setHasToPayAmount((double) Math.round(stayMinutesDouble / 60 * Simulator.getRegularPricePerHour() *100) / 100);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}