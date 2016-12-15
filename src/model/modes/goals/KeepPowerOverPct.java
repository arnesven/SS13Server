package model.modes.goals;

import model.Actor;
import model.GameData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.objects.consoles.PowerSource;
import model.objects.general.GameObject;

/**
 * Created by erini02 on 08/12/16.
 */
public class KeepPowerOverPct extends PersonalGoal {
    private final double level;
    private final int levelInt;
    private boolean failed;

    public KeepPowerOverPct(int i) {
        this.levelInt = i;
        this.level = ((double)i)/100.0;
        this.failed = false;
    }

    @Override
    public String getText() {
        return "Keep the station's power over "+ levelInt + "% the whole game.";
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new PowerCheckDecorator(belongingTo.getCharacter()));
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return !failed;
    }

    private class PowerCheckDecorator extends CharacterDecorator {
        public PowerCheckDecorator(GameCharacter character) {
            super(character, "powerchecker");
        }

        @Override
        public void doAtEndOfTurn(GameData gameData) {
            if (failed) {
                return;
            }
            Room genRoom = null;
            try {
                genRoom = gameData.getMap().getRoom("Generator");

                for (GameObject o : genRoom.getObjects()) {
                    if (o instanceof PowerSource) {
                        if (((PowerSource) o).getPowerOutput() <= level) {
                            failed = true;
                            getActor().addTolastTurnInfo("You have failed your personal goal.");
                        }
                    }
                }
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }
    }
}
