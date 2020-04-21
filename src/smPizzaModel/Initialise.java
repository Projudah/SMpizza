package smPizzaModel;

import java.util.ArrayList;

import simulationModelling.ScheduledAction;

class Initialise extends ScheduledAction{
	
	static SMPizza model;
	
	 //Variable
	double[] ts = { 0.0, -1.0 };
	int tsix = 0;
	
	
	// Constructor
	// public Initialise(SMPizza model) { this.model = model; }

	@Override
	public double timeSequence() {
			// Called at t = 0
			return ts[tsix++];
	}

	@Override
	public void actionEvent() {
		
	   	// Set number of workers on makeTable to zero (initially no one is working)
		model.rqMakeTable.numBusy = 0;
		
		// no pizzas in load area
		model.rgLoadArea.usedSpace = 0;
		
		// no pizzas in unload area
		model.qUnloadArea.n = 0;
		
		// Set delivery area
	    model.qDeliveryArea.n = 0;
	    
	    //no calls
	    // model.qTechphone.n = 0;

	    model.output.numOrders = 0;
	    model.output.numOrdersSatisfied = 0;
	    

	    /*for(i=pos;i<rq.MakeTable.position;i++)
	    {
	    	 pos=none;
	    }*/
		
	}

}
