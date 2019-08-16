package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.CombineChemicalsAction;
import model.map.rooms.LabRoom;

import java.util.ArrayList;

/**
 * Created by erini02 on 09/09/17.
 */
public class ChemicalApparatus extends ElectricalMachinery {
    public ChemicalApparatus(LabRoom labRoom) {
        super("Chemical Apparatus", labRoom);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        Action combine = new CombineChemicalsAction(gameData, cl);

        if (combine.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(combine);
        }
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("chemicalapparatus", "chemical.png", 64, this);
    }
}
