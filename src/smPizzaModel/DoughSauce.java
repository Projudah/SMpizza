package smPizzaModel;

import simulationModelling.ConditionalActivity;

public class DoughSauce extends ConditionalActivity 
{
	static SMPizza model;
	Pizza iCPizza;
	
	protected static boolean precondition()
	{
		return model.udp.CanStartDoughSaucing(); //come finish
	}

	public void startingEvent() 
	{
		Order iCOrder = model.qTechphone.get(0);
        iCPizza = new Pizza();
        iCPizza.associatedOrder = iCOrder;
		iCPizza.size = model.rvp.SizeOfPizza();
        model.rqMakeTable.numBusy++;
        iCOrder.uNumPizzasStarted++;

        if(iCOrder.uNumPizzasStarted >=iCOrder.uNumPizzasCompleted){
            model.qTechphone.remove(0);
        }
        model.rqMakeTable.position[MakeTable.POS1] = iCPizza;
	}

	protected double duration() 
	{
		return model.rvp.uDoughSaucingTime(iCPizza.size);
	}

	protected void terminatingEvent() 
	{
		model.rqMakeTable.numBusy--;
	}

}
