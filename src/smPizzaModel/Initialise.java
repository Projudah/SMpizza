package smPizzaModel;

import java.util.ArrayList;

import simulationModelling.ScheduledAction;

class Initialise extends ScheduledAction {

	static SMPizza model;

	// Variable
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
		// Set all queue in model to zero (by making arrayList to clear all the items in
		// them)
		model.qTechphone.clear(); // empties the telephone queue
		model.qSlide.clear(); // empties the slide queue
		model.qDeliveryArea.clear();
		model.qUnloadArea.clear();
		// Set number of workers on makeTable to zero (initially no one is working)
		model.rqMakeTable.numBusy = 0;

		// no pizzas in load area
		model.rgLoadArea.usedSpace = 0;

		for (int pos = MakeTable.POS1; pos <= MakeTable.POS5; pos++) {
			model.rqMakeTable.position[pos] = MakeTable.NO_PIZZA;
		}
		model.rqMakeTable.doughSaucing = false;
		model.rqMakeTable.addingPrimIngr = false;

		model.output.ssovNumOrders = 0;
		model.output.ssovNumOrdersSatisfied = 0;
		model.rgDeliveryDrivers.numBusy = 0;

	}

}
