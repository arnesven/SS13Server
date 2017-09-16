package model.characters.crew;

import model.characters.general.GameCharacter;
import model.items.general.Chemicals;
import model.items.general.GameItem;
import model.items.general.ZippoLighter;
import model.items.suits.OxygenMask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 09/09/17.
 */
public class ChemistCharacter extends CrewCharacter {
    public ChemistCharacter() {
        super("Chemist", 1, 7.5);
    }

    @Override
    public List<GameItem> getCrewSpecificItems() {
        List<GameItem> list = new ArrayList<>();
        list.add(Chemicals.createRandomChemicals());
        list.add(Chemicals.createRandomChemicals());
        list.add(Chemicals.createRandomChemicals());
        list.add(Chemicals.createRandomChemicals());
        list.add(new OxygenMask());
        list.add(new ZippoLighter());

        return list;
    }

    @Override
    public GameCharacter clone() {
        return new ChemistCharacter();
    }
}