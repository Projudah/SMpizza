package smPizzaModel;

import simulationModelling.ConditionalActivity;
import smPizzaModel.Orders.Type;

public class CutBoxing extends ConditionalActivity{

	static SMPizza model;
	Baking baking;
	Pizza pizza;
	Orders order;
	
	public static boolean precondition(SMPizza model)
	{
		boolean retVal = false;
		if(model.rCutBoxEmp.isBusy == false )
		{
			retVal = true;
		}
		return(retVal);
		
	}
	
	public void startingEvent()
	{
		model.rCutBoxEmp.isBusy = true;
	    this.pizza = model.qUnloadArea.spRemoveQue();
	    this.order = this.pizza.associatedOrder;
	}
	
	public double duration()
	{
		return RVP.BoxCuttingTime();
	}
	
	public void terminatingEvent()
	{
		this.order.uNumPizzasCompleted += 1;
		this.pizza.spleave();
		if(this.order.uNumPizzasCompleted == this.order.uNumPizzas)
		{
			
			if(this.order.uType == Type.D)
			{
				model.qDeliveryArea.spInsertQue(this.order);
				model.qDeliveryArea.n++;
			}
			
			else
			{
				model.output.numOrders++;
				if((model.getClock() - this.order.startTime) <=  Constants.TAKE_OUT_SATIS)
				{
					model.output.numOrdersSatisfied++;
				}
				
				 model.output.propOrdersSatisfied = model.output.numOrdersSatisfied/model.output.numOrders;
				 this.order.spleave();
			}
		}

		model.rCutBoxEmp.isBusy = false;
	}
	
        
}
