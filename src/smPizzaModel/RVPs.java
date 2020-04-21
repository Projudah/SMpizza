package smPizzaModel;

import cern.jet.random.Exponential;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import cern.jet.random.Uniform;
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
	private final EmpiricalWalker pizzaSizeDM;
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
			   System.out.println("tankerSizeDM returned invalid value");
		   	   size = Size.SMALL;		   	
		}
		return(size);
	}

	protected double uDoughSaucingTime(final Size size){
		double srvTm = 0;
		if(size == Size.LARGE){
			srvTm = triangularDistribution(0.5, 0.7, 0.8);
		}else if(size == Size.MEDIUM){
			srvTm = triangularDistribution(0.4, 0.6, 0.8);
		}else if(size == Size.SMALL){
			srvTm = triangularDistribution(0.3, 0.5, 0.7);
		}else{
			System.out.println("rvpuSrvTm - invalid type "+size);
		}		
		return(srvTm);
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
