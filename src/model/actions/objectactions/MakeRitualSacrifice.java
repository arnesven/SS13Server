package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.general.AnimalCharacter;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.objects.Altar;
import util.Logger;

import java.util.List;

public class MakeRitualSacrifice extends Action {
    private final Altar altar;
    private Actor target;
    private boolean isSelf = false;

    public MakeRitualSacrifice(Altar altar) {
        super("Make Ritual Sacrifice", SensoryLevel.PHYSICAL_ACTIVITY);
        this.altar = altar;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        if (isSelf) {
            String gender = whosAsking.getCharacter().getGender().equals("man")?"himself":"herself";
            return " sacrificed " + gender + " on the altar of Kali!";
        }
        return " sacrificed " + target.getPublicName() + " on the altar of Kali!";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        for (Actor a : whosAsking.getPosition().getActors()) {
            if (a.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof HumanCharacter || ch instanceof AnimalCharacter)) {
                if (a == whosAsking) {
                    opts.addOption("Yourself");
                } else {
                    opts.addOption(a.getPublicName());
                }
            }
        }
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (target == null) {
            performingClient.addTolastTurnInfo("Huh, nobody to sacrifice? " + failed(gameData, performingClient));
        } else {
            performingClient.addTolastTurnInfo("You sacrificed " + target.getPublicName() + " on the altar of Kali!");
            target.getAsTarget().beExposedTo(performingClient, new SacrificeDamage(), gameData);
            altar.setSacrifice(true);
            altar.addToPoints(25);
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        if (args.get(0).equals("Yourself")) {
            target = performingClient;
            isSelf = true;
        } else {
            for (Actor a : performingClient.getPosition().getActors()) {
                if (a.getPublicName().contains(args.get(0)) || args.get(0).contains(a.getPublicName())) {
                    target = a;
                    break;
                }
            }
        }
        Logger.log(Logger.CRITICAL, "Make ritual sacrifice: Could not find target for this action!");

    }
}
