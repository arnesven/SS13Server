package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.suits.SuitItem;

import java.util.List;

public class ExchangeWithSuitOnRoomFloorAction extends ExchangeWithSuitInInventoryAction {
    private final SuitItem suit;

    public ExchangeWithSuitOnRoomFloorAction(Actor cl, SuitItem suitItem) {
        super(cl, suitItem);
        this.suit = suitItem;
    }


    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!performingClient.getPosition().getItems().contains(suit)) {
            performingClient.addTolastTurnInfo("What? the " + suit.getPublicName(performingClient) + " is gone! " + failed(gameData, performingClient));
        } else {
            getSelectedSuit().removeYourself(performingClient.getCharacter().getEquipment());
            performingClient.getPosition().addItem(getSelectedSuit());
            performingClient.getPosition().getItems().remove(suit);
            suit.putYourselfOn(performingClient.getCharacter().getEquipment());
            performingClient.addTolastTurnInfo("You took off the " + getSelectedSuit().getPublicName(performingClient) +
                    " and put on the " + suit.getPublicName(performingClient) + ".");
        }
    }


}
