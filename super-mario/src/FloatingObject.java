/**
 * An abstract class of FloatingObject. Inheritors are Coin, InvinciblePower and DoubleScore
 */
public abstract class FloatingObject extends Entities {

    private static final int VERTICAL_SPEED = -10;

    /**
     * The constructor of FloatingObject
     * @param entity the string that represents FloatingObject entity
     * @param x X coordinate of FloatingObject object being referred to
     * @param y Y coordinate of FloatingObject object being referred to
     */
    public FloatingObject(String entity, double x, double y) {
        super(entity);
        super.goToLocation(x, y);
    }

    /**
     * Float the entity
     */
    protected void floatUp() {
        if (super.isVisited()) { this.goToLocation(super.getX(), super.getY() + VERTICAL_SPEED); }
    }
}
