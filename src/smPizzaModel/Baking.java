package smPizzaModel;

import simulationModelling.SequelActivity;


public class Baking extends SequelActivity {

	static SMPizza model;
	Pizza iCpizza;

	public Baking(Pizza pizza){
		this.iCpizza = pizza;
	}
	
	public void startingEvent()
	{
		model.rgLoadArea.usedSpace -= this.iCpizza.size;
	}
	
	
	public double duration()
	{
		return Constants.BAKE_TIME;
	}
	
	
	public void terminatingEvent()
	{
		model.qUnloadArea.add(this.iCpizza);
	}
	

}
