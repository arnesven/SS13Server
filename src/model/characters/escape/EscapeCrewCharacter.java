package model.characters.escape;

import model.characters.crew.CrewCharacter;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public class EscapeCrewCharacter extends CrewCharacter {
    public EscapeCrewCharacter() {
        super("Human", 1, 19.0);
    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<>();
    }

    @Override
    public List<GameItem> getCrewSpecificItems() {
        return new ArrayList<>();
    }

    @Override
    public GameCharacter clone() {
        return new EscapeCrewCharacter();
    }
}
