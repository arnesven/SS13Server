package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.characters.general.SantaClauseCharacter;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 22/11/16.
 */
public class GiveAwayGiftsBehavior implements ActionBehavior {
    private final SantaClauseCharacter santaCharacter;

    public GiveAwayGiftsBehavior(SantaClauseCharacter character) {
        santaCharacter = character;
    }

    @Override
    public void act(Actor npc, GameData gameData) {
        List<Actor> receivers = new ArrayList<>();
        //Logger.log(" Santa giving gifts!");
        for (Actor a : npc.getPosition().getActors()) {
            if (a != npc) {
                if (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof HumanCharacter)) {
                    if (!santaCharacter.alreadyReceivedGift(a)) {
                        if (npc.getItems().size() > 0) {
                            GameItem gift = npc.getItems().get(0);
                            npc.getItems().remove(gift);
                            a.addItem(gift, npc.getAsTarget());
                            santaCharacter.gotAGift(a);
                            receivers.add(a);
                        }
                    }
                }
            }
        }

        if (receivers.size() > 0) {
            for (Actor a : npc.getPosition().getActors()) {
                for (Actor receiver : receivers) {
                    String name = receiver.getPublicName();
                    if (a == receiver) {
                        name = "You";
                    }

                    a.addTolastTurnInfo(name + " got a gift from " + npc.getPublicName() + ".");

                }
            }
        }

    }
}
