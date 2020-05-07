package model.objects.recycling;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.items.general.GameItem;
import model.map.rooms.JanitorialRoom;
import model.map.rooms.Room;
import model.objects.general.CrateObject;
import model.objects.general.GameObject;

public class RecyclingContainer extends CrateObject {
    private static final int CONTAINER_MAX_SIZE = 12;

    public RecyclingContainer(Room r) {
        super(r, "Recycling Container");
    }

    public boolean isFull() {
        return getInventory().size() >= CONTAINER_MAX_SIZE;
    }

    public void recycle(GameItem selectedItem) {
        getInventory().add(selectedItem);
        selectedItem.setPosition(getPosition());
        getPosition().addToEventsHappened(new ItemGotRecycledEvent());
    }

    private class ItemGotRecycledEvent extends Event {
        @Override
        public void apply(GameData gameData) {

        }

        @Override
        public String getDistantDescription() {
            return "<b><i>Ka-chunk!</i></b>";
        }

        @Override
        public String howYouAppear(Actor performingClient) {
            return "<b><i>Ka-chunk!</i></b> Sounds like something fill into the recycling container.";
        }

        @Override
        public SensoryLevel getSense() {
            return new SensoryLevel(SensoryLevel.VisualLevel.INVISIBLE, SensoryLevel.AudioLevel.VERY_LOUD, SensoryLevel.OlfactoryLevel.UNSMELLABLE);
        }
    }
}
