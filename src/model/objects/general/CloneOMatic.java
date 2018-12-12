package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionGroup;
import model.actions.general.SensoryLevel;
import model.actions.objectactions.ClonePersonAction;
import model.actions.objectactions.StuffCorpseIntoClonerAction;
import model.map.rooms.Room;
import model.objects.consoles.GeneticsConsole;

import java.util.ArrayList;
import java.util.List;

public class CloneOMatic extends ElectricalMachinery {
    private final GeneticsConsole geneticsConsole;
    private boolean justStuffed;
    private List<Actor> storedActors;
    private double charge;

    public CloneOMatic(Room r, GeneticsConsole gc) {
        super("Clone-O-Matic", r);
        this.geneticsConsole = gc;
        storedActors = new ArrayList<>();
        charge = 0.25;
        justStuffed = false;
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

        //TODO: combine these three into one "Clone-O-Matic action"
        Action check = new CloneOMatic.CheckChargeAction();
        at.add(check);

        if (!isInUse()) {
            Action stuff = new StuffCorpseIntoClonerAction(this, cl);
            if (stuff.getOptions(gameData, cl).numberOfSuboptions() > 0) {
                at.add(stuff);
            }

            Action clone = new ClonePersonAction(this, cl, geneticsConsole);
            if (clone.getOptions(gameData, cl).numberOfSuboptions() > 0) {
                at.add(clone);
            }
        }

    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isInUse()) {
            return new Sprite("clonerinuse", "cloning.png", 1, 0);
        }
        if (justStuffed()) {
            return new Sprite("clonerstuffed", "cloning.png", 5, 0);
        }
        return new Sprite("cloner", "cloning.png", 0, 0);
    }

    private boolean justStuffed() {
        return justStuffed;
    }

    public void setJustStuffed(boolean justStuffed) {
        this.justStuffed = justStuffed;
    }

    public void addCharge(double v) {
        this.charge += v;
    }

    public void storeActor(Actor target) {
        storedActors.add(target);
    }

    public List<Actor> getStoredActors() {
        return storedActors;
    }

    public double getCharge() {
        return charge;
    }

    private class CheckChargeAction extends Action {

        public CheckChargeAction() {
            super("Check Charge " + charge, SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Checked the Clone-O-Matic";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {

        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
