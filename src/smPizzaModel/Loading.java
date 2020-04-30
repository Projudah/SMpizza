package smPizzaModel;

import simulationModelling.ConditionalActivity;

public class Loading extends ConditionalActivity{

	static SMPizza model;
	Baking baking;
	Pizza iCPizza;
	
	public static boolean precondition()
	{
		return udpCanLoadPizza();
	}
	
	public void startingEvent()
	{
		this.iCPizza = udpGetNextPizza();
		model.rgLoadArea.usedSpace += this.iCPizza.size;
		System.out.println("Remaining space: "+(model.rgLoadArea.size-model.rgLoadArea.usedSpace));	
	}
	
	public double duration()
	{
		return Constants.LOAD_TIME;
	}
	
	public void terminatingEvent()
	{	
		Baking baking = new Baking(this.iCPizza);
		model.spStart(baking);
	}
	
	//UDP
	protected static boolean udpCanLoadPizza(){
		double freeSpace = model.rgLoadArea.size - model.rgLoadArea.usedSpace;
		for(Pizza pizza : model.qSlide){
			if(pizza.size <= freeSpace) {
				return true;
			}
		}
		return false;
	}
		
	protected Pizza udpGetNextPizza(){
		double freeSpace = model.rgLoadArea.size - model.rgLoadArea.usedSpace;
		for(Pizza pizza : model.qSlide){
			if(pizza.size <= freeSpace){
				model.qSlide.remove(pizza);
				return pizza;
			}
		}
		return null;
	}
	
}
