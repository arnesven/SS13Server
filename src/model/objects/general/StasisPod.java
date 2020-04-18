package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.characteractions.NuclearExplosiveDamage;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.objectactions.FreezeYourselfAction;
import model.characters.decorators.FrozenDecorator;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.events.damage.ColdDamage;
import model.events.damage.Damager;
import model.items.NoSuchThingException;
import model.items.suits.Equipment;
import model.map.rooms.Room;
import util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by erini02 on 29/04/16.
 */
public class StasisPod extends ElectricalMachinery {
    private Actor person;
    private Event timer;

    public StasisPod(Room r) {
        super("Stasis Pod", r);
        setHealth(2.5);
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        if (!isVacant()) {
            return super.getPublicName(whosAsking) + " (occupied)";
        }
        return super.getPublicName(whosAsking);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isVacant()) {
            return new Sprite("stasispodvacant", "cryogenic.png", 116, this);
        } else {
            return new Sprite("stasispodoccupied", "cryogenic.png", 119, this);
        }
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (isVacant()) {
            if (!cl.getCharacter().getEquipment().hasAnyEquipment() ||
                    (cl.getCharacter().getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT) != null &&
                    cl.getCharacter().getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT).permitsOver())) {
                at.add(new FreezeYourselfAction(this));
            }
        } else {
            at.add(new Action("Eject from Stasis Pod", SensoryLevel.OPERATE_DEVICE) {

                @Override
                protected String getVerb(Actor whosAsking) {
                    return "Ejected someone from the pod.";
                }

                @Override
                protected void execute(GameData gameData, Actor performingClient) {
                    if (!StasisPod.this.isVacant()) {
                        StasisPod.this.eject();
                    } else {
                        performingClient.addTolastTurnInfo("What, the stasis pod was empty? " + Action.FAILED_STRING);
                    }
                }

                @Override
                public void setArguments(List<String> args, Actor performingClient) {

                }
            });
        }
    }

    public boolean isVacant() {
        return person == null;
    }


    public void setPerson(Actor person) {
        this.person = person;
    }



    private void eject() {
        Logger.log("Ejecting player from pod!");
        if (person == null) {
            Logger.log("Person was null!");
        } else if (person.getPosition() == null) {
            Logger.log("Persons position was null!");
        }

        person.getPosition().addActor(person);
        person.removeInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof FrozenDecorator;
            }
        });

        for (Actor a : person.getPosition().getActors()) {
            if (a != person) {
                a.addTolastTurnInfo(person.getPublicName() + " was ejected from the pod.");
            }
        }
        StasisPod.this.cancelTimer();
        person = null;
    }

    @Override
    public void thisJustBroke(GameData gameData) {
        if (!isVacant()) {
            Actor a = this.getPerson();
            this.eject();
            a.getAsTarget().beExposedTo(null, new ColdDamage(3.0), gameData);
        }
    }

    public void putIntoPod(GameData gameData, Actor performingClient, String timeChosen) {
        try {
            performingClient.getPosition().removeActor(performingClient);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

        this.setPerson(performingClient);
        performingClient.setCharacter(new FrozenDecorator(performingClient.getCharacter()));
        if (!timeChosen.equals("Forever")) {
            Scanner scan = new Scanner(timeChosen);
            this.timer = new Event() {

                int time = scan.nextInt();

                @Override
                public void apply(GameData gameData) {
                    if (time == 0) {
                        if (!StasisPod.this.isVacant()) {
                            StasisPod.this.eject();
                        }

                    } else {
                        performingClient.addTolastTurnInfo("You have " + time + " more rounds in the pod.");

                    }
                    time--;
                }

                @Override
                public String howYouAppear(Actor performingClient) {
                    return "";
                }

                @Override
                public SensoryLevel getSense() {
                    return SensoryLevel.NO_SENSE;
                }

                @Override
                public boolean shouldBeRemoved(GameData gameData) {
                    return time == -1 || StasisPod.this.getTimer() == null;
                }
            };
            gameData.addEvent(timer);
        }
    }

    @Override
    public void beExposedTo(Actor performingClient, Damager damage, GameData gameData) {
        if (damage instanceof NuclearExplosiveDamage) {
            if (this.person != null) {
                this.person.getAsTarget().beExposedTo(performingClient, damage, gameData);
            }
        }
        super.beExposedTo(performingClient, damage, gameData);
    }

    private void cancelTimer() {
        timer = null;
    }

    public Event getTimer() {
        return timer;
    }

    public Actor getPerson() {
        return person;
    }
}
