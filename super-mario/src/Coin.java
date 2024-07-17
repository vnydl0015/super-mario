/**
 * Coin extends from FloatingObject since it can float up upon collision.
 */
public class Coin extends FloatingObject {
    /**
     * Value of each coin
     */
    public static final int COIN_VALUE = Integer.parseInt(GameSetting.getProps().
            getProperty("gameObjects.coin.value"));

    /**
     * The constructor of Coin
     * @param entity the string that represents Coin entity
     * @param x X coordinate of Coin object being referred to
     * @param y Y coordinate of Coin object being referred to
     */
    public Coin(String entity, double x, double y) { super(entity, x, y); }
}