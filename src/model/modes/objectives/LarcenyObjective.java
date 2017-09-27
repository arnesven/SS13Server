package model.modes.objectives;

import model.GameData;
import model.Player;
import model.items.general.GameItem;
import model.items.general.Locatable;
import model.items.suits.SuitItem;

public class LarcenyObjective implements TraitorObjective {

	private GameData gameData;
	private Player victim;
	private GameItem item;
	private Player traitor;

	public LarcenyObjective(GameData gameData, Player traitor, Player victim, GameItem it) {
		this.gameData = gameData;
		this.victim = victim;
		this.traitor = traitor;
		for (GameItem it2 : victim.getItems()) {
			if (it2.getClass() == it.getClass()) {
				this.item = it2;
				break;
			}
		}
	}

	@Override
	public String getText() {
		return "Steal the " + victim.getBaseName() + "'s " + item.getBaseName();
	}

	@Override
	public boolean isCompleted(GameData gameData) {
		return traitor.getItems().contains(item) || wearingItem(item, traitor);
	}

    private boolean wearingItem(GameItem item, Player traitor) {
        if (item instanceof SuitItem) {
            if (recursiveIsWearing((SuitItem)item, traitor.getCharacter().getSuit())) {
                return true;
            }
        }
        return false;
    }

    private boolean recursiveIsWearing(SuitItem item, SuitItem suit) {
        if (suit == null) {
            return false;
        }
        if (item == suit) {
            return true;
        }

        return recursiveIsWearing(item, suit.getUnder());

    }

    @Override
	public boolean wasCompleted() {
		return isCompleted(gameData);
	}

	@Override
	public int getPoints() {
		return 600;
	}

	@Override
	public Locatable getLocatable() {
		return item;
	}

}
