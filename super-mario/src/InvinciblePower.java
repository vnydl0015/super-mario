/**
 * InvinciblePower extends from Powers as it can shield player when activated. It is a class of its own to avoid
 * getting mixed up with properties of DoubleScore.
 */
public class InvinciblePower extends Powers {

    /**
     * The constructor of InvinciblePower
     * @param entity the string that represents InvinciblePower entity
     * @param x X coordinate of InvinciblePower object being referred to
     * @param y Y coordinate of InvinciblePower object being referred to
     */
    public InvinciblePower(String entity, double x, double y) { super(entity, x, y); }
}
