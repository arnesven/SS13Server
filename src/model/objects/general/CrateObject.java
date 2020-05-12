package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.ShowPackCrateFancyFrameAction;
import model.characters.crew.QuarterMasterCharacter;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.map.rooms.Room;
import model.objects.shipments.Shipment;

import java.util.ArrayList;

public class CrateObject extends ContainerObject {

	private boolean isOpen = true;

	public CrateObject(Room position, Shipment ship, GameData gameData) {
		super(ship.getName() + " Crate", position);
		for (GameItem it : ship) {
			getInventory().add(it);
		}
        ship.hasArrivedIn(position, gameData);
	}

	protected CrateObject(Room position, String name) {
		super(name, position);
	}

	@Override
	public String getPublicName(Actor whosAsking) {
		if (whosAsking.numberOfSeen(CrateObject.class) > 1) {
			return super.getPublicName(whosAsking) + " #" + getUid();
		}
		return super.getPublicName(whosAsking);
	}

	@Override
    public final Sprite getSprite(Player whosAsking) {
        if (isOpen) {
        	return getOpenSprite(whosAsking);
		}
		return getClosedSprite(whosAsking);
    }

	protected Sprite getClosedSprite(Player whosAsking) {
		return new Sprite("crateclosed", "storage.png", 39, this);
	}

	protected Sprite getOpenSprite(Player whosAsking) {
		return new Sprite("crateopen", "storage.png", 41, this);
	}

	@Override
	public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
		super.addSpecificActionsFor(gameData, cl, at);
		Action pca = new ShowPackCrateFancyFrameAction(this, cl, gameData);
		if (cl.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof QuarterMasterCharacter)) {
			at.add(pca);
		}
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void close() {
		isOpen = false;
	}

	public void open() {
		isOpen = true;
	}

	public int getMSRP() {
		int sum = 0;
		for (GameItem it : getInventory()) {
			sum += it.getCost();
		}
		return sum;
	}

	public boolean allSameType() {
		if (getInventory().size() > 0) {
			Class<? extends GameItem> firstClass = getInventory().get(0).getClass();
			for (GameItem it : getInventory()) {
				if (it.getClass() != firstClass) {
					return false;
				}
			}
		}

		return true;
	}
}
