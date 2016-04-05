package model.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.Player;
import model.items.GameItem;
import model.items.suits.SuitItem;
import model.map.Room;

public class EvidenceBox extends ContainerObject {

	private Map<GameItem, Actor> affects = new HashMap<>();
	
	public EvidenceBox(Room position) {
		super("Evidence Box", position);
	}
	
	@Override
	protected char getIcon(Player whosAsking) {
		return '#';
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
	
	public static EvidenceBox find(GameData gameData) {
		for (GameObject o : gameData.getObjects()) {
			if (o instanceof EvidenceBox) {
				return (EvidenceBox) o;
			}
		}
		throw new NoSuchElementException("Did not find EvidenceBox on station!");
	}

	
	
}
