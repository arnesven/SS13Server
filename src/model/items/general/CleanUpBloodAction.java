package model.items.general;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SearchAction;
import model.characters.general.AnimalCharacter;
import model.characters.general.GameCharacter;
import model.map.floors.BurntFloorSet;
import model.npcs.NPC;
import model.objects.decorations.BurnMark;
import model.objects.general.BloodyMess;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

public class CleanUpBloodAction extends SearchAction {

    public CleanUpBloodAction() {
        setName("Mop Floor");
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Mopped the floors";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!GameItem.hasAnItemOfClass(performingClient, Mop.class)) {
            performingClient.addTolastTurnInfo("You don't have a mop! " + failed(gameData, performingClient));
            return;
        }

        List<GameObject> objectsToBeRemoved = new ArrayList<>();

        for (GameObject obj : performingClient.getPosition().getObjects()) {
            if (obj instanceof BloodyMess || obj instanceof BurnMark) {
                objectsToBeRemoved.add(obj);
            }
        }
        performingClient.getPosition().getObjects().removeAll(objectsToBeRemoved);

        List<Actor> bodiesToBeRemoved = new ArrayList<>();
        for (Actor a : performingClient.getPosition().getActors()) {
            if (a instanceof NPC && a.isDead() && a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AnimalCharacter)) {
                bodiesToBeRemoved.add(a);
            }
        }
        performingClient.getPosition().getActors().removeAll(bodiesToBeRemoved);

        if (objectsToBeRemoved.size() > 0 || bodiesToBeRemoved.size() > 0) {
            performingClient.addTolastTurnInfo("You cleaned up the floors.");
        }

        if (performingClient.getPosition().getFloorSet() instanceof BurntFloorSet) {
            performingClient.getPosition().setFloorSet(((BurntFloorSet)performingClient.getPosition().getFloorSet()).getUnburntFloorSet());
        }
        super.execute(gameData, performingClient);

    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
