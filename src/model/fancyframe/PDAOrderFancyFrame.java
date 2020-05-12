package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.UsePDAAction;
import model.items.general.GameItem;
import model.items.general.PDA;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

public class PDAOrderFancyFrame extends FancyFrame {
    private final PDA pda;
    private String ordered = "";

    public PDAOrderFancyFrame(Player p, PDA pda, GameData gameData) {
        super(p.getFancyFrame());
        this.pda = pda;

        buildContent(gameData, p);
    }

    private void buildContent(GameData gameData, Player p) {
        StringBuilder content = new StringBuilder();
        content.append("Uses left: " + pda.getUsesLeft() + "<br>");
        for (GameItem it : PDA.getOrderableItems()) {
            String text = it.getBaseName();
            String gimme = "[order]";
            if (text.equals(this.ordered)) {
                gimme = "<b>" + gimme + "</b>";
            }
            content.append(HTMLText.makeImage(it.getSprite(p)) + " " + text + " " +
                           HTMLText.makeFancyFrameLink("ORDER " + text, gimme) + "<br/>" +
                                   "<i>" + HTMLText.makeText("Yellow", "serif", 2, it.getDescription(gameData, p)) + "</i><br/>");
        }


        setData("PDA - Order Items", false, HTMLText.makeColoredBackground("#7E1010",
                HTMLText.makeText("yellow", content.toString())));
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("ORDER")) {
            List<String> args  = new ArrayList<>();
            args.add("Order Item");
            this.ordered = event.replace("ORDER ", "");
            args.add(ordered);
            Action usePDA = new UsePDAAction(pda.getTraitorMode(), pda, gameData);
            usePDA.setActionTreeArguments(args, player);
            player.setNextAction(usePDA);
            readyThePlayer(gameData, player);
            buildContent(gameData, player);
        }
    }
}
