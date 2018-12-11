package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.npcs.NPC;
import model.objects.consoles.GeneticsConsole;
import model.objects.general.CloneOMatic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClonePersonAction extends Action {
    private final Actor actor;
    private final CloneOMatic cloner;
    private final GeneticsConsole geneticsConsole;
    private final Set<Actor> targets = new HashSet<>();
    private Actor selectedTarget;

    public ClonePersonAction(CloneOMatic cloneOMatic, Actor cl, GeneticsConsole geneticsConsole) {
        super("Clone Person", SensoryLevel.OPERATE_DEVICE);
        this.cloner = cloneOMatic;
        this.actor = cl;
        this.geneticsConsole = geneticsConsole;
        for (Actor a : cloner.getStoredActors()) {
            targets.add(a);
        }

        for (Actor a : geneticsConsole.getStoredActors()) {
            targets.add(a);
        }
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Used the cloner";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (Actor a : targets) {
            opts.addOption(a.getBaseName());
        }

        return super.getOptions(gameData, whosAsking);
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        cloner.setInUse(true);
        performingClient.addTolastTurnInfo("The cloner whirs, inside you see something growing!");

        gameData.addEvent(new Event() {

            private final int turn = gameData.getRound();

            @Override
            public void apply(GameData gameData) {
                if (gameData.getRound() == turn + 1) {
                    if (selectedTarget instanceof Player) {
                        Player clonedPlayer = (Player) selectedTarget;

                        try {
                            Player p = gameData.addNewPlayer("CloneOf" + gameData.getClidForPlayer(clonedPlayer));
                            p.setCharacter(selectedTarget.getCharacter().clone());
                            p.moveIntoRoom(cloner.getPosition());
                            informAllInRoom(p);

                        } catch (NoSuchThingException e) {
                            e.printStackTrace();
                        } catch (IllegalStateException ise) {
                            performingClient.addTolastTurnInfo("The cloning has failed...");
                        }
                    } else {
                        NPC clonedNPC = (NPC) selectedTarget;
                        gameData.addNPC(clonedNPC.clone());
                        clonedNPC.moveIntoRoom(clonedNPC.getPosition());
                    }

                    cloner.setJustStuffed(false);
                    cloner.setInUse(false);
                }
            }

            @Override
            public String howYouAppear(Actor performingClient) {
                return null;
            }

            @Override
            public SensoryLevel getSense() {
                return SensoryLevel.NO_SENSE;
            }

            @Override
            public boolean shouldBeRemoved(GameData gameData) {
                return gameData.getRound() > turn;
            }
        });


    }

    private void informAllInRoom(Actor p) {
        for (Actor a : p.getPosition().getActors()) {
            p.addTolastTurnInfo(p.getPublicName() + " was ejected from the cloner.");
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (Actor a : targets) {
            if (a.getBaseName().equals(args.get(0))) {
                selectedTarget = a;
            }
        }
    }
}
