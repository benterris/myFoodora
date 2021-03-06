package myfoodora;
import java.lang.Math ;



/**
 *	A fidelity card that gives a certain probability to earn a meal for free every time an order is passed
 */

public class LotteryFidelityCard implements FidelityCard, java.io.Serializable{
	
	private static final long serialVersionUID = 7595242174164729122L;

	public double computeNewPrice (double price){
		
		double nbAleatoire = Math.random();
		double p = 0.05;
		if (nbAleatoire <= p){
			price = 0;
		}
		return Math.floor(price*100)/100;
	}

}