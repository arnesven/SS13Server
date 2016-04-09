package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.characteractions.TrainNPCAction;
import model.characters.GameCharacter;
import model.items.GameItem;
import model.items.MedKit;
import model.items.weapons.Flamer;
import model.items.weapons.Knife;
import model.map.Room;
import model.npcs.ChimpNPC;
import model.npcs.Trainable;

public class BiologistCharacter extends GameCharacter {

	public BiologistCharacter() {
		super("Biologist", 3, 11.0);
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new MedKit());
		list.add(new Flamer());
		return list;
	}
	
	@Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		super.addCharacterSpecificActions(gameData, at);
		Trainable train = getTrainableInRoom(this.getPosition());
		if (train != null) {
			at.add(new TrainNPCAction(this.getActor(), gameData));
		}
	}

	private Trainable getTrainableInRoom(Room position) {
		for (Actor a : position.getActors()) {
			if (a instanceof Trainable) {
				return (Trainable)a;
			}
		}
		return null;
	}

}
