package model.actions.characteractions;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.ChangelingCharacter;
import model.events.damage.CorrosiveDamage;
import model.objects.general.BreakableObject;
import model.objects.general.GameObject;

import java.util.List;

/**
 * Created by erini02 on 20/04/16.
 */
public class SprayAcidAction extends Action {
    private final ChangelingCharacter ling;

    public SprayAcidAction(ChangelingCharacter ling) {
        super("Spray Acid", new SensoryLevel(SensoryLevel.VisualLevel.CLEARLY_VISIBLE,
                SensoryLevel.AudioLevel.ALMOST_QUIET,
                SensoryLevel.OlfactoryLevel.WHIFF));
        this.ling = ling;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Sprayed acid on everybody!";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        for (Actor a : performingClient.getPosition().getActors()) {
            if (a != performingClient) {
                ling.setAcidSprayed(true);
                a.getCharacter().beExposedTo(performingClient, new CorrosiveDamage());
                performingClient.addTolastTurnInfo("You sprayed acid on " + a.getPublicName(performingClient));
            }
        }
        for (GameObject o : performingClient.getPosition().getObjects()) {
            if (o instanceof BreakableObject) {
                ((BreakableObject) o).beExposedTo(performingClient, new CorrosiveDamage());
                performingClient.addTolastTurnInfo("You sprayed acid on " + o.getPublicName(performingClient));
            }
        }


    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) { }

    @Override
    public Sprite getAbilitySprite() {
        return new Sprite("changelingsprayacid", "interface_alien.png", 0, 2, null);
    }
}
