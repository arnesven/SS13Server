package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.RoomParts;
import model.items.general.Tools;

public class ArchitectCharacter extends CrewCharacter {

	public ArchitectCharacter() {
		super("Architect", 26, 6.0);
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new Tools());
        for (int i = 1; i >= 0; --i) {
            list.add(new RoomParts());
        }
		return list;
	}

	@Override
	public GameCharacter clone() {
		return new ArchitectCharacter();
	}

    @Override
    public int getStartingMoney() {
        return 75;
    }
}
