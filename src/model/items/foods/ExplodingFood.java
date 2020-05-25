package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.characters.general.GameCharacter;
import model.events.SpontaneousExplosionEvent;
import model.events.damage.ExplosiveDamage;
import model.items.general.ExplodableItem;
import model.items.general.GameItem;
import model.map.rooms.Room;

public class ExplodingFood extends FoodItem {

    private final ExplodableItem expel;
    private FoodItem innerItem;
	private Actor maker;

	public ExplodingFood(FoodItem selectedItem, Actor maker, ExplodableItem expel, int cost) {
		super(selectedItem.getBaseName(), selectedItem.getWeight(), cost);
		this.innerItem = selectedItem;
		this.maker = maker;
        this.expel = expel;
        expel.setConceledWithin(this);
	}

    @Override
    public GameItem getTrueItem() {
        return innerItem;
    }

    @Override
    public String getFullName(Actor whosAsking) {
        if (whosAsking == maker) {
            return "Exploding " + super.getFullName(whosAsking);
        }
        return super.getFullName(whosAsking);
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        if (whosAsking == maker) {
            return "Exploding " + super.getPublicName(whosAsking);
        }
        return super.getPublicName(whosAsking);
    }

    @Override
	public double getFireRisk() {
		return innerItem.getFireRisk();
	}

	@Override
	protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
		innerItem.triggerSpecificReaction(eatenBy, gameData);
		eatenBy.getAsTarget().beExposedTo(maker, new ExplosiveDamage(2.0, this){
			@Override
			public String getText() {
				return "You exploded!";
			}
		}, gameData);
        eatenBy.getItems().remove(expel.getAsItem());
        eatenBy.getPosition().addItem(expel.getAsItem());
        expel.explode(gameData, eatenBy.getPosition(), maker);
		eatenBy.getPosition().addToEventsHappened(new SpontaneousExplosionEvent());
	}

    @Override
    public void gotGivenTo(Actor to, Target from) {
        super.gotGivenTo(to, from);
        innerItem.setHolder(to.getCharacter());
        expel.getAsItem().setHolder(to.getCharacter());
    }

    @Override
    public void setHolder(GameCharacter gameCharacter) {
        super.setHolder(gameCharacter);
        innerItem.setHolder(gameCharacter);
        expel.getAsItem().setHolder(gameCharacter);
    }

    @Override
    public void setPosition(Room p) {
        super.setPosition(p);
        innerItem.setPosition(p);
        expel.getAsItem().setPosition(p);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return innerItem.getSprite(whosAsking);
    }

    @Override
	public FoodItem clone() {
        return innerItem.clone();
    }

    @Override
    public boolean canBeCooked(GameData gameData, Actor performingClient) {
        return innerItem.canBeCooked(gameData, performingClient);
    }

    @Override
    public String getExtraDescriptionStats(GameData gameData, Player performingClient) {
        return innerItem.getExtraDescriptionStats(gameData, performingClient);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return innerItem.getDescription(gameData, performingClient);
    }
}
