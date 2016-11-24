package model.actions.general;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.Player;
import model.GameData;
import model.Target;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.npcs.NPC;
import model.objects.general.GameObject;
import util.Logger;

public abstract class TargetingAction extends Action {

	private ArrayList<Target> targets = new ArrayList<>();
	protected ArrayList<GameItem> withWhats = new ArrayList<>();
	protected Target target;
	protected GameItem item;
	protected Actor performer;
    protected String otherArgs;

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
			Target target = cl;
			if (isViableForThisAction(target) && !(ap == cl)) {
				this.addTarget(target);
			}
		}
		
		for (NPC npc : ap.getPosition().getNPCs()) {
			Target target = npc;
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

	public void addClientsItemsToAction(Actor client) { }
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		List<ActionOption> optlist = new ArrayList<>();
		for (GameItem gi : withWhats) {
            if (gi.getActionOptions(whosAsking).numberOfSuboptions() > 0) {
                optlist.add(gi.getActionOptions(whosAsking));
            } else {
                optlist.add(new ActionOption(gi.getFullName(whosAsking)));
            }
		}
		
		ActionOption root = super.getOptions(gameData, whosAsking);
		for (Target t : getTargets()) {
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
		if (!target.canBeInteractedBy(performingClient)) {
			performingClient.addTolastTurnInfo("What? " + target.getName() + " can't be targeted! Your action failed.");
			
		} else if (item != null && !itemAvailable(performingClient, item)) {
			performingClient.addTolastTurnInfo("What? the " + item.getPublicName(performingClient) + " was no longer there! Your action failed.");
		
		} else {
			applyTargetingAction(gameData, performingClient, target, item);
		}
	}

	protected boolean itemAvailable(Actor performingClient, GameItem it) {
        return performingClient.getItems().contains(it);
    }

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
        try {
            this.target = findTarget(args.get(0));
        } catch (NoSuchThingException e) {
            Logger.log(Logger.CRITICAL, "What, target wasn't there?");
            return;
        }
        if (args.size() > 1) {
            try {
                this.item = findItem(args.get(1), performingClient);
            } catch (NoSuchThingException e) {
                Logger.log(Logger.CRITICAL, "What, item wasn't there?");
                return;
            }
        }
        if (args.size() > 2) {
            otherArgs = args.get(2);
        }
	}
	
	public List<Target> getTargets() {
		return targets;
	}

	public void addWithWhat(GameItem it) {
		withWhats.add(it);
	}

	private GameItem findItem(String string, Actor whosAsking) throws NoSuchThingException {
		for (GameItem g : withWhats) {
			if (g.getPublicName(whosAsking).equals(string)) {
				return g;
			}
		}
		
		throw new NoSuchThingException("Did not find object for this targeting action.");
	}

	private Target findTarget(String name) throws NoSuchThingException {
		for (Target c : targets) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		if (name.equals("Yourself")) {
			return performer.getAsTarget();
		}
		throw new NoSuchThingException("Did not find target client for this targeting action!");
	}

	public int getNoOfTargets() {
		return targets.size();
	}

	public List<GameItem> getWithWhats() {
		return withWhats;
	}
}
