package model.actions.general;

import model.Actor;
import model.Player;
import model.GameData;
import model.Target;
import model.events.AttackOfOpportunityEvent;
import model.items.general.GameItem;
import model.items.weapons.Weapon;


public class AttackAction extends TargetingAction {
	
	public AttackAction(Actor ap) {
		super("Attack", SensoryLevel.PHYSICAL_ACTIVITY, ap);
	}


	
	@Override
	public void addClientsItemsToAction(Player client) {
		withWhats.add(client.getCharacter().getDefaultWeapon());
		for (GameItem it : client.getItems()) {
			if (it instanceof Weapon) {
				if (((Weapon)it).isReadyToUse()) {
					withWhats.add(it);
				}
			}
		}
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		Weapon w = (Weapon)item;
		if (w.isReadyToUse()) {
            w.doAttack(performingClient, target, gameData);

			this.setSense(w.getSensedAs());
            if (target instanceof Actor && !target.isDead()) {
                checkAttackOfOpportunity(gameData, performingClient, (Actor)target, w);
            }
		} else {
			performingClient.addTolastTurnInfo(w.getPublicName(performingClient) + 
												" isn't working."); 
		}
	}

    private void checkAttackOfOpportunity(GameData gameData,
                                          Actor performingClient,
                                          Actor target, Weapon w) {
        if (performingClient.getCharacter().getsAttackOfOpportunity(w)) {
            gameData.addMovementEvent(new AttackOfOpportunityEvent(performingClient, target, w));
            performingClient.addTolastTurnInfo("You've got " + target.getPublicName() + " pinned!");
            target.addTolastTurnInfo(performingClient.getPublicName() + " has got you pinned!");
        }
    }


    @Override
	protected String getVerb(Actor whosAsking) {
		if (target.isDead()) {
			return "killed";
		}
		return getName().toLowerCase() + "ed";
	}
	
	@Override
	public String getDistantDescription(Actor whosAsking) {
		return "You hear a loud bang.";
	}
	
	@Override
	protected boolean itemAvailable(Actor performingClient, GameItem it) {
		return super.itemAvailable(performingClient, it) || 
				it == performingClient.getCharacter().getDefaultWeapon();
	}


	
}