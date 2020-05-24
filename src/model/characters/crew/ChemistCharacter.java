package model.characters.crew;

import model.characters.general.GameCharacter;
import model.items.chemicals.Chemicals;
import model.items.general.GameItem;
import model.items.keycard.ScienceKeyCard;
import model.items.suits.InsulatedGloves;
import model.items.suits.OxygenMask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 09/09/17.
 */
public class ChemistCharacter extends CrewCharacter {
    public ChemistCharacter() {
        super("Chemist", SCIENCE_TYPE, 1, 7.5);
    }

    @Override
    public List<GameItem> getCrewSpecificItems() {
        List<GameItem> list = new ArrayList<>();
        list.add(new ScienceKeyCard());
        list.add(new OxygenMask());
        list.add(new InsulatedGloves());
        list.add(Chemicals.createRandomChemicals());
        list.add(Chemicals.createRandomChemicals());
        list.add(Chemicals.createRandomChemicals());
        list.add(Chemicals.createRandomChemicals());

        return list;
    }

    @Override
    public GameCharacter clone() {
        return new ChemistCharacter();
    }


    @Override
    public String getJobDescription() {
        return new JobDescriptionMaker(this, "Chemical compounds and reactions is the domain of your experiments.", "Labrat").makeString();
    }

}
