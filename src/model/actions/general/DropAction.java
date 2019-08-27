package model.actions.general;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.items.general.GameItem;
import model.items.suits.SuitItem;

public class DropAction extends Action {

	private Actor ap;
	private GameItem item;
    private boolean allItems = false;
    private boolean stripNaked = false;

    public DropAction(Actor clientActionPerformer) {
		super("Drop", SensoryLevel.PHYSICAL_ACTIVITY);
		ap = clientActionPerformer;
		
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


		SuitItem suit = performingClient.getCharacter().getSuit();
		if (item == suit) {
			performingClient.takeOffSuit();
		} else {
			if (!performingClient.getItems().contains(item)) {
				performingClient.addTolastTurnInfo("What? the " + item.getPublicName(performingClient) + " was no longer there! Your action failed.");
				return;
			}
			performingClient.getItems().remove(item);
		}
		performingClient.getPosition().addItem(item);
        item.setPosition(performingClient.getPosition());
        item.setHolder(null);
		performingClient.addTolastTurnInfo("You dropped the " + item.getPublicName(performingClient) + ".");
	}

    private void stripNaked(GameData gameData, Actor performingClient) {
        do {
            SuitItem s = performingClient.getCharacter().getSuit();
            if (s != null) {
                performingClient.takeOffSuit();
                performingClient.getPosition().addItem(s);
            } else {
                break;
            }
        } while (true);
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
		
		if (ap.getCharacter().getSuit() != null) {
			opt.addOption(ap.getCharacter().getSuit().getPublicName(whosAsking)  
					+ " ("+ String.format("%.1f", ap.getCharacter().getSuit().getWeight()) + " kg)");
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
	public void setArguments(List<String> args, Actor performingClient) {
        if (args.get(0).equals("All Items")) {
            this.allItems = true;
            return;
        } else if (args.get(0).equals("Strip Naked")) {
            this.stripNaked = true;
            return;
        }


		if (ap.getCharacter().getSuit() != null) {
			if (args.get(0).contains(ap.getCharacter().getSuit().getPublicName(performingClient))) {
				this.item = ap.getCharacter().getSuit();
				return;
			}
		}
		for (GameItem it : ap.getItems()){
			if (args.get(0).contains(it.getFullName(performingClient))) {
				this.item = it;
				return;
			}
		}
		throw new NoSuchElementException("No such item found for drop action");
	}

    @Override
    public boolean hasSpecialOptions() {
        return false;
    }
}
