package model.modes.goals;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.PrayerAction;
import model.characters.crew.ChaplainCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.misc.ReligiousFigure;
import model.objects.Altar;
import util.Logger;
import util.MyRandom;

public class PrayToReligiousFigure extends PersonalGoal {
    private final int times;
    private final ReligiousFigure god;
    private int didTimes;

    public PrayToReligiousFigure(int times) {
        this.times = times;
        this.god = MyRandom.sample(Altar.getReligiousFigures());
        didTimes = 0;
    }

    @Override
    public String getText() {
        return "You are a " + god.getPracticionerName() + ", pray to " + god.getName() + " " + times + " times!";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return didTimes >= times;
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        belongingTo.setCharacter(new CharacterDecorator(belongingTo.getCharacter(), "prayercounter") {
            @Override
            public void doAfterActions(GameData gameData) {
                super.doAfterActions(gameData);
                Action a = ((Player)getActor()).getNextAction();
                if (a instanceof PrayerAction) {
                    if (((PrayerAction) a).getSelectedGod().getName().equals(god.getName())) {
                        didTimes += 1;
                    }
                }
            }
        });
    }

    @Override
    public boolean isApplicable(GameData gameData, Actor potential) {
        return potential.getCharacter().checkInstance((GameCharacter gc) -> !(gc instanceof ChaplainCharacter));
    }
}
