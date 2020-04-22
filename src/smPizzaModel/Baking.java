package smPizzaModel;

import simulationModelling.SequelActivity;


public class Baking extends SequelActivity {

	static SMPizza model;
	Pizza pizza;

	public Baking(Pizza pizza){
		this.pizza = pizza;
	}
	
	public void startingEvent()
	{
		model.rgLoadArea.usedSpace -= this.pizza.size.getValue();
	}
	
	
	public double duration()
	{
		return Constants.BAKE_TIME;
	}
	
	
	public void terminatingEvent()
	{
		model.qUnloadArea.add(this.pizza);
	}
	

}
