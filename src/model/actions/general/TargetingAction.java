package model.actions.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.SpriteObject;
import model.Actor;
import model.Player;
import model.GameData;
import model.Target;
import model.characters.decorators.InProximityOfTargetOneRoundDecorator;
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
        if (target == null) {
            return super.getDescription(whosAsking);
        }
		String name = target.getName();
		if (performer.getAsTarget() == target) {
			name = (performer.getCharacter().getGender().equals("man")?"him":"her") + "self";
		}
		
		return super.getDescription(whosAsking) + " " + name + 
				(item!=null?(" with " + item.getPublicName(whosAsking)):"");
	}
	
	private void addTargetsToAction(Actor ap) {
		for (Actor a : ap.getPosition().getActors()) {
			Target target = a.getAsTarget();
			if (isViableForThisAction(target) && !(ap == a) && okAsTargetWhenAbsolutePositions(target, ap)) {
				this.addTarget(target);
			}
		}

		for (GameObject ob : ap.getPosition().getObjects()) {
			if (ob instanceof Target) {
				Target objectAsTarget = (Target)ob;
				if (((Target) ob).isTargetable() && isViableForThisAction(objectAsTarget) && okAsTargetWhenAbsolutePositions(objectAsTarget, ap)) {
					this.addTarget((Target)ob);
				}
			}
		}
		
		addMoreTargets(ap);
	}

	private boolean okAsTargetWhenAbsolutePositions(Target objectAsTarget, Actor ap) {
		if (objectAsTarget instanceof SpriteObject) {
			if (ap.hasAbsolutePosition() && ((SpriteObject) objectAsTarget).hasAbsolutePosition()) {
				if (SpriteObject.distance(ap, (SpriteObject)objectAsTarget) >= 1.0) {
					return false;
				}
			}
		}

		return true;
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
				ActionOption opt;
				if (t instanceof Actor) {
					opt = new ActionOption(((Actor) t).getPublicName(whosAsking));
				} else {
					opt = new ActionOption(t.getName());
				}
				opt.addAll(optlist);
				root.addOption(opt);
			}
		}
		return root;
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
        if (target == null) {
            performingClient.addTolastTurnInfo("What, the target wasn't there? " + failed(gameData, performingClient));
            return;
        }
		if (!target.canBeInteractedBy(performingClient)) {
			performingClient.addTolastTurnInfo("What? " + target.getName() + " can't be targeted! Your action failed.");
			
		} else if (item != null && !itemAvailable(performingClient, item)) {
			performingClient.addTolastTurnInfo("What? the " + item.getPublicName(performingClient) + " was no longer there! Your action failed.");
		
		} else {
			applyTargetingAction(gameData, performingClient, target, item);
			if (requiresProximityToTarget() && target instanceof SpriteObject) {
				performingClient.setCharacter(new InProximityOfTargetOneRoundDecorator(performingClient.getCharacter(),
						(SpriteObject)target, gameData.getRound()));
			}

		}
	}

	protected abstract boolean requiresProximityToTarget();

	protected boolean itemAvailable(Actor performingClient, GameItem it) {
        return performingClient.getItems().contains(it);
    }

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
        try {
            this.target = findTarget(args.get(0), performingClient);
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

	protected GameItem findItem(String string, Actor whosAsking) throws NoSuchThingException {
		for (GameItem g : withWhats) {
            if (g.getFullName(whosAsking).equals(string) || string.contains(g.getPublicName(whosAsking))) {
                return g;
            }

		}
		
		throw new NoSuchThingException("Did not find object for this targeting action.");
	}

	protected Target findTarget(String name, Actor whosAsking) throws NoSuchThingException {
		for (Target c : targets) {
			if (name.contains(c.getName()) || c.getName().contains(name) ||
					(c instanceof Actor && ((Actor) c).getPublicName(whosAsking).contains(name))) {
				if (isViableForThisAction(c)) {
					return c;
				}
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

    public Target getTarget() {
        return target;
    }

    public GameItem getItem() {
        return item;
    }

    public void stripAllTargetsBut(Target target) {
	    targets.removeIf((Target t) -> t != target);
    }

    public void stripAllTargets() {
        targets.clear();
    }

	public void setTarget(Target target) {
		this.target = target;
	}
}
