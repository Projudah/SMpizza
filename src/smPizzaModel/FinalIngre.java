package smPizzaModel;

import java.util.Arrays;

import simulationModelling.ConditionalActivity;

public class FinalIngre extends ConditionalActivity {

    static SMPizza model;

    protected static boolean precondition()
    {
       return model.udp.CanStartFinalIngre();
    }

    @Override
    public void startingEvent() {
        model.rqMakeTable.position[MakeTable.POS5] = model.rqMakeTable.position[MakeTable.POS4];
        model.rqMakeTable.position[MakeTable.POS4] = null;
        model.rqMakeTable.numBusy++;

    }

    @Override
    protected double duration() {
        return model.rvp.uFinalIngrTime(model.rqMakeTable.position[MakeTable.POS5].size);
    }

    @Override
    protected void terminatingEvent() {
        model.qSlide.add(model.rqMakeTable.position[MakeTable.POS5]);
        model.rqMakeTable.position[MakeTable.POS5] = null;
        model.rqMakeTable.numBusy--;
    }
}
