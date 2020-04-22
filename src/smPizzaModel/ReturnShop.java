package smPizzaModel;


import simulationModelling.SequelActivity;


public class ReturnShop extends SequelActivity {
	static SMPizza model;
	private double deliveryTime;
    
	public ReturnShop(double dTime) {
		this.deliveryTime = dTime;
	}
	
	public void startingEvent() 
	{
		
	}
	protected double duration() 
	{
		return deliveryTime;
	}

	protected void terminatingEvent() 
	{
		model.rgDeliveryDrivers.numBusy--;
	}

	
}
