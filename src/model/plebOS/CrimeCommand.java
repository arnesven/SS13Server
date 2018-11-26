package model.plebOS;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.NoSuchThingException;
import model.objects.consoles.CrimeRecordsConsole;
import util.Pair;

import java.util.List;
import java.util.Map;

public class CrimeCommand extends PlebOSCommandHandler {
    public CrimeCommand() {
        super("crime");
    }

    @Override
    protected boolean doesReadyUser() {
        return true;
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender,
                                  String rest, ComputerSystemSession loginInstance) {
        try {
            CrimeRecordsConsole console = gameData.findObjectOfType(CrimeRecordsConsole.class);
            boolean stuff = false;
            if (console.getSentenceMap().size() > 0) {
                stuff = true;
                gameData.getChat().plebOSSay("SENTENCED ----- ROUNDS ", sender);
                for (Map.Entry<Actor, Integer> e : console.getSentenceMap().entrySet()) {
                    gameData.getChat().plebOSSay("-> " + e.getKey().getBaseName() + " - " + e.getValue(), sender);
                }
            }

            if (console.getReportedActors().size() > 0) {
                gameData.getChat().plebOSSay("REPORTS ----- OFFENSE (REPORTED BY) ", sender);
                stuff = true;
                for (Map.Entry<Actor, List<Pair<String, Actor>>> e : console.getReportedActors().entrySet()) {
                    StringBuffer buf = new StringBuffer();
                    for (Pair<String, Actor> p : e.getValue()) {
                        buf.append(p.first + " (" + p.second.getBaseName() + "), ");
                    }

                    gameData.getChat().plebOSSay("-> " + e.getKey().getBaseName() + " - " + buf.toString(), sender);
                }
            }

            if (!stuff) {
                gameData.getChat().plebOSSay("No sentences or reports found", sender);
            }

        } catch (NoSuchThingException e) {
            gameData.getChat().plebOSSay("Error - No connection to crime records", sender);
        }

    }
}
