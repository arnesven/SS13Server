package model.characters.crew;

import java.util.ArrayList;

import java.util.List;

import model.GameData;
import model.actions.Action;
import model.actions.characteractions.ReprogramAction;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.RobotParts;
import model.items.general.Tools;
import model.items.general.FireExtinguisher;

public class RoboticistCharacter extends CrewCharacter {

	public RoboticistCharacter() {
		super("Roboticist", 15, 7.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Tools());
		list.add(new FireExtinguisher());
        list.add(new RobotParts());
		return list;
	}

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        Action a = new ReprogramAction();
        if (a.getOptions(gameData, getActor()).numberOfSuboptions() > 0) {
            at.add(new ReprogramAction());
        }
    }

    @Override
	public GameCharacter clone() {
		return new RoboticistCharacter();
	}

}
