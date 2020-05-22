package model.items.weapons;


import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.itemactions.CancelAction;
import model.characters.decorators.StunnedDecorator;
import model.characters.general.GameCharacter;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.InstanceChecker;
import model.events.RemoveInstanceLaterEvent;
import model.npcs.NPC;

public class StunBaton extends AmmoWeapon {

	public StunBaton() {
		super("Stun Baton", 0.95, 0.0, false, 0.5, 4, 90);
	}

	@Override
	public StunBaton clone() {
		return new StunBaton();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("stunbaton", "weapons.png", 4, this);
    }

    @Override
    public boolean wasCriticalHit() {
        return false; // stun baton never crits.
    }

    @Override
	public boolean isAttackSuccessful(boolean reduced) {
		boolean success = super.isAttackSuccessful(reduced);
		if (!success) {
			shots++;
			// you missed, stun baton gets refunded a shot.
		}
		return success;
	}

	@Override
	public void usedOnBy(Target target, Actor performingClient,
			final GameData gameData) {
		if (target instanceof Actor) {
			final Actor victim = (Actor)target;
			reduceMovement(gameData, victim);
			cancelAction(gameData, victim);
			performingClient.addTolastTurnInfo("You stunned " + target.getName());
			victim.addTolastTurnInfo("*ZAP* You were stunned!");
		}
	}

	private void cancelAction(GameData gameData, Actor victim) {
		if (victim instanceof Player) {
			((Player)victim).setNextAction(new CancelAction());
		} else if (victim instanceof NPC) {
			((NPC)victim).setCancelled(true);
		}
	}

	private void reduceMovement(final GameData gameData, final Actor victim) {
		victim.setCharacter(new StunnedDecorator(victim.getCharacter()));
		
		gameData.addEvent(new RemoveInstanceLaterEvent(victim, gameData.getRound(), 
				1, new InstanceChecker() {
					
					@Override
					public boolean checkInstanceOf(GameCharacter ch) {
						return ch instanceof StunnedDecorator;
					}
				}));
		
	}

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return "Good for stunning people. Stunned characters cannot move for one round and can be looted by others.";
	}
}