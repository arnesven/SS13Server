package model.items.suits;

import graphics.sprites.RegularBlackShoesSprite;
import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.crew.*;
import model.characters.decorators.DisguisedAs;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;
import model.characters.visitors.CaptainsDaughter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutFit extends TorsoAndShoesSuit {

	private GameCharacter type;

	public OutFit(GameCharacter chara) {
		super(chara.getBaseName() + "'s Outfit", 0.5, 49);
		this.type = chara;
	}

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Some clothing.";
    }

    @Override
    public int getEquipmentSlot() {
        return Equipment.TORSO_SLOT;
    }

    @Override
    public boolean blocksSlot(int targetSlot) {
        return false;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (type instanceof DetectiveCharacter) {
            return new Sprite("detectivesuniform", "uniforms.png", 4, 6, this);
        } else if (type instanceof BiologistCharacter) {
                return new Sprite("biologistuniform", "uniforms.png", 3, 3, this);
        } else if (type instanceof DoctorCharacter) {
            return new Sprite("doctorsuniform", "uniforms.png", 6, 1, this);
        } else if (type instanceof EngineerCharacter) {
            return new Sprite("engineersuniform", "uniforms.png", 5, 4, this);
        } else if (type instanceof ScienceOfficerCharacter) {
            return new Sprite("scioffuniform", "uniforms.png",  7, 6, this);
        } else if (type instanceof GeneticistCharacter) {
            return new Sprite("geneticistuniform", "uniforms.png", 5, this);
        } else if (type instanceof ChemistCharacter) {
            return new Sprite("chemistuniform", "uniforms.png", 4, 4, this);
        } else if (type instanceof RoboticistCharacter) {
            return new Sprite("roboticistuniform", "uniforms.png", 3, 4, this);
        } else if (type instanceof ArchitectCharacter) {
            return new Sprite("techniciansuniform", "uniforms.png", 6, 4, this);
        } else if (type instanceof  ChefCharacter) {
            return new Sprite("chefsuniform", "uniforms.png", 5, 1, this);
        } else if (type instanceof  BartenderCharacter) {
            return new Sprite("bartendersuniform", "uniforms.png", 6, 6, this);
        } else if (type instanceof JanitorCharacter) {
            return new Sprite("janitorsuniform", "uniforms.png", 4, 1, this);
        } else if (type instanceof ChaplainCharacter) {
            return new Sprite("chaplainsuniform", "uniforms.png", 7, 1, this);
        } else if (type instanceof TouristCharacter) {
            return new Sprite("touristuniform", "uniforms.png", 6, 7, this);
        } else if (type instanceof StaffAssistantCharacter) {
            return new Sprite("staffassistantuniform", "uniforms.png", 0, 3, this);
        } else if (type instanceof QuarterMasterCharacter) {
            return new Sprite("quartermasteruniform", "uniforms.png", 3, 4, this);
        } else if (type instanceof MinerCharacter) {
            return new Sprite("mineruniform", "uniforms.png", 7, 2, this);
        } else if (type instanceof SecurityCharacter) {
            return new Sprite("securityuniform", "uniforms.png", 7, 0, this);
        }
        return super.getSprite(whosAsking);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        if (type instanceof CaptainCharacter) {
            List<Sprite> list = new ArrayList<>();
            list.add(makeOutfit("capclothesandshoes", "suit.png", 101, 0, this));
            return new Sprite("captainsuniformworn", "head.png", 8, 6, 32, 32, list, this);
        } else if (type instanceof HeadOfStaffCharacter) {
            return makeOutfit("hosuniformworn", "uniform2.png", 23, 18, this);
        } else if (type instanceof DetectiveCharacter) {
            return makeOutfit("detectivesuniformworn", "uniform.png", 0, 15, this);
        } else if (type instanceof BiologistCharacter) {
            return makeOutfit("biologistuniformworn", "uniform.png", 3, 3, this);
        } else if (type instanceof DoctorCharacter) {
            return makeOutfit("doctorsuniformworn", "uniform.png", 6, 1, this);
        } else if (type instanceof EngineerCharacter) {
            return makeOutfit("engineersuniformworn", "uniform2.png", 2, 6, this);
        } else if (type instanceof ScienceOfficerCharacter) {
            return makeOutfit("scieoffuniformworn", "uniform.png", 10, 15, this);
        } else if (type instanceof GeneticistCharacter) {
            return makeOutfit("geneticistuniformworn", "uniform2.png", 9, 7, this);
        } else if (type instanceof ChemistCharacter) {
            return makeOutfit("chemistuniformworn", "uniform.png", 16, 6, this);
        } else if (type instanceof RoboticistCharacter) {
            return makeOutfit("roboticistsuniformworn", "uniform2.png", 1, 7, this);
        } else if (type instanceof ArchitectCharacter) {
            return makeOutfit("techniciansuniformworn", "uniform2.png", 27, 5, this);
        } else if (type instanceof ChefCharacter) {
            return makeOutfit("chefsuniformworn", "uniform.png", 18, 13, this);
        } else if (type instanceof  BartenderCharacter) {
            return makeOutfit("bartenderuniformworn", "uniform.png", 17, 7, this);
        } else if (type instanceof  JanitorCharacter) {
            return makeOutfit("janitorsuniformworn", "uniform.png", 6, 10, this);
        } else if (type instanceof  ChaplainCharacter) {
            return makeOutfit("chaplainsuniformworn", "uniform.png", 2, 7, this);
        } else if (type instanceof TouristCharacter) {
            return makeOutfit("touristsuniformworn", "uniform2.png", 14, 14, this);
        } else if (type instanceof CaptainsDaughter) {
            return makeOutfit("captainsdaughteruniformworn", "uniform2.png", 22, 14, this);
        } else if (type instanceof StaffAssistantCharacter) {
            return makeOutfit("staffassuniformworn", "uniform2.png",19,5, this);
        } else if (type instanceof QuarterMasterCharacter) {
            return makeOutfit("quartermasteruniformworn", "uniform2.png", 31, 18, this);
        } else if (type instanceof MinerCharacter) {
            return makeOutfit("mineruniformworm", "uniform2.png", 29, 3, this);
        } else if (type instanceof SecurityCharacter) {
            return makeOutfit("securityuniformworn", "uniform2.png", 16, 0, this);
        }
        return super.getWornSprite(whosAsking);
    }

    public static Sprite makeOutfit(String outfitname, String map, int col, int row, SpriteObject objRef) {
        List<Sprite> list = new ArrayList<>();
        list.add(new RegularBlackShoesSprite());
        return new Sprite(outfitname, map, col, row, 32, 32, list, objRef);
    }

    @Override
	public void beingTakenOff(Actor actionPerformer) {
        InstanceChecker disguiesCheck = new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof DisguisedAs;
            }
        };

        if (actionPerformer.getCharacter().checkInstance(disguiesCheck)) {
            actionPerformer.removeInstance(disguiesCheck);
        }
	}

	@Override
	public boolean permitsOver() {
		return true;
	}

    @Override
    protected Map<Integer, Sprite> getOtherSprites() {
	    Map<Integer, Sprite> map = new HashMap<>();
	    map.put(Equipment.FEET_SLOT, RegularBlackShoesSprite.EQ_SPRITE);
        return map;
    }

    @Override
	public void beingPutOn(Actor actionPerformer) {
		actionPerformer.setCharacter(new DisguisedAs(actionPerformer.getCharacter(), type.getBaseName()));
	}


	@Override
	public OutFit clone() {
		return new OutFit(this.type);
	}

    public GameCharacter getType() {
        return type;
    }
}
