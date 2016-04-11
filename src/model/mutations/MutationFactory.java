package model.mutations;

import java.util.ArrayList;
import java.util.List;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.actions.Action;
import model.characters.GameCharacter;
import model.characters.decorators.FireProtection;

public class MutationFactory {

	public static Mutation getActorMutation(GameCharacter target, GameData gameData) {
		return new CopyCharacterPowerMutation(target); 
		
	}

	private static List<Mutation> randMutes() {
		List<Mutation> mutes = new ArrayList<>();
		mutes.add(new StrongerMutation());
		mutes.add(new SluggishMutation());
		mutes.add(new HuskMutation());
		mutes.add(new BlueMutation());
		return mutes;
	}
	
	public static Mutation getRandomMutation() {
		return MyRandom.sample(randMutes());
	}

}
