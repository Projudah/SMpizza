package smPizzaModel;

import java.util.Arrays;

import simulationModelling.ConditionalActivity;

import static smPizzaModel.RVPs.triangularDistribution;

public class FinalIngre extends ConditionalActivity {

    static SMPizza model;
    Pizza iCPizza;
    protected static boolean precondition()
    {
       return model.udp.CanStartFinalIngre();
    }

    @Override
    public void startingEvent() {
        iCPizza = model.rqMakeTable.position[MakeTable.POS4];
        model.rqMakeTable.position[MakeTable.POS5] = model.rqMakeTable.position[MakeTable.POS4];
        model.rqMakeTable.position[MakeTable.POS4] = null;
        model.rqMakeTable.numBusy++;

    }

    @Override
    protected double duration() {
        return rvpuFinalIngrTime(iCPizza.size);
    }

    @Override
    protected void terminatingEvent() {
        model.qSlide.add(iCPizza);
        model.rqMakeTable.position[MakeTable.POS5] = null;
        model.rqMakeTable.numBusy--;
    }

    // RVP
    protected double rvpuFinalIngrTime(Pizza.Size size){
        double Tm = 0;
        if(size == Pizza.Size.LARGE){
            Tm = triangularDistribution(0.5, 0.6, 0.7);
        }else if(size == Pizza.Size.MEDIUM){
            Tm = triangularDistribution(0.4, 0.5, 0.6);
        }else if(size == Pizza.Size.SMALL){
            Tm = triangularDistribution(0.3, 0.4, 0.5);
        }else{
            System.out.println("uFinalIngrTime - invalid type "+size);
        }
        System.out.println("rvpuFinalIngrTime"+ Tm);
        return(Tm);
    }
}
