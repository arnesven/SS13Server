package model.npcs;

import model.Actor;
import model.GameData;
import model.actions.characteractions.MakeSoilAction;
import model.actions.characteractions.StartCampFire;
import model.actions.general.Action;
import model.actions.objectactions.PlantAction;
import model.items.general.GameItem;
import model.items.seeds.SeedsItem;
import model.map.rooms.ExoticPlanet;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.objects.general.SoilPatch;
import model.objects.general.GameObject;
import model.objects.general.JungleCampFire;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 16/09/17.
 */
public class JungleManNPC extends NPC {
    public JungleManNPC(ExoticPlanet exoticPlanet) {
        super(new JungleManCharacter(exoticPlanet.getID()),
                new MeanderingMovement(0.15),
                new JungleManBehavior(), exoticPlanet);

    }

    private static class JungleManBehavior implements ActionBehavior {
        @Override
        public void act(Actor npc, GameData gameData) {
            SoilPatch sp = roomHasUnplantedSoil(npc);
            if (sp != null && npc.getItems().size() > 0) {
                PlantAction planting = new PlantAction(sp);
                GameItem seeds = MyRandom.sample(npc.getItems());
                if (seeds instanceof SeedsItem) {
                    List<String> args = new ArrayList<>();
                    args.add(seeds.getPublicName(npc));
                    planting.setArguments(args, npc);
                    planting.doTheAction(gameData, npc);
                } else {
                    for (Actor a : npc.getPosition().getActors()) {
                        a.addTolastTurnInfo("Jungle man tried planting a " + seeds.getPublicName(a) + " in the soil.");
                    }
                }
            } else if (roomHasCampfire(npc)) {
                if (MyRandom.nextDouble() < 0.5 && roomHasUnplantedSoil(npc) == null) {
                    Action a = new MakeSoilAction();
                    a.doTheAction(gameData, npc);
                } else {
                    for (Actor a : npc.getPosition().getActors()) {
                        a.addTolastTurnInfo("Jungle man tended the camp fire.");
                    }
                }
            } else if (MyRandom.nextDouble() < 0.5) {
                Action a = new StartCampFire();
                a.doTheAction(gameData, npc);
            }


        }

        private SoilPatch roomHasUnplantedSoil(Actor npc) {
            for (GameObject obj : npc.getPosition().getObjects()) {
                if (obj instanceof SoilPatch && !((SoilPatch) obj).isPlanted()) {
                    return (SoilPatch)obj;
                }
            }
            return null;
        }

        private boolean roomHasCampfire(Actor npc) {
            for (GameObject obj : npc.getPosition().getObjects()) {
                if (obj instanceof JungleCampFire) {
                    return true;
                }
            }
            return false;
        }
    }
}
