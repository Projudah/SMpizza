package smPizzaModel;

import simulationModelling.ConditionalActivity;

public class PrimaryIngre extends ConditionalActivity {

    static SMPizza model;
    protected static boolean precondition()
    { // TODO: needs UDP to be implemented to complete this
        return model.udp.CanStartDoughSaucing();
    }

    @Override
    public void startingEvent() {
        model.rqMakeTable.position[MakeTable.POS3] = model.rqMakeTable.position[MakeTable.POS2];
        model.rqMakeTable.position[MakeTable.POS2] = null;
        model.rqMakeTable.numBusy++;
    }

    @Override
    protected double duration() {
        return 0; // TODO: needs RVP to be implemented to complete this
    }

    @Override
    protected void terminatingEvent() {
        model.rqMakeTable.position[MakeTable.POS4] = model.rqMakeTable.position[MakeTable.POS3];
        model.rqMakeTable.position[MakeTable.POS3] = null;
        model.rqMakeTable.numBusy--;
    }
}
