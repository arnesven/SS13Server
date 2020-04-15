package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.objectactions.JumpStationAction;
import model.actions.objectactions.SpinUpFTLAction;
import model.objects.consoles.Console;
import model.objects.consoles.FTLControl;
import util.HTMLText;

public class FTLControlFancyFrame extends ConsoleFancyFrame {
    private final FTLControl ftl;
    private boolean pushedButton = false;

    public FTLControlFancyFrame(Console console, GameData gameData, Player performingClient) {
        super(performingClient.getFancyFrame(), console, gameData, "blue", "white");
        this.ftl = (FTLControl)console;

        concreteRebuild(gameData, performingClient);
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();

        String buttonFg = "black";
        String buttonBg = "white";
        if (pushedButton) {
            String tmp = buttonFg;
            buttonFg = buttonBg;
            buttonBg = tmp;
        }

        if (ftl.isSpunUp()) {
            content.append(HTMLText.makeCentered("<br>" +
                    "<b>FTL Spun up and Ready!</b><br/><br/>" +
                    HTMLText.makeText("Red", "Yellow", "Courier", 4, "Warning: High Power Drain!") + "<br/><br/>" +
                    HTMLText.makeFancyFrameLink("JUMP", HTMLText.makeText(buttonFg, buttonBg, "courier", 4, "[JUMP]"
                    ))));
        } else {
            content.append(HTMLText.makeCentered("<br>" +
                    "<b>FTL Unspun!</b><br/><br/>" + "<br/><br/>" +
                    HTMLText.makeFancyFrameLink("SPINUP", HTMLText.makeText(buttonFg, buttonBg, "courier", 4, "[SPIN UP]"
                    ))));
        }

        setData(ftl.getPublicName(player), false, content.toString());
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("SPINUP")) {
            player.setNextAction(new SpinUpFTLAction(ftl));
            concreteRebuild(gameData, player);
            readyThePlayer(gameData, player);
            this.pushedButton = true;
        } else if (event.contains("JUMP")) {
            player.setNextAction(new JumpStationAction(ftl));
            concreteRebuild(gameData, player);
            readyThePlayer(gameData, player);
            this.pushedButton = true;
        }
    }

    @Override
    public void doAtEndOfTurn(GameData gameData, Player actor) {
        pushedButton = false;
        concreteRebuild(gameData, actor);
    }
}
