package smPizzaModel;

import cern.jet.random.Normal;
import cern.jet.random.engine.MersenneTwister;
import simulationModelling.ConditionalActivity;
import smPizzaModel.Order.Type;

public class CutBoxing extends ConditionalActivity{

	static SMPizza model;
	Pizza iCPizza;
	Order iCOrder;
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
	    this.iCPizza = model.qUnloadArea.remove(0);
		this.iCOrder = this.iCPizza.associatedOrder;
		System.out.println(this.iCOrder.startTime+"___________________STARTING CUTBOX FOR "+(this.iCOrder.startTime));

	}
	
	public double duration()
	{
		return rvpBoxCuttingTime();
	}

	public void terminatingEvent(){
		System.out.println(this.iCOrder.startTime+"___________________ENDING CUTBOX "+(model.getClock() - this.iCOrder.startTime));
		iCOrder.uNumPizzasCompleted++;
		// this.pizza.spleave(); don't need to have any SP
		System.out.println(Integer.toString(iCOrder.uNumPizzasCompleted) +"/"+ Integer.toString(iCOrder.uNumPizzas));
		if(iCOrder.uNumPizzasCompleted >= iCOrder.uNumPizzas)
		{
			System.out.println(this.iCOrder.uType+"-----------------------------------------"+this.iCOrder.startTime);
			if(this.iCOrder.uType == Type.DELIVERY)
			{
				model.qDeliveryArea.add(this.iCOrder);
			}
			else
			{
				System.out.println("____________________________________----Increasing num orders from "+model.output.ssovNumOrders);
				model.output.ssovNumOrders++;
				if((model.getClock() - this.iCOrder.startTime) <=  Constants.TAKE_OUT_SATIS)
				{
					model.output.ssovNumOrdersSatisfied++;
				}
				
				model.output.ssovPropOrdersSatisfied = (double) model.output.ssovNumOrdersSatisfied/model.output.ssovNumOrders;
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
