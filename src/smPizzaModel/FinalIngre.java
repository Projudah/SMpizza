package smPizzaModel;

import simulationModelling.ConditionalActivity;

public class FinalIngre extends ConditionalActivity {

    static SMPizza model;
    Pizza iCPizza;
    protected static boolean precondition()
    {
       boolean retVal = false;
       if ((model.rqMakeTable.numBusy <= model.rqMakeTable.numPersons) &&
               (model.rqMakeTable.position[MakeTable.POS5] == null) &&
               (model.rqMakeTable.position[MakeTable.POS4] != null)){
           retVal = true;
       }

       return retVal;
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
        return model.rvp.uFinalIngrTime(iCPizza.size);
    }

    @Override
    protected void terminatingEvent() {
        model.qSlide.add(iCPizza);
        model.rqMakeTable.position[MakeTable.POS5] = null;
        model.rqMakeTable.numBusy--;

    }
}
