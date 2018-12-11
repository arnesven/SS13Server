package model.objects.consoles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.GeneTIXAction;
import model.map.rooms.Room;
import model.mutations.Mutation;

public class GeneticsConsole extends Console {

	public static final String BLAST_STRING = "The syringe was blasted with radiation.";
	private List<Mutation> knownMutations = new ArrayList<>();
	private List<Actor> storedActors = new ArrayList<>();
	
	public GeneticsConsole(Room r) {
		super("GeneTIX-61", r);
	}

	@Override
	protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        Action cons = new GeneTIXAction(gameData, cl, this);
        if (cons.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(cons);
        }
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("geneticsconsole", "computer2.png", 11, 7);
    }

	public void addKnownMutation(Mutation known) {
		for (Mutation m : knownMutations) {
			if (m.getClass() == known.getClass()) {
				return;
			}
		}
		knownMutations.add(known);
	}
	
	public Iterator<Mutation> getKwonMutationsIterator() {
		return knownMutations.iterator();
	}

	public int nuOfKnown() {
		return knownMutations.size();
	}

    public void storeDNA(Actor originalActor) {
        storedActors.add(originalActor);
    }

    public List<Actor> getStoredActors() {
	    return storedActors;
    }
}
