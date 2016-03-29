package model.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.text.html.Option;

import model.Actor;
import model.Player;
import model.GameData;
import model.Target;
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

	/**
	 * When a target has been deemed viable for this action,
	 * this method is called to actually add that target to this
	 * action's targets.
	 * @param cl
	 */
	public void addTarget(Target cl) {
		targets.add(cl);
	}

	@Override
	public boolean isArgumentOf(Target t) {
		return target == t;
	}
	
	@Override
	public String getDescription(Actor whosAsking) {
		String name = target.getName();
		if (performer.getAsTarget() == target) {
			name = (performer.getCharacter().getGender().equals("man")?"him":"her") + "self";
		}
		
		return super.getDescription(whosAsking) + " " + name + 
				(item!=null?(" with " + item.getPublicName(whosAsking)):"");
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
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		List<ActionOption> optlist = new ArrayList<>();
		for (GameItem gi : withWhats) {
			optlist.add(new ActionOption(gi.getPublicName(whosAsking)));
		}
		
		ActionOption root = super.getOptions(gameData, whosAsking);
		for (Target t : targets) {
			if (performer == t) {
				root.addOption("Yourself");
			} else {
				ActionOption opt = new ActionOption(t.getName());
				opt.addAll(optlist);
				root.addOption(opt);
			}
		}
		return root;
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		applyTargetingAction(gameData, performingClient, target, item);
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		this.target = findTarget(args.get(0));
		if (args.size() > 1) {
			this.item = findItem(args.get(1), performingClient);
		}
	}
	
	public List<Target> getTargets() {
		return targets;
	}

	public void addWithWhat(GameItem it) {
		withWhats.add(it);
	}

	private GameItem findItem(String string, Actor whosAsking) {
		for (GameItem g : withWhats) {
			if (g.getPublicName(whosAsking).equals(string)) {
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
		if (name.equals("Yourself")) {
			return performer.getAsTarget();
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
