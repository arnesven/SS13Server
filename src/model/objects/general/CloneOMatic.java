package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionGroup;
import model.actions.general.ActionOption;
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

        Action cloneAction = new CloneOMaticAction(cl, this, gameData);

        at.add(cloneAction);

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
            super("Check Charge (" + (int)(charge*100) + "%)", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Checked the Clone-O-Matic";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            performingClient.addTolastTurnInfo("Clone-O-Matic at " + (int)(charge*100) + "% biomass.");
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }

    private class CloneOMaticAction extends Action {

        private final CloneOMatic cloner;
        private List<Action> innerActions = new ArrayList<>();
        private Action selectedAction;

        public CloneOMaticAction(Actor cl, CloneOMatic cloner, GameData gameData) {
            super("Clone-O-Matic", SensoryLevel.OPERATE_DEVICE);
            this.cloner = cloner;
            Action check = new CloneOMatic.CheckChargeAction();
            innerActions.add(check);

            if (!isInUse()) {
                Action stuff = new StuffCorpseIntoClonerAction(cloner, cl);
                if (stuff.getOptions(gameData, cl).numberOfSuboptions() > 0) {
                    innerActions.add(stuff);
                }

                Action clone = new ClonePersonAction(cloner, cl, geneticsConsole);
                if (clone.getOptions(gameData, cl).numberOfSuboptions() > 0) {
                    innerActions.add(clone);
                }
            }
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {

            ActionOption opts = super.getOptions(gameData, whosAsking);

            for (Action a : innerActions) {
                opts.addOption(a.getOptions(gameData, whosAsking));
            }

            return opts;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Fiddled with " + cloner.getPublicName(whosAsking);
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            selectedAction.doTheAction(gameData, performingClient);
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {
            List<String> subList = args.subList(1, args.size());
            for (Action a : innerActions) {
                if (args.get(0).equals(a.getName())) {
                    selectedAction = a;
                    break;
                }
            }
            selectedAction.setArguments(subList, performingClient);
        }
    }
}
