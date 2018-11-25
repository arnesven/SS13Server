package comm.chat.plebOS;

import model.GameData;
import model.Player;
import model.items.NoSuchThingException;
import model.objects.consoles.AIConsole;
import model.plebOS.ComputerSystemLogin;

import java.util.List;

public class AIAlarmsCommand extends PlebOSCommandHandler {
    public AIAlarmsCommand() {
        super("alarms");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest, ComputerSystemLogin loginInstance) {
        List<String> alarms = null;
        try {
            alarms = gameData.findObjectOfType(AIConsole.class).getAlarms(gameData);
            if (alarms.size() > 0) {
                for (String alarm : alarms) {
                    gameData.getChat().plebOSSay(alarm, sender);
                }
            } else {
                gameData.getChat().plebOSSay("No alarms.", sender);
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
            gameData.getChat().plebOSSay("No connection to AI.", sender);
        }


    }
}
