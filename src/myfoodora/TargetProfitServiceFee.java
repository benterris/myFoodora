package myfoodora;

import java.util.ArrayList;
import java.util.Calendar;

public class TargetProfitServiceFee implements ProfitPolicy, java.io.Serializable {

	private static final long serialVersionUID = 4812243803665997187L;
	private MyFoodora sys;
	
	public TargetProfitServiceFee(MyFoodora sys){
		this.sys = sys;
	}
	
	/**
	 * Computes the service fee necessary to meet targetProfit, based on last month's orders, knowing that :
	 * targetProfit = sumOverOrders((markupPercentage*priceOfOrder) - reductionsDueToCards) + numberOfOrders*(serviceFee - deliveryCost)
	 */
	@Override
	public void computeVariablesToTargetProfit(double targetProfit) {
		Calendar oneMonthAgo = Calendar.getInstance();
		oneMonthAgo.add(Calendar.MONTH, -1); //retrieve one month to the current date to select orders only after this date
		
		ArrayList<Order> historyOfOrders = sys.getHistoryOfOrders();
		double sum = 0; //will store the value of sumOverOrders(markupPercentage*priceOfOrder - reductionDueToCards)
		int N = 0;
		
		for(Order o : historyOfOrders){
			//we only select the orders that took place in the last month
			if(oneMonthAgo.before(o.getDate())){
				sum += sys.getMarkupPercentage()*o.getDueToRestaurant() - o.getDiscountDueToCards();
				N += 1;
			}
		}
		double serviceFee = Math.floor((sys.getDeliveryCost() + (targetProfit - sum)/N)*100)/100;
		
		sys.setServiceFee(serviceFee);
	}
	
}
