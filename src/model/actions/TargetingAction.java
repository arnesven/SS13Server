package model.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import model.Client;
import model.GameData;
import model.characters.GameCharacter;
import model.items.GameItem;
import model.npcs.NPC;

public abstract class TargetingAction extends Action {

	private ArrayList<Target> targets = new ArrayList<>();
	protected ArrayList<GameItem> withWhats = new ArrayList<>();
	protected Target target;
	private GameItem item;
	private ActionPerformer performer;
	
	public TargetingAction(String name, boolean b, ActionPerformer ap) {
		super(name, b);
		this.performer = ap;
		this.addTargetsToAction(ap);
	}
	
	protected abstract void applyTargetingAction(GameData gameData,
			ActionPerformer performingClient, Target target, GameItem item);

	public void addTarget(Target cl) {
		targets.add(cl);
	}

	@Override
	public boolean isArgumentOf(Target t) {
		return target == t;
	}
	
	@Override
	protected String getPrintString(ActionPerformer performingClient) {
		return super.getPrintString(performingClient) + " " + target.getName() + 
				(item==null?(" with " + item.getName()):"");
	}
	
	private void addTargetsToAction(ActionPerformer ap) {
		for (Client cl: ap.getPosition().getClients()) {
			Target target = (Target)cl;
			if (isViableForThisAction(target) && !ap.isClient(cl)) {
				this.addTarget(target);
			}
		}
		
		for (NPC npc : ap.getPosition().getNPCs()) {
			Target target = (Target)npc;
			if (isViableForThisAction(target) && !ap.isNPC(npc)) {
				this.addTarget(target);
			}
		}
		
		addMoreTargets(ap);
	}

	protected void addMoreTargets(ActionPerformer ap) {
		// Override this method if you want your targeting action to have more targets
	}

	protected boolean isViableForThisAction(Target target2) {
		return target2.isTargetable();
	}
	
	
	public void printAndExecute(GameData gameData) {
		super.printAndExecute(gameData, performer);
	}

	public void adddClientsItemsToAction(Client client) { }
	
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
	protected void execute(GameData gameData, ActionPerformer performingClient) {
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
}
