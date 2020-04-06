package model.actions.general;

import model.Actor;
import model.GameData;
import model.Player;
import model.items.suits.SuitItem;

public class OverlayDropAction extends DropAction {
    public OverlayDropAction(Player forWhom) {
        super(forWhom);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);
        opt.clearAll();

        for (SuitItem s : whosAsking.getCharacter().getEquipment().getTopEquipmentAsList()) {
            opt.addOption(s.getPublicName(whosAsking)
                    + " ("+ String.format("%.1f", s.getWeight()) + " kg)");
        }


        if (whosAsking.getItems().size() > 0) {
            opt.addOption("All Items");
        }
        if (!whosAsking.getCharacter().getEquipment().isNaked()) {
            opt.addOption("Strip Naked");
        }
		return opt;
    }

    @Override
    public boolean hasSpecialOptions() {
        return true;
    }
}
