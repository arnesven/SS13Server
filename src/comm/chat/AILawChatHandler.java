package comm.chat;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.GameData;
import model.Player;
import model.items.NoSuchThingException;
import model.objects.consoles.AIConsole;
import model.plebOS.ComputerSystemLogin;

public class AILawChatHandler extends PlebOSCommandHandler  {

    public AILawChatHandler() {
        super("ailaw ");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest, ComputerSystemLogin loginInstance) {
        try {
            gameData.findObjectOfType(AIConsole.class).addCustomLawToAvailable(rest.replace("$ailaw ", ""));
            gameData.getChat().plebOSSay("New AI Law added", sender);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

}
