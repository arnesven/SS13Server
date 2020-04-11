package model.plebOS;

import comm.chat.AILawChatHandler;
import comm.chat.plebOS.AIAlarmsCommand;
import comm.chat.plebOS.PlebOSCommandHandler;
import model.Actor;
import model.GameData;
import model.GameState;
import model.Player;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.map.rooms.ElevatorRoom;
import model.objects.consoles.Console;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComputerSystemSession implements Serializable {
    private final Player user;
    private final Console console;
    private final GameData gameData;
    private Directory currentDirectory;

    public ComputerSystemSession(Player sender, Console con, GameData gameData) {
        this.user = sender;
        this.console = con;
        this.gameData = gameData;
        this.currentDirectory = gameData.getComputerSystem().getRootDirectory();

        sender.setCharacter(new LoggedInDecorator(sender.getCharacter(),
                "logged in", this));
        gameData.getChat().plebOSSay("", sender);
        gameData.getChat().plebOSSay("Welcome to plebOS 1.25", true, sender);
        gameData.getChat().plebOSSay("", sender);
        gameData.getChat().plebOSSay("There are 151 crucial system updates ready to be installed,", sender);
        gameData.getChat().plebOSSay("please notify the system administrator!", sender);
        gameData.getChat().plebOSSay("", sender);
        gameData.getChat().plebOSSay("Last login on 24/03/2113, 4.15 pm", sender);
        gameData.getChat().plebOSSay("Welcome " + sender.getCharacter().getBaseName().toLowerCase(), sender);

        if (gameData.getGameState() != GameState.PRE_GAME) {
            for (Actor a : sender.getPosition().getActors()) {
                if (a != sender) {
                    a.addTolastTurnInfo(sender.getPublicName() +
                            " fiddled with " + console.getPublicName(a) + ".");
                }
            }
        }
    }

    public Directory getCurrentDirectory() {
        return currentDirectory;
    }


    public static ComputerSystemSession getLogin(Player sender) {
        GameCharacter gc = sender.getCharacter();
        while (!(gc instanceof LoggedInDecorator)) {
            gc = ((CharacterDecorator)gc).getInner();
        }

        return ((LoggedInDecorator)gc).getLogin();
    }

    public List<PlebOSCommandHandler> getAvailableCommands() {
        List<PlebOSCommandHandler> list = new ArrayList<>();
        list.addAll(gameData.getComputerSystem().getAllPlebosCommands());

        for (FileSystemNode fsn : currentDirectory.getContents()) {
            if (fsn instanceof PlebosFile) {
                if (((PlebosFile)fsn).isExecutable()) {
                    list.add(((PlebosFile)fsn).getExecutable());
                }
            }
        }

        return list;
    }

    public void setCurrentDirectory(Directory rootDirectory) {
        this.currentDirectory = rootDirectory;
    }

    public void logOut(GameData gameData) {
        user.addTolastTurnInfo("You logged out from the console.");
        console.setLoggedInAt(null);
        gameData.getChat().serverSay("You logged out from the console", user);
        if (user.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof LoggedInDecorator)) {
            user.removeInstance((GameCharacter gc) -> gc instanceof LoggedInDecorator);
        }
    }

    public Console getConsole() {
        return console;
    }
}
