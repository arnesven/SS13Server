package model.objects.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.actions.objectactions.ActivateBioScannerAction;
import model.actions.objectactions.WalkUpToElectricalMachineryAction;
import model.fancyframe.BioScannerFancyFrame;
import model.fancyframe.FancyFrame;
import model.fancyframe.UsingGameObjectFancyFrameDecorator;
import model.items.general.Locatable;
import model.objects.DetachableObject;
import model.objects.DraggableObject;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.items.chemicals.Chemicals;

public class BioScanner extends DetachableElectricalMachinery implements Locatable {

    public static final int CAN_BE_USED_EARLIEST_TURN = 10;
    private Chemicals loadedChem;

    public BioScanner(Room pos) {
        super("BioScanner", pos);
        loadedChem = null;
    }

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("bioscanner", "cryogenic.png", 104, this);
    }

    @Override
    public String getName() {
        if (loadedChem == null) {
            return super.getName() + " (off)";
        }
        return super.getName();
    }


    @Override
    public String getDetachingDescription() {
        return "The " + getName() + " came loose from the floor!";
    }


    @Override
    public int getDetachTimeRounds() {
        return 0;
    }

    @Override
    public void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

        if (cl instanceof Player) {
            at.add(new WalkUpToElectricalMachineryAction(gameData, (Player) cl, this) {
                @Override
                protected FancyFrame getFancyFrame(GameData gameData, Actor performingClient) {
                    return new BioScannerFancyFrame(gameData, (Player) cl, BioScanner.this);
                }

                @Override
                protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
                    super.doTheFreeAction(args, p, gameData);
                }
            });
        } else if (5 <= gameData.getRound()) {

            at.add(new ActivateBioScannerAction(this));
        }
    }

    @Override
    public double getPowerConsumption() {
        if (loadedChem != null) {
            return super.getPowerConsumption();
        }
        return 0.0;
    }

    public void giveBioScannerOutput(GameData gameData, Actor performingClient, BioScannerFancyFrame fancyFrame) {
        int infected = 0;
        for (Player p : gameData.getPlayersAsList()) {
            if (p.isInfected()) {
                infected++;
            }
        }
        for (NPC npc : gameData.getNPCs()) {
            if (npc.isInfected()) {
                infected++;
            }
        }
        int dieroll = MyRandom.nextInt(8);
        if (dieroll == 0) {
            infected--;
        } else if (dieroll == 7) {
            infected++;
        }
        infected = Math.max(infected, 0);
        if (fancyFrame == null) {
            performingClient.addTolastTurnInfo("BioScanner; " + infected + " infected crew members detected.");
        } else {
            fancyFrame.setResult(infected, gameData, (Player)performingClient);
        }
    }

    public Chemicals getLoadedChems() {
        return loadedChem;
    }

    public void setLoadedChemicals(Chemicals chems) {
        loadedChem = chems;
    }

    public boolean isLoaded() {
        return loadedChem != null;
    }

}
