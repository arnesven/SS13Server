package model.characters.decorators;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.map.GameMap;
import model.map.rooms.Room;
import model.modes.HuntGameMode;
import model.objects.AlienEggObject;
import model.objects.general.GameObject;

public class RespawnAsAlienAfterDeathDecorator extends CharacterDecorator {
    private final GameData gameData;
    private final HuntGameMode gameMode;
    private int diedRound;

    public RespawnAsAlienAfterDeathDecorator(GameCharacter character, GameData gameData, HuntGameMode hgm) {
        super(character, "Respawn as alien");
        this.gameData = gameData;
        this.gameMode = hgm;
    }

    @Override
    public void doUponDeath(Actor killer, GameItem killItem) {
        super.doUponDeath(killer, killItem);
        diedRound = gameData.getRound();
    }

    @Override
    public void doAtEndOfTurn(GameData gameData) {
        super.doAtEndOfTurn(gameData);
        if (getActor() instanceof Player) {
            if (isDead() && !gameMode.gameOver(gameData)) {
                if (gameData.getRound() == diedRound) {
                    ((Player) getActor()).setFancyFrame(gameMode.getJustDiedFancyFrame((Player)getActor()));
                } else if (gameData.getRound() > diedRound) {
                    for (Room r : gameData.getMap().getRoomsForLevel(GameMap.STATION_LEVEL_NAME)) {
                        for (GameObject obj : r.getObjects()) {
                            if (obj instanceof AlienEggObject) {
                                if (((AlienEggObject) obj).getNumber() > 0) {
                                    ((Player) getActor()).setFancyFrame(gameMode.getRespawnedFancyFrame((Player)getActor()));
                                    ((AlienEggObject)obj).splitOffIntoEggActor(getActor(), gameData);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
