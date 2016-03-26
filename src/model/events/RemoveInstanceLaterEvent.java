package model.events;

import model.Actor;
import model.GameData;
import model.actions.SensoryLevel;
import model.characters.GameCharacter;
import model.characters.decorators.InstanceRemover;

public class RemoveInstanceLaterEvent extends Event {


	private int roundHappened;
	private InstanceRemover remover;
	private Actor victim;
	private int duration;


	/**
	 * @param victim from who an instance should be removed
	 * @param round the round in which this instance was applied
	 * @param duration the number of rounds in which the instance should be removed
	 * @param instRem the instanceremover
	 */
	public RemoveInstanceLaterEvent(Actor victim, int round, int duration,
			InstanceRemover instRem) {
		this.victim = victim;
		roundHappened = round;
		this.remover = instRem;
		this.duration = duration;
	}

	@Override
	public String howYouAppear(Actor performingClient) {
		return "";
	}

	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.NO_SENSE;
	}

	@Override
	public void apply(GameData gameData) {
		if (gameData.getRound() == roundHappened+duration) {
			victim.removeInstance(remover);
		}
	}

	@Override
	public boolean shouldBeRemoved(GameData gameData) {
		if (gameData.getRound() == roundHappened+duration) {
			return true;
		}
		return false;
	}


}
