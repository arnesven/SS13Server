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
		if (!gameData.getRoom("Brig").getActors().contains(inmate)) {
			System.out.println(inmate.getBaseName() + " has been sprung!");
			console.getSentenceMap().remove(inmate);
			remove = true;
		} else {

			int remaining = console.getSentenceMap().get(inmate);
			if (remaining == 0) {
				remove = true;
				console.release(gameData, inmate);
			} else {
				inmate.addTolastTurnInfo("You have " + remaining + " more round(s) on your sentance.");
				remaining--;
				console.getSentenceMap().put(inmate, remaining);
				
			}
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
