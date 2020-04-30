package smPizzaModel;

import cern.jet.random.*;
import cern.jet.random.engine.MersenneTwister;
import simulationModelling.ScheduledAction;

class OrderArrivals extends ScheduledAction 
{
	static SMPizza model;  // reference to model object

	public double timeSequence() { return rvpDuOrder(); }

	public void actionEvent()
	{
		// WArrival Action Sequence SCS
	     Order iCOrder = new Order();
         iCOrder.uType = rvpuOrderType();
         iCOrder.startTime = model.getClock();
		 iCOrder.uNumPizzas = rvpNumberOfPizzas();
		 iCOrder.uNumPizzasStarted = 0;
		 iCOrder.uNumPizzasCompleted = 0;
		 model.qTechphone.add(iCOrder);
		//  System.out.println("Incomiung order "+iCOrder.uNumPizzas);
	}

	// RVP
	/* Random Variate Procedure for Arrivals */
	static private final double MINPEAKLENGTH = 15, MAXPEAKLENGTH = 20, MINPEAKORDERS=20,
			MAXPEAKORDERS=60, MINOFFPEAKORDERS = 5, MAXOFFPEAKORDERS = 20, PEAKSTARTMEAN=90.5, PEAKSTARTSDEV=52.1;
	public static Uniform lengthOfPeak, peakOrdersPerHour, offPeakOrdersPerHour ;
	public static Normal  peakStartTime;
	public static Exponential interArrDist;  // Exponential distribution for interarrival times
	static double p_length, peak_start_time;
	static int tot;
	static double pMEAN;
	static  double oMEAN;

	/* Random Variate procedure for order Type */
	final static double PERCAR = 0.6;
	final static double PERDEL = 0.4;
	public static EmpiricalWalker orderTypeDM, pizzaNumDM;
	private static final double [] orderSizePdf = { PERDEL, PERCAR }; // for creating discrete PDF

	/* Random Variate procedure for number of pizzas in order*/
	final static double PER1 = 0.64;
	final static double PER2 = 0.31;
	final static double PER3 = 0.5;
	private static final double [] pizzaNumPdf = { PER1, PER2, PER3 }; // for creating discrete PDF

	// Initialise the RVP
	static void initRvps(Seeds sd) {
		// Set up distribution functions
		lengthOfPeak = new Uniform(MINPEAKLENGTH, MAXPEAKLENGTH, sd.arr);
		peakOrdersPerHour = new Uniform(MINPEAKORDERS, MAXPEAKORDERS, sd.arr);
		offPeakOrdersPerHour = new Uniform(MINOFFPEAKORDERS, MAXOFFPEAKORDERS, sd.arr);
		peakStartTime = new Normal(PEAKSTARTMEAN, PEAKSTARTSDEV, new MersenneTwister(sd.arr));

		double peak_per_hour = peakOrdersPerHour.nextDouble();
		double off_peak_per_hour = offPeakOrdersPerHour.nextDouble();

		p_length = lengthOfPeak.nextDouble();
		peak_start_time = peakStartTime.nextDouble();

		pMEAN = 60/peak_per_hour;
		oMEAN = 60/off_peak_per_hour;

		// Initialise Internal modules, user modules and input variables
		/* For order arrivals*/
		interArrDist = new Exponential(1.0/oMEAN, new MersenneTwister(sd.arr));
		/* For order type */
		orderTypeDM = new EmpiricalWalker(orderSizePdf, Empirical.NO_INTERPOLATION, new MersenneTwister(sd.ot));
		/* For number of pizzas in a order */
		pizzaNumDM = new EmpiricalWalker(pizzaNumPdf, Empirical.NO_INTERPOLATION, new MersenneTwister(sd.np));
	}

	/* RVP for DuOrder arrivals*/
	public double rvpDuOrder(){
		double nxtArrival;
		double mean;
		tot++;
		if(peak_start_time <= model.getClock() && model.getClock() <= peak_start_time + p_length){
			mean = pMEAN;
			// System.out.println("ITS PEAK TIME");
		}else{
			mean = oMEAN;
			// System.out.println("NONO PEAK TIME");
		}

		nxtArrival = model.getClock()+interArrDist.nextDouble(1.0/mean);
		// System.out.println(tot+" NEXT ORDER IN "+ (nxtArrival - model.getClock()));
		if(nxtArrival > model.closingTime)
			nxtArrival = -1.0;  // Ends time sequence
		return(nxtArrival);
	}
	/* RVP for type of order*/
	public Order.Type rvpuOrderType() {
		Order.Type type;
		switch(orderTypeDM.nextInt())
		{
			case 0: type = Order.Type.DELIVERY; break;
			case 1: type = Order.Type.CARRYOUT; break;
			default:
				System.out.println("uOrderType returned invalid value");
				type = Order.Type.DELIVERY;
		}
		return(type);
	}

	/* RVP for the number of pizzas in the order*/
	public int rvpNumberOfPizzas() {
		int num = pizzaNumDM.nextInt() + 1;
		return(num);
	}

}