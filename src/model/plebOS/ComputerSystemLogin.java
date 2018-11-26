package model.plebOS;

import model.Actor;
import model.GameData;
import model.Player;
import model.objects.consoles.Console;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComputerSystemLogin implements Serializable {
    private final Player user;
    private final Console console;
    private final GameData gameData;
    private Directory currentDirectory;


    public ComputerSystemLogin(Player sender, Console con, GameData gameData) {
        this.user = sender;
        this.console = con;
        this.gameData = gameData;
        currentDirectory = new Directory("/");

        sender.setCharacter(new LoggedInDecorator(sender.getCharacter(),
                "logged in", this));
        gameData.getChat().plebOSSay("$login", sender);
        gameData.getChat().plebOSSay("user: " + sender.getCharacter().getBaseName().toLowerCase(), sender);
        gameData.getChat().plebOSSay("password: ●●●●●●", sender);
        gameData.getChat().plebOSSay("", sender);
        gameData.getChat().plebOSSay("Welcome to plebOS 1.25", true, sender);
        gameData.getChat().plebOSSay("", sender);
        gameData.getChat().plebOSSay("There are 151 crucial system updates ready to be installed,", sender);
        gameData.getChat().plebOSSay("please notify the system administrator!", sender);
        gameData.getChat().plebOSSay("", sender);
        gameData.getChat().plebOSSay("Last login on 24/03/2113, 4.15 pm", sender);

        for (Actor a : sender.getPosition().getActors()) {
            if (a != sender) {
                a.addTolastTurnInfo(sender.getPublicName() +
                        " fiddled with " + console.getPublicName(a) + ".");
            }
        }
    }

    public String getCurrentDirectory() {
        return currentDirectory.toString();
    }

    public List<String> getFilesForCurrentDirectory() {
        List<String> list = new ArrayList<>();
        list.add(".");
        list.add("..");
        list.add("alarms");
        list.add("ailaw");
        return list;
    }
}
