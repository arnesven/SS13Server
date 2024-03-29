package model.modes.goals;

import model.Actor;
import model.GameData;
import model.Player;
import model.PlayerSettings;
import model.characters.crew.*;
import model.characters.decorators.PersonalGoalDecorator;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.characters.visitors.VisitorCharacter;
import model.events.BackgroundEvent;
import model.modes.GameMode;
import util.MyRandom;

import java.io.Serializable;
import java.util.*;

/**
 * Created by erini02 on 03/12/16.
 */
public class  PersonalGoalAssigner implements Serializable {
    private Map<Actor, PersonalGoal> goalsForActors = new HashMap<>();
    private final Collection<PersonalGoal> generalGoals;
    private final Map<String, Collection<PersonalGoal>> jobSpecificGoals;
    private final Collection<PersonalGoal> pairGoals;

    public PersonalGoalAssigner(GameData gameData) {
        generalGoals = createGeneralGoals();
        jobSpecificGoals = createJobSpecificGoals(gameData);
        pairGoals = createPairGoals(gameData);
    }


    private Map<String,Collection<PersonalGoal>> createJobSpecificGoals(GameData gameData) {
        Map<String, Collection<PersonalGoal>> jobSpecificGoals = newEmptyMap();
        jobSpecificGoals.get(new CaptainCharacter().getBaseName()).add(new KeepYourCaptainlyness());
        jobSpecificGoals.get(new CaptainCharacter().getBaseName()).add(new CallEscapeShuttleGoal());
        jobSpecificGoals.get(new CaptainCharacter().getBaseName()).add(new KeepCrewAliveGoal());
        jobSpecificGoals.get(new HeadOfStaffCharacter().getBaseName()).add(new DoJobChange());
        jobSpecificGoals.get(new HeadOfStaffCharacter().getBaseName()).add(new OrderShipmentsGoal(MyRandom.nextInt(2)+2));
        jobSpecificGoals.get(new HeadOfStaffCharacter().getBaseName()).add(new KeepCrewAliveGoal());
        jobSpecificGoals.get(new SecurityOfficerCharacter().getBaseName()).add(new BatonABaddieGoal(gameData));
        jobSpecificGoals.get(new SecurityOfficerCharacter().getBaseName()).add(new BrigAnAntagonistGoal(gameData));
        jobSpecificGoals.get(new DetectiveCharacter().getBaseName()).add(new GuessGameModeGoal(MyRandom.nextInt(5)+12));
        jobSpecificGoals.get(new DoctorCharacter().getBaseName()).add(new HealXTimes(MyRandom.nextInt(3)+3));
        jobSpecificGoals.get(new DoctorCharacter().getBaseName()).add(new InjectXTimes(MyRandom.nextInt(2)+2));
        jobSpecificGoals.get(new BiologistCharacter().getBaseName()).add(new PlantXTimesGoal(MyRandom.nextInt(2) + 2));
        jobSpecificGoals.get(new BiologistCharacter().getBaseName()).add(new TrainXDifferentAnimals(MyRandom.nextInt(3)+2));
        jobSpecificGoals.get(new EngineerCharacter().getBaseName()).add(new KeepPowerOverPct(MyRandom.nextInt(7)*5+50));
        jobSpecificGoals.get(new EngineerCharacter().getBaseName()).add(new AlignSolarArraysGoal(MyRandom.nextInt(2)+1));
        jobSpecificGoals.get(new ScienceOfficerCharacter().getBaseName()).add(new LeaveStationGoal());
        jobSpecificGoals.get(new ScienceOfficerCharacter().getBaseName()).add(new FinishMonolithExperimentGoal());
        jobSpecificGoals.get(new GeneticistCharacter().getBaseName()).add(new InjectXTimes(MyRandom.nextInt(2)+2));
        jobSpecificGoals.get(new RoboticistCharacter().getBaseName()).add(new BorgBackToLifeGoal());
        jobSpecificGoals.get(new RoboticistCharacter().getBaseName()).add(new BuildRobotsAction());
        jobSpecificGoals.get(new ArchitectCharacter().getBaseName()).add(new BuildNewRoomGoal());
        jobSpecificGoals.get(new ArchitectCharacter().getBaseName()).add(new BuildNewDoorsGoal(MyRandom.nextInt(2)+2));
        jobSpecificGoals.get(new ChefCharacter().getBaseName()).add(new GetOthersToEatYourFood(MyRandom.nextInt(3)+2));
        jobSpecificGoals.get(new ChefCharacter().getBaseName()).add(new MakeCustomDishGoal());
        jobSpecificGoals.get(new BartenderCharacter().getBaseName()).add( new HaveABusyBar(MyRandom.nextInt(2)+3));
        jobSpecificGoals.get(new JanitorCharacter().getBaseName()).add(new CleanUpBloodyMesses(MyRandom.nextInt(2)+2));
        jobSpecificGoals.get(new JanitorCharacter().getBaseName()).add(new CarryManyThings(MyRandom.nextInt(7)+7));
        jobSpecificGoals.get(new ChaplainCharacter().getBaseName()).add(new SingXSermonsGoal());
        jobSpecificGoals.get(new ChaplainCharacter().getBaseName()).add(new MarryXPeople(MyRandom.nextInt(6)));
        jobSpecificGoals.get(new QuarterMasterCharacter().getBaseName()).add(new SellXCratesGoal(MyRandom.nextInt(1)+2));
        jobSpecificGoals.get(new QuarterMasterCharacter().getBaseName()).add(new OrderShipmentsGoal(MyRandom.nextInt(2)+2));
        jobSpecificGoals.get(new MinerCharacter().getBaseName()).add(new MineXCrystalGoal(MyRandom.nextInt(3)+5));
        // Job goal for Visitor
        return jobSpecificGoals;
    }

    private static Collection<PersonalGoal> createGeneralGoals() {
        HashSet<PersonalGoal> goals = new HashSet<>();
        goals.add(new DrugDealerGaoal());
        goals.add(new CollectMoneyTask(MyRandom.nextInt(3)*100 + 300));
        goals.add(new ParasiteKiller(MyRandom.nextInt(3) + 3));
        goals.add(new FireManGoal(MyRandom.nextInt(3) + 3));
        goals.add(new HullBreachGoal(MyRandom.nextInt(2) + 2));
        goals.add(new PlaySlotsGoal(MyRandom.nextInt(2) + 3));
        goals.add(new AvengerGoal());
        goals.add(new CollectThreeChemicals());
        goals.add(new CollectThreeWeapons());
        goals.add(new BeColdGoal());
        goals.add(new BeHotGoal());
        goals.add(new JumpTheStationGoal());
        goals.add(new EatDifferentFoods(MyRandom.nextInt(2)+3));
        goals.add(new GoIntoSpaceGoal());
        goals.add(new CrawlIntoVentilationShaft());
        goals.add(new SickOfYourselfGoal());
        goals.add(new LayerUponLayerGoal(MyRandom.nextInt(4)+3));
        goals.add(new PacifistGoal(MyRandom.nextInt(2)));
        goals.add(new PervertGoal());
        goals.add(new BeatUpTheClownGoal());
        goals.add(new DrinkAlcoholGoal(MyRandom.nextInt(2)+2));
        goals.add(new BeOnARapido());
        goals.add(new HaveABrain());
        goals.add(new HaveACrowbar());
        goals.add(new HaveAMushroom());
        goals.add(new HaveAnOrange());
        goals.add(new GetMarriedGoal());
        goals.add(new PrayToReligiousFigure(MyRandom.nextInt(2)+1));
        goals.add(new EscapeOnEscapeShuttleGoal());
       // goals.add(new TakeDifferentTypesOfDamage());
        goals.add(new ThreeRandomSubgoals());
        goals.add(new ThreeRandomSubgoals());
        goals.add(new ThreeRandomSubgoals());
        goals.add(new ThreeRandomSubgoals());
        return goals;
    }

    private Collection<PersonalGoal> createPairGoals(GameData gameData) {
        Collection<PersonalGoal> goals = new ArrayList<>();

        return goals;

    }



    public void addTasks(GameData gameData) {
        for (Actor a : gameData.getActors()) {
            if (generalGoals.size() > 0) {
                if (a instanceof Player) {
                    if (!((Player) a).isASpectator() && !(a.getInnermostCharacter() instanceof AICharacter)) {
                        if (!gameData.getGameMode().isAntagonist((Player) a)) {
                            if (((Player) a).getSettings().get(PlayerSettings.GIVE_ME_A_TASK)) {
                                setGoal(gameData, a);
                            }
                        }
                    }
                }
            }
        }
    }


    public Map<Actor, PersonalGoal> getGoalsForActors() {
        return goalsForActors;
    }



    private void setGoal(GameData gameData, Actor a) {
        PersonalGoal goal = null;
        if (jobSpecificGoals.get(a.getBaseName()).isEmpty() || MyRandom.nextDouble() < 0.667) {
            goal = giveGoalToActorFrom(a, gameData, generalGoals);
        } else {
            goal = giveGoalToActorFrom(a, gameData, jobSpecificGoals.get(a.getBaseName()));
        }
        if (goal != null) {
            PersonalGoal finalGoal = goal;
            a.setCharacter(new PersonalGoalDecorator(a.getCharacter(), finalGoal));
        }
    }

    public void giveGoalToLateJoiner(GameData gameData, Player p) {
        setGoal(gameData, p);
    }

    private PersonalGoal giveGoalToActorFrom(Actor a, GameData gameData, Collection<PersonalGoal> setOfGoals) {
        PersonalGoal pt;
        do {
            pt = MyRandom.sample(setOfGoals);
            setOfGoals.remove(pt);
            if (pt.isApplicable(gameData, a)) {
                pt.setBelongsTo(a);
                goalsForActors.put(a, pt);
                a.addTolastTurnInfo("Personal Goal: " + pt.getText());
                break;
            }
        } while (setOfGoals.size() > 0);
        return pt;
    }




    private static HashMap<String, Collection<PersonalGoal>> newEmptyMap() {
        HashMap<String, Collection<PersonalGoal>> result = new HashMap<>();
        for (GameCharacter gc : GameMode.getAllCrew()) {
            if (gc instanceof VisitorCharacter) {
                for (GameCharacter gc2 : ((VisitorCharacter) gc).getSubtypes()) {
                    result.put(gc2.getBaseName(), new HashSet<>());
                }
            } else {
                result.put(gc.getBaseName(), new HashSet<>());
            }
        }
        result.put((new AICharacter(0, null, false)).getBaseName(), new HashSet<>());
        return result;
    }



}
