package smPizzaModel;

import simulationModelling.SequelActivity;


public class Baking extends SequelActivity {

	static SMPizza model;
	Pizza pizza;
	
	public void startingEvent()
	{
		/* Empty activity*/	
	}
	
	
	public double duration()
	{
		return Constants.BAKE_TIME;
	}
	
	
	public void terminatingEvent()
	{
		model.qUnloadArea.add(this.pizza);

	}
	
	
	public void spStartSequel(Pizza pizza) {
	}

}
