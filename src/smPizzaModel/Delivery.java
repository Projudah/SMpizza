package smPizzaModel;

import smPizzaModel.Output;
import simulationModelling.ConditionalActivity;

public class Delivery extends ConditionalActivity {
	
		static SMPizza model;
		private Order iCOrder;
		private double deliveryTime;
		
		protected static boolean precondition()
		{
			return (model.qDeliveryArea.size() > 0 && model.rgDeliveryDrivers.numBusy < model.rgDeliveryDrivers.totalNumber);
		}

		public void startingEvent() 
		{
			model.rgDeliveryDrivers.numBusy++;
			iCOrder = model.qDeliveryArea.remove(0);
			deliveryTime = model.rvp.uDeliveryTime();
		}

		protected double duration() 
		{
			return deliveryTime;
		}

		protected void terminatingEvent() 
		{
			model.output.numOrders++;
			if(model.getClock()-iCOrder.startTime <= Constants.DELIV_SATIS){
				model.output.numOrdersSatisfied++;	 
		    }
			model.output.propOrdersSatisfied = model.output.numOrders/model.output.numOrdersSatisfied;
		    
		    //startSequel
		    ReturnShop returnAct = new ReturnShop(deliveryTime);
			model.spStart(returnAct);
		}

}
