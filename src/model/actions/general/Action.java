package model.actions.general;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.events.Experienceable;
import util.Logger;
import util.MyStrings;

/**
 * @author erini02
 * Represent an action which can be taken by a client.
 * TODO: NPCs should also be able to take actions.
 */
public abstract class Action extends Experienceable implements Serializable {
	
	private static final String FAILED_STRING = "Your action failed.";
	private String name;
	private SensoryLevel senses;
	protected Actor performer;
    private boolean wasDeadBeforeApplied = false;
    private List<String> savedArgs;
    private boolean actionFailed;


    /**
	 * @param name the name of this action
	 * @param senses if the action is stealthy it will not be displayed to hidden players standing in that room.
	 */
	public Action(String name, SensoryLevel senses) {
		this.name = name;
		this.senses = senses;
		actionFailed = false;
	}


    public void setName(String string) {
		this.name = string;
	}

	@Override
	final public String toString() {
		throw new UnsupportedOperationException("toString() should not be called on Actions!");
	}
	
	public String getName() {
		return name;
	}

	public void doTheAction(GameData gameData, Actor performingClient) {
		this.execute(gameData, performingClient);
		this.performer = performingClient;
		performingClient.getPosition().addToActionsHappened(this);
	}
	
	/**
	 * Gets the string which is a textual description of the action to all
	 * hidden clients in the same room.
	 * DoTheAction must have been called before!
	 * @param whosAsking the ActionPerformer who is performing the action
	 * @return the textual description of the action (as seen by bystanders).
	 */
	public String getDescription(Actor whosAsking) {
		if (performer == null) {
            //return "someone FARTED";
			throw new IllegalStateException("doTheAction was not called before call to getDescription!");
        }
        String middle = " ";
        if (actionFailed) {
		    middle = " tried to ";
        }
		return performer.getPublicName() + middle + this.getVerb(whosAsking).toLowerCase() + "";
	}

	/**
	 * Gets the name of the aciton as a verb, passed tense
	 * @return the verb as a string.
	 */
	protected abstract String getVerb(Actor whosAsking);

	/**
	 * Returns true if cl is a argument for this action.
	 * To be overloaded by specific actions.
	 * @param t, the client to check if it is an argument of this action
	 * @return true if the client is an argument of this action, false otherwise.
	 */
	protected boolean isArgumentOf(Target t) {
		return false;
	}

	/**
	 * This puts the selected action into effect. Remember, if
	 * this action uses an item, it may be gone before this action
	 * executes, in which case the action will fail.
	 * Do not call this method before calling setArguments!
	 * @param gameData the game's data
	 * @param performingClient who is performing this action
	 */
	protected abstract void execute(GameData gameData, Actor performingClient);

	/**
	 * Sets the arguments with which this action was executed
	 * MUST be called before execute
	 * @param args the arguments
	 */
	protected abstract void setArguments(List<String> args, Actor performingClient);

	public SensoryLevel getSense() {
		return senses;
	}

	public void setSense(SensoryLevel sensedAs) {
		this.senses = sensedAs;
	}

	public String getDistantDescription(Actor whosAsking) {
		return "";
	}

	public void lateExecution(GameData gameData, Actor performingClient) {	}

	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		return new ActionOption(getName());
	}


    public void experienceNear(Player p) {
        String text = getDescription(p);
        if (!text.contains(p.getPublicName()) && !text.toLowerCase().contains("you")) {
            p.addTolastTurnInfo(text);
        }
    }

    public void experienceFar(Player p) {
        if (p.getCharacter().doesPerceive(this)) {
            String text = getDistantDescription(p);
            p.addTolastTurnInfo(text);
        }
    }

    public Actor getPerformer() {
        return performer;
    }

    public void uniquefy(GameData gameData, Actor a) {
        //Logger.log("   Uniqifying action for " + this.getName());
        getOptions(gameData, a).uniquefy();
    }

    public static void uniquefiyList(GameData gameData, List<Action> at, Actor act) {
        // Logger.log("Uniquifying list for " + act.getBaseName());
            for (Action a : at) {
                a.uniquefy(gameData, act);
            }
    }

    public void setDeadBeforeApplied() {
        this.wasDeadBeforeApplied = true;
    }

    public boolean wasDeadBeforeApplied() {
        return this.wasDeadBeforeApplied;
    }


    public static String makeActionListString(GameData gameData, List<Action> list, Actor whosAsking) {
	    StringBuilder result = new StringBuilder("{");
        for (Action a : list) {
            ActionOption opts = a.getOptions(gameData, whosAsking);
            opts.uniquefy();
            result.append(opts.makeBracketedString());
        }
        result.append("}");
        return result.toString();
    }


    public static String makeActionListStringSpecOptions(GameData gameData, List<Action> list, Player whosAsking) {
        String result = "{";
        for (Action a : list) {
            ActionOption opts = a.getOptions(gameData, whosAsking);
            opts.uniquefy();

            if (a.hasSpecialOptions()) {
                result += opts.makeBracketedString();
            } else {
                result += a.getName() + "{}";
            }
        }
        result += "}";
        return result;
    }

    public boolean hasSpecialOptions() {
	    return true;
    }

    public void setOverlayArguments(List<String> args,  Actor performingClient) {
	    saveArgs(args);
	    setArguments(args, performingClient);
    }

    private void saveArgs(List<String> args) {
        savedArgs = new ArrayList<>();
        for (String arg : args) {
            savedArgs.add(arg);
        }
    }

    public void setInventoryArguments(List<String> newArgs, Player player) {
	    saveArgs(newArgs);
        setArguments(newArgs, player);
    }

    public void setActionTreeArguments(List<String> args, Actor performer) {
	    saveArgs(args);
	    setArguments(args, performer);
    }

    public boolean isAmongOptions(GameData gameData, Actor whosAsking, String publicName) {
        for (ActionOption opts : getOptions(gameData, whosAsking).getSuboptions()) {
            if (opts.getName().contains(publicName)) {
                return true;
            }
        }
        return false;
    }


    public boolean doesSetPlayerReady() {
        return true;
    }

    public boolean doesCommitThePlayer() { return false;}

    public String getFullName() {
	    String argString = "";
	    if (savedArgs != null) {
            String args = MyStrings.join(savedArgs, ", ");
            argString = ", " + args.substring(1, args.length()-1);
        }
        return getName() +  argString;
    }

    public Sprite getAbilitySprite() {
        return new Sprite("abilitysprite", "interface.png", 15, 16, null);
    }

    public String failed(GameData gameData, Actor whoFailed) {
	    this.actionFailed = true;
	    return FAILED_STRING;
    }
}
