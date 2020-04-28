package model.characters.crew;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.characteractions.TrainNPCAction;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.MotionTracker;
import model.items.keycard.ScienceKeyCard;
import model.items.seeds.TomatoSeeds;
import model.map.rooms.Room;
import model.npcs.animals.Trainable;

public class BiologistCharacter extends CrewCharacter {

	public BiologistCharacter() {
		super("Biologist", SCIENCE_TYPE, 3, 11.0);
	}

    @Override
    public List<GameItem> getCrewSpecificItems() {
		ArrayList<GameItem> list = new ArrayList<>();
		list.add(new TomatoSeeds());
		list.add(new MotionTracker());
		list.add(new ScienceKeyCard());
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
		for (Actor a : getActor().getPosition().getActors()) {
			if (a instanceof Trainable && !a.isDead()) {
				return (Trainable)a;
			}
		}
		return null;
	}

	@Override
	public GameCharacter clone() {
		return new BiologistCharacter();
	}

    @Override
    public int getStartingMoney() {
        return 50;
    }


	@Override
	public String getJobDescription() {
		return new JobDescriptionMaker(this, "You study plant life and animal behavior when put on a space station.",
				"Train Animal").makeString();
	}

}
