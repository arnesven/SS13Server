package model.modes.goals;

import model.actions.objectactions.OrderShipmentAction;

public class OrderShipmentsGoal extends DidAnActionGoal{
    public OrderShipmentsGoal(int i) {
        super(i, OrderShipmentAction.class);
    }

    @Override
    protected String getNoun() {
        return "shipments (from Requisitions Console)";
    }

    @Override
    protected String getVerb() {
        return "Order at least";
    }
}
