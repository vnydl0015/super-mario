/**
 * Superclass to DoubleScore and InvinciblePower.
 */
public abstract class Powers extends FloatingObject {

    private static int maxFrame;
    private int currFrame = 0;

    /**
     * The constructor of Powers
     * @param entity the string that represents Powers entity
     * @param x X coordinate of Powers object being referred to
     * @param y Y coordinate of Powers object being referred to
     */
    public Powers(String entity, double x, double y) {
        super(entity, x, y);
        maxFrame = Integer.parseInt(GameSetting.getProps().getProperty("gameObjects." + entity + ".maxFrames"));
    }

    /**
     * Reduce the current frame of either double score or invincible power
     * isScore: True if it is double score. False if it is invincible power
     * health: Player's health
     */
    protected void reduceCurrFrame(boolean isScore, Health health) {
        // reduce current frame
        if (currFrame > 0) {
            currFrame--;
        } else {
            // deactivate the powers
            if (isScore) {
                Score.setMultiplier(DoubleScore.SINGLE);
            } else {
                health.setIsInflicted(true);
            }
        }
    }

    /**
     * Set current frame of either double score or invincible power to max
     * isScore: True if it is double score. False if it is invincible power
     * health: Player's health
     */
    protected void setMaxFrame(boolean isScore, Health health) {
        currFrame = maxFrame;
        // activate the powers
        if (isScore) {
            Score.setMultiplier(DoubleScore.DOUBLE);
        } else {
            health.setIsInflicted(false);
        }
    }
}
