package smPizzaModel;

import java.util.Arrays;

import simulationModelling.ConditionalActivity;

import static smPizzaModel.RVPs.triangularDistribution;

public class PrimaryIngre extends ConditionalActivity {

    static SMPizza model;
    Pizza iCPizza;
    protected static boolean precondition() {
       return udpCanStartPrimaryIngre();
    }

    @Override
    public void startingEvent() {
        iCPizza = new Pizza();
        iCPizza = model.rqMakeTable.position[MakeTable.POS2];
        model.rqMakeTable.numBusy++;
        System.out.println();
        model.rqMakeTable.position[MakeTable.POS3] = model.rqMakeTable.position[MakeTable.POS2];
        model.rqMakeTable.position[MakeTable.POS2] = null;
        model.rqMakeTable.positionBusy[MakeTable.POS3] = true;
    }

    @Override
    protected double duration() {
        return rvpuPrimaryIngrTime(iCPizza.size);
    }

    @Override
    protected void terminatingEvent() {
        model.rqMakeTable.positionBusy[MakeTable.POS3] = false;
        model.rqMakeTable.numBusy--;
    }

    
    //UDP
    //Throws Null pointer exception while using nested if
    protected static boolean udpCanStartPrimaryIngre(){
		if(model.rqMakeTable.position[MakeTable.POS3] != null){
			return false;
		}
		if(FinalIngre.udpCanStartFinalIngre()){
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
    
    
    // RVP
    public double rvpuPrimaryIngrTime(Pizza.Size size){
        double Tm = 0;
        if(size == Pizza.Size.LARGE){
            Tm = triangularDistribution(0.6, 0.8, 1);
        }else if(size == Pizza.Size.MEDIUM){
            Tm = triangularDistribution(0.5, 0.7, 0.9);
        }else if(size == Pizza.Size.SMALL){
            Tm = triangularDistribution(0.4, 0.5, 0.6);
        }else{
            System.out.println("uPrimaryIngrTime - invalid type "+size);
        }
        return(Tm);
    }
}
