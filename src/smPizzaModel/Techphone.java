package smPizzaModel;

import java.util.ArrayList;
import java.util.Queue;
//import com.sun.jmx.remote.util.OrderClassLoaders;

public class Techphone {

	protected int n;
	protected ArrayList<Pizza> list;
	
	protected Queue<Orders> orders;
	
	protected void addOrder(Orders order) {
		
	}
	protected Orders getOrder() {
		return orders.poll();
	}
}
