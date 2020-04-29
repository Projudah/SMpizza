package smPizzaModel;

import simulationModelling.SequelActivity;


public class Baking extends SequelActivity {

	static SMPizza model;
	Pizza iCPizza;

	public Baking(Pizza pizza){
		this.iCPizza = pizza;
	}
	
	public void startingEvent()
	{
		model.rgLoadArea.usedSpace -= this.iCPizza.size.getValue();
	}
	
	
	public double duration()
	{
		return Constants.BAKE_TIME;
	}
	
	
	public void terminatingEvent()
	{
		model.qUnloadArea.add(this.iCPizza);
	}
	

}
