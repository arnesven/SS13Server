package model.events;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.objects.consoles.CrimeRecordsConsole;
import util.Logger;

public class SentenceCountdownEvent extends Event {

	private Actor inmate;
	private CrimeRecordsConsole console;
	private boolean remove;
	private boolean firstTime;

	public SentenceCountdownEvent(GameData gameData, Actor worst,
			CrimeRecordsConsole crimeRecordsConsole) {
		this.inmate = worst;
		this.console = crimeRecordsConsole;
		this.remove = false;
		this.firstTime = true;
	}

	@Override
	public void apply(GameData gameData) {
        try {
            if (!gameData.getRoom("Brig").getActors().contains(inmate)) {
                Logger.log(Logger.INTERESTING, inmate.getBaseName() + " has been sprung!");
                console.getSentenceMap().remove(inmate);
                remove = true;
            } else {

                int remaining = console.getSentenceMap().get(inmate);
                if (remaining == 0) {
                    remove = true;
//                    gameData.addEvent(new Event() {
//
//                        @Override
//                        public String howYouAppear(Actor performingClient) {
//                            return "";
//                        }
//
//                        @Override
//                        public SensoryLevel getSense() {
//                            return SensoryLevel.NO_SENSE;
//                        }
//
//                        @Override
//                        public void apply(GameData gameData) {
                            console.release(gameData, inmate);
                            inmate.addTolastTurnInfo("JudgeBot; I believe these were your personal affects.");
//                        }
//
//                        @Override
//                        public boolean shouldBeRemoved(GameData gameData) {
//                            return true;
//                        }
//                    });
                } else {
                    if (!firstTime) {
                            inmate.addTolastTurnInfo("You have " + remaining + " more round(s) on your sentence.");
                    }

                    for (Actor a : console.getReleaseIntoRoom().getActors()) {
                        a.addTolastTurnInfo(inmate.getPublicName() + " looks unhappy in the brig.");
                    }

                    firstTime = false;
                    remaining--;
                    console.getSentenceMap().put(inmate, remaining);
                }
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
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
