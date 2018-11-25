package comm.chat;

import model.GameData;
import model.Player;
import model.items.NoSuchThingException;
import model.objects.consoles.AIConsole;
import model.plebOS.ComputerSystemLogin;

public class AILawChatHandler extends ChatCommandHandler {

    public AILawChatHandler() {
        super("ailaw");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest) {
        try {
            gameData.findObjectOfType(AIConsole.class).addCustomLawToAvailable(rest.replace("/ailaw ", ""));
            gameData.getChat().serverSay("New AI Law added");
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }
}
