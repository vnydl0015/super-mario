/**
 * DoubleScore extends from Powers as it can double the score when activated. It is a class of its own to avoid
 * getting mixed up with properties of InvinciblePower.
 */
public class DoubleScore extends Powers {
    /**
     * Multiplier of 2
     */
    public static final int DOUBLE = 2;
    /**
     * Multiplier of 1
     */
    public static final int SINGLE = 1;

    /**
     * The constructor of DoubleScore
     * @param entity the string that represents DoubleScore entity
     * @param x X coordinate of DoubleScore object being referred to
     * @param y Y coordinate of DoubleScore object being referred to
     */
    public DoubleScore(String entity, double x, double y) { super(entity, x, y); }
}
