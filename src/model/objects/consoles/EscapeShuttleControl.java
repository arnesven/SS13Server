package model.objects.consoles;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.fancyframe.ConsoleFancyFrame;
import model.map.rooms.EscapeShuttle;
import model.objects.general.GameObject;
import org.w3c.dom.html.HTMLTableElement;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

public class EscapeShuttleControl extends ShuttleControl {
    private final EscapeShuttle shuttle;

    public EscapeShuttleControl(EscapeShuttle escapeShuttle) {
        super(escapeShuttle, false);
        this.shuttle = escapeShuttle;
    }

    @Override
    public boolean isPowered() {
        return true;
    }

    @Override
    public double getPowerConsumption() {
        return 0.0;
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new LeaveNowAction());
        at.add(new SitDownAtConsoleAction(gameData, this) {
            @Override
            protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
                return new EscapeShuttleControlFancyFrame(performingClient, gameData, console);
            }
        });
    }

    private class LeaveNowAction extends Action {
        public LeaveNowAction() {
            super("Leave Now", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "fiddled with Shuttle Control";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            shuttle.leaveNow(gameData);
        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {

        }
    }

    private class EscapeShuttleControlFancyFrame extends ConsoleFancyFrame {
        private final Console console;
        private boolean buttonPressed;

        public EscapeShuttleControlFancyFrame(Player performingClient, GameData gameData, Console console) {
            super(performingClient.getFancyFrame(), console, gameData, "#ca9f21", "black");
            this.console = console;
            concreteRebuild(gameData, performingClient);
            this.buttonPressed = false;
        }

        @Override
        protected void concreteRebuild(GameData gameData, Player player) {
            StringBuilder content = new StringBuilder();
            content.append(HTMLText.makeCentered(HTMLText.makeGrayButton(buttonPressed, "LEAVE", "[leave now]")));

            setData(console.getPublicName(player), false, content.toString());
        }

        @Override
        protected void consoleHandleEvent(GameData gameData, Player player, String event) {
            if (event.contains("LEAVE")) {
                LeaveNowAction lna = new LeaveNowAction();
                lna.setActionTreeArguments(new ArrayList<>(), player);
                player.setNextAction(lna);
                readyThePlayer(gameData, player);
                buttonPressed = true;
                concreteRebuild(gameData, player);
            }
        }
    }
}
