import bagel.Window;
import bagel.util.Colour;
import bagel.DrawOptions;

/**
 * Unique properties of Health
 */
public class Health extends Message implements Updatable {

    private double healthScore;
    private static boolean getInflicted = true;
    private boolean isEnemy = false;
    private static final int DIE_SPEED = 2;

    /**
     * The constructor of health.
     * @param entity String that represents Health
     */
    public Health(String entity) {
        super(entity + "Health");
        healthScore = Double.parseDouble(GameSetting.getProps().getProperty("gameObjects." + entity + ".health"));
        // to distinguish between enemy boss and player
        if (entity.equals("enemyBoss")) { isEnemy = true; }
    }

    /**
     * Reduces either player's or enemy boss' health by value
     * @param value damage size
     */
    public void updateValue(double value) {
        if (isEnemy || getInflicted) { healthScore -= value; }
    }

    /**
     * Draw health score of either player or enemy boss
     */
    @Override
    public void myDrawString() {
        getWordProps().drawString(getMessageString() + (int) Math.round(healthScore * 100), getX(), getY(),
                isEnemy ? (new DrawOptions().setBlendColour(Colour.RED)):
                        new DrawOptions().setBlendColour(Colour.WHITE));
    }

    /**
     * Check health score of player. If below or equal 0, drop player into the ground
     * @param entity either player or enemy boss
     * @return true is entity is dead and below the ground. Otherwise, false
     */
    public boolean isDying(Entities entity, GameSetting gameSetting) {
        if (healthScore <= 0) {
            entity.goToLocation(entity.getX(), entity.getY() + DIE_SPEED);
            if (!isEnemy) { gameSetting.setCanMove(false); }
            return entity.getY() > Window.getHeight();
        }
        return false;
    }

    /**
     * Set isInflicted status
     * @param isInflicted True if the entity can be hurt
     */
    public void setIsInflicted(boolean isInflicted) { getInflicted = isInflicted; }

    /**
     * Get isInflicted status
     * @return True if entity can be hurt
     */
    public boolean getInflicted() { return getInflicted; }

    /**
     * Get health score
     * @return health score
     */
    public double getHealthScore() { return healthScore; }
}
