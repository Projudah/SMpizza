package smPizzaModel;

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
        model.rqMakeTable.position[MakeTable.POS3] = model.rqMakeTable.position[MakeTable.POS2];
        model.rqMakeTable.position[MakeTable.POS2] = null;
        model.rqMakeTable.numBusy++;
    }

    @Override
    protected double duration() {
        return model.rvp.uPrimaryIngrTime(iCPizza.size);
    }

    @Override
    protected void terminatingEvent() {
        model.rqMakeTable.position[MakeTable.POS4] = model.rqMakeTable.position[MakeTable.POS3];
        model.rqMakeTable.position[MakeTable.POS3] = null;
        model.rqMakeTable.numBusy--;
    }
}
