package smPizzaModel;
import smPizzaModel.Constants;

class DVPs 
{
	static SMPizza model;  // for accessing the clock
	
	// Constructor
	// Translate deterministic value procedures into methods
        /* -------------------------------------------------
	                       Example
	protected double getEmpNum()  // for getting next value of EmpNum(t)
	{
	   double nextTime;
	   if(model.clock == 0.0) nextTime = 90.0;
	   else if(model.clock == 90.0) nextTime = 210.0;
	   else if(model.clock == 210.0) nextTime = 420.0;
	   else if(model.clock == 420.0) nextTime = 540.0;
	   else nextTime = -1.0;  // stop scheduling
	   return(nextTime);
	}
	------------------------------------------------------------*/

	protected double GetTotalCost(int size, int tableNum, int drivers){
		double baseCost = 0;

		switch(size){
			case 520: baseCost = 35000; break;
			case 605: baseCost = 65000; break;
			default: baseCost = 0;
		}

		double tableCost = tableNum * Constants.MAKETABLE_COST_PERHOUR;
		double driverCost = drivers * Constants.DRIVER_COST_PERHOUR;

		double sumCost = tableCost + driverCost + Constants.CUTANDBOX_COST_PERHOUR;

		return baseCost + (sumCost) * Constants.HOURS_OPENED;
	}
}
