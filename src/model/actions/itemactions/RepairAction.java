package model.actions.itemactions;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.Tools;
import model.items.tools.CraftingTools;
import model.items.tools.RepairTools;
import model.objects.general.BreakableObject;
import model.objects.general.Repairable;

public class RepairAction extends TargetingAction {

	public RepairAction(Actor ap) {
		super("Repair", SensoryLevel.PHYSICAL_ACTIVITY, ap);
	}



	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		target.addToHealth(target.getMaxHealth() - target.getHealth());
		performingClient.addTolastTurnInfo("You repaired " + target.getName());
        ((Repairable)target).doWhenRepaired(gameData);
		RepairTools rt = null;
		try {
			rt = GameItem.getItemFromActor(performingClient, new RepairTools());
			rt.makeHoldInHand(performingClient);
		} catch (NoSuchThingException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean isViableForThisAction(Target target2) {
		return target2 instanceof Repairable && 
				( ((Repairable)target2).isDamaged() || ((Repairable)target2).isBroken() );
		
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "tinkered with " + target.getName();
	}

	@Override
	public Sprite getAbilitySprite() {
		return new CraftingTools().getSprite(null);
	}
}