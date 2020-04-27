package smPizzaModel;

import cern.jet.random.Exponential;
import cern.jet.random.Normal;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import simulationModelling.ScheduledAction;

class OrderArrivals extends ScheduledAction 
{
	static SMPizza model;  // reference to model object

//	public double timeSequence()
//	{
//		return model.rvp.DuOrder();
//	}
	public double timeSequence()
	{
		return rvpDuOrder();
	}

	public void actionEvent()
	{
		// WArrival Action Sequence SCS
	     Order iCOrder = new Order();
         iCOrder.uType = model.rvp.uOrderType();
         iCOrder.startTime = model.getClock();
		 iCOrder.uNumPizzas = model.rvp.NumberOfPizzas();
		 iCOrder.uNumPizzasStarted = 0;
		 iCOrder.uNumPizzasCompleted = 0;
		 model.qTechphone.add(iCOrder);
		 System.out.println("Incomiung order "+iCOrder.uNumPizzas);
	}


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

	static void initRvps(Seeds sd)
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

		pMEAN = 60/peak_per_hour;
		oMEAN = 60/off_peak_per_hour;

		// Initialise Internal modules, user modules and input variables
		interArrDist = new Exponential(1.0/oMEAN, new MersenneTwister(sd.arr));
	}


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

}