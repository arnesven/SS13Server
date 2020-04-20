package model.characters.general;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.actions.general.SensoryLevel;
import model.characters.decorators.CharacterDecorator;
import model.items.general.GameItem;
import model.items.weapons.Weapon;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/11/16.
 */
public class CounterAttackAction extends Action {

    private List<Actor> aggressors = new ArrayList<>();

    public CounterAttackAction() {
        super("Counter-Attack", SensoryLevel.PHYSICAL_ACTIVITY);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "counter-attacked";
    }


    @Override
    protected void execute(GameData gameData, Actor performingClient) {

        gameData.executeAtEndOfRound(performingClient, this);


    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        if (!performingClient.getCharacter().checkInstance((GameCharacter gc ) -> gc instanceof RecordWhoIsAttackingActorDecorator)) {
            performingClient.setCharacter(new RecordWhoIsAttackingActorDecorator(performingClient.getCharacter()));
        }
    }

    @Override
    public void lateExecution(GameData gameData, Actor performingClient) {
        if (!aggressors.isEmpty()) {

            // TODO, target was not set, gave a null ptr exception. Was counter attack vs autoturret.
//            AttackAction atk = new AttackAction(performingClient);
//            List<String> args = new ArrayList<>();
//
//            args.add(MyRandom.sample(aggressors).getPublicName());
//            Weapon w = getWeapon(performingClient);
//            atk.addWithWhat(w);
//
//            args.add(w.getPublicName(performingClient));
//
//            atk.setArguments(args, performingClient);
//            atk.printAndExecute(gameData);
        }
        if (performingClient.getCharacter().checkInstance((GameCharacter gc ) -> gc instanceof RecordWhoIsAttackingActorDecorator)) {
            performingClient.removeInstance((GameCharacter gc ) -> gc instanceof RecordWhoIsAttackingActorDecorator);
        }

    }

    private class RecordWhoIsAttackingActorDecorator extends CharacterDecorator {
        public RecordWhoIsAttackingActorDecorator(GameCharacter character) {
            super(character,"recording who is attacking");
        }

        @Override
        public boolean beAttackedBy(Actor performingClient, Weapon weapon, GameData gameData) {
            aggressors.add(performingClient);
            return super.beAttackedBy(performingClient, weapon, gameData);
        }
    }

     private Weapon getWeapon(Actor npc) {
		for (GameItem it : npc.getItems()) {
			if (it instanceof Weapon) {
				if (((Weapon)it).isReadyToUse()) {
					return (Weapon)it;
				}
			}
		}

		return npc.getCharacter().getDefaultWeapon();
	}
}
