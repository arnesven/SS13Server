package model.actions.manager;

import model.actions.general.Action;

import java.util.HashMap;
import java.util.Map;

public class ActionManager {

    private static Map<Long, Action> storedActions = new HashMap<>();

    public static void store(Action action) {
        storedActions.put(action.getUID(), action);
    }

    public static void clearMap() {
        storedActions.clear();
        Action.resetUidCounter();
    }
}
