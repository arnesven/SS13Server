package model.fancyframe;

import model.Bank;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.SellCrateAction;
import model.objects.consoles.Console;
import model.objects.consoles.MarketConsole;
import model.objects.general.CrateObject;
import model.objects.general.GameObject;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

public class MarketConsoleFancyFrame extends ConsoleFancyFrame {
    private final MarketConsole console;
    private String selling = "";

    public MarketConsoleFancyFrame(Player performingClient, GameData gameData, MarketConsole console) {
        super(performingClient.getFancyFrame(), console, gameData, "#00198a", "#a3f4ff");
        this.console = console;


        buildContent(performingClient, gameData);
    }

    private void buildContent(Player performingClient, GameData gameData) {
        StringBuilder content = new StringBuilder();
        content.append("_______________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE HISTORY", "[history]"));
        content.append("<br><b>Station Funds:</b> $$ " + gameData.getGameMode().getBank().getStationMoney() + "<br/>");

        for (GameObject obj : console.getPosition().getObjects()) {
            if (obj instanceof CrateObject && ((CrateObject) obj).canBeSold()) {
                String text = obj.getPublicName(performingClient) + " for $$ " + console.getValueFor((CrateObject)obj, gameData);
                String buttonText = "[sell]";
                if (selling.equals(text)) {
                    buttonText = "<b>" + buttonText + "</b>";
                }
                content.append(HTMLText.makeImage(obj.getSprite(performingClient)) + " " +
                        HTMLText.makeFancyFrameLink("SELL " + text, buttonText) + " " +
                        text + "<br/>");
            }
        }

        setData(console.getPublicName(performingClient), false, content.toString());
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        buildContent(player, gameData);
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("SELL")) {
            List<String> args = new ArrayList<>();
            this.selling = event.replace("SELL ", "");
            args.add(selling);
            Action sellAction = new SellCrateAction(gameData, player, console);
            sellAction.setActionTreeArguments(args, player);
            player.setNextAction(sellAction);
            readyThePlayer(gameData, player);
            rebuildInterface(gameData, player);
        }
    }

    @Override
    public void doAtEndOfTurn(GameData gameData, Player actor) {
        this.selling = "";
    }
}
