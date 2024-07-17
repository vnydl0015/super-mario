import java.util.Random;

/**
 * Superclass to Enemy and FlyPlatform.
 */
public abstract class RandomMoveObject extends Entities {

    private double displacement = 0;
    private boolean reversingRandomMovement = false;
    private final int randomDirection = new Random().nextBoolean() ? GameSetting.LEFT : GameSetting.RIGHT;
    private final int MAX_DISPLACEMENT;
    private final int RANDOM_SPEED;

    /**
     * The constructor of RandomMoveObject
     * @param entity the string that represents RandomMoveObject entity
     * @param x X coordinate of RandomMoveObject object being referred to
     * @param y Y coordinate of RandomMoveObject object being referred to
     */
    public RandomMoveObject(String entity, double x, double y) {
        super(entity);
        super.goToLocation(x, y);
        MAX_DISPLACEMENT = Integer.parseInt(GameSetting.getProps().getProperty("gameObjects." + entity +
                ".maxRandomDisplacementX"));
        RANDOM_SPEED = Integer.parseInt(GameSetting.getProps().getProperty("gameObjects." + entity + ".randomSpeed"));
    }

    /**
     * Creating random horizontal movement
     */
    protected void randomMove() {
        int currSpeed = RANDOM_SPEED;
        // move in one direction (either left or right)
        if (displacement <= MAX_DISPLACEMENT) {
            currSpeed *= (reversingRandomMovement ? GameSetting.RIGHT : GameSetting.LEFT);
            this.goToLocation(super.getX() + (currSpeed * randomDirection), super.getY());
            displacement += currSpeed;
        }
        // move to the opposite direction
        if (displacement == MAX_DISPLACEMENT && !reversingRandomMovement) { reversingRandomMovement = true; }
        // reset to original location
        if (displacement == 0 && reversingRandomMovement) { reversingRandomMovement = false; }
    }
}
