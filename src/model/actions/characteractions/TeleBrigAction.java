package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.objects.consoles.CrimeRecordsConsole;

/**
 * Created by erini02 on 25/10/16.
 */
public class TeleBrigAction extends TargetingAction {
    public TeleBrigAction(Actor ap) {
        super("TeleBrig", SensoryLevel.PHYSICAL_ACTIVITY, ap);
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        try {
            CrimeRecordsConsole console = gameData.findObjectOfType(CrimeRecordsConsole.class);

            String crimes;
            try {
                crimes = console.getCrimeStringFor((Actor) target);
            } catch (NoSuchThingException nste) {
                crimes = "nothing in particular";
            }
            ((Actor)target).addTolastTurnInfo("SecuriTRON; \"" + target.getName() +
                    " you are arrested for " + crimes + ".\"");
            console.teleBrig((Actor)target, gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        performingClient.addTolastTurnInfo("You telebrigged " + target.getName() + "!");
    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        return target2 instanceof Actor && target2.isTargetable();
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Telebrigged somone.";
    }
}
