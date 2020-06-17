package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.QuickAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.items.weapons.AmmoWeapon;
import model.items.weapons.Ammunition;

import java.util.List;

/**
 * Created by erini02 on 17/11/16.
 */
public class LoadWeaponAction extends Action implements QuickAction {


    private final Ammunition ammo;
    private AmmoWeapon selected;

    public LoadWeaponAction(Ammunition ammo) {
        super("Load Gun", SensoryLevel.PHYSICAL_ACTIVITY);
        this.ammo = ammo;
    }


    @Override
    protected String getVerb(Actor whosAsking) {
        return "loaded a gun";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (GameItem it : whosAsking.getItems()) {
            if (it instanceof AmmoWeapon) {
                if (ammo.canBeLoadedIntoGun((AmmoWeapon) it)) {
                    opts.addOption(it.getPublicName(whosAsking));
                }
            }
        }
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        ammo.loadIntoGun(selected);
        performingClient.addTolastTurnInfo("You loaded the " + selected.getPublicName(performingClient) + ".");
        performingClient.getItems().remove(ammo);
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (GameItem it : performingClient.getItems()) {
            if (it.getPublicName(performingClient).equals(args.get(0))) {
                selected = (AmmoWeapon)it;
            }
        }
    }

    @Override
    public void performQuickAction(GameData gameData, Player performer) {
        execute(gameData, performer);
    }

    @Override
    public boolean isValidToExecute(GameData gameData, Player performer) {
        return true;
    }

    @Override
    public List<Player> getPlayersWhoNeedToBeUpdated(GameData gameData, Player performer) {
        return List.of(performer);
    }
}
