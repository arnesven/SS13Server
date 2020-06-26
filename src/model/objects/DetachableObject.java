package model.objects;

import model.Actor;
import model.GameData;

public interface DetachableObject {
    String getName();

    void detachYourself(GameData gameData, Actor performer);

    String getDetachingDescription();

    int getDetatchTimeRounds();
}
