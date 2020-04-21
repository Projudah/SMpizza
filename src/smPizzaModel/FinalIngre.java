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
        model.rqMakeTable.position[MakeTable.POS5] = model.rqMakeTable.position[MakeTable.POS4];
        model.rqMakeTable.position[MakeTable.POS4] = null;
        model.rqMakeTable.numBusy++;
    }

    @Override
    protected double duration() {
        return 0; // TODO: needs RVP to be implemented to complete this
    }

    @Override
    protected void terminatingEvent() {
        // TODO: Needs to check for correctness
        iCPizza = model.rqMakeTable.position[MakeTable.POS5];
        model.qSlide.spInsertQue(iCPizza);
        model.rqMakeTable.position[MakeTable.POS5] = null;
        model.rqMakeTable.numBusy--;

    }
}
