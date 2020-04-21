package smPizzaModel;

import smPizzaModel.Output;
import simulationModelling.ConditionalActivity;

public class Delivery extends ConditionalActivity {
	
		static SMPizza model;
		private Order iCOrder;
		private double deliveryTime;
		
		protected static boolean precondition()
		{
			return (model.qDeliveryArea.size() >0 && model.rgDeliveryDrivers.numBusy<model.rgDeliveryDrivers.totalNumber);
		}

		public void startingEvent() 
		{
			model.rgDeliveryDrivers.numBusy++;
			iCOrder = model.qDeliveryArea.get(0);
			deliveryTime =duration();
		}

		protected double duration() 
		{
			double dur = model.rvp.uDeliveryTime();
			return dur;
		}

		protected void terminatingEvent() 
		{
			Output output = model.output; // Create local reference to output object
			output.numOrders++;
			if(model.getClock()-iCOrder.startTime <= 45){
		         output.numOrdersSatisfied++;	 
		        }
		    output.propOrdersSatisfied = (double)output.numOrders/output.numOrdersSatisfied;
		    model.qDeliveryArea.remove(0);
		    
		    //startSequel
		    ReturnShop returnAct = new ReturnShop(model, deliveryTime);
			model.spStart(returnAct);
		}

}
