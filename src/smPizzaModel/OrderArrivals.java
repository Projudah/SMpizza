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
	     Order iCOrder = new Order();
         iCOrder.uType = model.rvp.uOrderType();
         iCOrder.startTime = model.getClock();
         iCOrder.numPizzas = model.rvp.NumberOfPizzas();
	     model.qTechphone.add(iCOrder);
	}


}