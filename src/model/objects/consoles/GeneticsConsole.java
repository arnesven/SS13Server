package model.objects.consoles;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.objectactions.GeneTIXAction;
import model.actions.objectactions.MutateSyringeAction;
import model.items.GameItem;
import model.items.Syringe;
import model.map.Room;
import model.objects.consoles.Console;
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

	public List<Mutation> getKnownMutations() {
		return knownMutations;
	}

}
