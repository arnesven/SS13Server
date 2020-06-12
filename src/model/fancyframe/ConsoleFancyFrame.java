package model.fancyframe;

import comm.chat.ChatCommandHandler;
import comm.chat.plebOS.PlebOSCommandHandler;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.objectactions.LoginAction;
import model.items.NoSuchThingException;
import model.objects.consoles.Console;
import model.plebOS.ComputerSystemSession;
import sounds.Sound;
import sounds.TerminalButtonSound;
import util.HTMLText;
import util.Logger;
import util.MyRandom;

public abstract class ConsoleFancyFrame extends FancyFrame {

    private final Console console;
    private final GameData gameData;
    private final String fgColor;
    private String bgColor;

    public ConsoleFancyFrame(FancyFrame old, Console console, GameData gameData, String bgColor, String fgColor) {
        super(old);
        this.console = console;
        this.bgColor = bgColor;
        this.gameData = gameData;
        this.fgColor = fgColor;
    }

    @Override
    protected void setData(String title, boolean hasInput, String html) {
        if (console.isBroken()) {
            super.setData(console.getName(), false, HTMLText.makeColoredBackground("Blue", HTMLText.makeCentered(HTMLText.makeText("yellow", "(Broken)"))));
            return;
        } else if (!console.isPowered()) {
            super.setData(console.getName(), false, HTMLText.makeColoredBackground("Black", HTMLText.makeCentered(HTMLText.makeText("white", "(No Power)"))));
            return;
        }

        if (console.getLoggedInActor() == null) {
            String newHtml = HTMLText.makeColoredBackground(this.bgColor, HTMLText.makeText(fgColor,
                    HTMLText.makeFancyFrameLink("LOGIN", "[log in]") + "" + html));
            super.setData(title, hasInput, newHtml);
        } else {
            super.setData(title, hasInput, html);
        }
    }


    @Override
    protected void beingDisposed() {
        Logger.log("Console fancy frame was disposed, logged in actor is: " + console.getLoggedInActor());
        if (console.getLoggedInActor() != null) {
            ((Player) console.getLoggedInActor()).refreshClientData();
            ComputerSystemSession login = ComputerSystemSession.getLogin((Player)console.getLoggedInActor());
            login.logOut(gameData);
        }
        console.setFancyFrameVacant();
    }

    public final void rebuildInterface(GameData gameData, Player player) {
        if (console.getLoggedInActor() == null) {
            concreteRebuild(gameData, player);
        }
    }

    protected abstract void concreteRebuild(GameData gameData, Player player);

    @Override
    public final void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("DISMISS")) {
            leaveFancyFrame(gameData, player);
            if (console.getLoggedInActor() == player) {
                ComputerSystemSession.getLogin(player).logOut(gameData);
                player.refreshClientData();
            }
            player.getSoundQueue().add(new Sound("terminal_leave"));
        } else if (event.contains("LOGIN")) {
            handleLogin(gameData, player, this);
        } else {
            consoleHandleEvent(gameData, player, event);
            player.getSoundQueue().add(makeRandomTerminalClick());
        }
    }

    private Sound makeRandomTerminalClick() {
        return new TerminalButtonSound();
    }

    @Override
    public final void handleInput(GameData gameData, Player player, String data) {
        super.handleInput(gameData, player, data);
        if (PlebOSCommandHandler.isLoggedIn(player)) {
            ComputerSystemSession login = ComputerSystemSession.getLogin(player);
            boolean handled = false;
            for (ChatCommandHandler posc : login.getAvailableCommands()) {
                if (posc.handle(gameData, player, data)) {
                    handled = true;
                    break;
                }
            }
            if (!handled) {
                if (PlebOSCommandHandler.isLoggedIn(player)) {
                    console.plebOSSay(data, player);
                    console.plebOSSay("Unknown command \"" + data + "\".", player);
                }
            }
            if (PlebOSCommandHandler.isLoggedIn(player)) {
                buildPlebosInterface(gameData, player);
            } else {
                rebuildInterface(gameData, player);
            }
            player.refreshClientData();
        } else {
            consoleHandleInput(gameData, player, data);
        }
    }

    protected void consoleHandleInput(GameData gameData, Player player, String data) {
        player.getSoundQueue().add(new Sound("terminal_prompt"));

    }

    private void handleLogin(GameData gameData, Player player, ConsoleFancyFrame consoleFancyFrame) {
        LoginAction la = new LoginAction(console, player, consoleFancyFrame);
        player.setNextAction(la);
        readyThePlayer(gameData, player);
    }

    protected abstract void consoleHandleEvent(GameData gameData, Player player, String event);


    public void buildPlebosInterface(GameData gameData, Player sender) {
        Logger.log("Building plebOS interface for " + sender.getName());
        StringBuilder totalOutput = new StringBuilder();
        for (String text : console.getPlebosTexts()) {
            totalOutput.append(text + "<br/>");
        }
        console.clearPlebosTexts();
        setData(console.getPublicName(sender), true, HTMLText.makeColoredBackground("black",
                HTMLText.makeText("yellow", "black",
                        "Courier", 4, totalOutput.toString())));
    }


    protected void greekify(StringBuilder content, String text, String whoItMakesSenseFor) {
        StringBuilder greek = new StringBuilder();
        for (int i = 0; i < text.length() ; ++i) {
            if ((text.charAt(i) >= 'A' && text.charAt(i) <= 'Z') ||
                    text.charAt(i) >= 'a' && text.charAt(i) <= 'z') {
                int greekNum = text.charAt(i) + 959;
                greek.append("&#" + greekNum + ";");
            } else {
                greek.append(text.charAt(i));
            }
        }
        content.append(HTMLText.makeCentered(HTMLText.makeText("white", greek.toString())));
        content.append(HTMLText.makeText("white",
                "<br/><i>(This screen probably makes sense to " + whoItMakesSenseFor + ", but it's all greek to you.)</i>"));
    }


}
