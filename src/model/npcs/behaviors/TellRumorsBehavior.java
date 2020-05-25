package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.characteractions.MarriageAction;
import model.actions.general.*;
import model.actions.itemactions.BuildRobotAction;
import model.actions.itemactions.ConsumeAction;
import model.actions.itemactions.DefuseBombAction;
import model.actions.itemactions.RepairAction;
import model.actions.objectactions.*;
import model.characters.crew.CrewCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.general.CleanUpBloodAction;
import model.items.suits.Equipment;
import model.objects.consoles.AIConsole;
import model.objects.consoles.CrimeRecordsConsole;
import model.objects.general.SlotMachine;
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
    public void act(Actor npc, GameData gameData) {
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
                        if (pl.getCharacter().getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT) != null &&
                                !pl.getCharacter().getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT).permitsOver()) {
                            gossip.add("I hear " + pl.getBaseName() + " likes to dress up.");
                        }
                        if (pl.getNextAction() instanceof RepairAction ||
                                pl.getNextAction() instanceof PutOnAction ||
                                pl.getNextAction() instanceof  DefuseBombAction) {
                            gossip.add("I hear " + pl.getBaseName() + " is a true hero.");
                        }
                        if (pl.getNextAction() instanceof ConsoleAction) {
                            gossip.add("I hear " + pl.getBaseName() + " likes computers.");
                        }
                        if (pl.getNextAction() instanceof CookFoodAction) {
                            gossip.add("I hear " + pl.getBaseName() + " is cooking up a storm!");
                        }
                        if (pl.getNextAction() instanceof RecycleAction) {
                            gossip.add("I hear " + pl.getBaseName() + " is a real environmentalist");
                        }
                        if (pl.getNextAction() instanceof PlantAction) {
                            gossip.add("I hear " + pl.getBaseName() + " has green fingers");
                        }
                        if (pl.getNextAction() instanceof ConsumeAction) {
                            gossip.add("I hear " + pl.getBaseName() + " is a hungry person");
                        }
                        if (pl.getNextAction() instanceof CleanUpBloodAction) {
                            gossip.add("I hear " + pl.getBaseName() + " is a cleanly person");
                        }
                        if (pl.isDead()) {
                            gossip.add("I hear " + pl.getBaseName() + " diead, RIP " + pl.getBaseName());
                        }
                        if (pl.isInSpace()) {
                            gossip.add("I hear " + pl.getBaseName() + " likes going EVA.");
                        }
                        if (pl.getHealth() < 3.0) {
                            gossip.add("I hear " + pl.getBaseName() + " is feeling under the weather, maybe you should go cheer " + pl.getBaseName() + "up?");
                        }
                        if (pl.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof MarriageAction.MarriedDecorator)) {
                            gossip.add("I hear " + pl.getCharacter() + " got married. Congratulations!");
                        }
                        if (pl.getNextAction() instanceof PrayerAction) {
                            gossip.add("I hear " + pl.getBaseName() + " is a religious person.");
                        }
                        if (pl.getNextAction() instanceof SearchAction) {
                            gossip.add("I hear " + pl.getBaseName() + " was looking for something, although I don't know what.");
                        }
                        if (pl.getNextAction() instanceof BuildRobotAction) {
                            gossip.add("I hear " + pl.getBaseName() + " build a robot. The more the merrier!");
                        }
                        if (pl.getNextAction() instanceof HackDoorAction) {
                            gossip.add("I hear " + pl.getBaseName() + " is fiddling with doors. What's " + (a.getCharacter().getGender().equals("man")?"he":"she") + " up to?");
                        }
                        if (pl.getNextAction() instanceof SlotMachineAction) {
                            gossip.add("I hear " + pl.getBaseName() + " likes to gamble. Good luck!");
                        }
                    }
                }

                try {
                    List<String> alarms = gameData.findObjectOfType(AIConsole.class).getAlarms(gameData);
                    if (alarms.size() > 1) {
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

                try {
                    for (Actor a : gameData.getRoom("Brig").getActors()) {
                        gossip.add("I hear " + a.getBaseName() + " was a bad " + (a.getCharacter().getGender().equals("man")?"boy":"girl") + ".");
                    }
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
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
