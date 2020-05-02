package smPizzaModel;

import java.util.Arrays;

import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import cern.jet.random.engine.MersenneTwister;
import simulationModelling.ConditionalActivity;

public class DoughSauce extends ConditionalActivity 
{
	static SMPizza model;
	Pizza iCPizza;
	
	protected static boolean precondition()
	{
		return udpCanStartDoughSaucing(); //come finish
	}

	public void startingEvent() 
	{
		Order iCOrder = model.qTechphone.get(0);
        iCPizza = new Pizza();
        iCPizza.associatedOrder = iCOrder;
		iCPizza.size = rvpSizeOfPizza();
        model.rqMakeTable.numBusy++;
        iCOrder.uNumPizzasStarted++;
		// model.print(Integer.toString(iCOrder.uNumPizzasStarted) +"/"+ Integer.toString(iCOrder.uNumPizzas));
        if(iCOrder.uNumPizzasStarted >=iCOrder.uNumPizzas){
            model.qTechphone.remove(0);
		}
		model.rqMakeTable.position[MakeTable.POS1] = iCPizza;
		model.rqMakeTable.positionBusy[MakeTable.POS1] = true;
	}

	protected double duration() 
	{
		return rvpuDoughSaucingTime(iCPizza.size);
	}

	protected void terminatingEvent() 
	{
		model.rqMakeTable.positionBusy[MakeTable.POS1] = false;
		model.rqMakeTable.numBusy--;
	}

	//UDP
	protected static boolean udpCanStartDoughSaucing(){
		if((model.rqMakeTable.position[MakeTable.POS1] != MakeTable.NO_PIZZA) ||
				(PrimaryIngre.udpCanStartPrimaryIngre() || FinalIngre.udpCanStartFinalIngre()) ||
				(model.rqMakeTable.numBusy >= model.rqMakeTable.numPersons) ||
				(model.qTechphone.isEmpty())){
			return false;
		}

		return true;
		}
	
	//RVP
	final static double PERSML = 0.12;
	final static double PERMED = 0.56;
	final static double PERLRG = 0.32;
	private static EmpiricalWalker pizzaSizeDM;
	final static double [] sizePdf = { PERSML, PERMED, PERLRG }; // for creating discrete PDF

	static void initRvps(Seeds sd) {
		pizzaSizeDM = new EmpiricalWalker(sizePdf, Empirical.NO_INTERPOLATION, new MersenneTwister(sd.sp));
	}

	protected double rvpuDoughSaucingTime(int size){
		double Tm = 0;
		if(size == Pizza.Size.LARGE.getValue()){
			Tm = RVPs.triangularDistribution(0.5, 0.7, 0.8);
		}else if(size == Pizza.Size.MEDIUM.getValue()){
			Tm = RVPs.triangularDistribution(0.4, 0.6, 0.8);
		}else if(size == Pizza.Size.SMALL.getValue()){
			Tm = RVPs.triangularDistribution(0.3, 0.5, 0.7);
		}else{
			model.print("uDoughSaucingTime - invalid type "+size);
		}
		return(Tm);
	}

	protected int rvpSizeOfPizza()
	{
		Pizza.Size size;
		switch(pizzaSizeDM.nextInt())
		{
			case 0: size = Pizza.Size.SMALL; break;
			case 1: size = Pizza.Size.MEDIUM; break;
			case 2: size = Pizza.Size.LARGE; break;
			default:
				model.print("SizeOfPizza returned invalid value");
				size = Pizza.Size.SMALL;
		}
		return(size.getValue());
	}
}
