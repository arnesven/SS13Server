package model.actions.characteractions;

import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.QuickAction;
import model.actions.general.Action;
import model.characters.general.ChangelingCharacter;
import model.characters.general.GameCharacter;
import model.characters.decorators.HuskDecorator;
import model.characters.decorators.InstanceChecker;
import model.items.NoSuchThingException;
import model.npcs.NPC;
import model.actions.general.SensoryLevel;
import sounds.Sound;
import util.Logger;

public class DevourCorpseAction extends Action implements QuickAction {

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
	public boolean hasRealSound() {
		return true;
	}

	@Override
	public Sound getRealSound() {
		return new Sound("eatfood");
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		Actor husk = getAHusk();
        try {
			performingClient.addTolastTurnInfo("You devoured the husk. Yum!");
                performingClient.getPosition().removeActor(husk);
            ling.addToPower(1);
        } catch (NoSuchThingException nste) {
            Logger.log(Logger.CRITICAL, "What, no husk to be devoured?");
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

	@Override
	public void performQuickAction(GameData gameData, Player performer) {
		execute(gameData, performer);
	}

	@Override
	public boolean isValidToExecute(GameData gameData, Player performer) {
		return true;
	}

	@Override
	public List<Player> getPlayersWhoNeedToBeUpdated(GameData gameData, Player performer) {
		return performer.getPosition().getClients();
	}


	@Override
	public Sprite getAbilitySprite() {
		return new Sprite("suckaction", "weapons2.png", 44, 47, null);
	}


}
