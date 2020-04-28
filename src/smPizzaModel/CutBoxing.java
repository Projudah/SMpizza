package smPizzaModel;

import cern.jet.random.Normal;
import cern.jet.random.engine.MersenneTwister;
import simulationModelling.ConditionalActivity;
import smPizzaModel.Order.Type;

public class CutBoxing extends ConditionalActivity{

	static SMPizza model;
	Pizza pizza;
	Order order;
	/************  Implementation of User Modules ***********/
	final static double BOXMEAN = 3.41;
	final static double BOXSDEV = 1.07;
	public static Normal boxingDist;
	
	public static boolean precondition()
	{
		return !model.rCutBoxEmp.isBusy && !model.qUnloadArea.isEmpty();
	}
	
	public void startingEvent()
	{
		model.rCutBoxEmp.isBusy = true;
	    this.pizza = model.qUnloadArea.remove(0);
		this.order = this.pizza.associatedOrder;
		System.out.println(this.order.startTime+"___________________STARTING CUTBOX FOR "+(this.order.startTime));

	}
	
	public double duration()
	{
		return rvpBoxCuttingTime();
	}

	public void terminatingEvent(){
		System.out.println(this.order.startTime+"___________________ENDING CUTBOX "+(model.getClock() - this.order.startTime));
		order.uNumPizzasCompleted++;
		// this.pizza.spleave(); don't need to have any SP
		System.out.println(Integer.toString(order.uNumPizzasCompleted) +"/"+ Integer.toString(order.uNumPizzas));
		if(order.uNumPizzasCompleted >= order.uNumPizzas)
		{
			System.out.println(this.order.uType+"-----------------------------------------"+this.order.startTime);
			if(this.order.uType == Type.DELIVERY)
			{
				model.qDeliveryArea.add(this.order);
			}
			else
			{
				System.out.println("____________________________________----Increasing num orders from "+model.output.numOrders);
				model.output.numOrders++;
				if((model.getClock() - this.order.startTime) <=  Constants.TAKE_OUT_SATIS)
				{
					model.output.numOrdersSatisfied++;
				}
				
				model.output.propOrdersSatisfied = (double) model.output.numOrdersSatisfied/model.output.numOrders;
			}
		}

		model.rCutBoxEmp.isBusy = false;
	}
	//	RVP
	// Initialise the RVP
	static void initRvps(Seeds sd) {
		boxingDist = new Normal(BOXMEAN, BOXSDEV, new MersenneTwister(sd.cb));
	}

	protected double rvpBoxCuttingTime(){
		double time = boxingDist.nextDouble();
		System.out.println("____________________CUT AND BOX TIME "+time);
		return time;
	}
}
