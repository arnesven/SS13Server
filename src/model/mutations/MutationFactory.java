package model.mutations;

import java.util.ArrayList;
import java.util.List;

import util.MyRandom;
import model.GameData;
import model.characters.general.GameCharacter;

public class MutationFactory {

	public static Mutation getActorMutation(GameCharacter target, GameData gameData) {
		return new CopyCharacterPowerMutation(target); 
		
	}

	private static List<Mutation> randMutes(GameData gameData) {
		List<Mutation> mutes = new ArrayList<>();
		mutes.add(new StrongerMutation());
		mutes.add(new SluggishMutation());
		mutes.add(new HuskMutation());
		mutes.add(new BlueMutation());
		mutes.add(new IronFists());
		mutes.add(new SexChange());
		mutes.add(new SuperSprint());
		mutes.add(new HorrorAppearance());
		mutes.add(new ChimpAppearance());
        mutes.add(new ExtraHealthMutation());
		
		return mutes;
	}
	
	public static Mutation getRandomMutation(GameData gameData) {
		return MyRandom.sample(randMutes(gameData));
	}

    public static List<Mutation> getAllMutations(GameData gameData) {
        return randMutes(gameData);
    }
}
