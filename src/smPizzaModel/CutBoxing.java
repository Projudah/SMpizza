package smPizzaModel;

import cern.jet.random.Normal;
import cern.jet.random.engine.MersenneTwister;
import simulationModelling.ConditionalActivity;
import smPizzaModel.Order.Type;

public class CutBoxing extends ConditionalActivity {

	static SMPizza model;
	Pizza iCPizza;
	Order order;
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
		this.order = this.iCPizza.associatedOrder;
		model.print("___________________STARTING CUTBOX FOR " + (this.order.startTime));

	}

	public double duration() {
		return rvpBoxCuttingTime();
	}

	public void terminatingEvent() {
		model.print("___________________ENDING CUTBOX FOR " + (this.order.startTime));
		order.uNumPizzasCompleted++;
		// this.pizza.spleave(); don't need to have any SP
		model.print((this.order.startTime) + " Order completion: " + Integer.toString(order.uNumPizzasCompleted) + "/"
				+ Integer.toString(order.uNumPizzas));
		if (order.uNumPizzasCompleted >= order.uNumPizzas) {
			model.print(this.order.uType + "-----------------------------------------" + this.order.startTime);
			if (this.order.uType == Type.DELIVERY) {
				model.qDeliveryArea.add(this.order);
			} else {
				model.print("____________________________________----Increasing num orders from "
						+ model.output.ssovNumOrders);
				model.output.ssovNumOrders++;
				if ((model.getClock() - this.order.startTime) <= Constants.TAKE_OUT_SATIS) {
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
