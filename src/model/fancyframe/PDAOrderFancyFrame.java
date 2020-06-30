package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.UsePDAAction;
import model.items.TraitorItem;
import model.items.general.GameItem;
import model.items.general.PDA;
import model.modes.GameMode;
import model.modes.MixedGameMode;
import model.modes.TraitorGameMode;
import model.modes.objectives.TraitorObjective;
import util.HTMLText;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PDAOrderFancyFrame extends FancyFrame {
    private final PDA pda;
    private String ordered = "";
    private boolean showOrder;
    private boolean otherInfo;

    public PDAOrderFancyFrame(Player p, PDA pda, GameData gameData) {
        super(p.getFancyFrame());
        this.pda = pda;
        showOrder = false;
        otherInfo = false;

        buildContent(gameData, p);
    }

    private void buildContent(GameData gameData, Player p) {
        StringBuilder content = new StringBuilder();
        if (showOrder) {
            content.append("<b>Telecrystals left: " + pda.getTelecrystalsLeft() + "</b><br>");
            for (TraitorItem tit : PDA.getOrderableItems()) {
                GameItem it = (GameItem) tit;
                String text = it.getBaseName();
                String gimme = "[order]";
                if (text.equals(this.ordered)) {
                    gimme = "<b>" + gimme + "</b>";
                }
                content.append(HTMLText.makeImage(it.getSprite(p)) + " " + text);
                if (pda.getTelecrystalsLeft() >= tit.getTelecrystalCost()) {
                    content.append(" (" + tit.getTelecrystalCost() + " TC) ");
                    content.append(HTMLText.makeFancyFrameLink("ORDER " + text, gimme));
                } else {
                    content.append(HTMLText.makeText("gray", "(not enough TC)"));
                }
                content.append("<br/>" + "<i>" + HTMLText.makeText("Yellow", "serif", 2, it.getDescription(gameData, p)) + "</i><br/>");
            }
        } else if (otherInfo) {
            List<Player> traitors = null;
            Map<Player, TraitorObjective> objectives = null;
            if (gameData.getGameMode() instanceof TraitorGameMode) {
                TraitorGameMode traitorMode = (TraitorGameMode) gameData.getGameMode();
                traitors = traitorMode.getTraitors();
                objectives = traitorMode.getObjectives();
            } else if (gameData.getGameMode() instanceof MixedGameMode) {
                traitors = ((MixedGameMode) gameData.getGameMode()).getTraitors();
                objectives = ((MixedGameMode) gameData.getGameMode()).getTraitorObjectives();
            }
                if (traitors != null && objectives != null && traitors.size() > 1) {
                    String others = "The hidden traitors are;<br/>";
                    for (Player p2 : traitors) {
                        if (p != p2) {
                            others += p2.getBaseName() + " (obj; \"" + objectives.get(p2).getText() + "\")" +  "<br/>";
                        }
                    }
                    content.append(others);
                } else {
                    content.append("You are the only traitor.");
                }

        } else {
            content.append(HTMLText.makeFancyFrameLink("OBJECTIVE", "# Show Objective") + "<br/>");
            content.append(HTMLText.makeFancyFrameLink("GOORDER", "# Order Items") + "<br/>");
            content.append(HTMLText.makeFancyFrameLink("OINFO", "# Other Info") + "<br/>");
        }

        setData("PDA - Interface", false, HTMLText.makeColoredBackground("#7E1010",
                HTMLText.makeText("yellow", content.toString())));
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("GOORDER")) {
            showOrder = true;
            buildContent(gameData, player);
        } else if (event.contains("ORDER")) {
            List<String> args  = new ArrayList<>();
            args.add("Order Item");
            this.ordered = event.replace("ORDER ", "");
            args.add(ordered);
            Action usePDA = new UsePDAAction(pda.getTraitorMode(), pda, gameData);
            usePDA.setActionTreeArguments(args, player);
            player.setNextAction(usePDA);
            readyThePlayer(gameData, player);
            buildContent(gameData, player);
        } else if (event.contains("OBJECTIVE")) {
            if (gameData.getGameMode() instanceof TraitorGameMode) {
                TraitorGameMode traitorMode = (TraitorGameMode)gameData.getGameMode();
                traitorMode.setAntagonistFancyFrame(player);
            } else if (gameData.getGameMode() instanceof MixedGameMode) {
                ((MixedGameMode) gameData.getGameMode()).setAntagonistFancyFrame(player);
            }
        } else if (event.contains("OINFO")) {
            otherInfo = true;
            buildContent(gameData, player);
        }
    }
}
