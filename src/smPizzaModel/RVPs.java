package smPizzaModel;

import cern.jet.random.Exponential;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import cern.jet.random.Uniform;
import smPizzaModel.Order.Type;
import smPizzaModel.Pizza.Size;

class RVPs 
{
	static SMPizza model; // for accessing the clock
    // Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define 
	// reference variables here and create the objects in the
	// constructor with seeds

	final static double PERSML = 0.12;
	final static double PERMED = 0.56;
	final static double PERLRG = 0.32;
	private final double [] sizePdf = { PERSML, PERMED, PERLRG }; // for creating discrete PDF
	private final double [] orderSizePdf = { 0.4, 0.6 }; // for creating discrete PDF
	private final EmpiricalWalker pizzaSizeDM, orderTypeDM;
	private final Uniform triangularDist;

	/* Random Variate Procedure for Arrivals */
	private final Exponential interArrDist;  // Exponential distribution for interarrival times
	private final double WMEAN1=10.0;


	// Constructor
	protected RVPs(final Seeds sd) 
	{ 
		// Set up distribution functions
		interArrDist = new Exponential(1.0/WMEAN1, new MersenneTwister(sd.arr));
		pizzaSizeDM = new EmpiricalWalker(sizePdf, Empirical.NO_INTERPOLATION, new MersenneTwister(sd.sp));
		orderTypeDM = new EmpiricalWalker(sizePdf, Empirical.NO_INTERPOLATION, new MersenneTwister(sd.sp));
		triangularDist = new Uniform(new MersenneTwister(sd.tri));
	}
	
	protected double duInput()  // for getting next value of duInput
	{
	    double nxtInterArr;

        nxtInterArr = interArrDist.nextDouble();
	    // Note that interarrival time is added to current
	    // clock value to get the next arrival time.
	    return(nxtInterArr+model.getClock());
	}

	protected Size SizeOfPizza()
	{
		Size size;		
		switch(pizzaSizeDM.nextInt())
		{
		   case 0: size = Size.SMALL; break;
		   case 1: size = Size.MEDIUM; break;
		   case 2: size = Size.LARGE; break;
		   default: 
			   System.out.println("SizeOfPizza returned invalid value");
		   	   size = Size.SMALL;		   	
		}
		return(size);
	}

	protected Type uOrderType()
	{
		Type type;		
		switch(orderTypeDM.nextInt())
		{
		   case 0: type = Type.DELIVERY; break;
		   case 1: type = Type.CARRYOUT; break;
		   default: 
			   System.out.println("uOrderType returned invalid value");
			   type = Type.DELIVERY;		   	
		}
		return(type);
	}

	protected double uDoughSaucingTime(final Size size){
		double Tm = 0;
		if(size == Size.LARGE){
			Tm = triangularDistribution(0.5, 0.7, 0.8);
		}else if(size == Size.MEDIUM){
			Tm = triangularDistribution(0.4, 0.6, 0.8);
		}else if(size == Size.SMALL){
			Tm = triangularDistribution(0.3, 0.5, 0.7);
		}else{
			System.out.println("uDoughSaucingTime - invalid type "+size);
		}		
		return(Tm);
	}

	protected double uPrimaryIngrTime(final Size size){
		double Tm = 0;
		if(size == Size.LARGE){
			Tm = triangularDistribution(0.6, 0.8, 1);
		}else if(size == Size.MEDIUM){
			Tm = triangularDistribution(0.5, 0.7, 0.9);
		}else if(size == Size.SMALL){
			Tm = triangularDistribution(0.4, 0.5, 0.6);
		}else{
			System.out.println("uPrimaryIngrTime - invalid type "+size);
		}		
		return(Tm);
	}

	protected double uFinalIngrTime(final Size size){
		double Tm = 0;
		if(size == Size.LARGE){
			Tm = triangularDistribution(0.5, 0.6, 0.7);
		}else if(size == Size.MEDIUM){
			Tm = triangularDistribution(0.4, 0.5, 0.6);
		}else if(size == Size.SMALL){
			Tm = triangularDistribution(0.3, 0.4, 0.5);
		}else{
			System.out.println("uFinalIngrTime - invalid type "+size);
		}		
		return(Tm);
	}

	public double triangularDistribution(final double min, final double mode, final double max) {
		final double F = (max - min) / (mode - min);
		final double rand = triangularDist.nextDouble();
		if (rand < F) {
			return min + Math.sqrt(rand * (mode - min) * (max - min));
		} else {
			return mode - Math.sqrt((1 - rand) * (mode - min) * (mode - max));
		}
	}

}
