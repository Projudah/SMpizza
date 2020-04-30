package smPizzaModel;

import smPizzaModel.Output;
import simulationModelling.ConditionalActivity;

import static smPizzaModel.RVPs.triangularDistribution;

public class Delivery extends ConditionalActivity {
	
		static SMPizza model;
		private Order iCOrder;
		private double deliveryTime;
		
		protected static boolean precondition() {
			return (model.qDeliveryArea.size() > 0 && model.rgDeliveryDrivers.numBusy < model.rgDeliveryDrivers.totalNumber);
		}

		public void startingEvent() {
			model.rgDeliveryDrivers.numBusy++;
			iCOrder = model.qDeliveryArea.remove(0);
			deliveryTime = rvpuDeliveryTime();
		}

		protected double duration() {
			model.print("delivery duration"+ deliveryTime);
			return deliveryTime;
		}

		protected void terminatingEvent() {
			model.print("ENDING DELIVERY "+iCOrder.startTime);
			model.print(model.getClock()-iCOrder.startTime);
			model.print("____________________________________----Increasing num orders from "+model.output.numOrders);
			model.output.numOrders++;
			if(model.getClock()-iCOrder.startTime <= Constants.DELIV_SATIS){
				model.output.numOrdersSatisfied++;	 
		    }
			model.output.propOrdersSatisfied = (double) model.output.numOrdersSatisfied/model.output.numOrders;
		    
		    //startSequel
		    ReturnShop returnAct = new ReturnShop(deliveryTime);
			model.spStart(returnAct);
		}

		/* RVP */
		protected double rvpuDeliveryTime(){
			return triangularDistribution(3, 5, 12);
		}

}
