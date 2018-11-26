package model.plebOS;

import comm.chat.AILawChatHandler;
import comm.chat.plebOS.AIAlarmsCommand;
import comm.chat.plebOS.LsCommand;
import comm.chat.plebOS.PlebOSCommandHandler;
import comm.chat.plebOS.PwdCommand;
import model.GameData;
import model.Player;
import model.objects.consoles.Console;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComputerSystem implements Serializable {

    private final Directory fileSystemRoot;

    public ComputerSystem() {
        fileSystemRoot = new Directory("/", null);
        fileSystemRoot.setParent(fileSystemRoot);
        Directory bin = new Directory("bin", fileSystemRoot);
        fileSystemRoot.add(bin);
        fileSystemRoot.add(new Directory("etc", fileSystemRoot));
        bin.add(new PlebosFile("ailaw", true, new AILawChatHandler()));
        bin.add(new PlebosFile("alarms", true, new AIAlarmsCommand()));
    }

    public void createLogin(Player performingClient, Console con, GameData gameData) {
        new ComputerSystemSession(performingClient, con, gameData);
    }

    public Directory getRootDirectory() {
        return fileSystemRoot;
    }

    public static List<PlebOSCommandHandler> getAllPlebosCommands() {
        List<PlebOSCommandHandler> list = new ArrayList<>();
        list.add(new PwdCommand());
        list.add(new LsCommand());
        return list;
    }

}
