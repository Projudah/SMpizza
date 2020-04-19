package smPizzaModel;

import java.util.Queue;

import com.sun.jmx.remote.util.OrderClassLoaders;

public class Telephone {
	
	protected Queue<Order> orders;
	
	protected void addOrder(Order order) {
		
	}
	protected Order getOrder() {
		return orders.poll();
	}

}
