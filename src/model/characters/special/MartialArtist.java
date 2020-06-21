package model.characters.special;

import model.Actor;
import model.Player;
import model.characters.general.GameCharacter;
import model.fancyframe.UnarmedCombatFancyFrame;
import model.items.weapons.UnarmedAttack;

public interface MartialArtist {
    static boolean is(Actor performingClient) {
        return performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof MartialArtist);
    }

    static void showUnarmedCombatFancyFrame(Player performingClient, UnarmedAttack item) {
        performingClient.setFancyFrame(new UnarmedCombatFancyFrame(performingClient, item));
    }




}
