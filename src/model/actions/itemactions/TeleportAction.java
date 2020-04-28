package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.InSpaceCharacterDecorator;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.TeleportingDecorator;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.items.general.Teleporter;
import model.map.rooms.SpaceRoom;
import model.objects.general.GameObject;
import model.objects.general.IncomingTeleporter;

import java.util.List;

/**
 * Created by erini02 on 18/04/16.
 */
public class TeleportAction extends Action {
    private final Teleporter teleporter;
    private Actor target;

    public TeleportAction(Teleporter teleporter) {
        super("Teleport to " + teleporter.getMarked().getName(), new SensoryLevel(SensoryLevel.VisualLevel.CLEARLY_VISIBLE,
                SensoryLevel.AudioLevel.SAME_ROOM, SensoryLevel.OlfactoryLevel.UNSMELLABLE));
        this.teleporter = teleporter;
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);
        for (Actor a : whosAsking.getPosition().getActors()) {
            if (a == whosAsking) {
                opt.addOption("Yourself");
            } else if (a.getAsTarget().isTargetable()){
                opt.addOption(a.getPublicName());
            }
        }
        return opt;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Used the teleporter on " + target.getPublicName();
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.addTolastTurnInfo("You used the teleporter on " +
                                        (target==performingClient?"yourself.":target.getPublicName()));
        if (target != performingClient) {
            target.addTolastTurnInfo(performingClient.getPublicName() + " is using the teleporter on you!");
        }
        target.setCharacter(new TeleportingDecorator(target.getCharacter()));
        final GameObject tpfield = new IncomingTeleporter(teleporter.getMarked());
        teleporter.getMarked().addObject(tpfield);
        teleporter.useOnce();

        gameData.addEvent(new Event() {
            private final int roundSet = gameData.getRound();

            @Override
            public void apply(GameData gameData) {
                if (gameData.getRound() == roundSet+1) {
                    target.removeInstance((GameCharacter gc) -> gc instanceof TeleportingDecorator);
                    teleporter.getMarked().removeObject(tpfield);
                    if (!target.isDead()) {
                        target.addTolastTurnInfo("You were teleported to " + teleporter.getMarked().getName());
                        target.moveIntoRoom(teleporter.getMarked());
                        if (teleporter.getMarked() instanceof SpaceRoom) {
                            performingClient.goToSpace(gameData);
                        } else {
                            if (performingClient.isInSpace()) {
                                performingClient.stopBeingInSpace();
                            }
                        }
                    }
                }
            }

            @Override
            public String howYouAppear(Actor performingClient) {
                return "";
            }

            @Override
            public SensoryLevel getSense() {
                return SensoryLevel.NO_SENSE;
            }

            @Override
            public boolean shouldBeRemoved(GameData gameData) {
                return gameData.getRound() > roundSet;
            }
        });
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        if (args.get(0).equals("Yourself")) {
            target = performingClient;
        } else {
            for (Actor a : performingClient.getPosition().getActors()) {
                if (args.get(0).contains(a.getPublicName())) {
                    target = a;
                    break;
                }
            }
        }
    }

    public void setTarget(Actor target) {
        this.target = target;
    }
}
