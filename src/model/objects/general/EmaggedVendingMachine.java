package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.items.HandCuffs;
import model.items.general.GameItem;
import model.items.general.Grenade;
import model.items.weapons.Flamer;
import model.items.weapons.Shotgun;
import model.items.weapons.StunBaton;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;
import model.objects.general.VendingMachine;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 15/12/16.
 */
public class EmaggedVendingMachine extends ElectricalMachinery {
    private final VendingMachine inner;
    private final ArrayList<GameItem> newSelection;
    private final ArrayList<String> selectionNames = new ArrayList<>();

    public EmaggedVendingMachine(VendingMachine vending) {
        super(vending.getBaseName(), vending.getPosition());
        this.inner = vending;
        newSelection = new ArrayList<GameItem>();
        newSelection.add(new StunBaton());
        newSelection.add(new Grenade());
        newSelection.add(new Flamer());
        newSelection.add(new HandCuffs());
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(inner.getSprite(whosAsking));
        sprs.add(new Sprite("emaggedfix", "vending2.png", 2, 4));
        return new Sprite("emaggedq"+inner.getBaseName(), "human.png", 0, 0, 32, 32, sprs);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        ArrayList<Action> before = new ArrayList<>();
        before.addAll(at);
        inner.addSpecificActionsFor(gameData, cl, before);
        before.removeAll(at);



        if (before.size() > 0) {
            Action a = before.get(0);
            at.add(new EmaggedVendingAction(a));

        }
    }

    private class EmaggedVendingAction extends Action {
        private final Action innerAction;
        private String selected = null;

        public EmaggedVendingAction(Action a) {
            super(a.getName(), a.getSense());
            innerAction = a;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Fiddled with Vending Machine";
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opt = innerAction.getOptions(gameData, whosAsking);

            for (ActionOption opts : opt.getSuboptions()) {
                String newName = opts.getName() + "*";
                opts.setName(newName);
                selectionNames.add(newName);
                if (selectionNames.size() == newSelection.size()) {
                    break;
                }
            }

            return opt;
        }

        @Override
        public void doTheAction(GameData gameData, Actor performingClient) {
            List<GameItem> before = new ArrayList<>();
            before.addAll(performingClient.getItems());

            innerAction.doTheAction(gameData, performingClient);

            List<GameItem> after = new ArrayList<>();
            after.addAll(performingClient.getItems());

            after.removeAll(before);

            while (after.size() > 0) {
                performingClient.getItems().remove(after.get(0));
                after.remove(0);
                if (after.size() == 0) {
                    performingClient.addItem(newSelection.get(selectionNames.indexOf(selected)).clone(), null);
                }
            }
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            innerAction.doTheAction(gameData, performingClient);
            Logger.log(Logger.CRITICAL, "Execute in emagged vending action, should not have been called!");
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {
            List<String> newArgs = new ArrayList<>();
            for (String arg : args) {
                if (arg.contains("*")) {
                    Logger.log("Removed asterisk");
                    selected = arg;
                    arg = arg.replaceAll("\\*", "");
                }
                Logger.log("arg is now" + arg);
                newArgs.add(arg);
            }
            innerAction.setArguments(newArgs, performingClient);
        }
    }
}
