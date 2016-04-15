package model.objects.consoles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.objectactions.GeneTIXAction;
import model.map.Room;
import model.mutations.Mutation;

public class GeneticsConsole extends Console {

	public static final String BLAST_STRING = "The syringe was blasted with radiation.";
	private List<Mutation> knownMutations = new ArrayList<>();
	
	public GeneticsConsole(Room r) {
		super("GeneTIX-61", r);
	}

	@Override
	protected void addActions(GameData gameData, Player cl, ArrayList<Action> at) {
        Action cons = new GeneTIXAction(gameData, cl, this);
        if (cons.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(cons);
        }
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

}
