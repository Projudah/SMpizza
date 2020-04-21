package smPizzaModel;

class UDPs 
{
	static SMPizza model;  // for accessing the clock
	
	// Constructor
	protected UDPs() {}

	// Translate User Defined Procedures into methods
    /*-------------------------------------------------
	                       Example
	    protected int ClerkReadyToCheckOut()
        {
        	int num = 0;
        	Clerk checker;
        	while(num < model.NumClerks)
        	{
        		checker = model.Clerks[num];
        		if((checker.currentstatus == Clerk.status.READYCHECKOUT)  && checker.list.size() != 0)
        		{return num;}
        		num +=1;
        	}
        	return -1;
        }
	------------------------------------------------------------*/
	protected boolean CanStartDoughSaucing(){
		if(model.rqMakeTable.position[MakeTable.POS1] != null){
			return false;
		}

		int freeExtraEmployees = (model.rqMakeTable.numPersons - model.rqMakeTable.numBusy) - 1; //all other employees minus the one 1 required to start this activity

		for(int pos=MakeTable.POS2 ; pos <= MakeTable.POS5; pos++){
			if(model.rqMakeTable.position[pos] != null && freeExtraEmployees <= 0){
				return false;
			}
			freeExtraEmployees--;
		}
		if(model.rqMakeTable.numBusy >= model.rqMakeTable.numPersons){
			return false;
		}
		if(model.qTechphone.isEmpty()){
			return false;
		}
		return true;

	}

	protected boolean CanStartPrimaryIngre(){
		if(model.rqMakeTable.position[MakeTable.POS3] != null){
			return false;
		}

		int freeExtraEmployees = (model.rqMakeTable.numPersons - model.rqMakeTable.numBusy) - 1; //all other employees minus the one 1 required to start this activity

		for(int pos=MakeTable.POS4 ; pos <= MakeTable.POS5; pos++){
			if(model.rqMakeTable.position[pos] != null && freeExtraEmployees <= 0){
				return false;
			}
			freeExtraEmployees--;
		}
		if(model.rqMakeTable.numBusy >= model.rqMakeTable.numPersons){
			return false;
		}
		if(model.rqMakeTable.position[MakeTable.POS2] == null){
			return false;
		}
		return true;

	}
	
}
