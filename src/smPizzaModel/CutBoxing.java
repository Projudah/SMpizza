package smPizzaModel;

import simulationModelling.ConditionalActivity;
import smPizzaModel.Order.Type;

public class CutBoxing extends ConditionalActivity{

	static SMPizza model;
	Pizza pizza;
	Order order;
	
	public static boolean precondition()
	{
		return !model.rCutBoxEmp.isBusy;
	}
	
	public void startingEvent()
	{
		model.rCutBoxEmp.isBusy = true;
	    this.pizza = model.qUnloadArea.remove(0);
	    this.order = this.pizza.associatedOrder;
	}
	
	public double duration()
	{
		return model.rvp.BoxCuttingTime();
	}
	
	public void terminatingEvent(){
		this.order.uNumPizzasCompleted += 1;
		// this.pizza.spleave(); don't need to have any SP
		if(this.order.uNumPizzasCompleted >= this.order.uNumPizzas)
		{
			
			if(this.order.uType == Type.DELIVERY)
			{
				model.qDeliveryArea.add(this.order);
			}
			else
			{
				model.output.numOrders++;
				if((model.getClock() - this.order.startTime) <=  Constants.TAKE_OUT_SATIS)
				{
					model.output.numOrdersSatisfied++;
				}
				
				model.output.propOrdersSatisfied = model.output.numOrdersSatisfied/model.output.numOrders;
			}
		}

		model.rCutBoxEmp.isBusy = false;
	}
	
        
}
