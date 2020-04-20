package smPizzaModel;

import java.util.ArrayList;
import java.util.Queue;
//import com.sun.jmx.remote.util.OrderClassLoaders;

public class Techphone {

	protected int n;
	protected ArrayList<Pizza> list;
	
	protected Queue<Order> order;
	
	protected void addOrder(Order order) {
		
	}
	protected Order getOrder() {
		return order.poll();
	}
}
