package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.general.GameCharacter;
import model.items.chemicals.EthanolChemicals;
import model.items.general.Chemicals;
import model.items.general.GameItem;
import model.items.general.MedKit;
import model.items.general.Syringe;
import model.items.keycard.ScienceKeyCard;

public class GeneticistCharacter extends CrewCharacter {

	public GeneticistCharacter() {
		super("Geneticist", SCIENCE_TYPE, 1, 8.0);
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new ScienceKeyCard());
		list.add(new Syringe());
		list.add(new MedKit());
		list.add(new EthanolChemicals());
		return list;
	}

	@Override
	public GameCharacter clone() {
		return new GeneticistCharacter();
	}


    @Override
    public int getStartingMoney() {
        return 100;
    }


	@Override
	public String getJobDescription() {
		return new JobDescriptionMaker(this, "What strange and/or useful mutations and genetic " +
				"alterations can you find? Just find a suitable test subject for genetical experiments",
				"Pro Injector, GeneTIX Expert").makeString();
	}

}
