package smPizzaModel;

import java.util.Arrays;

import simulationModelling.ConditionalActivity;

import static smPizzaModel.RVPs.triangularDistribution;

public class PrimaryIngre extends ConditionalActivity {

    static SMPizza model;

    protected static boolean precondition() {
        return udpCanStartPrimaryIngre();
    }

    @Override
    public void startingEvent() {
        model.rqMakeTable.numBusy++;
        model.rqMakeTable.position[MakeTable.POS3] = model.rqMakeTable.position[MakeTable.POS2];
        model.rqMakeTable.position[MakeTable.POS2] = MakeTable.NO_PIZZA;
        model.rqMakeTable.addingPrimIngr = true;
    }

    @Override
    protected double duration() {
        return rvpuPrimaryIngrTime(model.rqMakeTable.position[MakeTable.POS3].size);
    }

    @Override
    protected void terminatingEvent() {
        model.rqMakeTable.addingPrimIngr = false;
        model.rqMakeTable.numBusy--;
    }

    // UDP
    protected static boolean udpCanStartPrimaryIngre() {
        if ((model.rqMakeTable.position[MakeTable.POS3] != MakeTable.NO_PIZZA) ||
            (FinalIngre.udpCanStartFinalIngre())||
            (model.rqMakeTable.numBusy >= model.rqMakeTable.numPersons) ||
            (model.rqMakeTable.position[MakeTable.POS2] == MakeTable.NO_PIZZA)) {
            return false;
        }
        return true;
    }

    // RVP
    public double rvpuPrimaryIngrTime(int size) {
        double Tm = 0;
        if (size == Pizza.Size.LARGE.getValue()) {
            Tm = triangularDistribution(0.6, 0.8, 1);
        } else if (size == Pizza.Size.MEDIUM.getValue()) {
            Tm = triangularDistribution(0.5, 0.7, 0.9);
        } else if (size == Pizza.Size.SMALL.getValue()) {
            Tm = triangularDistribution(0.4, 0.5, 0.6);
        } else {
            model.print("uPrimaryIngrTime - invalid type " + size);
        }
        return (Tm);
    }
}
