package model.fancyframe;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.objectactions.SlotMachineAction;
import model.actions.objectactions.WalkUpToElectricalMachineryAction;
import model.items.NoSuchThingException;
import model.items.general.MoneyStack;
import model.objects.general.SlotMachine;
import util.HTMLText;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class SlotMachineFancyFrame extends FancyFrame {
    private final SlotMachine slots;
    private final GameData gameData;
    private final Player player;
    private boolean inUse;
    private int betSize;
    private String winText;
    private List<Sprite> resultingSymbols;

    public SlotMachineFancyFrame(Player performingClient, GameData gameData, SlotMachine slots) {
        super(performingClient.getFancyFrame());
        inUse = false;
        this.slots = slots;
        this.gameData = gameData;
        this.player = performingClient;
        setSize(270, 360);

        buildContent(gameData, performingClient);
    }


    private void buildContent(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();
        if (slots.isPowered()) {
            content.append(HTMLText.makeBox("Yellow", "Black", HTMLText.makeCentered(HTMLText.makeText("Yellow", "SPIN TO WIN!"))));
            StringBuilder table = new StringBuilder();

            for (int i = 0; i <= slots.getPayoutTable().size() / 2; i++) {
                table.append(slots.getPayoutTable().get(i) + " | " + slots.getPayoutTable().get(i + 5) + "<br/>");
            }

            table.append("<br/>");

            content.append(HTMLText.makeCentered(HTMLText.makeText("Yellow", "courier", 2, table.toString())));

            content.append("<br/>");

            StringBuilder symbols = new StringBuilder();
            symbols.append("<table border=\"1\" bgcolor=\"black\"><tr><td><table border=\"1\" bgcolor=\"#E3E3E3\"><tr>");
            if (resultingSymbols != null) {
                for (int i = 0; i < 3; ++i) {
                    symbols.append("<td>" + HTMLText.makeImage(resultingSymbols.get(i)) + "</td>");
                }
            } else if (inUse) {
                for (int i = 0; i < 3; ++i) {
                    symbols.append("<td>" + HTMLText.makeImage(MyRandom.sample(slots.getBlurrySymbols())) + "</td>");
                }
            } else {
                for (int i = 0; i < 3; ++i) {
                    symbols.append("<td>" + HTMLText.makeImage(MyRandom.sample(slots.getAllSymbols())) + "</td>");
                }
            }
            symbols.append("</tr></table></td></tr></table>");

            int actorsDollars = 0;
            try {
                MoneyStack actorsMoney = MoneyStack.getActorsMoney(player);
                actorsDollars = actorsMoney.getAmount();
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
            symbols.append("<br/>");
            for (int i = 0; i < slots.getBetSizes().length; i++) {
                int betSize = slots.getBetSizes()[i];
                if (betSize <= actorsDollars) {
                    symbols.append(HTMLText.makeFancyFrameLink("SET BET " + betSize,
                            HTMLText.makeText("Yellow", "[" + betSize + "] ")));
                } else {
                    symbols.append("[" + betSize + "] ");
                }

                if (i == 3) {
                    symbols.append("<br/>");
                }
            }
            symbols.append(HTMLText.makeText("Yellow", "<br/>BET YOUR $$ HERE!<br/></br/>"));
            content.append(HTMLText.makeCentered(symbols.toString()));

            if (winText != null) {
                content.append(HTMLText.makeBox("Yellow", "Black", HTMLText.makeCentered(HTMLText.makeText("Yellow", winText))));
            } else {
                content.append(HTMLText.makeBox("Black", "Black", "___"));
            }
        } else {
            content.append(HTMLText.makeCentered("<i>No power...</i>"));
        }


        setData("Slot Machine", false, HTMLText.makeColoredBackground("purple", content.toString()));
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("SET BET")) {
            if (player.getPosition() != slots.getPosition()) {
                leaveFancyFrame(gameData, player);
                return;
            }
            this.betSize = Integer.parseInt(event.replace("SET BET ", ""));
            List<String> args = new ArrayList<>();
            args.add("Bet $$ " + betSize);
            inUse = true;
            resultingSymbols = null;
            winText = "GOOD LUCK!";
            SlotMachineAction sma = new SlotMachineAction(slots, this);
            sma.setActionTreeArguments(args, player);
            player.setNextAction(sma);
            readyThePlayer(gameData, player);
            rebuildInterface(gameData, player);
        }
    }

    @Override
    public void rebuildInterface(GameData gameData, Player player) {
        super.rebuildInterface(gameData, player);
        buildContent(gameData, player);
    }


    public void setResult(List<Sprite> result, int payOut) {
        inUse = false;
        if (payOut > 0) {
            this.winText = "YOU WIN $$ " + payOut + "!";
        } else {
            this.winText = "MAYBE NEXT TIME!";
        }
        this.resultingSymbols = result;
        rebuildInterface(gameData, player);
    }
}
