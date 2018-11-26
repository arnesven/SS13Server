package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;
import model.objects.consoles.Console;

import java.util.List;

public class LoginAction extends ConsoleAction {


    private final Console con;

    public LoginAction(Console con, Actor cl) {
        super("Login at " + con.getPublicName(cl), SensoryLevel.OPERATE_DEVICE);
        this.con = con;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with " + con.getPublicName(whosAsking) ;
    }


    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        gameData.getChat().serverSay("You logged in at the " + con.getPublicName(performingClient), (Player)performingClient);
        gameData.getComputerSystem().createLogin((Player)performingClient, con, gameData);
        performingClient.addTolastTurnInfo("You logged in at the " + con.getPublicName(performingClient));
        performingClient.addTolastTurnInfo("(Interact via the chat, initiate commands with '$')");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
