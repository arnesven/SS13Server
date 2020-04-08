package model.actions.characteractions;

import java.util.List;

import model.Target;
import model.actions.LootAction;
import model.characters.decorators.HandCuffedDecorator;
import model.characters.decorators.PinnedDecorator;
import model.characters.decorators.StunnedDecorator;
import model.characters.general.GameCharacter;
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

public class StealAction extends LootAction {

	private static final double BASE_STEAL_CHANCE = 0.85;
	private static final double REDUCED_STEAL_CHANCE = 0.10;
	private double EXTRA_STEAL_CHANCE = 1.00;


	public StealAction(Actor performer) {
		super(performer);
		setName("Pickpocket");
		this.performer = performer;
	}

	@Override
	protected String getLootVerb() {
		return "stole";
	}

	@Override
	public boolean isViableForThisAction(Target target2) {

		if (!(target2 instanceof Actor)) {
			return false;
		}

		Actor actorTarget = (Actor) target2;
		if (actorTarget == performer || !target2.isTargetable()) {
			return false;
		}
		return true;
	}

	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		for (ActionOption opt2 : opt.getSuboptions()) {
			opt2.getSuboptions().removeIf((ActionOption opt3) -> opt3.getName().equals("All Items"));
		}
		return opt;
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {

			double chance = BASE_STEAL_CHANCE;
			
			if (victimIsWatching(performingClient, getTarget()) ||
					victimIsAttacking(performingClient)) {
				chance = REDUCED_STEAL_CHANCE;
			} else if (victimIsExposed((Actor)getTarget())) {
				chance = EXTRA_STEAL_CHANCE;
			}

			if (MyRandom.nextDouble() < chance) {
				super.execute(gameData, performingClient);
			} else {
				performingClient.addTolastTurnInfo("You failed to steal from " + getTarget().getName());
				((Actor)getTarget()).addTolastTurnInfo(performingClient.getPublicName() + " tried to pickpocket you!");
			}
		}

	private boolean victimIsExposed(Actor actorTarget) {
		return  actorTarget.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof PinnedDecorator ||
			gc instanceof StunnedDecorator || gc instanceof HandCuffedDecorator);
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



	private boolean victimIsWatching(Actor performingClient, Target victim) {
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

	
}
