package model.actions.objectactions;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.ActionOption;
import model.characters.crew.GeneticistCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.Syringe;
import model.mutations.Mutation;
import model.mutations.MutationFactory;
import model.objects.consoles.GeneticsConsole;
import model.actions.general.SensoryLevel;

public class MutateSyringeAction extends ConsoleAction {

    private GeneticsConsole console;
    private Syringe syringe;
    private ArrayList<Mutation> geneticistsOptions;
    private String genOptStr;

    public MutateSyringeAction(GeneticsConsole geneticsConsole) {
        super("Analyze Genes", SensoryLevel.OPERATE_DEVICE);
        this.console = geneticsConsole;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with console";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);

        for (GameItem gi : whosAsking.getItems()) {
            if (gi instanceof Syringe) {
                if (((Syringe) gi).isFilled()) {
                    ActionOption subopt = new ActionOption(getNameFor((Syringe) gi));
                    addGeneticistOpts(subopt, whosAsking, gameData);
                    opt.addOption(subopt);
                }
            }
        }

        return opt;
    }

    private void addGeneticistOpts(ActionOption subopt, Actor whosAsking, GameData gameData) {
        if (isGeneticist(whosAsking)) {
            geneticistsOptions = new ArrayList<>();
            while (geneticistsOptions.size() < 3) {
                Mutation mut = MutationFactory.getRandomMutation(gameData);
                if (!geneticistsOptions.contains(mut)) {
                    geneticistsOptions.add(mut);
                }
            }
            for (Mutation m : geneticistsOptions) {
                subopt.addOption(m.getName());
            }
        }
    }

    private boolean isGeneticist(Actor whosAsking) {
        return whosAsking.getCharacter().checkInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof GeneticistCharacter;
            }
        });
    }

    private String getNameFor(Syringe gi) {
        return gi.getBaseName() + " (" + gi.getMutation().getName() + ")";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (performingClient.getItems().contains(syringe)) {
            performingClient.addTolastTurnInfo(GeneticsConsole.BLAST_STRING);
            console.addKnownMutation(syringe.getMutation());
            if (isGeneticist(performingClient)) {
                for (Mutation m : MutationFactory.getAllMutations(gameData)) {
                    if (m.getName().equals(genOptStr)) {
                        syringe.setMutation(m);
                        break;
                    }
                }
            } else {
                syringe.setMutation(MutationFactory.getRandomMutation(gameData));
            }
        } else {
            performingClient.addTolastTurnInfo("What? The syringe is gone! Your action failed!");
        }

    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        this.syringe = null;
        for (GameItem gi : performingClient.getItems()) {
            if (gi instanceof Syringe) {
                if (getNameFor((Syringe) gi).equals(args.get(0))) {
                    this.syringe = (Syringe) gi;
                    break;
                }
            }
        }
        if (args.size() > 1 && isGeneticist(performingClient)) {
            genOptStr = args.get(1);
        }


    }

}
