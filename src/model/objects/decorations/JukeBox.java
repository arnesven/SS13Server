package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.QuickAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.map.rooms.BarRoom;
import model.map.rooms.JukeBoxRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JukeBox extends GameObject {
    public JukeBox(Room barRoom) {
        super("JukeBox", barRoom);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("jukebox", "jukebox.png", 0, 2, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        super.addSpecificActionsFor(gameData, cl, at);
        at.add(new ChangeMusicAction());
    }


    private Set<String> getAvailableTunes() {
        Set<String> result = new HashSet<>();
        result.add("ambidet1");
        result.add("title1");
        result.add("title2");
        result.add("ambivapor1");
        result.add("nothing");
        result.remove(getPosition().getAmbientSound());
        return result;
    }

    private class ChangeMusicAction extends Action implements QuickAction {

        private String selectedTune;

        public ChangeMusicAction() {
            super("Change Music", SensoryLevel.OPERATE_DEVICE);
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

        @Override
        protected String getVerb(Actor whosAsking) {
            return "changed the music on the jukebox";
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opts = super.getOptions(gameData, whosAsking);
            for (String s : JukeBox.this.getAvailableTunes()) {
                opts.addOption(s);
            }
            return opts;
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (JukeBox.this.getPosition() instanceof JukeBoxRoom) {
                ((JukeBoxRoom)JukeBox.this.getPosition()).setAmbientSound(selectedTune);
            }
        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {
            this.selectedTune = args.get(0);
        }
    }

}
