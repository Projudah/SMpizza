package smPizzaModel;

import cern.jet.random.Exponential;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import cern.jet.random.Uniform;
import cern.jet.random.Normal;
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

	final static double PERCAR = 0.6;
	final static double PERDEL = 0.4;

	final static double PER1 = 0.64;
	final static double PER2 = 0.31;
	final static double PER3 = 0.5;


	final static double BOXMEAN = 3.41;
	final static double BOXSDEV = 1.07;

	final static double MINPEAKLENGTH = 15, MAXPEAKLENGTH = 20, MINPEAKORDERS=20, 
	MAXPEAKORDERS=60, MINOFFPEAKORDERS = 5, MAXOFFPEAKORDERS = 20, PEAKSTARTMEAN=90.5, PEAKSTARTSDEV=52.1;

	private final double [] sizePdf = { PERSML, PERMED, PERLRG }; // for creating discrete PDF
	private final double [] orderSizePdf = { PERDEL, PERCAR }; // for creating discrete PDF
	private final double [] pizzaNumPdf = { PER1, PER2, PER3 }; // for creating discrete PDF

	private final EmpiricalWalker pizzaSizeDM, orderTypeDM, pizzaNumDM;
	private final Uniform triangularDist, lengthOfPeak, peakOrdersPerHour, offPeakOrdersPerHour ;
	private final Normal boxingDist, peakStartTime;

	/* Random Variate Procedure for Arrivals */
	private final Exponential peakinterArrDist, offpeakinterArrDist;  // Exponential distribution for interarrival times

	double p_length, peak_start_time;


	// Constructor
	protected RVPs(final Seeds sd) 
	{ 
	// Set up distribution functions
		lengthOfPeak = new Uniform(MINPEAKLENGTH, MAXPEAKLENGTH, sd.arr);
		peakOrdersPerHour = new Uniform(MINPEAKORDERS, MAXPEAKORDERS, sd.arr);
		offPeakOrdersPerHour = new Uniform(MINOFFPEAKORDERS, MAXOFFPEAKORDERS, sd.arr);
		peakStartTime = new Normal(PEAKSTARTMEAN, PEAKSTARTSDEV, new MersenneTwister(sd.arr));

		double peak_per_hour = peakOrdersPerHour.nextDouble();
		double off_peak_per_hour = offPeakOrdersPerHour.nextDouble();

		p_length = lengthOfPeak.nextDouble();
		peak_start_time = peakStartTime.nextDouble();

		double pMEAN = 1/peak_per_hour;
		double oMEAN = 1/off_peak_per_hour;

		peakinterArrDist = new Exponential(1.0/pMEAN, new MersenneTwister(sd.arr));
		offpeakinterArrDist = new Exponential(1.0/oMEAN, new MersenneTwister(sd.arr));

		pizzaSizeDM = new EmpiricalWalker(sizePdf, Empirical.NO_INTERPOLATION, new MersenneTwister(sd.sp));
		orderTypeDM = new EmpiricalWalker(orderSizePdf, Empirical.NO_INTERPOLATION, new MersenneTwister(sd.ot));
		pizzaNumDM = new EmpiricalWalker(pizzaNumPdf, Empirical.NO_INTERPOLATION, new MersenneTwister(sd.np));

		triangularDist = new Uniform(new MersenneTwister(sd.tri));
		boxingDist = new Normal(BOXMEAN, BOXSDEV, new MersenneTwister(sd.cb));
	}
	
	protected double DuOrder()  // for getting next value of DuOrder
	{
		double nxtInterArr;
		
		if( peak_start_time <= model.getClock() && model.getClock() <= peak_start_time + p_length){
			return peakinterArrDist.nextDouble() + model.getClock();
		}
	    return offpeakinterArrDist.nextDouble() + model.getClock();
	}

	protected double BoxCuttingTime(){
		return boxingDist.nextDouble();
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

	protected double uDeliveryTime(){
		return triangularDistribution(3, 5, 12);
	}

	protected int NumberOfPizzas()
	{
		int num = pizzaNumDM.nextInt() + 1;	
		return(num);
	}

	protected double uDoughSaucingTime(Size size){
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

	protected double uPrimaryIngrTime(Size size){
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

	protected double uFinalIngrTime(Size size){
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
