package model.events;

import model.Actor;
import model.GameData;
import model.actions.SensoryLevel;
import model.objects.consoles.CrimeRecordsConsole;

public class SentenceCountdownEvent extends Event {

	private Actor inmate;
	private CrimeRecordsConsole console;
	private boolean remove;

	public SentenceCountdownEvent(GameData gameData, Actor worst,
			CrimeRecordsConsole crimeRecordsConsole) {
		this.inmate = worst;
		this.console = crimeRecordsConsole;
		this.remove = false;
	}

	@Override
	public void apply(GameData gameData) {
		int remaining = console.getSentenceMap().get(inmate);
		if (remaining == 0) {
			remove = true;
			console.getSentenceMap().remove(inmate);
			inmate.moveIntoRoom(gameData.getRoom("Port Hall Front"));
			inmate.addTolastTurnInfo("You were released from the brig.");
		} else {
			remaining--;
			console.getSentenceMap().put(inmate, remaining);
			inmate.addTolastTurnInfo("You have " + remaining + " more round(s) on your sentance.");
		}
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
	public boolean shouldBeRemoved(GameData gameData) {
		return remove;
	}
}
