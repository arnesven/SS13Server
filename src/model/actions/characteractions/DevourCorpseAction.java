package model.actions.characteractions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.characters.general.ChangelingCharacter;
import model.characters.general.GameCharacter;
import model.characters.decorators.HuskDecorator;
import model.characters.decorators.InstanceChecker;
import model.npcs.NPC;
import model.actions.SensoryLevel;

public class DevourCorpseAction extends Action {

	private ChangelingCharacter ling;

	public DevourCorpseAction(ChangelingCharacter changelingCharacter) {
		super("Devour Husk", SensoryLevel.PHYSICAL_ACTIVITY);
		this.ling = changelingCharacter;
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Devoured a husk!";
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		Actor husk = getAHusk();
		if (husk instanceof Player) {
			performingClient.getPosition().removePlayer((Player) husk);
		} else {
			performingClient.getPosition().removeNPC((NPC) husk);
		}
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) { }

	public boolean roomHasHusks() {
		return getAHusk() != null;
	}

	private Actor getAHusk() {
		for (Actor a : ling.getPosition().getActors()) {
			if (a.getCharacter().checkInstance(new InstanceChecker() {

				@Override
				public boolean checkInstanceOf(GameCharacter ch) {
					return ch instanceof HuskDecorator;
				}
			})) {
				return a;
			}
		}
		return null;
	}
}
