package model.characters.crew;

import java.util.ArrayList;

import java.util.List;

import model.GameData;
import model.actions.general.Action;
import model.actions.characteractions.ReprogramAction;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.RobotParts;
import model.items.general.Tools;
import model.items.general.FireExtinguisher;
import model.items.suits.InsulatedGloves;
import util.Logger;

public class RoboticistCharacter extends CrewCharacter {

	public RoboticistCharacter() {
		super("Roboticist", TECHNICAL_TYPE,448, 7.0);
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Tools());
		list.add(new FireExtinguisher());
        list.add(new RobotParts());
        list.add(new InsulatedGloves());
		return list;
	}

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        Action a = new ReprogramAction();
        try {
            if (a.getOptions(gameData, getActor()).numberOfSuboptions() > 0) {
                at.add(new ReprogramAction());
            }
        } catch (IllegalStateException ise) {
            Logger.log(ise.getMessage());
        }
    }

    @Override
	public GameCharacter clone() {
		return new RoboticistCharacter();
	}

    @Override
    public int getStartingMoney() {
        return 100;
    }

    @Override
    public String getJobDescription() {
        return new JobDescriptionMaker(this, "The station needs robots for dangerous or unwanted jobs."+
                " You build and program them. Also cyborgs!", "Reporgramming, Build Robot").makeString();
    }
}
