package model.plebOS;

import comm.chat.AILawChatHandler;
import comm.chat.plebOS.AIAlarmsCommand;
import comm.chat.plebOS.LsCommand;
import comm.chat.plebOS.PlebOSCommandHandler;
import comm.chat.plebOS.PwdCommand;
import model.Actor;
import model.GameData;
import model.Player;
import model.objects.consoles.Console;
import util.Logger;
import util.MyRandom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComputerSystem implements Serializable {

    private final Directory fileSystemRoot;

    public ComputerSystem() {
        fileSystemRoot = new Directory("/", null);
        fileSystemRoot.setParent(fileSystemRoot);
        Directory adm = new Directory("adm", fileSystemRoot);
        fileSystemRoot.add(adm);
        adm.add(new PlebosFile("ailaw", true, new AILawChatHandler()));
        adm.add(new PlebosFile("alarms", true, new AIAlarmsCommand()));
        adm.add(new PlebosFile("crime", true, new CrimeCommand()));
        adm.add(new PlebosFile("power", true, new PowerCommand()));
        adm.add(new PlebosFile("budget", true, new BudgetCommand()));

        Directory bin = new Directory("bin", fileSystemRoot);
        bin.add(new PlebosFile("ls", true, new LsCommand()));
        bin.add(new PlebosFile("pwd", true, new PwdCommand()));
        bin.add(new PlebosFile("cd", true, new CdCommand()));
        bin.add(new PlebosFile("cat", true, new CatCommand()));
        fileSystemRoot.add(bin);

        Directory etc = new Directory("etc", fileSystemRoot);
        fileSystemRoot.add(etc);



    }

    public void createLogin(Player performingClient, Console con, GameData gameData) {
        con.setLoggedInAt(performingClient);
        new ComputerSystemSession(performingClient, con, gameData);
    }

    public Directory getRootDirectory() {
        return fileSystemRoot;
    }

    public List<PlebOSCommandHandler> getAllPlebosCommands() {
        List<PlebOSCommandHandler> list = new ArrayList<>();
        FileSystemNode bin = getFileSystemNode("/bin");
        for (FileSystemNode f : ((Directory)bin).getContents()) {
            if (f instanceof PlebosFile && ((PlebosFile) f).isExecutable()) {
                list.add(((PlebosFile) f).getExecutable());
            }
        }
        return list;
    }

    public FileSystemNode getFileSystemNode(String path) {
        String[] dirs = path.split("/");
        Logger.log("Splitted: ->" + Arrays.toString(dirs) + "<-");

        FileSystemNode d = fileSystemRoot;
        for (int i = 1; i < dirs.length; ++i) {
            d = ((Directory) d).getNodeForeName(dirs[i]);
        }

        return d;
    }

    public void setUpGame(GameData gameData) {
        Directory etc = (Directory)getFileSystemNode("/etc");
        PlebosFile pwdfile = new PlebosFile("passwords.txt");
        for (Actor a : gameData.getActors()) {
            if (a.getCharacter().isCrew()) {
                pwdfile.appendLine(a.getCharacter().getBaseName().toLowerCase()
                        + ":" + MyRandom.randomHexString(14));
            }
        }
        etc.add(pwdfile);
    }
}
