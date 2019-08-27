package model.actions.general;

import model.Actor;
import model.GameData;
import model.Player;

public class OverlayDropAction extends DropAction {
    public OverlayDropAction(Player forWhom) {
        super(forWhom);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);
        opt.clearAll();

		if (whosAsking.getCharacter().getSuit() != null) {
			opt.addOption(whosAsking.getCharacter().getSuit().getPublicName(whosAsking)
					+ " ("+ String.format("%.1f", whosAsking.getCharacter().getSuit().getWeight()) + " kg)");
		}

        if (whosAsking.getItems().size() > 0) {
            opt.addOption("All Items");
        }
        if (whosAsking.getCharacter().getSuit() != null) {
            opt.addOption("Strip Naked");
        }
		return opt;
    }

    @Override
    public boolean hasSpecialOptions() {
        return true;
    }
}
