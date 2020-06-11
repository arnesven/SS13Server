package model.actions.general;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.QuickAction;
import model.items.general.GameItem;
import model.items.suits.SuitItem;
import sounds.Sound;

public class DropAction extends Action implements QuickAction {

	private Actor ap;
	private boolean allItems = false;
    private boolean stripNaked = false;
    private String requestedItem;
    private GameItem item = null;


    public DropAction(Actor clientActionPerformer) {
		super("Drop", SensoryLevel.PHYSICAL_ACTIVITY);
		ap = clientActionPerformer;
		
	}

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return item.getDropSound();
    }

    @Override
	protected void execute(GameData gameData, Actor performingClient) {
        if (allItems) {
            dropAllItems(gameData, performingClient);
            return;
        }

        if ( stripNaked) {
            stripNaked(gameData, performingClient);
            return;
        }

        for (SuitItem s : ap.getCharacter().getEquipment().getTopEquipmentAsList()) {
            if (requestedItem.contains(s.getPublicName(performingClient))) {
                item = s;
            }
        }
        for (GameItem it : ap.getItems()){
            if (requestedItem.contains(it.getFullName(performingClient))) {
                item = it;
            }
        }

        if (item == null) {
            performingClient.addTolastTurnInfo("What, the " + requestedItem + " wasn't there? " +
                    failed(gameData, performingClient));
            return;
        }

        boolean wasEquipped = false;
        for (SuitItem s : performingClient.getCharacter().getEquipment().getTopEquipmentAsList()) {
            if (item == s) {
                performingClient.getCharacter().removeEquipment(s);
                wasEquipped = true;
                break;
            }
        }

        if (!wasEquipped) {
            if (!performingClient.getItems().contains(item)) {
                performingClient.addTolastTurnInfo("What? the " + item.getPublicName(performingClient) + " was no longer there! Your action failed.");
                return;
            } else {
                performingClient.getItems().remove(item);
            }
        }

        performingClient.getPosition().addItem(item);
        item.setPosition(performingClient.getPosition());
        item.setHolder(null);
		performingClient.addTolastTurnInfo("You dropped the " + item.getPublicName(performingClient) + ".");
	}

    private void stripNaked(GameData gameData, Actor performingClient) {
        List<SuitItem> allThingsWorn = performingClient.getCharacter().getEquipment().getSuitsAsList();
        performingClient.getCharacter().getEquipment().removeEverything();
        for (SuitItem it : allThingsWorn) {
            performingClient.getPosition().addItem(it);
        }
        performingClient.addTolastTurnInfo("You stripped naked.");
    }

    private void dropAllItems(GameData gameData, Actor performingClient) {
        List<GameItem> itemsToDrop = new ArrayList<>();
        itemsToDrop.addAll(performingClient.getItems());

        for (GameItem it : itemsToDrop) {
            performingClient.getItems().remove(it);
            performingClient.getPosition().addItem(it);
        }
        performingClient.addTolastTurnInfo("You dropped all your stuff.");
    }


    @Override
	protected String getVerb(Actor whosAsking) {
		return "Dropped";
	}
	
	@Override
	public String getDescription(Actor whosAsking) {
        if (allItems) {
            return super.getDescription(whosAsking) + " stuff";
        } else if (stripNaked) {
            return whosAsking.getPublicName() + " stripped naked";
        }
		return super.getDescription(whosAsking) + " " + item.getPublicName(whosAsking);
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		for (GameItem gi : ap.getItems()) {
			opt.addOption(gi.getFullName(whosAsking) 
					+ " ("+ String.format("%.1f", gi.getWeight()) + " kg)");
		}

		for (SuitItem s : ap.getCharacter().getEquipment().getTopEquipmentAsList()) {
                opt.addOption(s.getPublicName(whosAsking)
                        + " (" + String.format("%.1f", s.getWeight()) + " kg)");

        }

        if (whosAsking.getItems().size() > 0) {
            opt.addOption("All Items");
        }
        if (whosAsking.getCharacter().getEquipment().hasAnyEquipment()) {
            opt.addOption("Strip Naked");
        }
		return opt;
	}
	
	
	@Override
	public void setArguments(List<String> args, Actor performingClient) {
        if (args.get(0).equals("All Items")) {
            this.allItems = true;
            return;
        } else if (args.get(0).equals("Strip Naked")) {
            this.stripNaked = true;
            return;
        }
        requestedItem = args.get(0);


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
}
