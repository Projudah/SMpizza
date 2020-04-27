package smPizzaModel;

import java.util.ArrayList;
import java.util.Arrays;

import simulationModelling.AOSimulationModel;
import simulationModelling.Behaviour;
import simulationModelling.SequelActivity;

//
// The Simulation model Class
public class SMPizza extends AOSimulationModel
{

	/* Input Variables */
	// Define any Independent Input Variables here
	
	  // Make Table object
	  protected MakeTable rqMakeTable = new MakeTable();
	  
	  // Oven loading zone
	  protected LoadArea rgLoadArea = new LoadArea();
	  
	  // Oven unload zone
	  protected ArrayList<Pizza> qUnloadArea = new ArrayList<Pizza>();

	  
	  //Slide
	  protected ArrayList<Pizza> qSlide = new ArrayList<Pizza>();
	  
	  // Boxing and cutting area
	  protected CutBoxEmp rCutBoxEmp = new CutBoxEmp();
	  
	  //Delivery Area
	  protected ArrayList<Order> qDeliveryArea = new ArrayList<Order>();

	  protected DeliveryDrivers rgDeliveryDrivers = new DeliveryDrivers();

	  
	  // No orders to read initially
	  protected Order qOrders = new Order();
	  
	  //No calls
	//   protected Techphone qTechphone = new Techphone();
	  protected ArrayList<Order> qTechphone = new ArrayList<Order>();
	
	// References to RVP and DVP objects
	protected RVPs rvp;  // Reference to rvp object - object created in constructor
	protected DVPs dvp = new DVPs();  // Reference to dvp object
	protected UDPs udp = new UDPs();

	// Output object
	protected Output output = new Output();
	boolean traceFlag = false;
	double closingTime;

	
	// Output values - define the public methods that return values
	// required for experimentation.


	// Constructor
	public SMPizza(double t0time, double tftime, int numPersons, int totalDrivers, int loadareaSize, Seeds sd, boolean flg)
	{
		traceFlag = flg;
		// Initialise parameters here
		closingTime = 180;
		initialiseClasses(sd);
		rqMakeTable.numPersons = numPersons;
		rgDeliveryDrivers.totalNumber = totalDrivers;
		rgLoadArea.size = loadareaSize;
		
		// Create RVP object with given seed
		rvp = new RVPs(sd);
		
		// rgCounter and qCustLine objects created in Initalise Action
		
		// Initialise the simulation model
		initAOSimulModel(t0time,tftime);   

		     // Schedule the first arrivals and employee scheduling
		Initialise init = new Initialise();
		scheduleAction(init);  // Should always be first one scheduled.
		// Schedule other scheduled actions and acitvities here
		OrderArrivals arrivals = new OrderArrivals();
		scheduleAction(arrivals);
	}

	/************  Implementation of Data Modules***********/	
	/*
	 * Testing preconditions
	 */

	 protected void initialiseClasses(Seeds sd){
		 Baking.model = this;
		 CutBoxing.model = this;
		 Delivery.model = this;
		 DoughSauce.model = this;
		 DVPs.model = this;
		 FinalIngre.model = this;
		 Initialise.model = this;
		 Loading.model = this;
		 MovePizzaOutOfPOS1.model = this;
		 MovePizzaOutOfPOS3.model = this;
		 OrderArrivals.model = this;
		 PrimaryIngre.model = this;
		 ReturnShop.model = this;
		 RVPs.model = this;
		 UDPs.model = this;
		 OrderArrivals.initRvps(sd);
	 }

	 public boolean implicitStopCondition() // termination explicit
	 {
		 boolean retVal = false;
		 //System.out.println("ClosingTime = " + closingTime + "currentTime = "
		 //		+ getClock() + "RG.Counter.n = " + rgCounter.size());
		 if (getClock() >= closingTime && !scanPreconditions() && this.sbl.size()==0){
			retVal = true;
		 	System.out.println("implicit stop condition returns " + retVal);
		 }
 
		 return (retVal);
	 }

	protected void testPreconditions(Behaviour behObj)
	{
		reschedule (behObj);
		// Check preconditions of Conditional Activities

		while (scanPreconditions() == true) /* repeat */;


		// Check preconditions of Interruptions in Extended Activities
	}

	private boolean scanPreconditions()
	{
		boolean statusChanged = false;
		// Conditional Actions ----------------------------
		if (MovePizzaOutOfPOS1.precondition() == true)
		{
			MovePizzaOutOfPOS1 act = new MovePizzaOutOfPOS1(); // Generate instance
			act.actionEvent();
			statusChanged = true;
		}

		if (MovePizzaOutOfPOS3.precondition() == true)
		{
			MovePizzaOutOfPOS3 act = new MovePizzaOutOfPOS3(); // Generate instance
			act.actionEvent();
			statusChanged = true;
		}

		// Conditional Activities ------------------------
		if (DoughSauce.precondition() == true)
		{
			DoughSauce act = new DoughSauce(); // Generate instance
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}

		if (PrimaryIngre.precondition() == true)
		{
			PrimaryIngre act = new PrimaryIngre(); // Generate instance
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}
		if (FinalIngre.precondition() == true)
		{
			FinalIngre act = new FinalIngre(); // Generate instance
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}
		if (Loading.precondition() == true)
		{
			Loading act = new Loading(); // Generate instance
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}
		if (CutBoxing.precondition() == true)
		{
			CutBoxing act = new CutBoxing(); // Generate instance
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}
		if (Delivery.precondition() == true)
		{
			Delivery act = new Delivery(); // Generate instance
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}
		return (statusChanged);
	}

	// Standard Procedure to start Sequel Activities with no parameters
	protected void spStart(SequelActivity seqAct)
	{
		seqAct.startingEvent();
		scheduleActivity(seqAct);
	}

	public void eventOccured() {
				//this.showSBL();
		// Can add other debug code to monitor the status of the system
		// See examples for suggestions on setup logging

		// Setup an updateTrjSequences() method in the Output class
		// and call here if you have Trajectory Sets
		// updateTrjSequences() 

		// PriorityQueue<SBNotice> sbl = this.getCopySBL();
		// kkShowSBL(sbl);
		// if (getClock() > closingTime-10 && this.sbl.size()>0){
		// 	closingTime+=10;
		// 	this.setTimef(closingTime);
		// 	System.out.println(this.getTimef());
		//  }
		if(traceFlag)
		{
			 System.out.println("Clock: "+getClock()+
	                    ", Q.Techphone.n: "+qTechphone.size()+
						", numOrders.n: "+output.numOrders+ ", satisf "+output.numOrdersSatisfied+ ", propnumsatisfied "+output.propOrdersSatisfied);
			System.out.println("Make Table Positions: "+Arrays.toString(rqMakeTable.position));
			 System.out.println("Delivery q: "+qDeliveryArea.size());
			System.out.println("CUTBOX "+qUnloadArea.size());
			 System.out.println("Drivers out"+rgDeliveryDrivers.numBusy);
			 System.out.println();
			 showSBL();
		}
		 //
	}

}


