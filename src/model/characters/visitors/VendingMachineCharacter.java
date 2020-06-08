package model.characters.visitors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.characters.general.RobotCharacter;
import model.items.NoSuchThingException;
import model.items.foods.Banana;
import model.items.foods.Doughnut;
import model.items.foods.NukaCola;
import model.items.foods.SpaceCheetos;
import model.items.general.*;
import model.items.weapons.Weapon;
import model.objects.general.JunkVendingMachine;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachineCharacter extends RobotCharacter {

    private Map<GameItem, Integer> costs;

    public VendingMachineCharacter() {
        super("Vending Machine", 0, 1.0);
    }

    @Override
    public List<GameItem> getStartingItems() {
        List<GameItem> list = new ArrayList<>();
        list.add(new Doughnut(null));
        list.add(new Banana(null));
        list.add(new PackOfSmokes());
        list.add(new ZippoLighter());
        list.add(new NukaCola(null));
        list.add(new SpaceCheetos(null));


        if (costs == null) {
            costs = new HashMap<>();
            for (GameItem it : list) {
                if (!(it instanceof MoneyStack)) {
                    costs.put(it, MyRandom.nextInt(it.getCost() * 2)+1);
                }
            }
        }

        return list;
    }

    @Override
    public void addActionsForActorsInRoom(GameData gameData, Actor otherActor, ArrayList<Action> at) {
        super.addActionsForActorsInRoom(gameData, otherActor, at);
        if (getItems().size() > 0) {
            at.add(new FakeUseVendingMachineAction(getActor()));
        }
    }

    @Override
    public Sprite getNormalSprite(Actor whosAsking) {
        JunkVendingMachine jvm = new JunkVendingMachine(null);
        if (!(whosAsking instanceof Player)) {
            return super.getNormalSprite(whosAsking);
        }
        return jvm.getSprite((Player)whosAsking);
    }

    @Override
    public GameCharacter clone() {
        return new VendingMachineCharacter();
    }

    @Override
    public Weapon getDefaultWeapon() {
        return Weapon.FLYING_CREDIT;
    }

    private class FakeUseVendingMachineAction extends Action {

        private final Actor vendingGuy;

        private GameItem selected;

        public FakeUseVendingMachineAction(Actor vendingMachineGuy) {
            super("Use Vending Machine", SensoryLevel.OPERATE_DEVICE);
            this.vendingGuy = vendingMachineGuy;

        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Used Vending Machine";
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opts = super.getOptions(gameData, whosAsking);
            for (GameItem it : costs.keySet()) {
                opts.addOption(it.getBaseName() + " ($$ " + costs.get(it) + ")");
            }

            return opts;
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            try {
                MoneyStack st = MoneyStack.getActorsMoney(performingClient);
                Logger.log(st + " " + costs + " " + selected + " " + costs.get(selected));
                if (st.getAmount() >= costs.get(selected)) {
                    performingClient.addTolastTurnInfo("You got a " + selected.getBaseName() + " from the Vending Machine.");
                    vendingGuy.addTolastTurnInfo(performingClient.getPublicName() +
                            " bought your " + selected.getBaseName() + " for $$ " + costs.get(selected));
                    performingClient.addItem(selected, vendingGuy.getAsTarget());
                    vendingGuy.getItems().remove(selected);
                    try {
                        st.subtractFrom(costs.get(selected));
                    } catch (ItemStackDepletedException e) {
                        performingClient.getItems().remove(st);
                    }

                    try {
                        MoneyStack vendorsMoney = MoneyStack.getActorsMoney(vendingGuy);
                        vendorsMoney.addTo(costs.get(selected));
                    } catch (NoSuchThingException e) {
                        vendingGuy.addItem(new MoneyStack(costs.get(selected)), performingClient.getAsTarget());
                    }


                }

            } catch (NoSuchThingException e) {
                performingClient.addTolastTurnInfo("You have no money, " + failed(gameData, performingClient));
            }





        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {
            for (GameItem it : costs.keySet()) {
                if (args.get(0).equals(it.getBaseName() +  " ($$ " + costs.get(it) + ")")) {
                    selected = it;
                }
            }
        }
    }
}
