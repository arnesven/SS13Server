package model.map.rooms;

import graphics.sprites.LifeBarSprite;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.special.SpectatorCharacter;
import model.items.NoSuchThingException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 20/12/16.
 */
public class SpectatorRoom extends Room {

    private final GameData gameData;

    public SpectatorRoom(GameData gameData) {
        super(999, "Players", "", 5, 5, 0, 0, new int[]{999}, new double[]{}, RoomType.hidden);
        this.gameData = gameData;
    }

    @Override
    public List<String> getInfo(Player whosAsking) {
        ArrayList<String> info = new ArrayList<>();
        for (Player p : gameData.getPlayersAsList()) {
            if (!(p.getInnermostCharacter() instanceof SpectatorCharacter)) {

                List<Sprite> spriteList = new ArrayList<>();
                spriteList.add(p.getCharacter().getSprite(whosAsking));
                for (int i = 0; i < p.getItems().size(); ++i) {
                    spriteList.add(p.getItems().get(i).getSprite(whosAsking));
                }
                Sprite sp = Sprite.makeSpectatorItems(spriteList);

                try {
                    info.add(sp.getName() + "<img>" + whosAsking.getCharacter().getHowPerceived(p) +
                            " [" + gameData.getClidForPlayer(p) + "]");
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }

                String sub = "Crew";
                if (gameData.getGameMode().isAntagonist(p)) {
                    sub = gameData.getGameMode().getAntagonistName(p);
                }
                info.add(new LifeBarSprite(p.getHealth()).getName() + "<img>" + sub);

            }
        }

        return info;
    }

    @Override
    public void pushHappeningsToPlayers() {
        for (Actor spectator : this.getActors()) {
            for (Actor a : gameData.getActors()) {
                if (a instanceof Player && !(a.getInnermostCharacter() instanceof SpectatorCharacter)) {

                    List<String> strs = new ArrayList<>();
                    try {
                        String act = ((Player) a).getNextAction().getDescription(spectator);
                        strs.add(act);
                    } catch (IllegalStateException ise) {
                        // probably not called this actions doTheAction...
                    }
//                    for (String s : ((Player) a).getLastTurnInfo()) {
//                        if (!s.toLowerCase().contains("you")) {
//                            strs.add(s);
//                        }
//                    }
                    for (String s : strs) {
                        spectator.addTolastTurnInfo(s);
                    }
                }
            }
        }


    }
}
