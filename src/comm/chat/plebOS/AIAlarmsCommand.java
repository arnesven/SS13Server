package comm.chat.plebOS;

import model.GameData;
import model.Player;
import model.items.NoSuchThingException;
import model.objects.consoles.AIConsole;
import model.plebOS.ComputerSystemSession;

import java.util.List;

public class AIAlarmsCommand extends PlebOSCommandHandler {
    public AIAlarmsCommand() {
        super("alarms");
    }

    @Override
    protected boolean doesReadyUser() {
        return true;
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest, ComputerSystemSession loginInstance) {
        List<String> alarms = null;
        try {
            alarms = gameData.findObjectOfType(AIConsole.class).getAlarms(gameData);
            if (alarms.size() > 0) {
                for (String alarm : alarms) {
                    loginInstance.getConsole().plebOSSay(alarm, sender);
                }
            } else {
                loginInstance.getConsole().plebOSSay("No alarms.", sender);
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
            loginInstance.getConsole().plebOSSay("No connection to AI.", sender);
        }


    }
}
