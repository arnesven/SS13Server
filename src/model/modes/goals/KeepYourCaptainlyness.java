package model.modes.goals;

import model.Actor;
import model.GameData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.suits.CaptainsOutfit;

/**
 * Created by erini02 on 25/08/17.
 */
public class KeepYourCaptainlyness extends PersonalGoal {

    private boolean tookOff;

    public KeepYourCaptainlyness() {
        tookOff = false;
    }

    @Override
    public String getText() {
        return "Don't take off your Captain's Outfit.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return !tookOff;
    }


    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);

        belongingTo.setCharacter(new CharacterDecorator(belongingTo.getCharacter(), "keepcaptainsoutfitondecorator") {
            @Override
            public void doAfterActions(GameData gameData) {
                super.doAfterActions(gameData);
                if (! (getActor().getCharacter().getSuit() instanceof CaptainsOutfit)) {
                    tookOff = true;
                }
            }
        });
    }
}
