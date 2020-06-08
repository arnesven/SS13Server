package model.objects.general;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.ItemHolder;
import model.actions.general.Action;
import model.actions.objectactions.ManageContainerAction;
import model.actions.objectactions.RetrieveAction;
import model.items.general.GameItem;
import model.map.rooms.Room;

public class ContainerObject extends GameObject implements ItemHolder {

private List<GameItem> inventory = new ArrayList<>();
	
	public ContainerObject(String name, Room position) {
		super(name, position);
	}
	
	public List<GameItem> getInventory() {
		return inventory;
	}
	
	
	@Override
	public void addSpecificActionsFor(GameData gameData, Actor cl,
                                      ArrayList<Action> at) {
		super.addSpecificActionsFor(gameData, cl, at);
		if (inventory.size() > 0 && cl.hasInventory()) {
			at.add(new RetrieveAction(this, cl));
		}
		if (cl instanceof Actor && cl.hasInventory()) {
			at.add(new ManageContainerAction(gameData, this));
		}
	}


    public boolean accessibleTo(Actor ap) {
        return true;
    }

	@Override
	public List<GameItem> getItems() {
		return getInventory();
	}
}
