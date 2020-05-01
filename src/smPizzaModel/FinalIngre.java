package smPizzaModel;

import java.util.Arrays;

import simulationModelling.ConditionalActivity;
import dataModelling.TriangularVariate;
import static smPizzaModel.RVPs.triangularDistribution;

public class FinalIngre extends ConditionalActivity {

    static SMPizza model;

    protected static boolean precondition()
    {
       return udpCanStartFinalIngre();
    }

    @Override
    public void startingEvent() {
        model.rqMakeTable.position[MakeTable.POS5] = model.rqMakeTable.position[MakeTable.POS4];
        model.rqMakeTable.position[MakeTable.POS4] = MakeTable.NO_PIZZA;
        model.rqMakeTable.numBusy++;

    }

    @Override
    protected double duration() {
        return rvpuFinalIngrTime(model.rqMakeTable.position[MakeTable.POS5].size);
    }

    @Override
    protected void terminatingEvent() {
        model.qSlide.add(model.rqMakeTable.position[MakeTable.POS5]);
        model.rqMakeTable.position[MakeTable.POS5] = MakeTable.NO_PIZZA;
        model.rqMakeTable.numBusy--;
    }

    //UDP
    protected static boolean udpCanStartFinalIngre(){
		return ((model.rqMakeTable.numBusy <= model.rqMakeTable.numPersons) &&
		(model.rqMakeTable.position[MakeTable.POS5] == MakeTable.NO_PIZZA) &&
		(model.rqMakeTable.position[MakeTable.POS4] != MakeTable.NO_PIZZA));
	}
    
    // RVP
    protected double rvpuFinalIngrTime(int size){
        double Tm = 0;
        if(size == Pizza.Size.LARGE.getValue()){
            Tm = triangularDistribution(0.5, 0.6, 0.7);
        }else if(size == Pizza.Size.MEDIUM.getValue()){
            Tm = triangularDistribution(0.4, 0.5, 0.6);
        }else if(size == Pizza.Size.SMALL.getValue()){
            Tm = triangularDistribution(0.3, 0.4, 0.5);
        }else{
            System.out.println("uFinalIngrTime - invalid type "+size);
        }
        System.out.println("rvpuFinalIngrTime"+ Tm);
        return(Tm);
    }
}
