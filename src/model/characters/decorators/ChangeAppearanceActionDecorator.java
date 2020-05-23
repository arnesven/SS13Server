package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.crew.CrewCharacter;
import model.characters.general.GameCharacter;
import model.characters.general.ShamblingAbomination;
import model.characters.general.OperativeCharacter;
import model.characters.general.WizardCharacter;
import model.items.suits.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 20/10/16.
 */
public class ChangeAppearanceActionDecorator extends CharacterDecorator {
    private final SuperSuit suit;

    public ChangeAppearanceActionDecorator(GameCharacter character, SuperSuit ss) {
        super(character, "Change Appearance Action");
        this.suit = ss;
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        at.add(new Action("Change Super Suit", SensoryLevel.NO_SENSE) {
            public GameCharacter chosen;

            @Override
            protected String getVerb(Actor whosAsking) {
                return "changed appearance.";
            }

            @Override
            public ActionOption getOptions(GameData gameData, Actor whosAsking) {
                ActionOption opts = super.getOptions(gameData, whosAsking);
                for (GameCharacter chara : suit.getAppearances(gameData)) {
                    opts.addOption(chara.getBaseName());
                }
                return opts;
            }

            @Override
            protected void execute(GameData gameData, Actor performingClient) {
                if (chosen instanceof ShamblingAbomination) {
                    suit.setAppearAsCharacter(chosen, performingClient);
                } else if (chosen instanceof OperativeCharacter) {
                    new OperativeSpaceSuit().putYourselfOn(chosen.getEquipment());
                    suit.setAppearAsCharacter(chosen, performingClient);
                } else if (chosen instanceof WizardCharacter) {
                    new WizardsOutfit().putYourselfOn(chosen.getEquipment());
                    suit.setAppearAsCharacter(chosen, performingClient);
                } else if (chosen.checkInstance((GameCharacter ch) -> ch instanceof CrewCharacter)){
                    suit.setAppearance(chosen.getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT), performingClient);
                } else {
                    suit.setAppearAsCharacter(chosen, performingClient);
                }
                performingClient.addTolastTurnInfo("You changed the super suit to look like a " + chosen.getBaseName() + ".");
            }

            @Override
            public void setArguments(List<String> args, Actor performingClient) {
                for (GameCharacter chara : suit.getAppearances(gameData)) {
                   if (args.get(0).equals(chara.getBaseName())) {
                       chosen = chara;
                   }
                }
            }

            @Override
            public Sprite getAbilitySprite() {
                return new Sprite("chsupersuitabi", "uniform2.png", 16, 17, null);
            }
        });

    }
}
