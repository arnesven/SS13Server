package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.actions.general.Action;
import model.actions.general.TargetingAction;
import model.actions.characteractions.InvestigateAction;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.ZippoLighter;
import model.items.keycard.SecurityKeyCard;
import model.items.suits.FancyGloves;
import model.items.suits.SunGlasses;
import model.items.weapons.Revolver;


public class DetectiveCharacter extends CrewCharacter {

	public DetectiveCharacter() {
		super("Detective", SECURITY_TYPE,12, 13.0);
	}
	
	@Override
	public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new SecurityKeyCard());
		list.add(new Revolver());
		list.add(new SunGlasses());
        list.add(new ZippoLighter());
        list.add(new FancyGloves());
     	return list;
	}
	
	@Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		super.addCharacterSpecificActions(gameData, at);
	
		TargetingAction act = new InvestigateAction(this.getActor());
		if (act.getNoOfTargets() > 0) {
			at.add(act);
		}
	}

	@Override
	public GameCharacter clone() {
		return new DetectiveCharacter();
	}

    @Override
    public int getStartingMoney() {
        return 25;
    }

	@Override
	public String getJobDescription() {
		return new JobDescriptionMaker(this, "There's something fishy going on here, " +
				" and you're gonna find out what!",
				"Investigate").makeString();
	}
}
