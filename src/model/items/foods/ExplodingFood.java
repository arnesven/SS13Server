package model.items.foods;

import model.Actor;
import model.GameData;
import model.events.damage.ExplosiveDamage;
import model.events.Explosion;

public class ExplodingFood extends FoodItem {

	private FoodItem innerItem;
	private Actor maker;

	public ExplodingFood(FoodItem selectedItem, Actor maker) {
		super(selectedItem.getBaseName(), selectedItem.getWeight());
		this.innerItem = selectedItem;
		this.maker = maker;
	}

	
	
	@Override
	public double getFireRisk() {
		return innerItem.getFireRisk();
	}

	@Override
	protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
		innerItem.triggerSpecificReaction(eatenBy, gameData);
		eatenBy.getAsTarget().beExposedTo(maker, new ExplosiveDamage(3.0){
			@Override
			public String getText() {
				return "You exploded!";
			}
		});
		eatenBy.getPosition().addToEventsHappened(new Explosion());
	}



	@Override
	public ExplodingFood clone() {
		return new ExplodingFood(innerItem.clone(), this.maker);
	}

}
