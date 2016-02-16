package model.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import model.Client;
import model.GameData;
import model.GameItem;

public abstract class TargetingAction extends Action {

	private ArrayList<Target> targets = new ArrayList<>();
	protected ArrayList<GameItem> withWhats = new ArrayList<>();
	private Target target;
	private GameItem item;
	
	public TargetingAction(String name, Client client) {
		super(name);
		this.addTargetsToAction(client);
	}
	
	protected abstract void applyTargetingAction(GameData gameData,
			Client performingClient, Target target, GameItem item);

	public void addTarget(Target cl) {
		targets.add(cl);
	}

	private void addTargetsToAction(Client client) {
		for (Client cl: client.getPosition().getClients()) {
			if (cl.getCharacter().isInteractable() && cl != client) {
				this.addTarget((Target)cl);
			}
		}
	}

	public void addItemsToAction(Client client) { }
	
	@Override
	public String toString() {
		String withWatString = "";
		for (GameItem gi : withWhats) {
			withWatString += gi.getName() + "{}";
		}
		
		String resultStr = "";
		for (Target t : targets) {
			resultStr += t.getName() + "{" + withWatString + "}";
		}
		return getName() + "{" + resultStr + "}";
	}

	@Override
	public void execute(GameData gameData, Client performingClient) {
		applyTargetingAction(gameData, performingClient, target, item);
	}

	@Override
	public void setArguments(java.util.List<String> args) {
		this.target = findTarget(args.get(0));
		if (args.size() > 1) {
			this.item = findItem(args.get(1));
		}
	}


	private GameItem findItem(String string) {
		for (GameItem g : withWhats) {
			if (g.getName().equals(string)) {
				return g;
			}
		}
		throw new NoSuchElementException("Did not find object for this targeting action.");
	}

	private Target findTarget(String name) {
		for (Target c : targets) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		throw new NoSuchElementException("Did not find target client for this targeting action!");
	}
}
