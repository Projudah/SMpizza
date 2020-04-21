package smPizzaModel;

import simulationModelling.ConditionalAction;

public class MovePizzaOutOfPOS3 extends ConditionalAction {

    static SMPizza model;

    public static boolean precondition(){
        boolean retVal = false;
        if((model.rqMakeTable.position[MakeTable.POS4] == null ) &&
                (model.rqMakeTable.position[MakeTable.POS3] != null )){
            retVal = true;
        }
        return retVal;
    }

    @Override
    protected void actionEvent() {
        model.rqMakeTable.position[MakeTable.POS4] = model.rqMakeTable.position[MakeTable.POS3];
        model.rqMakeTable.position[MakeTable.POS3] = null;
    }
}
