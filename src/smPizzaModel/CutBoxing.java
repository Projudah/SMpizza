package smPizzaModel;

import cern.jet.random.Normal;
import cern.jet.random.engine.MersenneTwister;
import simulationModelling.ConditionalActivity;
import smPizzaModel.Order.Type;

public class CutBoxing extends ConditionalActivity {

	static SMPizza model;
	Pizza iCPizza;
	Order iCOrder;
	/************ Implementation of User Modules ***********/
	final static double BOXMEAN = 0.341;
	final static double BOXSDEV = 0.107;
	public static Normal boxingDist;

	public static boolean precondition() {
		return !model.rCutBoxEmp.isBusy && !model.qUnloadArea.isEmpty();
	}

	public void startingEvent() {
		model.rCutBoxEmp.isBusy = true;
		this.iCPizza = model.qUnloadArea.remove(0);
		this.iCOrder = this.iCPizza.associatedOrder;
		model.print("___________________STARTING CUTBOX FOR " + (this.iCOrder.startTime));

	}

	public double duration() {
		return rvpBoxCuttingTime();
	}

	public void terminatingEvent() {
		model.print("___________________ENDING CUTBOX FOR " + (this.iCOrder.startTime));
		iCOrder.uNumPizzasCompleted++;
		// this.pizza.spleave(); don't need to have any SP
		model.print((this.iCOrder.startTime) + " Order completion: " + Integer.toString(iCOrder.uNumPizzasCompleted) + "/"
				+ Integer.toString(iCOrder.uNumPizzas));
		if (iCOrder.uNumPizzasCompleted >= iCOrder.uNumPizzas) {
			model.print(this.iCOrder.uType + "-----------------------------------------" + this.iCOrder.startTime);
			if (this.iCOrder.uType == Type.DELIVERY) {
				model.qDeliveryArea.add(this.iCOrder);
			} else {
				model.print("____________________________________----Increasing num iCOrders from "
						+ model.output.ssovNumOrders);
				model.output.ssovNumOrders++;
				if ((model.getClock() - this.iCOrder.startTime) <= Constants.TAKE_OUT_SATIS) {
					model.output.ssovNumOrdersSatisfied++;
				}

				model.output.ssovPropOrdersSatisfied = (double) model.output.ssovNumOrdersSatisfied
						/ model.output.ssovNumOrders;
			}
		}

		model.rCutBoxEmp.isBusy = false;
	}

	// RVP
	// Initialise the RVP
	static void initRvps(Seeds sd) {
		boxingDist = new Normal(BOXMEAN, BOXSDEV, new MersenneTwister(sd.cb));
	}

	protected double rvpBoxCuttingTime() {
		double time = boxingDist.nextDouble();
		model.print("____________________CUT AND BOX TIME " + time);
		return time;
	}
}
