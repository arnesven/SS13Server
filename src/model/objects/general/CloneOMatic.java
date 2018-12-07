package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.StuffCorpseIntoClonerAction;
import model.map.rooms.Room;
import model.objects.consoles.GeneticsConsole;

import java.util.ArrayList;
import java.util.List;

public class CloneOMatic extends ElectricalMachinery {
    private final GeneticsConsole geneticsConsole;
    private List<Actor> storedActors;
    private double charge;

    public CloneOMatic(Room r, GeneticsConsole gc) {
        super("Clone-O-Matic", r);
        this.geneticsConsole = gc;
        storedActors = new ArrayList<>();
        charge = 0.5;
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        Action stuff = new StuffCorpseIntoClonerAction(this, this, cl);
        if (stuff.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(stuff);
        }


    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isInUse()) {
            return new Sprite("cloner", "cloning.png", 1, 0);
        }
        return new Sprite("cloner", "cloning.png", 0, 0);
    }

    public void addCharge(double v) {
        this.charge += v;
    }

    public void storeActor(Actor target) {
        storedActors.add(target);
    }
}
