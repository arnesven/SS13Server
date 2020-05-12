package model.items.general;

import graphics.OverlaySprite;
import graphics.sprites.AlsoSeePowerVision;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.objectactions.PowerConsoleAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.objects.consoles.Console;
import model.objects.consoles.GeneratorConsole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 14/04/16.
 */
public class PowerRadio extends Radio {

    public PowerRadio() {
        super("Power Radio", 89);
    }

    @Override
    protected Console getSpecificConsole(GameData gameData) throws NoSuchThingException {
        return gameData.findObjectOfType(GeneratorConsole.class);
    }

    @Override
    protected List<Action> getSpecificActions(GameData gameData) {
        try {
            List<Action> res = new ArrayList<>();
            res.add(new PowerConsoleAction(gameData.findObjectOfType(GeneratorConsole.class)));
            return res;
        } catch (NoSuchThingException e) {
            throw new IllegalStateException("Cannot get specific action for Power Radio, no power console found on station.");
        }
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("powerradio", "device.png", 13, this);
    }

    @Override
    public GameItem clone() {
        return new PowerRadio();
    }


    @Override
    public void gotGivenTo(Actor to, Target from) {
        super.gotGivenTo(to, from);
        to.setCharacter(new PowerOverlayDecorator(to.getCharacter()));
        if (from instanceof Actor) {
            removeDecorator((Actor)from);

        }
    }

    @Override
    public void gotAddedToRoom(Actor cameFrom, Room to) {
        super.gotAddedToRoom(cameFrom, to);
        if (cameFrom != null) {
            removeDecorator(cameFrom);
        }
    }

    private void removeDecorator(Actor from) {
        if (from.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof PowerOverlayDecorator)) {
            from.removeInstance(((GameCharacter ch) -> ch instanceof PowerOverlayDecorator));
        }
    }

    private class PowerOverlayDecorator extends CharacterDecorator {
        public PowerOverlayDecorator(GameCharacter character) {
            super(character, "power overlay");
        }

        @Override
        public List<OverlaySprite> getOverlayStrings(Player player, GameData gameData) {
            List<OverlaySprite> strs = new ArrayList<>();
            strs.addAll(new AlsoSeePowerVision().getOverlaySprites(player, gameData));
            return strs;
        }
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A device which lets the user access a subset of the generator consoles functions. A connection to the console is needed however.";
    }
}
