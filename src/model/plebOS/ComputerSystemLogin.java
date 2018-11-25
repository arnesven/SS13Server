package model.plebOS;

import model.GameData;
import model.Player;
import model.objects.consoles.Console;

import java.io.Serializable;

public class ComputerSystemLogin implements Serializable {
    private final Player user;
    private final Console console;
    private final GameData gameData;

    public ComputerSystemLogin(Player sender, Console con, GameData gameData) {
        this.user = sender;
        this.console = con;
        this.gameData = gameData;

        sender.setCharacter(new LoggedInDecorator(sender.getCharacter(),
                "logged in", this));
        gameData.getChat().plebOSSay("Welcome to plebOS 1.25", true, sender);
        gameData.getChat().plebOSSay("", sender);
        gameData.getChat().plebOSSay("There are 151 crucial system updates ready to be installed", sender);
        gameData.getChat().plebOSSay("please notify the system administrator!", sender);
        gameData.getChat().plebOSSay("", sender);
        gameData.getChat().plebOSSay("Last login on 24/03/2113, 4.15 pm", sender);
        gameData.getChat().plebOSSay("Welcome " + sender.getCharacter().getBaseName(), sender);


    }

}
