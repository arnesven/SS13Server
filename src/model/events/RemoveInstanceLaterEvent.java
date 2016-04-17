package model.events;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.characters.decorators.InstanceChecker;

public class RemoveInstanceLaterEvent extends Event {


	private int roundHappened;
	private InstanceChecker checker;
	private Actor victim;
	private int duration;


	/**
	 * @param victim from who an instance should be removed
	 * @param round the round in which this instance was applied
	 * @param duration the number of rounds in which the instance should be removed
	 * @param instRem the instanceremover
	 */
	public RemoveInstanceLaterEvent(Actor victim, int round, int duration,
			InstanceChecker instRem) {
		this.victim = victim;
		roundHappened = round;
		this.checker = instRem;
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
			victim.removeInstance(checker);
		}
	}

	@Override
	public boolean shouldBeRemoved(GameData gameData) {
        return gameData.getRound() == roundHappened + duration;
    }


}
