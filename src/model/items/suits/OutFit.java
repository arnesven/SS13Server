package model.items.suits;

import graphics.RegularBlackShoesSprite;
import graphics.Sprite;
import model.Actor;
import model.characters.crew.*;
import model.characters.decorators.DisguisedAs;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;
import model.objects.general.DispenserObject;

import java.util.ArrayList;
import java.util.List;

public class OutFit extends SuitItem {

	private GameCharacter type;

	public OutFit(GameCharacter chara) {
		super(chara.getBaseName() + "'s Outfit", 0.5);
		this.type = chara;
	}


    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (type instanceof DetectiveCharacter) {
            return new Sprite("detectivesuniform", "uniforms.png", 4, 6);
        } else if (type instanceof BiologistCharacter) {
                return new Sprite("biologistuniform", "uniforms.png", 3, 3);
        } else if (type instanceof DoctorCharacter) {
            return new Sprite("doctorsuniform", "uniforms.png", 6, 1);
        } else if (type instanceof EngineerCharacter) {
            return new Sprite("engineersuniform", "uniforms.png", 5, 4);
        } else if (type instanceof ScienceOfficerCharacter) {
            return new Sprite("scioffuniform", "uniforms.png",  7, 6);
        } else if (type instanceof GeneticistCharacter) {
            return new Sprite("geneticistuniform", "uniforms.png", 5);
        } else if (type instanceof RoboticistCharacter) {
            return new Sprite("roboticistuniform", "uniforms.png", 3, 4);
        } else if (type instanceof  TechnicianCharacter) {
            return new Sprite("techniciansuniform", "uniforms.png", 6, 4);
        } else if (type instanceof  ChefCharacter) {
            return new Sprite("chefsuniform", "uniforms.png", 5, 1);
        } else if (type instanceof  BartenderCharacter) {
            return new Sprite("bartendersuniform", "uniforms.png", 6, 6);
        } else if (type instanceof JanitorCharacter) {
            return new Sprite("janitorsuniform", "uniforms.png", 4, 1);
        } else if (type instanceof ChaplainCharacter) {
            return new Sprite("chaplainsuniform", "uniforms.png", 7, 1);
        } else if (type instanceof TouristCharacter) {
            return new Sprite("touristuniform", "uniforms.png", 6, 7);
        }
        return super.getSprite(whosAsking);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        if (type instanceof CaptainCharacter) {
            List<Sprite> list = new ArrayList<>();
            list.add(makeOutfit("capclothesandshoes", "uniform.png", 11, 5));
            return new Sprite("captainsuniformworn", "head.png", 8, 6, 32, 32, list);
        } else if (type instanceof HeadOfStaffCharacter) {
            return makeOutfit("hosuniformworn", "uniform2.png", 23, 18);
        } else if (type instanceof DetectiveCharacter) {
            return makeOutfit("detectivesuniformworn", "uniform.png", 0, 15);
        } else if (type instanceof BiologistCharacter) {
            return makeOutfit("biologistuniformworn", "uniform.png", 3, 3);
        } else if (type instanceof DoctorCharacter) {
            return makeOutfit("doctorsuniformworn", "uniform.png", 6, 1);
        } else if (type instanceof EngineerCharacter) {
            return makeOutfit("engineersuniformworn", "uniform2.png", 2, 6);
        } else if (type instanceof ScienceOfficerCharacter) {
            return makeOutfit("scieoffuniformworn", "uniform.png", 10, 15);
        } else if (type instanceof GeneticistCharacter) {
            return makeOutfit("geneticistuniformworn", "uniform2.png", 9, 7);
        } else if (type instanceof RoboticistCharacter) {
            return makeOutfit("roboticistsuniformworn", "uniform2.png", 1, 7);
        } else if (type instanceof TechnicianCharacter) {
            return makeOutfit("techniciansuniformworn", "uniform2.png", 27, 5);
        } else if (type instanceof ChefCharacter) {
            return makeOutfit("chefsuniformworn", "uniform.png", 18, 13);
        } else if (type instanceof  BartenderCharacter) {
            return makeOutfit("bartenderuniformworn", "uniform.png", 17, 7);
        } else if (type instanceof  JanitorCharacter) {
            return makeOutfit("janitorsuniformworn", "uniform.png", 6, 10);
        } else if (type instanceof  ChaplainCharacter) {
            return makeOutfit("chaplainsuniformworn", "uniform.png", 2, 7);
        } else if (type instanceof TouristCharacter) {
            return makeOutfit("touristsuniformworn", "uniform2.png", 14, 14);
        }
        return super.getWornSprite(whosAsking);
    }

    public static Sprite makeOutfit(String outfitname, String map, int i, int i1) {
        List<Sprite> list = new ArrayList<>();
        list.add(new RegularBlackShoesSprite());
        return new Sprite(outfitname, map, i, i1, 32, 32, list);
    }

    @Override
	public void beingTakenOff(Actor actionPerformer) {
		actionPerformer.removeInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof DisguisedAs;
            }
        });
	}

	@Override
	public boolean permitsOver() {
		return true;
	}

	@Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.setCharacter(new DisguisedAs(actionPerformer.getCharacter(), type.getBaseName()));
	}


	@Override
	public OutFit clone() {
		return new OutFit(this.type);
	}

}
