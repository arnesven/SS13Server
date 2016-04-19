package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.map.Room;

import java.util.List;

/**
 * Created by erini02 on 19/04/16.
 */
public class SendATextAction extends Action {
    private final GameData gameData;
    private Actor selected;
    private String mess;

    public SendATextAction(GameData gameData) {
        super("Send A Text",
                new SensoryLevel(SensoryLevel.VisualLevel.STEALTHY,
                        SensoryLevel.AudioLevel.INAUDIBLE,
                        SensoryLevel.OlfactoryLevel.UNSMELLABLE));
        this.gameData = gameData;

    }


    @Override
    protected String getVerb(Actor whosAsking) {
        return "Sent a text";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);
        for (Actor a : gameData.getActors()) {
            if (a.getCharacter().isCrew()) {
                ActionOption crewOpt = new ActionOption(a.getBaseName());
                addMessages(crewOpt, gameData);
                opt.addOption(crewOpt);
            }
        }
        return opt;
    }

    private void addMessages(ActionOption crewOpt, GameData gameData) {
        crewOpt.addOption("Get to work!");

        ActionOption come = new ActionOption("Get to");
        for (Room r : gameData.getRooms()) {
            come.addOption(r.getName());
        }

        crewOpt.addOption(come);

        ActionOption beware = new ActionOption("Beware of");
        for (String str : gameData.getGameMode().getAvailCharsAsStrings()) {
            beware.addOption(str);
        }
        beware.addOption("Chimp");
        crewOpt.addOption(beware);

        ActionOption think = new ActionOption("I think");
        think.addOption("there is a hive");
        think.addOption("there are operatives");
        think.addOption("there are traitors");
        think.addOption("there is a changeling");
        crewOpt.addOption(think);
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        selected.addTolastTurnInfo(mess);
        performingClient.addTolastTurnInfo("You texted " + selected.getBaseName());
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (Actor a : gameData.getActors()) {
            if (a.getBaseName().equals(args.get(0))) {
                selected = a;
            }
        }
        mess = "Head of Staff texted you; ";
        for (int i = 1; i < args.size(); ++i) {
            mess += args.get(i) + " ";
        }
        mess += ".";
    }
}
