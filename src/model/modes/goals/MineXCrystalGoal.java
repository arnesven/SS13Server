package model.modes.goals;

import model.Actor;
import model.GameData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.mining.OreShardBag;

public class MineXCrystalGoal extends PersonalGoal {
    private final int crystals;
    private boolean completed;

    public MineXCrystalGoal(int i) {
        this.crystals = i;
        completed = false;
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new OreShardBagCheckerDecorator(belongingTo.getCharacter()));
    }

    @Override
    public String getText() {
        return "Mine " + crystals + " ore shards during the game. (Put them in your Ore Shard Bag).";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return completed;
    }

    private class OreShardBagCheckerDecorator extends CharacterDecorator {
        public OreShardBagCheckerDecorator(GameCharacter character) {
            super(character, "Ore Shard Bag Checker");
        }

        @Override
        public void doAtEndOfTurn(GameData gameData) {
            super.doAtEndOfTurn(gameData);
            for (GameItem it : getItems()) {
                if (it instanceof OreShardBag) {
                    if (((OreShardBag) it).getShards().size() >= crystals) {
                        completed = true;
                    }
                }
            }
        }
    }
}
