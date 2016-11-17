package model.actions.general;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.items.general.GameItem;
import model.items.suits.MerchantSuit;
import model.items.suits.SuitItem;
import model.objects.general.ContainerObject;
import model.objects.general.GameObject;
import util.Logger;

public class PutOnAction extends Action {

	private List<SuitItem> options = new ArrayList<>();
	private SuitItem selectedItem;
	private Actor putOnner;
	private Actor lootVictim = null;
    private ContainerObject lootObject;

    public PutOnAction(Actor ap) {
		super("Put on", SensoryLevel.PHYSICAL_ACTIVITY);
		this.putOnner = ap;
		if (ap.getCharacter().getSuit() == null) {
			addOptions(ap);
		} else if (ap.getCharacter().getSuit().permitsOver()) {
			addOptions(ap);
		}
	}

	private void addOptions(Actor ap) {
		for (GameItem it : ap.getItems()) {
			if (it instanceof SuitItem) {
				options.add((SuitItem)it);
			}
		}
		for (GameItem it : ap.getPosition().getItems()) {
			if (it instanceof SuitItem) {
				options.add((SuitItem)it);
			}
		}
		
		for (Actor actor : ap.getPosition().getActors()) {
			if (actor.isDead() && actor.getCharacter().getSuit() != null && actor.getCharacter().isVisible()) {
				options.add(actor.getCharacter().getSuit());
			}
		}

        for (GameObject obj : ap.getPosition().getObjects()) {
            if (obj instanceof ContainerObject) {
                ContainerObject container = (ContainerObject) obj;
                if (container.accessibleTo(ap)) {
                    for (GameItem it : container.getInventory()) {
                        if (it instanceof SuitItem) {
                            options.add((SuitItem) it);
                        }
                    }
                }
            }
        }
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		if (selectedItem == null) {
			return "failed";
		}
		return "put on the " + selectedItem.getPublicName(whosAsking);
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		if (selectedItem == null) {
			performingClient.addTolastTurnInfo("Your action failed!");
			return;
		}
		
		if (performingClient.getItems().contains(selectedItem)) {
			performingClient.getItems().remove(selectedItem);
		} else if (performingClient.getPosition().getItems().contains(selectedItem)) {
			performingClient.getPosition().getItems().remove(selectedItem);
		} else if (lootVictim != null && lootVictim.getCharacter().getSuit() == selectedItem) {
			lootVictim.getCharacter().removeSuit();
		} else if (lootObject != null && lootObject.getInventory().contains(selectedItem)) {
            lootObject.getInventory().remove(lootObject);
        } else {
			performingClient.addTolastTurnInfo("The " + selectedItem.getPublicName(performingClient) + " is gone! Your action failed.");
			return;
		}
		
		performingClient.putOnSuit(selectedItem);
	
		performingClient.addTolastTurnInfo("You put on the " + selectedItem.getPublicName(performingClient) + ".");
		
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
        Logger.log("Setting arg for put on" + args.get(0));
		for (GameItem it : putOnner.getItems()) {
			if (it.getPublicName(performingClient).equals(args.get(0))) {
				selectedItem = (SuitItem)it;
				return;
			}
		}
		for (GameItem it : putOnner.getPosition().getItems()) {
			if (it.getPublicName(performingClient).equals(args.get(0))) {
				selectedItem = (SuitItem)it;
				return;
			}
		}
		for (Actor actor : putOnner.getPosition().getActors()) {
			if (actor.isDead() && actor.getCharacter().getSuit() != null) {
				Logger.log("Dead guys suit: " + actor.getCharacter().getSuit());
				if (actor.getCharacter().getSuit().getPublicName(performingClient).equals(args.get(0))) {
					selectedItem = actor.getCharacter().getSuit();
					Logger.log("Looting a suit of a dead body " + selectedItem.getPublicName(performingClient));
					lootVictim  = actor;
					return;
				}
			}
		}
        for (GameObject obj : putOnner.getPosition().getObjects()) {
            if (obj instanceof ContainerObject) {
                ContainerObject container = (ContainerObject) obj;
                for (GameItem it : container.getInventory()) {

                    if (it.getPublicName(putOnner).equals(args.get(0))) {
                        selectedItem = (SuitItem) it;
                        lootObject = container;
                        return;
                    }
                }
            }
        }
		
		
	}

	public int getNoOfOptions() {
		return options.size();
	}

	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = new ActionOption(this.getName());
		for (GameItem it : options) {
			opt.addOption(it.getPublicName(whosAsking));
		}
		return opt;
	}
	
}
