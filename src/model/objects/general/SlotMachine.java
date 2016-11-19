package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.SlotMachineAction;
import model.items.NoSuchThingException;
import model.items.general.MoneyStack;
import model.map.BarRoom;
import util.HTMLText;
import util.MyRandom;

import java.util.*;

/**
 * Created by erini02 on 17/11/16.
 */
public class SlotMachine extends ElectricalMachinery {
    public SlotMachine(BarRoom barRoom) {
        super("Slot Machine", barRoom);

    }

    public static int[] getBetSizes() {
        return new int[]{1, 3, 5, 10, 100, 200, 1000};
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        Action slotAction = new SlotMachineAction(this);
        if (slotAction.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(slotAction);
        }
    }

    public void play(Actor performingClient, int bettedAmount) {
        performingClient.addTolastTurnInfo("You put in $$ " + bettedAmount + " and pulled the handle...");
        List<String> result = generateResult();

        StringBuffer buf = new StringBuffer("");
        buf.append(result.get(0));
        buf.append(result.get(1));
        buf.append(result.get(2));
        performingClient.addTolastTurnInfo(buf.toString());
        int payOut = getPayout(result, bettedAmount);
        if (payOut > 0) {
            performingClient.addTolastTurnInfo("You win $$ " + payOut + "!");
            try {
                MoneyStack.getActorsMoney(performingClient).addTo(payOut);
            } catch (NoSuchThingException e) {
                performingClient.addItem(new MoneyStack(payOut), this);
            }
        }


    }

    private int getPayout(List<String> result, int bettedAmount) {
        int factor = 1;
        for (String str : allIcons()) {
            if (str.equals(result.get(0))) {
                break;
            }
            factor++;
        }

        if (result.get(0).equals(result.get(1)) && result.get(1).equals(result.get(2))) {
            return bettedAmount * factor;
        }

        if (result.get(0).equals(result.get(1)) || result.get(1).equals(result.get(2))) {
            return bettedAmount * 2;
        }

        return 0;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("slotmachine", "vending2.png", 6, 13);
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

    private Collection<String> allIcons() {
        List<String> set = new ArrayList<>();
        String font = "courier";
        int size = 10;
        set.add(HTMLText.makeText("brown", font, size, "☕"));
        set.add(HTMLText.makeText("red", font, size, "♥"));
        set.add(HTMLText.makeText("purple", font, size, "☯"));

        set.add(HTMLText.makeText("yellow", font, size, "⛃"));
        set.add(HTMLText.makeText("green", font, size, "♻"));
        set.add(HTMLText.makeText("orange", font, size, "❼"));

        set.add(HTMLText.makeText("black", font, size, "☠"));
        set.add(HTMLText.makeText("Chartreuse", font, size, "⚑"));
        set.add(HTMLText.makeText("Cyan", font, size, "☂"));


        return set;
    }
}
