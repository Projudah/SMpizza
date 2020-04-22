package smPizzaModel;

import java.util.Arrays;

import simulationModelling.ConditionalActivity;

public class PrimaryIngre extends ConditionalActivity {

    static SMPizza model;
    Pizza iCPizza;
    protected static boolean precondition() {
       return model.udp.CanStartPrimaryIngre();
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
        return model.rvp.uPrimaryIngrTime(iCPizza.size);
    }

    @Override
    protected void terminatingEvent() {
        model.rqMakeTable.positionBusy[MakeTable.POS3] = false;
        model.rqMakeTable.numBusy--;
    }
}
