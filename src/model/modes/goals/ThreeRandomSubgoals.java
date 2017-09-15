package model.modes.goals;

import model.Actor;
import model.GameData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.foods.Banana;
import model.items.foods.Beer;
import model.items.foods.FoodItem;
import model.items.foods.SpaceBurger;
import model.items.general.*;
import util.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by erini02 on 08/12/16.
 */
public class ThreeRandomSubgoals extends CompositePersonalGoal {

  @Override
    protected List<PersonalGoal> initializeGoals() {

        List<PersonalGoal> subGoals = new ArrayList<>();

        subGoals.add(new PlaySaxophoneGoal());
        subGoals.add(new EatASpaceBurgerGoal());
        subGoals.add(new DrinkABeerGoal());
        subGoals.add(new EatABanana());
        subGoals.add(new HaveAMedkit());
        subGoals.add(new HaveAToolkit());
        subGoals.add(new HaveAPackOfSmokes());
        subGoals.add(new GetNakedGoal());
        subGoals.add(new HaveAChemicals());
        subGoals.add(new KeepHealthy());
        subGoals.add(new ParasiteKiller(1));
        subGoals.add(new CollectMoneyTask(250));
        subGoals.add(new PlaySlotsGoal(1));
        subGoals.add(new FireManGoal(1));
        subGoals.add(new HullBreachGoal(1));
        String[] someLocations = new String[]{"Lab", "Armory", "Bridge", "Greenhouse", "Sickbay", "Dorms", "Bar", "Kitchen", "Brig"};
        for (String s : someLocations) {
            subGoals.add(new VisitSpecificRoomGoal(s));
        }

        Collections.shuffle(subGoals);
        while (subGoals.size() > 3) {
            subGoals.remove(0);
        }
        return subGoals;
    }


    private static class PlaySaxophoneGoal extends DidAnActionGoal {
        public PlaySaxophoneGoal() {
            super(1, Saxophone.PlaySaxophoneAction.class);
        }

        @Override
        public String getText() {
            return "play the saxophone";
        }

        @Override
        protected String getNoun() {
             return "saxophone";
        }

        @Override
        protected String getVerb() {
             return "play";
        }
    }

    private static class EatASpaceBurgerGoal extends ConsumeASpecific {
        @Override
        public String getText() {
            return "eat a space burger";
        }

        @Override
        protected boolean checkFoodType(FoodItem foodItem) {
            return foodItem instanceof SpaceBurger;
        }
    }

    private static class DrinkABeerGoal extends ConsumeASpecific {

        @Override
        public String getText() {
            return "drink a beer";
        }

        @Override
        protected boolean checkFoodType(FoodItem foodItem) {
            return foodItem instanceof Beer;
        }
    }

    private static class EatABanana extends ConsumeASpecific {

        @Override
        public String getText() {
            return "eat a banana";
        }

        @Override
        protected boolean checkFoodType(FoodItem foodItem) {
            return foodItem instanceof Banana;
        }
    }

    private static abstract class ConsumeASpecific extends PersonalGoal {
        private boolean done = false;

        @Override
        public void setBelongsTo(Actor belongingTo) {
            super.setBelongsTo(belongingTo);
            belongingTo.setCharacter(new CheckEatBurgerDecorator(belongingTo.getCharacter()));
        }

        @Override
        public abstract String getText();

        @Override
        public boolean isCompleted(GameData gameData) {
            return done;
        }

        private class CheckEatBurgerDecorator extends CharacterDecorator {
            public CheckEatBurgerDecorator(GameCharacter character) {
                super(character, "consumecounter");
            }

            @Override
            public void doWhenConsumeItem(FoodItem foodItem, GameData gameData) {
                super.doWhenConsumeItem(foodItem, gameData);
               if (ConsumeASpecific.this.checkFoodType(foodItem)) {
                   done = true;
               }
            }
        }

        protected abstract boolean checkFoodType(FoodItem foodItem);
    }


    private class HaveAMedkit extends PersonalGoal {
        @Override
        public String getText() {
            return "have a medkit";
        }

        @Override
        public boolean isCompleted(GameData gameData) {
            return GameItem.hasAnItem(getBelongsTo(), new MedKit());
        }
    }

    private class HaveAPackOfSmokes extends PersonalGoal {
        @Override
        public String getText() {
            return "have a pack of smokes";
        }

        @Override
        public boolean isCompleted(GameData gameData) {
            return GameItem.hasAnItem(getBelongsTo(), new PackOfSmokes());
        }
    }

    private class HaveAToolkit extends PersonalGoal {
        @Override
        public String getText() {
            return "have a toolkit";
        }

        @Override
        public boolean isCompleted(GameData gameData) {
            return GameItem.hasAnItem(getBelongsTo(), new Tools());
        }
    }

    private static class GetNakedGoal extends PersonalGoal {
        @Override
        public String getText() {
            return "get naked";
        }

        @Override
        public boolean isCompleted(GameData gameData) {
            return getBelongsTo().getCharacter().getSuit() == null;
        }
    }


    private static class VisitSpecificRoomGoal extends PersonalGoal {

        private boolean visited = false;
        private String roomName;

        public VisitSpecificRoomGoal(String name) {
            this.roomName = name;
        }

        @Override
        public String getText() {
            return "visit the " + this.getRoomName();
        }

        @Override
        public boolean isCompleted(GameData gameData) {
            return visited;
        }

        @Override
        public void setBelongsTo(Actor belongingTo) {
            super.setBelongsTo(belongingTo);
            belongingTo.setCharacter(new CheckIfVisitingDecorator(getBelongsTo().getCharacter()));
        }

        protected String getRoomName() {
            return this.roomName;
        }

        private class CheckIfVisitingDecorator extends CharacterDecorator {
            public CheckIfVisitingDecorator(GameCharacter inner) {
                super(inner, "checkifvisiting");
            }

            @Override
            public void doAfterMovement(GameData gameData) {
                super.doAfterMovement(gameData);
                Logger.log("Checking if visiting " + getRoomName());
                if (getActor().getPosition().getName().equals(VisitSpecificRoomGoal.this.getRoomName())) {
                    visited = true;
                    Logger.log("   .... yes");
                }
            }
        }
    }


    private class HaveAChemicals extends PersonalGoal {
        @Override
        public String getText() {
            return "get some chemicals";
        }

        @Override
        public boolean isCompleted(GameData gameData) {
            return GameItem.hasAnItemOfClass(getBelongsTo(), Chemicals.class);
        }
    }

    private class KeepHealthy extends PersonalGoal {
        @Override
        public String getText() {
            return "keep healthy";
        }

        @Override
        public boolean isCompleted(GameData gameData) {
            return getBelongsTo().getCharacter().getHealth() >= 2.0;
        }
    }
}
