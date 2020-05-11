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
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("crate", "storage.png", 39, this);
    }

	@Override
	public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
		super.addSpecificActionsFor(gameData, cl, at);
		Action pca = new ShowPackCrateFancyFrameAction(this, cl, gameData);
		if (cl.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof QuarterMasterCharacter)) {
			at.add(pca);
		}
	}
}
