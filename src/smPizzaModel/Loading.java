package smPizzaModel;

import simulationModelling.ConditionalActivity;

public class Loading extends ConditionalActivity{

	static SMPizza model;
	Baking baking;
	Pizza pizza;
	
	public static boolean precondition(SMPizza model)
	{
		boolean retVal = false;
		if(model.udp.CanLoadPizza() == true)
		{
			retVal = true;
		}
		return(retVal);
		
	}
	
	public void startingEvent()
	{
		this.pizza = (Pizza) model.udp.GetNextPizza();
		model.rgLoadArea.usedSpace = model.rgLoadArea.usedSpace + this.pizza.size.getValue();		
	}
	
	public double duration()
	{
		return Constants.LOAD_TIME;
	}
	
	public void terminatingEvent()
	{
		this.baking.spStartSequel(this.pizza);

	}
}
