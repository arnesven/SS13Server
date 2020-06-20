package model.objects.clawcrane;

import model.items.CapsulePrize;

class ClawCraneCapsulePrize extends ClawCranePrize {
    public ClawCraneCapsulePrize(ClawCraneGame game) {
        super(game, new CapsulePrize());
    }
}
