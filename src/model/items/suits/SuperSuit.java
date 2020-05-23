package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.crew.CrewCharacter;
import model.characters.decorators.ChangeAppearanceActionDecorator;
import model.characters.decorators.DisguisedAs;
import model.characters.decorators.InstanceChecker;
import model.characters.general.*;
import model.characters.visitors.VisitorCharacter;
import model.items.TraitorItem;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 20/10/16.
 */
public class SuperSuit extends SuitItem implements TraitorItem {
    private SuitItem appearance = null;
    private GameCharacter appearAsCharacter = null;

    public SuperSuit() {
        super("Super Suit", 1.0, 149);
    }

    @Override
    public String getBaseName() {
        if (appearAsCharacter == null) {
            if (appearance == null) {
                return "Super Suit";
            } else {
                return "Super Suit (" + appearance.getBaseName() + ")";
            }
        }
        return "Super Suit (" + appearAsCharacter.getBaseName() + ")";
    }

    @Override
    public SuitItem clone() {
        return new SuperSuit();
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        if (actionPerformer.getCharacter().checkInstance(((GameCharacter ch) -> ch instanceof ChangeAppearanceActionDecorator))) {
            actionPerformer.removeInstance(((GameCharacter ch) -> ch instanceof ChangeAppearanceActionDecorator));
        }
        removeDisguise(actionPerformer);
    }



    @Override
    public void beingPutOn(Actor actionPerformer) {
       disguise(actionPerformer);
        if (!actionPerformer.getCharacter().checkInstance(((GameCharacter ch) -> ch instanceof ChangeAppearanceActionDecorator))) {
            actionPerformer.setCharacter(new ChangeAppearanceActionDecorator(actionPerformer.getCharacter(), this));
        }
    }


    @Override
    public int getEquipmentSlot() {
        if (appearance == null) {
            return Equipment.TORSO_SLOT;
        }
        return appearance.getEquipmentSlot();
    }

    @Override
    public boolean blocksSlot(int targetSlot) {
        if (appearance == null) {
            return false;
        }
        return appearance.blocksSlot(targetSlot);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("supersuit", "uniforms.png", 0, 5, this);
    }

    @Override
    public Sprite getWornSprite(Actor whosAsking) {
        if (appearAsCharacter == null) {
            return super.getWornSprite(whosAsking);
        }
        if (appearAsCharacter.checkInstance(((GameCharacter ch) -> ch instanceof CrewCharacter))) {
            return (new OutFit(appearAsCharacter)).getWornSprite(whosAsking);
        }
        if (appearAsCharacter.checkInstance(((GameCharacter ch) -> ch instanceof ShamblingAbomination))) {
            return (new ShamblingAbomination()).getSprite(whosAsking);
        }
        if (appearAsCharacter.checkInstance(((GameCharacter ch) -> ch instanceof SantaClauseCharacter))) {
            return (new SantaSuit()).getWornSprite(whosAsking);
        }

        if (appearAsCharacter.getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT) != null) {
            return appearAsCharacter.getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT).getWornSprite(whosAsking);
        }
        return appearAsCharacter.getSprite(whosAsking);
    }

    @Override
    public boolean permitsOver() {
        return false;
    }

    public boolean setAppearance(SuitItem appearance, Actor who) {
        if (appearance != null) {
            this.appearance = appearance;
        }

        if (appearance instanceof OutFit) {
            if (!appearance.permitsOver()) {
                return false;
            }
            this.appearAsCharacter = ((OutFit)appearance).getType();
        }
        removeDisguise(who);
        disguise(who);
        if (appearAsCharacter == null) {
            return false;
        }
        return true;
    }

    public List<GameCharacter> getAppearances(GameData gameData) {
        List<GameCharacter> chars = new ArrayList<>();
        chars.addAll(gameData.getGameMode().getAllCrew());

        int i = 0;
        for (; i < chars.size() ; ++i) {
            if (chars.get(i) instanceof VisitorCharacter) {
                break;
            }
        }
        if (i < chars.size()) {
            VisitorCharacter v = (VisitorCharacter) chars.remove(i); // removing visitor
            //chars.addAll(v.getSubtypes()); // doesn't work too well
        }
        chars.add(new WizardCharacter(1));
        chars.add(new ShamblingAbomination());
        chars.add(new OperativeCharacter(1,1));
        chars.add(new RobotCharacter("TARS", 1, 20.0));
        chars.add(new SantaClauseCharacter(3));
        return chars;
    }

    public void setAppearAsCharacter(GameCharacter appearAsCharacter, Actor who) {
        this.appearAsCharacter = appearAsCharacter;
        removeDisguise(who);
        disguise(who);
    }


    private void disguise(Actor actionPerformer) {
        if (appearance != null && appearAsCharacter != null) {
            Logger.log(Logger.CRITICAL, "actionPerformer is" + actionPerformer.toString());
            Logger.log(Logger.CRITICAL, "appearAsAcharacter is" + appearAsCharacter.toString()); // <-- crasch here nullptr
            actionPerformer.setCharacter(new DisguisedAs(actionPerformer.getCharacter(), appearAsCharacter.getBaseName()));
        }
    }

    private void removeDisguise(Actor actionPerformer) {
        InstanceChecker disguiesCheck = new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof DisguisedAs;
            }
        };

        if (actionPerformer.getCharacter().checkInstance(disguiesCheck)) {
            actionPerformer.removeInstance(disguiesCheck);
        }
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "The latest in textile technology and fashion! This suit can be programmed to have almost any appearance!";
    }

    @Override
    public int getTelecrystalCost() {
        return 2;
    }
}
