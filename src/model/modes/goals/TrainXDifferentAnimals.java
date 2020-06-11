package model.modes.goals;

import model.GameData;
import model.actions.characteractions.TrainNPCAction;
import model.actions.general.Action;
import model.npcs.NPC;
import model.npcs.animals.Trainable;

import java.util.HashSet;
import java.util.Set;

public class TrainXDifferentAnimals extends DidAnActionGoal {
    private final int diffAnimals;
    private Set<Trainable> trained = new HashSet<>();

    public TrainXDifferentAnimals(int i) {
        super(i, TrainNPCAction.class);
        this.diffAnimals = i;
    }

    @Override
    protected String getNoun() {
        return "different animals";
    }

    @Override
    protected String getVerb() {
        return "train";
    }

    @Override
    protected void actionDone(Action a) {
        if (a instanceof TrainNPCAction) {
            if (((TrainNPCAction) a).getTarget() instanceof Trainable) {
                trained.add(((TrainNPCAction) a).getTarget());
            }
        }
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return super.isCompleted(gameData) && trained.size() >= diffAnimals;
    }
}
