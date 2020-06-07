package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.SlotMachineAction;
import model.actions.objectactions.WalkUpToElectricalMachineryAction;
import model.fancyframe.FancyFrame;
import model.fancyframe.SlotMachineFancyFrame;
import model.items.NoSuchThingException;
import model.items.general.ItemStackDepletedException;
import model.items.general.MoneyStack;
import model.map.rooms.BarRoom;
import model.map.rooms.Room;
import util.HTMLText;
import util.MyRandom;

import java.util.*;

/**
 * Created by erini02 on 17/11/16.
 */
public class SlotMachine extends ElectricalMachinery {
    private boolean alreadyBrokeOnce = false;
    private List<Sprite> symbolSet;
    private Set<Sprite> blurrySet;
    private List<String> icons;
    private int[] payouts = new int[]{3, 5, 13, 25, 50, 69, 77, 100, 169};

    public SlotMachine(Room barRoom) {
        super("Slot Machine", barRoom);
        symbolSet = makeSymbolList();
        blurrySet = makeBlurrySet();
        icons = allIcons();
    }


    @Override
    public double getPowerConsumption() {
        return 0.000350; // 350 W
    }

    public static int[] getBetSizes() {
        return new int[]{1, 3, 5, 10, 100, 200, 1000};
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (cl instanceof Player) {
            at.add(new WalkUpToElectricalMachineryAction(gameData, (Player)cl, this) {
                @Override
                protected FancyFrame getFancyFrame(GameData gameData, Actor performingClient) {
                    return new SlotMachineFancyFrame((Player)performingClient, gameData, SlotMachine.this);
                }
            });
        } else {
            at.add(new SlotMachineAction(this));
        }
    }

    public void play(GameData gameData, Actor performingClient, int bettedAmount, SlotMachineFancyFrame ff) {
        MoneyStack m = null;
        try {
            m = MoneyStack.getActorsMoney(performingClient);
            m.subtractFrom(bettedAmount);
        } catch (ItemStackDepletedException e) {
            performingClient.getItems().remove(m);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        gameData.getGameMode().getBank().addToStationMoney(bettedAmount, this);


        performingClient.addTolastTurnInfo("You played on the slot machine.");
        List<Sprite> result = generateFancyFrameResult();


        int payOut = getPayout(result, bettedAmount, symbolSet, payouts);
        if (payOut > gameData.getGameMode().getBank().getStationMoney()) {
            performingClient.addTolastTurnInfo("Station funds insufficient for maximum payout.");
            payOut = gameData.getGameMode().getBank().getStationMoney();
        }
        if (payOut > 0) {
            try {
                MoneyStack.getActorsMoney(performingClient).addTo(payOut);
            } catch (NoSuchThingException e) {
                performingClient.addItem(new MoneyStack(payOut), this);
            }
        }
        gameData.getGameMode().getBank().subtractFromStationMoney(payOut, this);
        ff.setResult(result, payOut);
    }

    @Override
    public void thisJustBroke(GameData gameData) {
        super.thisJustBroke(gameData);
        if (!alreadyBrokeOnce) {
            alreadyBrokeOnce = true;
            getPosition().addItem(new MoneyStack(MyRandom.nextInt(200)+100));
            getPosition().addItem(new MoneyStack(MyRandom.nextInt(100)+50));
            getPosition().addItem(new MoneyStack(MyRandom.nextInt(50)+20));
        }

    }

    private static int getPayout(List<?> result, int bettedAmount, List<?> reference, int[] payouts) {
        int factor = 0;
        for (Object str : reference) {
            if (str.equals(result.get(0))) {
                break;
            }
            factor++;
        }

        if (result.get(0).equals(result.get(1)) && result.get(1).equals(result.get(2))) {
            return bettedAmount * payouts[factor];
        }

        if (result.get(0).equals(result.get(1)) || result.get(1).equals(result.get(2))) {
            return bettedAmount * 2;
        }

        return 0;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("slotmachine", "vending2.png", 6, 13, this);
    }

    private  List<String> generateResult() {
        List<String> res = new ArrayList<>();
        List<String> all = new ArrayList<>();
        all.addAll(allIcons());
        for (int i = 3; i >= 0; --i) {
            res.add(MyRandom.sample(all));
        }

        return res;
    }


    private List<Sprite> generateFancyFrameResult() {
        List<Sprite> result = new ArrayList<>();
        List<Sprite> tmp = new ArrayList<>();
        tmp.addAll(symbolSet);
        for (int i = 3; i > 0; --i) {
            result.add(MyRandom.sample(tmp));
        }
        return result;
    }


    private List<String> allIcons() {
        List<String> set = new ArrayList<>();
        String font = "courier";
        int size = 3;
        set.add(HTMLText.makeText("gray", font, size, "BAR"));
        set.add(HTMLText.makeText("red", font, size, "TOM"));
        set.add(HTMLText.makeText("red", font, size, "_FX"));

        set.add(HTMLText.makeText("white", font, size, "_MK"));
        set.add(HTMLText.makeText("Yellow", font, size, "_TK"));
        set.add(HTMLText.makeText("black", font, size, "CAT"));

        set.add(HTMLText.makeText("orange", font, size, "_7_"));
        set.add(HTMLText.makeText("Yellow", font, size, "CUP"));
        set.add(HTMLText.makeText("#00bdff", font, size, "s13"));


        return set;
    }

    public List<Sprite> getAllSymbols() {
        return symbolSet;
    }

    private List<Sprite> makeSymbolList() {
        List<Sprite> set = new ArrayList<>();
        set.add(new Sprite("crowbarslotssymbol", "slotmachine.png", 6, null));
        set.add(new Sprite("tomatoslotssymbol", "slotmachine.png", 1, null));
        set.add(new Sprite("fireextslotssymbol", "slotmachine.png", 5, null));
        set.add(new Sprite("medkitslotssymbol", "slotmachine.png", 3, null));
        set.add(new Sprite("toolkitslotssymbol", "slotmachine.png", 4, null));
        set.add(new Sprite("catslotssymbol", "slotmachine.png", 7, null));
        set.add(new Sprite("sevenslotssymbol", "slotmachine.png", 8, null));
        set.add(new Sprite("trophyslotssymbol", "slotmachine.png", 2, null));
        set.add(new Sprite("ss13slotssymbol", "slotmachine.png", 0, null));
        return set;
    }

    public Set<Sprite> getBlurrySymbols() {
        return blurrySet;
    }


    private Set<Sprite> makeBlurrySet() {
        Set<Sprite> set = new HashSet<>();
        set.add(new Sprite("blurry1slotssymbol", "slotmachine.png", 0, 1, null));
        set.add(new Sprite("blurry2slotssymbol", "slotmachine.png", 1, 1, null));
        set.add(new Sprite("blurry3slotssymbol", "slotmachine.png", 2, 1, null));
        set.add(new Sprite("blurry4slotssymbol", "slotmachine.png", 3, 1, null));
        return set;
    }

    public List<String> getPayoutTable() {
        List<String> res = new ArrayList<>();
        for (int i = getAllSymbols().size()-1; i >= 0; --i) {
            res.add(icons.get(i) + " " + icons.get(i) + " " + icons .get(i) + " = x" + payouts[i]);
        }
        res.add(HTMLText.makeText("Green", "_X_ _X_ _Y_") + " = x2");
        res.add(HTMLText.makeText("Pink", "_Y_ _X_ _X_") + " = x2");
        return res;
    }
}
