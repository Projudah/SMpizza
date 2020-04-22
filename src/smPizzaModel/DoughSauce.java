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
		// System.out.println(Integer.toString(iCOrder.uNumPizzasStarted) +"/"+ Integer.toString(iCOrder.uNumPizzas));
        if(iCOrder.uNumPizzasStarted >=iCOrder.uNumPizzas){
            model.qTechphone.remove(0);
        }
	}

	protected double duration() 
	{
		return model.rvp.uDoughSaucingTime(iCPizza.size);
	}

	protected void terminatingEvent() 
	{
		model.rqMakeTable.position[MakeTable.POS1] = iCPizza;
		model.rqMakeTable.numBusy--;
	}

}
