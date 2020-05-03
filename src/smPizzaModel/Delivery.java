package smPizzaModel;

import smPizzaModel.Output;
import simulationModelling.ConditionalActivity;

import static smPizzaModel.RVPs.triangularDistribution;

public class Delivery extends ConditionalActivity {

	static SMPizza model;
	private Order iCOrder;
	private double deliveryTime;

	protected static boolean precondition() {
		return (model.qDeliveryArea.size() > 0
				&& model.rgDeliveryDrivers.numBusy < model.rgDeliveryDrivers.totalNumber);
	}

	public void startingEvent() {
		model.rgDeliveryDrivers.numBusy++;
		iCOrder = model.qDeliveryArea.remove(0);
		deliveryTime = rvpuDeliveryTime();
	}

	protected double duration() {
		model.print("delivery duration" + deliveryTime);
		return deliveryTime;
	}

	protected void terminatingEvent() {
		model.print("ENDING DELIVERY " + iCOrder.startTime);
		model.print(model.getClock() - iCOrder.startTime);
		model.print("____________________________________----Increasing num orders from " + model.output.ssovNumOrders);
		model.output.ssovNumOrders++;
		if (model.getClock() - iCOrder.startTime <= Constants.DELIV_SATIS) {
			model.output.ssovNumOrdersSatisfied++;
		}
		model.output.ssovPropOrdersSatisfied = (double) model.output.ssovNumOrdersSatisfied / model.output.ssovNumOrders;

		// startSequel
		ReturnShop returnAct = new ReturnShop(deliveryTime);
		model.spStart(returnAct);
	}

	/* RVP */
	protected double rvpuDeliveryTime() {
		return triangularDistribution(3, 5, 12);
	}

}
