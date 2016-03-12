package model.items.weapons;

import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.SensoryLevel;
import model.actions.itemactions.CancelAction;
import model.characters.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.InpededMovement;
import model.characters.decorators.InstanceRemover;
import model.events.Event;
import model.npcs.NPC;

public class StunBaton extends AmmoWeapon {

	public StunBaton() {
		super("Stun Baton", 0.75, 0.0, false, 0.5, 3);
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
		victim.setCharacter(new InpededMovement(victim.getCharacter(), 
								"Stunned", 0));
		
		gameData.addEvent(new Event() {
			int roundHappened = gameData.getRound();
			@Override
			public String howYouAppear(Actor performingClient) {
				return "";
			}

			@Override
			public SensoryLevel getSense() {
				return SensoryLevel.NO_SENSE;
			}

			@Override
			public double getProbability() {
				return 1.0;
			}

			@Override
			public void apply(GameData gameData) {
				if (gameData.getRound() == roundHappened+1) {
					InstanceRemover inpedeRemover = new InstanceRemover() {

						@Override
						public GameCharacter removeInstance(GameCharacter ch) {
							if (ch instanceof InpededMovement) {
								return ((CharacterDecorator)ch).getInner();
							} else if (ch instanceof CharacterDecorator) {
								return removeInstance(((CharacterDecorator)ch).getInner());
							}
							throw new NoSuchElementException("Did not find that instance!");
						}
					};

					victim.removeInstance(inpedeRemover);
				}
			}
			
			@Override
			public boolean shouldBeRemoved(GameData gameData) {
				if (gameData.getRound() == roundHappened+1) {
					return true;
				}
				return false;
			}
		});
	}
}
