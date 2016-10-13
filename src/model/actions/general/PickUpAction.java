package model.actions.general;

import java.util.List;
import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.Player;
import model.PlayerSettings;
import model.items.general.GameItem;
import model.items.general.HidableItem;

public class PickUpAction extends Action {

	private Actor ap;
	private GameItem item;

	public PickUpAction(Actor clientActionPerformer) {
		super("Pick up", SensoryLevel.PHYSICAL_ACTIVITY);
		ap = clientActionPerformer;
	}

	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (performingClient.getPosition().getItems().contains(item)) {
			performingClient.addTolastTurnInfo("You picked up the " + item.getPublicName(performingClient) + ".");
			performingClient.getPosition().getItems().remove(item);
			boolean wasEncumbered = performingClient.getCharacter().isEncumbered();
            performingClient.getCharacter().giveItem(item, null);
            if (!wasEncumbered && performingClient.getCharacter().isEncumbered()) {
                if (performingClient instanceof Player &&
                        ((Player)performingClient).getSettings().get(PlayerSettings.AUTO_DROP_ITEMS_ON_PICK_UP)) {
                    dropButNot(performingClient, item);
                }
            }

		} else {
			performingClient.addTolastTurnInfo("You failed to pick up the " + item.getPublicName(performingClient) + "!");
			
		}
		
	}

    private void dropButNot(Actor performingClient, GameItem item) {
        while (performingClient.getCharacter().isEncumbered() &&
                performingClient.getCharacter().getItems().size() > 1) {
            GameItem it = performingClient.getCharacter().getItems().get(performingClient.getCharacter().getItems().size()-2);
            if (it != item) {
                performingClient.addTolastTurnInfo("You dropped the " + it.getPublicName(performingClient) + ".");
                performingClient.getCharacter().getItems().remove(it);
                performingClient.getPosition().getItems().add(it);
            }
        }
    }

    @Override
	protected String getVerb(Actor whosAsking) {
		return "picked up";
	}
	
	@Override
	public String getDescription(Actor whosAsking) {
		return super.getDescription(whosAsking) + " the " + item.getPublicName(whosAsking);
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = new ActionOption(this.getName());
		for (GameItem gi : ap.getPosition().getItems()) {
            if (gi instanceof HidableItem) {
                if (!((HidableItem) gi).isHidden()) {
                    opt.addOption(gi.getPublicName(whosAsking) + String.format(" (%.1f kg)", gi.getWeight()));
                }
            } else {
                opt.addOption(gi.getPublicName(whosAsking) + String.format(" (%.1f kg)", gi.getWeight()));
            }
		}
		return opt;
	}
	
	public void setItem(GameItem it) {
		this.item = it;
	}
	
	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		for (GameItem it : ap.getPosition().getItems()){
			if (args.get(0).contains(it.getPublicName(performer))) {
				setItem(it);
				return;
			}
		}
		throw new NoSuchElementException("No such item found for pick up action");
	}


}
