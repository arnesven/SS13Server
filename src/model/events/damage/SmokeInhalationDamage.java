package model.events.damage;

public class SmokeInhalationDamage extends DamagerImpl {
    @Override
    public String getText() {
        return "Cough, cough... you're getting smoke in your lungs!";
    }

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return true;
    }

    @Override
    public String getName() {
        return "Smoke Inhalation";
    }

    @Override
    public double getDamage() {
        return 0.1;
    }
}
