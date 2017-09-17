package model.objects.ai;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.events.damage.LaserBlast;
import model.items.weapons.AutoTurretLaser;
import model.map.rooms.Room;
import model.objects.consoles.AIConsole;
import model.objects.general.ElectricalMachinery;
import model.objects.general.RemotelyOperateable;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 02/11/16.
 */
public class AITurret extends ElectricalMachinery implements RemotelyOperateable{
    private boolean autoIsOn;
    private Event autoEvent;
    private int lastFiredOnTurn;

    public AITurret(Room room, AIConsole aiCons, GameData gameData) {
        super("AutoTurret", room);
        setPowerPriority(1);
        autoIsOn = false;
        lastFiredOnTurn = 0;

    }

    public void addPassiveTurretEvent(AIConsole aiCons, GameData gameData) {
        gameData.addEvent(new Event() {
            @Override
            public void apply(GameData gameData) {
                if (aiCons.getAIPlayer() == null) {
                    if (aiCons.isCorrupt()) {
                        attackRandomActor(aiCons, gameData);
                    }
                }
            }

            private void attackRandomActor(AIConsole aiCons, GameData gameData) {
                List<Target> targets = new ArrayList<>();
                for (Actor a : getPosition().getActors()) {
                    if (a.getAsTarget().isTargetable()) {
                        targets.add(a.getAsTarget());
                    }
                }
                if (targets.size() > 0) {
                    Target t = MyRandom.sample(targets);
                    ((Actor) t).addTolastTurnInfo("The AutoTurret fired at you!");
                    if (MyRandom.nextDouble() < 0.9) {
                        t.beExposedTo((Actor) t, new LaserBlast());
                    } else {
                        ((Actor) t).addTolastTurnInfo("...but it missed.");
                    }
                }
            }

            @Override
            public String howYouAppear(Actor performingClient) {
                return "";
            }

            @Override
            public SensoryLevel getSense() {
                return SensoryLevel.NO_SENSE;
            }
        });
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("autoturret", "turret.png", 0, 10);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (cl.getCharacter().checkInstance((GameCharacter ch) ->  ch instanceof AICharacter)) {
            if (autoIsOn) {
                at.add(makeAutoOffAction(gameData));
            } else {
                at.add(makeAutoOnAction(gameData, cl));
            }
            Action attack = makeAttackAction(gameData);
            if (attack.getOptions(gameData, cl).numberOfSuboptions() > 0) {
                at.add(attack);
            }
        }
    }

    private Action makeAutoOffAction(GameData gameData) {
        return new Action("AutoFire OFF", SensoryLevel.NO_SENSE) {
            @Override
            protected String getVerb(Actor whosAsking) {
                return "";
            }

            @Override
            protected void execute(GameData gameData, Actor performingClient) {
                autoIsOn = false;
                gameData.removeEvent(autoEvent);
                autoEvent.setShouldBeRemoved(true);
                performingClient.addTolastTurnInfo("AutoFire OFF.");

            }

            @Override
            public void setArguments(List<String> args, Actor performingClient) {

            }
        };
    }

    private Action makeAutoOnAction(GameData gameData, Actor cl) {
        return new Action("AutoFire ON", SensoryLevel.NO_SENSE) {
            @Override
            protected String getVerb(Actor whosAsking) {
                return "";
            }

            @Override
            protected void execute(GameData gameData, Actor performingClient) {
                autoIsOn = true;
                performingClient.addTolastTurnInfo("AutoTurret will now fire at anyone in this room.");
                autoEvent = new Event(){

                    @Override
                    public void apply(GameData gameData) {
                        Action attack = makeAttackAction(gameData);
                        if (attack.getOptions(gameData, performingClient).numberOfSuboptions() > 0) {
                            if (!hasFiredThisTurn(gameData)) {
                                ArrayList<String> args = new ArrayList<>();
                                args.add(attack.getOptions(gameData, performingClient).getRandomOption().getName());
                                attack.setArguments(args, performingClient);
                                attack.doTheAction(gameData, performingClient);
                                lastFiredOnTurn = gameData.getRound();
                            }
                        }

                    }

                    @Override
                    public String howYouAppear(Actor performingClient) {
                        return "";
                    }

                    @Override
                    public SensoryLevel getSense() {
                        return SensoryLevel.NO_SENSE;
                    }
                };
                gameData.addEvent(autoEvent);
            }

            @Override
            public void setArguments(List<String> args, Actor performingClient) {

            }
        };
    }

    private boolean hasFiredThisTurn(GameData gameData) {
        return lastFiredOnTurn == gameData.getRound();
    }


    private Action makeAttackAction(GameData gameData) {
        return new Action("Attack with turret", SensoryLevel.NO_SENSE) {
            private Room pos = AITurret.this.getPosition();
            private Actor targeted = null;

            @Override
            protected String getVerb(Actor whosAsking) {
                return "lasered";
            }

            @Override
            protected void execute(GameData gameData, Actor performingClient) {
                if (targeted != null) {
                    targeted.addTolastTurnInfo("The AutoTurret fired at you!");
                    targeted.getAsTarget().beAttackedBy(performingClient, new AutoTurretLaser());
                    lastFiredOnTurn = gameData.getRound();
                    for (Actor a : pos.getActors()) {
                        if (a != targeted) {
                            a.addTolastTurnInfo("The AutoTurret fired on " + targeted.getBaseName() + ".");
                        }
                    }

                } else {
                    performingClient.addTolastTurnInfo("The AutoTurret failed to fire.");
                }
            }

            @Override
            public ActionOption getOptions(GameData gameData, Actor whosAsking) {
                ActionOption opt = super.getOptions(gameData, whosAsking);
                for (Actor a : pos.getActors()) {
                    if (a.getAsTarget().isTargetable()) {
                        opt.addOption(a.getPublicName());
                    }
                }
                return opt;
            }

            @Override
            public void setArguments(List<String> args, Actor performingClient) {
                for (Actor a : pos.getActors()) {
                    if (a.getPublicName().equals(args.get(0))) {
                        targeted = a;
                        break;
                    }
                }
            }
        };
    }
}
