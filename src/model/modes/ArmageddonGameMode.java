package model.modes;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.events.SpontaneousExplosionEvent;
import model.events.ZombifierEvent;
import model.events.ambient.AmbientEvent;
import model.events.ambient.SpontaneousCrazyness;

import java.util.ArrayList;

/**
 * Created by erini02 on 03/05/16.
 */
public class ArmageddonGameMode extends GameMode {

    private ZombifierEvent zombieEvent;

    @Override
    public String getName() {
        return "Armageddon";
    }

    @Override
    protected void setUpOtherStuff(GameData gameData) {
        for (Event e : getEvents().values()) {
            if (e instanceof SpontaneousCrazyness || e instanceof SpontaneousExplosionEvent) {
                e.setProbability(e.getProbability() * 5.0);
            } else {
                e.setProbability(e.getProbability() * 2.0);
            }
        }
        zombieEvent = new ZombifierEvent();
        gameData.addEvent(zombieEvent);

    }

    @Override
    protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters,
                                    GameData gameData) {

    }

    public GameOver getGameResultType(GameData gameData) {
        if (gameData.isAllDead()) {
            return GameOver.ALL_DEAD;
        } else if (gameData.getRound() == gameData.getNoOfRounds()) {
            return GameOver.TIME_IS_UP;
        }
        return null;
    }

        @Override
    public boolean gameOver(GameData gameData) {
        return getGameResultType(gameData) != null;
    }

    @Override
    public void setStartingLastTurnInfo() {
        // none
    }

    @Override
    protected void addAntagonistStartingMessage(Player c) {
        // No antagonists in this game mode
    }

    @Override
    protected void addProtagonistStartingMessage(Player c) {
        c.addTolastTurnInfo("Armageddon has come! Just try to survive!");
    }

    @Override
    protected boolean isAntagonist(Player c) {
        return false;
    }

    @Override
    public String getSummary(GameData gameData) {
        return new GameStats(gameData, this) {
            @Override
            protected String getModeSpecificStatus(Actor value) {
                return zombieEvent.isZombie(value)?"<span style='background-color: #22FF22'>Zombie</span>":"";
            }

            @Override
            protected String getExtraDeadInfo(Actor value) {
                return zombieEvent.isZombie(value)?"(Zombie)":"";
            }

            @Override
            protected String modeSpecificExtraInfo(Actor value) {
                return "";
            }

            @Override
            public String getMode() {
                return ArmageddonGameMode.this.getName();
            }

            @Override
            public String getOutcome() {
                GameOver status = getGameResultType(gameData);
                if (status == GameOver.TIME_IS_UP) {
                    return "Crew Team Wins!";
                }
                if (status == GameOver.ALL_DEAD) {
                    return "Everybody Lost!";
                }

                throw new IllegalStateException("Tried to get game outcome before game was over!");
            }

            @Override
            public String getEnding() {
                GameOver status = getGameResultType(gameData);
                if (status == GameOver.TIME_IS_UP) {
                    return "Time limit reached";
                }

                return "All players died";
            }

            @Override
            public String getContent() {
                return "";
            }
        }.toString();
    }
}
