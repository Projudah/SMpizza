package smPizzaModel;


import simulationModelling.SequelActivity;


public class ReturnShop extends SequelActivity {
	SMPizza model;
	private double deliveryTime;
    
	public ReturnShop(SMPizza m, double dTime) {
		this.model = m;
		this.deliveryTime = dTime;
	}
	
	
	protected double timeSequence() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void startingEvent() 
	{
		
	}
	protected double duration() 
	{
		//double dur = model.rvp.uDeliveryTime();
		return deliveryTime;
	}

	protected void terminatingEvent() 
	{
		this.model.rgDeliveryDrivers.numBusy--;
	}

	
}
