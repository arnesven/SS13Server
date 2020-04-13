package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;
import model.fancyframe.ConsoleFancyFrame;
import model.objects.consoles.Console;

import java.util.List;

public class LoginAction extends ConsoleAction {


    private final Console con;
    private final ConsoleFancyFrame fancyFrame;

    public LoginAction(Console con, Actor cl, ConsoleFancyFrame consoleFancyFrame) {
        super("Login at " + con.getPublicName(cl), SensoryLevel.OPERATE_DEVICE);
        this.con = con;
        this.fancyFrame = consoleFancyFrame;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with " + con.getPublicName(whosAsking) ;
    }


    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        gameData.getChat().serverSay("You logged in at the " + con.getPublicName(performingClient), (Player)performingClient);
        gameData.getComputerSystem().createLogin((Player)performingClient, con, gameData, fancyFrame);
        performingClient.addTolastTurnInfo("You logged in at the " + con.getPublicName(performingClient));
        performingClient.addTolastTurnInfo("(Interact via the small window)");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
