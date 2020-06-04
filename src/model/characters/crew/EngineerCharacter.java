package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.general.GameCharacter;
import model.items.general.ElectronicParts;
import model.items.general.GameItem;
import model.items.general.PowerRadio;
import model.items.general.Tools;
import model.items.keycard.EngineeringKeyCard;
import model.items.suits.FireSuit;
import model.items.suits.InsulatedGloves;
import model.items.tools.RepairTools;

public class EngineerCharacter extends CrewCharacter {

	public EngineerCharacter() {
		super("Engineer", TECHNICAL_TYPE,26, 10.0);
	}


    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new EngineeringKeyCard());
		list.add(new FireSuit());
		list.add(new RepairTools());
		list.add(new PowerRadio());
        list.add(new ElectronicParts());
        list.add(new InsulatedGloves());
        return list;
	}


	@Override
	public GameCharacter clone() {
		return new EngineerCharacter();
	}

    @Override
    public int getStartingMoney() {
        return 120;
    }


	@Override
	public String getJobDescription() {
		return new JobDescriptionMaker(this,
				"Craving Power? The Engineer's got it all in his/her hands." +
						" Make sure the power level is just right, or bad things WILL happen.", "Craft Electronics").makeString();
	}

}
