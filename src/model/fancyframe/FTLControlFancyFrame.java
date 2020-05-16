package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.JumpStationAction;
import model.actions.objectactions.SpinUpFTLAction;
import model.map.MapLevel;
import model.objects.consoles.Console;
import model.objects.consoles.FTLControl;
import util.HTMLText;
import util.MyRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FTLControlFancyFrame extends ConsoleFancyFrame {
    private final FTLControl ftl;
    private boolean pushedButton = false;
    private boolean chartCourseShowing;
    private String destination;

    public FTLControlFancyFrame(Console console, GameData gameData, Player performingClient) {
        super(performingClient.getFancyFrame(), console, gameData, "blue", "white");
        this.ftl = (FTLControl)console;
        chartCourseShowing = false;

        concreteRebuild(gameData, performingClient);
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();

        if (chartCourseShowing) {
            content.append("______________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE", "[back]"));
            content.append("<br/><b>Set navigational coordinates:</b><br/>");
            List<Integer[]> jumpLevels = new ArrayList<>();
            jumpLevels.addAll(gameData.getMap().getJumpableToLevels());
            Collections.sort(jumpLevels, new Comparator<Integer[]>() {
                @Override
                public int compare(Integer[] t2, Integer[] t1) {
                    return  t2[0]*100000+t2[1]*100+t2[2] -
                            (t1[0]*100000+t1[1]*100+t1[2]);
                }
            });
            for (Integer[] coords : jumpLevels) {
                String text = coords[0] + "??-" + coords[1] + "??-" + coords[2] + "??";
                String extra = "";
                if (text.equals(destination)) {
                    extra = " &lt;-SET";
                }
                content.append(HTMLText.makeFancyFrameLink("SETDEST " + text, text) + extra + "<br/>");
            }

        } else {
            content.append("________________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE", "[astronav]"));
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
                        HTMLText.makeFancyFrameLink("JUMP", HTMLText.makeText(buttonFg, buttonBg, "courier", 4, "[JUMP]")) +
                "<br/>to: " + (destination==null?"unknown":destination)));
            } else {
                content.append(HTMLText.makeCentered("<br>" +
                        "<b>FTL Unspun!</b><br/><br/>" + "<br/><br/>" +
                        HTMLText.makeFancyFrameLink("SPINUP", HTMLText.makeText(buttonFg, buttonBg, "courier", 4, "[SPIN UP]"
                        ))));
            }
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
            Action jump = new JumpStationAction(ftl);
            List<String> args = new ArrayList<>();
            if (destination == null) {
                args.add(makeRandomDestination(gameData));
            } else {
                args.add(destination);
            }
            jump.setActionTreeArguments(args, player);
            player.setNextAction(jump);
            concreteRebuild(gameData, player);
            readyThePlayer(gameData, player);
            this.pushedButton = true;
        } else if (event.contains("CHANGEPAGE")) {
            chartCourseShowing = !chartCourseShowing;
            concreteRebuild(gameData, player);
        } else if (event.contains("SETDEST")) {
            this.destination = event.replace("SETDEST ", "");
            concreteRebuild(gameData, player);
            readyThePlayer(gameData, player);
        }
    }

    private String makeRandomDestination(GameData gameData) {
        Integer[] pos = MyRandom.sample(gameData.getMap().getJumpableToLevels());
        return pos[0] + "??-" + pos[1]+ "??-" + pos[2] + "??";
    }

    @Override
    public void doAtEndOfTurn(GameData gameData, Player actor) {
        pushedButton = false;
     //   concreteRebuild(gameData, actor); // this ruins login..
    }
}
