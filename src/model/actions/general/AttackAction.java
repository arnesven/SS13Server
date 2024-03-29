package model.actions.general;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.characters.decorators.PinnedDecorator;
import model.characters.special.MartialArtist;
import model.items.general.GameItem;
import model.items.weapons.UnarmedAttack;
import model.items.weapons.Weapon;
import model.objects.general.BreakableObject;
import sounds.Sound;

import java.util.List;


public class AttackAction extends TargetingAction {

	private Weapon weapon;
	private boolean attackSuccessful;
	private boolean setsReady;

	public AttackAction(Actor ap) {
		super("Attack", SensoryLevel.PHYSICAL_ACTIVITY, ap);
		attackSuccessful = false;
		setsReady = true;
	}

	@Override
	public boolean hasRealSound() {
		return weapon != null && weapon.hasRealSound();
	}

	@Override
	public Sound getRealSound() {
		if (!attackSuccessful && weapon.hasMissSound()) {
			return weapon.getMissSound();
		}
		return weapon.getRealSound();
	}

	@Override
	public void addClientsItemsToAction(Actor client) {
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
	protected boolean requiresProximityToTarget() {
		return weapon.isMeleeWeapon();
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		Weapon w = (Weapon)item;
		if (w.isReadyToUse()) {
            this.attackSuccessful = w.doAttack(performingClient, target, gameData);
			this.weapon = w;
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
            performingClient.addTolastTurnInfo("You've got " + target.getPublicName() + " pinned!");
            target.addTolastTurnInfo(performingClient.getPublicName() + " has got you pinned!");
            if (target instanceof Actor) {
				Actor victim = (Actor)target;
				victim.setCharacter(new PinnedDecorator(victim.getCharacter(), performingClient, w));
			}
        }
    }


    @Override
	protected String getVerb(Actor whosAsking) {
        if (target == null) {
            return "attacked";
        }
		if (target.isDead()) {
            if (target instanceof BreakableObject) {
                return "destroyed";
            }
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

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		super.setArguments(args, performingClient);
		if (item instanceof UnarmedAttack && performingClient instanceof Player && MartialArtist.is(performingClient)) {
			MartialArtist.showUnarmedCombatFancyFrame((Player)performingClient, (UnarmedAttack)item);
			setsReady = false;
		}
	}

	@Override
	public boolean doesSetPlayerReady() {
		return setsReady;
	}
}
