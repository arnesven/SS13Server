package model.objects.consoles;

import java.util.*;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.actions.general.Action;
import model.actions.objectactions.GeneTIXAction;
import model.characters.crew.GeneticistCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.GeneticsConsoleFancyFrame;
import model.map.rooms.Room;
import model.mutations.Mutation;
import model.mutations.MutationFactory;

public class GeneticsConsole extends Console {

	public static final String BLAST_STRING = "The syringe was blasted with radiation.";
	private List<Mutation> knownMutations = new ArrayList<>();
	private List<Actor> storedActors = new ArrayList<>();
	private Map<Integer, Set<Mutation>> randomMutationsMap = new HashMap<>();
	
	public GeneticsConsole(Room r) {
		super("GeneTIX-61", r);
	}

	@Override
	protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        Action cons = new GeneTIXAction(gameData, cl, this);
        if (cons.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(cons);
        }
        if (cl instanceof Player) {
        	at.add(new SitDownAtConsoleAction(gameData, this, (Player)cl) {
				@Override
				protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
					return new GeneticsConsoleFancyFrame(performingClient, console, gameData);
				}
			});
		}

    }

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("geneticsconsole", "Cryogenic2.png", 13, 9, this);
    }

	public void addKnownMutation(Mutation known) {
		for (Mutation m : knownMutations) {
			if (m.getClass() == known.getClass()) {
				return;
			}
		}
		knownMutations.add(known);
	}
	
	public Iterator<Mutation> getKnownMutationsIterator() {
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

	public static boolean isGeneticist(Actor whosAsking) {
		return whosAsking.getCharacter().checkInstance(new InstanceChecker() {
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof GeneticistCharacter;
			}
		});
	}

	public Set<Mutation> getRandomMutationsForRound(GameData gameData) {
		Set<Mutation> set = randomMutationsMap.get(gameData.getRound());
		if (randomMutationsMap.get(gameData.getRound()) == null) {
			set = new HashSet<>();
			for (int i = 3; i > 0; --i) {
				set.add(MutationFactory.getRandomMutation(gameData));
			}
			randomMutationsMap.put(gameData.getRound(), set);
		}
		return set;
	}
}
