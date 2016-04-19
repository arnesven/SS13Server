package model.actions.characteractions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.general.ChangelingCharacter;
import model.characters.general.GameCharacter;
import model.characters.general.HorrorCharacter;
import model.characters.decorators.NoSuchInstanceException;

public class ChangeFormAction extends Action {

	private ChangelingCharacter ling;
	private GameCharacter selected;

	public ChangeFormAction(ChangelingCharacter ling) {
		super("Change Form", SensoryLevel.PHYSICAL_ACTIVITY);
		this.ling = ling;
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Changed form";
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		ling.changeInto(selected);
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		boolean hasUltimate = false;
		for (GameCharacter c : ling.getForms()) {
			if (c instanceof HorrorCharacter) {
				hasUltimate = true;
			} else if (c == ling.getForm()) {
				// do not add
			} else {
				opt.addOption(c.getBaseName());
			}
		}
		if (hasUltimate) {
			opt.addOption("Ultimate Form");
		}
		return opt;
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		for (GameCharacter c : ling.getForms()) {
			if (c instanceof HorrorCharacter) {
				if (args.get(0).equals("Ultimate Form")) {
					selected = c;
					return;
				}
			} else {
				if (args.get(0).equals(c.getBaseName())) {
					selected = c;
					return;
				}
			}
		}
		throw new NoSuchInstanceException("Did not find the form being transformed into!");
	}

}
