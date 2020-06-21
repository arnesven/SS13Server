package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.characteractions.ArrestAndTeleportToPrisonPlanetAction;
import model.actions.general.Action;
import model.characters.special.MartialArtist;
import model.items.general.GameItem;
import model.items.general.Teleporter;
import model.items.weapons.Shotgun;
import model.items.weapons.ShotgunShells;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/11/16.
 */
public class GalacticFederalMarshalCharacter extends HumanCharacter implements MartialArtist {
    private final int number;

    public GalacticFederalMarshalCharacter(int i, int room) {
        super("Marshal #" + i, room, 8.33);
        this.number = i;
    }

    @Override
    public List<GameItem> getStartingItems() {
        List<GameItem> gi = new ArrayList<>();
        gi.add(new Shotgun());
        gi.add(new ShotgunShells());
        gi.add(new ShotgunShells());
        gi.add(new Teleporter());
        return gi;
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);

        Action arrest = new ArrestAndTeleportToPrisonPlanetAction();
        if (arrest.getOptions(gameData, getActor()).numberOfSuboptions() > 0) {
            at.add(arrest);
        }

        at.add(new CounterAttackAction());
    }

    @Override
    public GameCharacter clone() {
        return new GalacticFederalMarshalCharacter(number, getPosition().getID());
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(super.getSprite(whosAsking));
        list.add(new Shotgun().getHandHeldSprite());
        return new Sprite(super.getSprite(whosAsking).getName() + "withshotgun",
                "human.png", 0, list, getActor());
    }
}
