package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.decorators.OnFireCharacterDecorator;
import model.characters.general.GameCharacter;
import model.map.rooms.DormsRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 10/09/17.
 */
public class Shower extends GameObject {
    public Shower(DormsRoom dormsRoom) {
        super("Shower", dormsRoom);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {

        List<Sprite> list = new ArrayList<>();
        list.add(new Sprite("showerhead", "watercloset.png", 7, 2));
        list.add(new Sprite("showerwater", "watercloset.png", 7, 3));
        list.add(new Sprite("showersteam", "watercloset.png", 0, 5));

        return new Sprite("totalshower", "watercloset.png", 1, list);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new Action("Shower Off", SensoryLevel.PHYSICAL_ACTIVITY) {
            @Override
            protected String getVerb(Actor whosAsking) {
                return "showered";
            }

            @Override
            protected void execute(GameData gameData, Actor performingClient) {
                if (performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OnFireCharacterDecorator)) {
                    performingClient.removeInstance((GameCharacter gc) -> gc instanceof OnFireCharacterDecorator);
                    performingClient.addTolastTurnInfo("You were put out by the shower.");
                } else {
                    performingClient.addTolastTurnInfo("You don't really feel much cleaner...");
                }
            }

            @Override
            public void setArguments(List<String> args, Actor performingClient) {

            }
        });
    }
}
