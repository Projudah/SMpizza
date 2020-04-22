package smPizzaModel;

import simulationModelling.ConditionalActivity;

public class Loading extends ConditionalActivity{

	static SMPizza model;
	Baking baking;
	Pizza pizza;
	
	public static boolean precondition()
	{
		return model.udp.CanLoadPizza();
	}
	
	public void startingEvent()
	{
		this.pizza = model.udp.GetNextPizza();
		model.rgLoadArea.usedSpace += this.pizza.size.getValue();		
	}
	
	public double duration()
	{
		return Constants.LOAD_TIME;
	}
	
	public void terminatingEvent()
	{
		Baking baking = new Baking(this.pizza);
		model.spStart(baking);

	}
}
