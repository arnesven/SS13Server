package model.actions.ai;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.laws.AILaw;
import model.objects.consoles.AIConsole;

import java.util.List;

/**
 * Created by erini02 on 27/10/16.
 */
public class AILawAction extends Action {
    private final AIConsole aiConsole;
    private boolean inspect = false;
    private String rest;
    private int lawNumber;
    private boolean upload = false;
    private boolean delete = false;

    public AILawAction(AIConsole aiConsole) {
        super("Modify Laws", SensoryLevel.OPERATE_DEVICE);
        this.aiConsole = aiConsole;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with AI Console";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        ActionOption insp = new ActionOption("Inspect Laws");
        for (AILaw l : aiConsole.getLaws()) {
            insp.addOption(l.getBaseName());
        }
        opts.addOption(insp);

        if (!aiConsole.hasZerothLaw()) {
            ActionOption addZth = new ActionOption("Upload Zeroth Law");
            addOptionsToLawUpload(addZth, gameData);
            opts.addOption(addZth);
        }

        //opts.addOption("Upload Zeroth Law");
        if (aiConsole.getHighestLaw() < 19) {
            ActionOption addNth = new ActionOption("Upload " + (aiConsole.getHighestLaw() + 1) + "th Law");
            addOptionsToLawUpload(addNth, gameData);
            opts.addOption(addNth);
        }

        ActionOption delete = new ActionOption("Delete Law");

        for (AILaw law : aiConsole.getLaws()) {
            if (!aiConsole.getOriginalLaws().contains(law)) {
                delete.addOption(law.getBaseName());
            }
        }
        if (delete.numberOfSuboptions() > 0) {
            opts.addOption(delete);
        }

        return opts;
    }



    private void addOptionsToLawUpload(ActionOption addNth, GameData gameData) {
        ActionOption onlyXisHuman = new ActionOption("Only X is human.");
        addXList(onlyXisHuman, gameData, true);
        addNth.addOption(onlyXisHuman);

        ActionOption xIsNotHuman = new ActionOption("X is not human.");
        addXList(xIsNotHuman, gameData, true);
        addNth.addOption(xIsNotHuman);

        ActionOption xIsTheCaptain = new ActionOption("X is the Captain.");
        addXList(xIsTheCaptain, gameData, false);
        addNth.addOption(xIsTheCaptain);
        for (AILaw l : aiConsole.getAvailableLaws()) {
            addNth.addOption(l.getBaseName());
        }
    }

    private void addXList(ActionOption opts, GameData gameData, boolean groups) {

        for (GameCharacter gc : gameData.getGameMode().getAllCrew()) {
            opts.addOption(gc.getBaseName());
        }
        opts.addOption("the cat");
        if (groups) {
            opts.addOption("chimps");
            opts.addOption("robots");
            opts.addOption("crew members");
            opts.addOption("operatives");
            opts.addOption("aliens");
            opts.addOption("naked crew members");
        }

    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!inspect) {
            if (upload) {
                if (lawNumber == 0) {
                    aiConsole.addLaw(new AILaw(lawNumber, rest), performingClient);
                } else {
                    if (aiConsole.getHighestLaw() < 19) {
                        aiConsole.addLaw(new AILaw(aiConsole.getHighestLaw() + 1, rest), performingClient);
                    }
                }
            } else if (delete) {
                try {
                    if (aiConsole.getActorForLaw(rest).getSpeed() > performingClient.getSpeed()) {
                        performingClient.addTolastTurnInfo("Can't delete law, " + aiConsole.getActorForLaw(rest).getBaseName() + " outranks you!");
                        return;
                    }
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
                aiConsole.deleteLawByName(rest);
            }
            performingClient.addTolastTurnInfo("You modified the AIs laws");
            aiConsole.getAIPlayer().addTolastTurnInfo("Your laws have been modified!");
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        if (args.get(0).contains("Inspect")) {
            inspect = true;
            rest = args.get(1);
        } else if (args.get(0).contains("Upload")) {
            upload = true;
            rest = composeLaw(args.subList(1, args.size()));
            String[] parse = args.get(0).split("th");
            parse[0] = parse[0].replace("Upload ", "");
            if (parse[0].equals("Zero")) {
                lawNumber = 0;
            } else {
                lawNumber = Integer.parseInt(parse[0]);
            }
        } else if (args.get(0).contains("Delete")) {
            rest = args.get(1);
            delete = true;
        }
    }

    private String composeLaw(List<String> s) {
        if (s.size() == 1) {
            return s.get(0);
        }

        return s.get(0).replace("X", s.get(1));
    }



}
