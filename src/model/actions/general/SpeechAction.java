package model.actions.general;

import java.util.List;

import model.Actor;
import model.GameData;

public class SpeechAction extends Action {

	private String word;

	public SpeechAction(String word) {
		super("Speak", SensoryLevel.SPEECH);
		this.word = word;
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "said " + word;
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		performingClient.addTolastTurnInfo("You said " + word);

	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		// TODO Auto-generated method stub

	}

}
