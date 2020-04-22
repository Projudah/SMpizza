package smPizzaModel;

import simulationModelling.ScheduledAction;

class OrderArrivals extends ScheduledAction 
{
	static SMPizza model;  // reference to model object

	public double timeSequence() 
	{
		return model.rvp.DuOrder();
	}

	public void actionEvent() 
	{
		// WArrival Action Sequence SCS
		System.out.println("Incomiung order");
	     Order iCOrder = new Order();
         iCOrder.uType = model.rvp.uOrderType();
         iCOrder.startTime = model.getClock();
		 iCOrder.uNumPizzas = model.rvp.NumberOfPizzas();
		 iCOrder.uNumPizzasStarted = 0;
		 iCOrder.uNumPizzasCompleted = 0;
	     model.qTechphone.add(iCOrder);
	}


}