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

		if(CanStartPrimaryIngre() || CanStartFinalIngre()){
			return false;
		}
		if(model.rqMakeTable.numBusy >= model.rqMakeTable.numPersons){
			return false;
		}
		if(model.qTechphone.isEmpty()) {
			return false;
		}
		return true;
	}

	protected boolean CanStartPrimaryIngre(){
		if(model.rqMakeTable.position[MakeTable.POS3] != null){
			return false;
		}


		if(CanStartFinalIngre()){
			return false;
		}
		if(model.rqMakeTable.numBusy >= model.rqMakeTable.numPersons){
			return false;
		}
		if(model.rqMakeTable.position[MakeTable.POS2] == null){
			return false;
		}
		return true;

	}

	protected boolean CanStartFinalIngre(){
		return ((model.rqMakeTable.numBusy <= model.rqMakeTable.numPersons) &&
		(model.rqMakeTable.position[MakeTable.POS5] == null) &&
		(model.rqMakeTable.position[MakeTable.POS4] != null));
	}

	protected boolean CanLoadPizza(){
		double freeSpace = model.rgLoadArea.size - model.rgLoadArea.usedSpace;
		for(Pizza pizza : model.qSlide){
			if(pizza.size <= freeSpace){
				return true;
			}
		}
		return false;
	}

	protected Pizza GetNextPizza(){
		double freeSpace = model.rgLoadArea.size - model.rgLoadArea.usedSpace;
		for(Pizza pizza : model.qSlide){
			if(pizza.size <= freeSpace){
				model.qSlide.remove(pizza);
				return pizza;
			}
		}
		return null;
	}
	
}
