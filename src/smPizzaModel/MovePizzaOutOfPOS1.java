package smPizzaModel;

import simulationModelling.ConditionalAction;

public class MovePizzaOutOfPOS1 extends ConditionalAction {
    static SMPizza model;

    public static boolean precondition()
    {
        boolean retVal = false;
        if((model.rqMakeTable.position[MakeTable.POS2] == MakeTable.NO_PIZZA ) &&
                (model.rqMakeTable.position[MakeTable.POS1] != MakeTable.NO_PIZZA ) &&
                (!model.rqMakeTable.positionBusy[MakeTable.POS1])) {
            retVal = true;
        }
        return(retVal);
    }

    @Override
    protected void actionEvent() {
        model.rqMakeTable.position[MakeTable.POS2] = model.rqMakeTable.position[MakeTable.POS1];
        model.rqMakeTable.position[MakeTable.POS1] = MakeTable.NO_PIZZA;
    }
}
