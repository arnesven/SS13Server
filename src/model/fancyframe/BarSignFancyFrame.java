package model.fancyframe;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.objects.consoles.BarSignAction;
import model.objects.consoles.BarSignControl;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BarSignFancyFrame extends ConsoleFancyFrame {
    private final BarSignControl console;
    private String selectedSign;

    public BarSignFancyFrame(Player performingClient, BarSignControl console, GameData gameData) {
        super(performingClient.getFancyFrame(), console, gameData, "#6f664d", "white");
        this.console = console;


        concreteRebuild(gameData, performingClient);
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();
        content.append("<br/>");
        for (Map.Entry<String, Sprite> entry : BarSignControl.getSigns().entrySet()) {
            String fgColor = "yellow";
            String bgColor = "#6f664d";
            if (entry.getKey().equals(selectedSign)) {
                fgColor = bgColor;
                bgColor = "yellow";
            }
            content.append(HTMLText.makeImage(entry.getValue()) + " " + entry.getKey() + " " +
                    HTMLText.makeFancyFrameLink("SET " +  entry.getKey(), HTMLText.makeText(fgColor, bgColor, "[set]")) + "<br/>");
        }

        setData(console.getPublicName(player), false, content.toString());
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("SET")) {
            List<String> args = new ArrayList<>();
            this.selectedSign = event.replace("SET ", "");
            args.add(selectedSign);
            BarSignAction bsa = new BarSignAction(console);
            bsa.setArguments(args, player);
            player.setNextAction(bsa);
            readyThePlayer(gameData, player);
            concreteRebuild(gameData, player);
        }
    }
}
