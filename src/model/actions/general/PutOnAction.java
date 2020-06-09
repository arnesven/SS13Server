package model.actions.general;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.QuickAction;
import model.items.general.GameItem;
import model.items.suits.MerchantSuit;
import model.items.suits.SuitItem;
import model.items.suits.Wearable;
import model.objects.general.ContainerObject;
import model.objects.general.GameObject;
import sounds.Sound;
import util.Logger;
import util.MyRandom;

public class PutOnAction extends Action implements QuickAction {

	private List<Wearable> options = new ArrayList<>();
	private Wearable selectedItem;
	private Actor putOnner;
	private Actor lootVictim = null;
    private ContainerObject lootObject;

    public PutOnAction(Actor ap) {
		super("Put on", SensoryLevel.PHYSICAL_ACTIVITY);
		this.putOnner = ap;
		addOptions(ap);
	}

	@Override
	public boolean hasRealSound() {
		return true;
	}

	@Override
	public Sound getRealSound() {
		return makeReustleSound();
	}


	private void addOptions(Actor ap) {
		for (GameItem it : ap.getItems()) {
			if (it instanceof Wearable) {
				if (((Wearable) it).canBeWornBy(ap)) {
					options.add((Wearable) it);
				}
			}
		}
		for (GameItem it : ap.getPosition().getItems()) {
			if (it instanceof Wearable) {
				if (((Wearable) it).canBeWornBy(ap)) {
					options.add((Wearable) it);
				}
			}
		}
		
		for (Actor actor : ap.getPosition().getActors()) {
			if (actor.isDead() && actor.getCharacter().isVisible()) {
				for (SuitItem s : actor.getCharacter().getEquipment().getSuitsAsList()) {
					if (s.canBeWornBy(ap)) {
						options.add(s);
					}
				}
			}
		}

        for (GameObject obj : ap.getPosition().getObjects()) {
            if (obj instanceof ContainerObject) {
                ContainerObject container = (ContainerObject) obj;
                if (container.accessibleTo(ap)) {
                    for (GameItem it : container.getInventory()) {
                        if (it instanceof Wearable) {
                        	if (((Wearable) it).canBeWornBy(ap)) {
								options.add((Wearable) it);
							}
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
		} else if (lootVictim != null && lootVictim.getCharacter().getEquipment().getSuitsAsList().contains(selectedItem)) {
			selectedItem.removeYourself(lootVictim.getCharacter().getEquipment());
		} else if (lootObject != null && lootObject.getInventory().contains(selectedItem)) {
            lootObject.getInventory().remove(selectedItem);
        } else {
			performingClient.addTolastTurnInfo("The " + selectedItem.getPublicName(performingClient) + " is gone! Your action failed.");
			return;
		}

        if (performingClient.getCharacter().getEquipment().getSuitsAsList().contains(selectedItem)) {
            throw new IllegalStateException("Tried putting the same item on itself!");
        }

		performingClient.putOnSuit(selectedItem);
		performingClient.addTolastTurnInfo("You put on the " + selectedItem.getPublicName(performingClient) + ".");
		
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
        Logger.log("Setting arg for put on: " + args.get(0));
		for (GameItem it : putOnner.getItems()) {
			if (args.get(0).contains(it.getPublicName(performingClient))) {
				selectedItem = (Wearable)it;
				return;
			}
		}
		for (GameItem it : putOnner.getPosition().getItems()) {
			if (args.get(0).contains(it.getPublicName(performingClient))) {
				selectedItem = (SuitItem)it;
				return;
			}
		}
		for (Actor actor : putOnner.getPosition().getActors()) {
			if (actor.isDead() && actor.getCharacter().getEquipment().hasAnyEquipment()) {
				for (SuitItem s : actor.getCharacter().getEquipment().getSuitsAsList()) {
					Logger.log("Dead guys suit: "  + s.getPublicName(performingClient));
					if (args.get(0).contains(s.getPublicName(performingClient))) {
						selectedItem = s;
						Logger.log("Looting a suit of a dead body " + selectedItem.getPublicName(performingClient));
						lootVictim = actor;
						return;
					}
				}
			}
		}
        for (GameObject obj : putOnner.getPosition().getObjects()) {
            if (obj instanceof ContainerObject) {
                ContainerObject container = (ContainerObject) obj;
                for (GameItem it : container.getInventory()) {

                    if (args.get(0).contains(it.getPublicName(putOnner))) {
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
		ActionOption opt = super.getOptions(gameData, whosAsking);
		for (Wearable it : options) {
			opt.addOption(it.getPublicName(whosAsking));
		}
		return opt;
	}

    @Override
    public boolean hasSpecialOptions() {
        return false;
    }

	@Override
	public void performQuickAction(GameData gameData, Player performer) {
		execute(gameData, performer);
	}

	@Override
	public boolean isValidToExecute(GameData gameData, Player performer) {
		return true;
	}

	@Override
	public List<Player> getPlayersWhoNeedToBeUpdated(GameData gameData, Player performer) {
		return performer.getPosition().getClients();
	}

	public static Sound makeReustleSound() {
		return new Sound("rustle" + MyRandom.nextInt(5) + 1);
	}

}
