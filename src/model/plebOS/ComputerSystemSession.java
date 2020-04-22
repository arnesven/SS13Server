package model.plebOS;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.Actor;
import model.GameData;
import model.GameState;
import model.Player;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.fancyframe.ConsoleFancyFrame;
import model.objects.consoles.Console;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComputerSystemSession implements Serializable {
    private final Player user;
    private final Console console;
    private final GameData gameData;
    private final ConsoleFancyFrame fancyFrame;
    private Directory currentDirectory;

    public ComputerSystemSession(Player sender, Console con, GameData gameData, ConsoleFancyFrame cff) {
        this.user = sender;
        this.console = con;
        this.gameData = gameData;
        this.fancyFrame = cff;
        this.currentDirectory = gameData.getComputerSystem().getRootDirectory();

        sender.setCharacter(new LoggedInDecorator(sender.getCharacter(),
                "logged in", this));
        console.plebOSSay("", sender);
        console.plebOSSay("<center>Welcome to plebOS 1.25</center>",  sender);
       // console.plebOSSay("There are 151 crucial system updates ready to be installed,", sender);
       // console.plebOSSay("please notify the system administrator!", sender);
        console.plebOSSay("", sender);
        console.plebOSSay("Last login on 24/03/2113, 4.15 pm", sender);
        console.plebOSSay("Welcome " + sender.getCharacter().getBaseName().toLowerCase(), sender);

        fancyFrame.buildPlebosInterface(gameData, sender);

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
        gameData.getChat().serverInSay("You logged out from the console", user);
        if (user.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof LoggedInDecorator)) {
            user.removeInstance((GameCharacter gc) -> gc instanceof LoggedInDecorator);
        }
    }

    public Console getConsole() {
        return console;
    }
}
