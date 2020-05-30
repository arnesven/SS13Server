package model.fancyframe;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.decorators.StunningSparksAnimationDecorator;
import model.events.FlashEvent;
import model.events.ambient.ElectricalFire;
import model.events.damage.ElectricalDamage;
import model.events.damage.SonicDamage;
import model.objects.monolith.MonolithExperimentsConsole;
import util.HTMLText;

import java.util.List;
import java.util.Scanner;

public class MonolithExperimentsConsoleFancyFrame extends ConsoleFancyFrame {
    private final MonolithExperimentsConsole console;

    public MonolithExperimentsConsoleFancyFrame(Player performingClient, GameData gameData, MonolithExperimentsConsole monolithExperimentsConsole) {
        super(performingClient.getFancyFrame(), monolithExperimentsConsole, gameData, "#79E5FF", "black");
        this.console = monolithExperimentsConsole;
        buildContent(gameData, performingClient);
    }

    private void buildContent(GameData gameData, Player performingClient) {
        StringBuilder content = new StringBuilder();

        content.append("<br/>");
        if (!console.getMonolith().isCoverUp()) {
            content.append("WARNING! GLASS COVER IS UP!<br/>");
        }
        content.append("Select test:<br/>");
        int index = 1;
        for (String test : console.getTestNames()) {
            content.append(index + ". " + test + " Test<br/>");
            index++;
        }
        content.append("0. Cancel Current Test<br/>");
        content.append("<br/>Test Results:<br/>" + console.getTestResults() + "<br/>");

        setData(console.getPublicName(performingClient), true, HTMLText.makeText("black", "courier", 4, content.toString()));
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        buildContent(gameData, player);
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {

    }

    @Override
    protected void consoleHandleInput(GameData gameData, Player player, String data) {
        super.consoleHandleInput(gameData, player, data);
        Scanner scan = new Scanner(data);
        if (scan.hasNextInt()) {
            int selected = scan.nextInt();
            if (selected > 0) {
                player.setNextAction(console.getTestForNumber(selected));
                readyThePlayer(gameData, player);
            } else {
                console.cancelTest();
            }
            player.refreshClientData();
        }

    }




}
