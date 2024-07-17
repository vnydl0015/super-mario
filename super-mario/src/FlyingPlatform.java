/**
 * FlyingPlatform can move randomly sideways
 */
public class FlyingPlatform extends RandomMoveObject {

    private final int HALF_LENGTH;
    private final int HALF_HEIGHT;

    /**
     * The constructor of FlyingPlatform
     * @param entity the string that represents FlyingPlatform entity
     * @param x X coordinate of FlyingPlatform object being referred to
     * @param y Y coordinate of FlyingPlatform object being referred to
     */
    public FlyingPlatform(String entity, double x, double y) {
        super(entity, x, y);
        HALF_HEIGHT = Integer.parseInt(GameSetting.getProps().getProperty("gameObjects.flyingPlatform.halfHeight"));
        HALF_LENGTH = Integer.parseInt(GameSetting.getProps().getProperty("gameObjects.flyingPlatform.halfLength"));
    }

    /**
     * Return true if all 3 conditions of the flying platform are satisfied
     */
    public boolean satisfyFlyingPlatformCondition(Player player) {
        return (Math.abs(this.getX() - player.getX()) < HALF_LENGTH) &&
                (Math.abs(this.getY() - player.getY()) <= HALF_HEIGHT) &&
                ((this.getY() - player.getY()) >= HALF_HEIGHT - 1);
    }
}
