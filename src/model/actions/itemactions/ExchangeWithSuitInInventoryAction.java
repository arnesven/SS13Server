package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.PutOnAction;
import model.actions.general.SensoryLevel;
import model.items.suits.SuitItem;
import sounds.Sound;

import java.util.List;

public class ExchangeWithSuitInInventoryAction extends Action {
    private final SuitItem suit;
    private SuitItem selectedSuit;

    public ExchangeWithSuitInInventoryAction(Actor forWhom, SuitItem suitItem) {
        super("Exchange " + suitItem.getPublicName(forWhom), SensoryLevel.PHYSICAL_ACTIVITY);
        this.suit = suitItem;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "changed clothes";
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return PutOnAction.makeReustleSound();
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        SuitItem forWhat = whosAsking.getCharacter().getEquipment().getEquipmentForSlot(suit.getEquipmentSlot());
        if (forWhat != null) {
            opts.addOption(forWhat.getPublicName(whosAsking));
        }

        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!performingClient.getItems().contains(suit)) {
            performingClient.addTolastTurnInfo("What? the " + suit.getPublicName(performingClient) + " is gone! " + failed(gameData, performingClient));
        } else {
            swapSuits(performingClient, suit, selectedSuit);
        }
    }

    protected void swapSuits(Actor performingClient, SuitItem suit, SuitItem selectedSuit) {
        selectedSuit.removeYourself(performingClient.getCharacter().getEquipment());
        performingClient.getCharacter().giveItem(selectedSuit, performingClient.getAsTarget());
        performingClient.getCharacter().getItems().remove(suit);
        suit.putYourselfOn(performingClient.getCharacter().getEquipment());
        performingClient.addTolastTurnInfo("You took off the " + selectedSuit.getPublicName(performingClient) +
                " and put on the " + suit.getPublicName(performingClient) + ".");
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        for (SuitItem it : performingClient.getCharacter().getEquipment().getTopEquipmentAsList()) {
            if (it.getPublicName(performingClient).equals(args.get(0))) {
                selectedSuit = it;
            }
        }

    }

    protected SuitItem getSelectedSuit() {
        return selectedSuit;
    }

}
