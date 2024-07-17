/**
 * Enemy extends from RandomMoveObject as it can move randomly in horizontal motion.
 */
public class Enemy extends RandomMoveObject {

    /**
     * Damage size of each enemy
     */
    public static final double DAMAGE_SIZE = Double.parseDouble(GameSetting.getProps().
            getProperty("gameObjects.enemy.damageSize"));

    /**
     * The constructor of Enemy
     * @param entity the string that represents Enemy entity
     * @param x X coordinate of Enemy object being referred to
     * @param y Y coordinate of Enemy object being referred to
     */
    public Enemy(String entity, double x, double y) { super(entity, x, y); }
}