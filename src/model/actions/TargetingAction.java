package model.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import model.Actor;
import model.Player;
import model.GameData;
import model.characters.GameCharacter;
import model.items.GameItem;
import model.npcs.NPC;
import model.objects.GameObject;

public abstract class TargetingAction extends Action {

	private ArrayList<Target> targets = new ArrayList<>();
	protected ArrayList<GameItem> withWhats = new ArrayList<>();
	protected Target target;
	protected GameItem item;
	protected Actor performer;
	
	public TargetingAction(String name, SensoryLevel s, Actor ap) {
		super(name, s);
		this.performer = ap;
		this.addTargetsToAction(ap);
	}
	
	protected abstract void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item);

	public void addTarget(Target cl) {
		targets.add(cl);
	}

	@Override
	public boolean isArgumentOf(Target t) {
		return target == t;
	}
	
	@Override
	public String getDescription() {
		return super.getDescription() + " " + target.getName() + 
				(item!=null?(" with " + item.getName()):"");
	}
	
	private void addTargetsToAction(Actor ap) {
		for (Player cl: ap.getPosition().getClients()) {
			Target target = (Target)cl;
			if (isViableForThisAction(target) && !(ap == cl)) {
				this.addTarget(target);
			}
		}
		
		for (NPC npc : ap.getPosition().getNPCs()) {
			Target target = (Target)npc;
			if (isViableForThisAction(target) && !(ap == npc)) {
				this.addTarget(target);
			}
		}
		
		for (GameObject ob : ap.getPosition().getObjects()) {
			if (ob instanceof Target) {
				Target objectAsTarget = (Target)ob;
				if (((Target) ob).isTargetable() && isViableForThisAction(objectAsTarget)) {
					this.addTarget((Target)ob);
				}
			}
		}
		
		addMoreTargets(ap);
	}

	protected void addMoreTargets(Actor ap) {
		// Override this method if you want your targeting action to have more targets
	}

	public boolean isViableForThisAction(Target target2) {
		return target2.isTargetable();
	}
	
	
	public void printAndExecute(GameData gameData) {
		super.doTheAction(gameData, performer);
	}

	public void addClientsItemsToAction(Player client) { }
	
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
	protected void execute(GameData gameData, Actor performingClient) {
		applyTargetingAction(gameData, performingClient, target, item);
	}

	@Override
	public void setArguments(java.util.List<String> args) {
		this.target = findTarget(args.get(0));
		if (args.size() > 1) {
			this.item = findItem(args.get(1));
		}
	}
	
	public List<Target> getTargets() {
		return targets;
	}

	public void addWithWhat(GameItem it) {
		withWhats.add(it);
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

	public int getNoOfTargets() {
		return targets.size();
	}

	public List<GameItem> getWithWhats() {
		return withWhats;
	}
}
