package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.actions.general.PutOnAction;
import model.actions.general.SensoryLevel;
import model.actions.itemactions.RepairAction;
import model.actions.objectactions.ConsoleAction;
import model.characters.crew.CrewCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.npcs.NPC;
import model.objects.consoles.AIConsole;
import model.objects.consoles.CrimeRecordsConsole;
import util.Logger;
import util.MyRandom;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 28/04/16.
 */
public class TellRumorsBehavior implements ActionBehavior {
    @Override
    public void act(NPC npc, GameData gameData) {
        gameData.executeAtEndOfRound(npc, new Action("Tell Rumors", SensoryLevel.NO_SENSE) {
            @Override
            protected String getVerb(Actor whosAsking) {
                return "";
            }

            @Override
            protected void execute(GameData gameData, Actor performingClient) {
                Actor a = MyRandom.sample(npc.getPosition().getActors());
                if (a != npc && MyRandom.nextDouble() < 0.5) {
                     a.getCharacter().giveItem(MyRandom.getRandomAlcohol(), npc.getAsTarget());
                }

            }

            @Override
            public void setArguments(List<String> args, Actor performingClient) {

            }

            @Override
            public void lateExecution(GameData gameData, Actor performingClient) {
                Logger.log("*** RAN late execution of TELL RUMORS ****");
                List<String> gossip = new ArrayList<String>();
               for (Actor a : gameData.getActors()) {
                    if (a instanceof Player && isACrew(a)) {
                        Player pl = (Player)a;
                        if (pl.getNextAction() instanceof AttackAction) {
                            gossip.add("I hear " + pl.getBaseName() + " is a violent person.");
                        }
                        if (pl.getCharacter().getSuit() != null && ! pl.getCharacter().getSuit().permitsOver()) {
                            gossip.add("I hear " + pl.getBaseName() + " likes to dress up.");
                        }
                        if (pl.getNextAction() instanceof RepairAction ||
                                pl.getNextAction() instanceof PutOnAction) {
                            gossip.add("I hear " + pl.getBaseName() + " is a true hero.");
                        }
                        if (pl.getNextAction() instanceof ConsoleAction) {
                            gossip.add("I hear " + pl.getBaseName() + " likes computers.");
                        }
                    }
                }

                try {
                    List<String> alarms = gameData.findObjectOfType(AIConsole.class).getAlarms(gameData);
                    if (alarms.size() > 2) {
                        gossip.add("I hear the AI has some alarms.");
                    }
                } catch (NoSuchThingException nste) {

                }

                try {
                    CrimeRecordsConsole crc = gameData.findObjectOfType(CrimeRecordsConsole.class);
                    for (List<Pair<String, Actor>> listOfpair : crc.getReportedActors().values()) {
                        for (Pair<String, Actor> pair : listOfpair) {
                            gossip.add("I hear " + pair.second.getBaseName() + " is a " + pair.second.getCharacter().getGender() + " of justice.");
                        }
                    }
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }

                for (Actor a : gameData.getRoom("Brig").getActors()) {
                    gossip.add("I hear " + a.getBaseName() + " was a bad " + (a.getCharacter().getGender().equals("man")?"boy":"girl") + ".");
                }

                gossip.add("What'll it be?");

                int randGossips = Math.min(MyRandom.nextInt(3), gossip.size());
                for (int i = 0; i < randGossips; ++i) {
                    String line = MyRandom.sample(gossip);
                    gossip.remove(line);
                    for (Actor a : npc.getPosition().getActors()) {
                        a.addTolastTurnInfo(npc.getBaseName() + " said \"" + line + "\"");
                    }
                }

            }
        });
    }

    private boolean isACrew(Actor a) {
        return a.getCharacter().checkInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof CrewCharacter;
            }
        });
    }
}
