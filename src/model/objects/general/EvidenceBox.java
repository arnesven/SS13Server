package model.objects.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import graphics.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.suits.SuitItem;
import model.map.Room;

public class EvidenceBox extends ContainerObject {

	private Map<GameItem, Actor> affects = new HashMap<>();
	
	public EvidenceBox(Room position) {
		super("Evidence Box", position);
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("evidencebox", "storage.png", 42);
    }

    public void addAffects(Actor owner, List<GameItem> affs) {
		for (GameItem it : affs) {
			affects.put(it, owner);
		}
		getInventory().addAll(affs);
	}

	public void addAffect(Actor worst, SuitItem suit) {
		getInventory().add(suit);
		affects.put(suit, worst);
	}
	
	public List<GameItem> removeAffects(Actor worst) {
		List<GameItem> itemsToRemove = new ArrayList<>();
		List<GameItem> itemsToReturn = new ArrayList<>();
		for (GameItem it : getInventory()) {
			if (affects.containsKey(it)) {
				if (affects.get(it) == worst) {
					itemsToReturn.add(it);
				}
			} else {
				itemsToRemove.add(it);
			}
		}

		this.getInventory().removeAll(itemsToReturn);
		for (GameItem it : itemsToRemove){
			affects.remove(it);
		}
		return itemsToReturn;
	}
	
	public static EvidenceBox find(GameData gameData) throws NoSuchThingException {
		for (GameObject o : gameData.getObjects()) {
			if (o instanceof EvidenceBox) {
				return (EvidenceBox) o;
			}
		}
		throw new NoSuchThingException("Did not find EvidenceBox on station!");
	}

	
	
}
