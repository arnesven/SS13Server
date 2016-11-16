package model.actions.characteractions;

import java.util.List;

import util.Logger;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.AttackAction;
import model.actions.general.SensoryLevel;
import model.actions.general.WatchAction;
import model.items.general.GameItem;

public class StealAction extends Action {

	private Actor victim;
	private GameItem item;
	
	private static final double BASE_STEAL_CHANCE = 0.90;
	private static final double REDUCED_STEAL_CHANCE = 0.10;

	
	public StealAction(Actor performer) {
		super("Pickpocket", SensoryLevel.NO_SENSE);
		this.performer = performer;
	}

	

	@Override
	protected String getVerb(Actor whosAsking) {
		return "stole";
	}

	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		
		for (Actor a : performer.getPosition().getActors()) {
			if (a != performer && a.getAsTarget().isTargetable()) {
				ActionOption subopt = new ActionOption(a.getPublicName());
				for (GameItem it : a.getItems()) {
					subopt.addOption(it.getPublicName(whosAsking));
				}
				if (subopt.numberOfSuboptions() > 0) {
					opt.addOption(subopt);
				}
			}
		}
		return opt;
	}


	@Override
	protected void execute(GameData gameData, Actor performingClient) {
        if (victim == null) {
            performingClient.addTolastTurnInfo("What? the target isn't there! Your action failed.");
            return;
        }

        if (item == null) {
            performingClient.addTolastTurnInfo("What? the item isn't there! Your action failed.");
            return;
        }

		if (performingClient.getPosition() != victim.getPosition()) {
			performingClient.addTolastTurnInfo("What? " + victim.getPublicName() + " isn't there! Your action failed.");
		} else if (!victim.getItems().contains(item)) {
			performingClient.addTolastTurnInfo("What? The " + item.getPublicName(performingClient) + " is gone!. Your action failed!");
		} else {
		
			double chance = BASE_STEAL_CHANCE;
			
			if (victimIsWatching(performingClient) || 
					victimIsAttacking(performingClient)) {
				chance = REDUCED_STEAL_CHANCE;
			}
			
			if (MyRandom.nextDouble() < chance) {
				performingClient.getCharacter().giveItem(item, victim.getAsTarget());
				victim.getCharacter().getItems().remove(item);
				performingClient.addTolastTurnInfo("You stole the " + item.getPublicName(performingClient) + " from " + victim.getPublicName());
			} else {
				performingClient.addTolastTurnInfo("You failed to steal from " + victim.getPublicName());
				victim.addTolastTurnInfo(performingClient.getPublicName() + " tried to pickpocket you!");
			}
		}
	}


	private boolean victimIsAttacking(Actor performingClient) {
        if (performingClient instanceof Player) {
		    if (((Player)performingClient).getNextAction() instanceof AttackAction) {
			    if (((AttackAction)((Player)performingClient).getNextAction()).isArgumentOf(performingClient.getAsTarget())) {
                    Logger.log(Logger.INTERESTING,
                            "Steal chance reduced because of attacking...");
				    return true;
			    }
		    }
        }
		return false;
	}



	private boolean victimIsWatching(Actor performingClient) {
		if (victim instanceof Player) {
			if (((Player)victim).getNextAction() instanceof WatchAction) {
				if (((WatchAction)((Player)victim).getNextAction()).isArgumentOf(performingClient.getAsTarget())) {
                    Logger.log(Logger.INTERESTING,
                            "Steal chance reduced because of watching...");
					return true;
				}
			}
		}

		return false;
	}



	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		for (Actor a : performer.getPosition().getActors()) {
			if (a.getPublicName().equals(args.get(0))) {
				victim = a;
				
				for (GameItem it : a.getItems()) {
					if (it.getPublicName(performingClient).equals(args.get(1))) {
						this.item = it;
					}
				}
				break;
			}
		}
	}

	
	
}
