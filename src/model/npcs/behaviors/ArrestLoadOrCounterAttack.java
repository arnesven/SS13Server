package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.actions.characteractions.ArrestAndTeleportToPrisonPlanetAction;
import model.actions.general.Action;
import model.characters.general.CounterAttackAction;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.weapons.Shotgun;
import model.items.weapons.ShotgunShells;
import model.npcs.GalacticFederalMarshalNPC;
import model.npcs.NPC;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/11/16.
 */
public class ArrestLoadOrCounterAttack implements ActionBehavior {
    private final GalacticFederalMarshalNPC mostWanted;

    public ArrestLoadOrCounterAttack(GalacticFederalMarshalNPC mostWantedCriminal) {
        this.mostWanted = mostWantedCriminal;
    }

    @Override
    public void act(Actor npc, GameData gameData) {
        if (!arrestCriminalIfAble(npc, gameData)) {
            loadShotgunIfAble(npc, gameData);
            counterAttack(npc, gameData);

        }
    }

    private void counterAttack(Actor npc, GameData gameData) {
        ArrayList<Action> actionList = new ArrayList<>();
        npc.getCharacter().addCharacterSpecificActions(gameData, actionList);
        for (Action a : actionList) {
             if (a instanceof CounterAttackAction) {
                 List<String> args = new ArrayList<>();
                 a.setActionTreeArguments(args, npc);
                 a.doTheAction(gameData, npc);
             }
        }
        Logger.log(Logger.INTERESTING, "  GFM counter-attacks.");
    }

    private boolean arrestCriminalIfAble(Actor npc, GameData gameData) {
        if (npc.getPosition() == mostWanted.getMostWanted().getPosition()) {
            Logger.log(Logger.INTERESTING, "  GFM found criminal!");
            ArrayList<Action> actionList = new ArrayList<>();
            npc.getCharacter().addCharacterSpecificActions(gameData, actionList);
            for (Action a : actionList) {
                if (a instanceof ArrestAndTeleportToPrisonPlanetAction) {
                    List<String> args = new ArrayList<>();
                    args.add(mostWanted.getMostWanted().getPublicName());
                    a.setActionTreeArguments(args, npc);
                    a.doTheAction(gameData, npc);
                }
            }
            return true;
        }
        return false;
    }

    private boolean loadShotgunIfAble(Actor npc, GameData gameData) {
        try {
            Shotgun shotgun = GameItem.getItemFromActor(npc, new Shotgun());

            if (!shotgun.isReadyToUse()) {
                Logger.log(Logger.INTERESTING, "  GFM reloads shotgun!");
                ShotgunShells shells = GameItem.getItemFromActor(npc, new ShotgunShells());
                ArrayList<Action> actionList = new ArrayList<>();
                shells.addYourActions(gameData, actionList, npc);
                List<String> args = new ArrayList<>();
                args.add(shotgun.getPublicName(npc));
                actionList.get(actionList.size()-1).setActionTreeArguments(args, npc);
                actionList.get(actionList.size()-1).doTheAction(gameData, npc);
                return true;
            }

        } catch (NoSuchThingException e) {
            // hasn't got his shotgun anymore.
            // or hasn't got any shells
        }
        return false;
    }
}
