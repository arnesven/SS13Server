package model.objects.general;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.ItemHolder;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.ManageContainerAction;
import model.actions.objectactions.RetrieveAction;
import model.items.general.GameItem;
import model.map.rooms.Room;

public class ContainerObject extends BreakableObject implements ItemHolder {

private List<GameItem> inventory = new ArrayList<>();
	
	public ContainerObject(String name, Room position) {
		super(name, 2.0, position);
	}
	
	public List<GameItem> getInventory() {
		return inventory;
	}


	@Override
	protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		if (inventory.size() > 0 && cl.hasInventory()) {
			at.add(new RetrieveAction(this, cl));
		}
		if (cl instanceof Player && cl.hasInventory()) {
			at.add(new ManageContainerAction(gameData, this, (Player)cl));
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
