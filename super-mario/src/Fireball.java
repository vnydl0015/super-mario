import bagel.Window;

import java.util.List;

/**
 * Distinct properties of Fireball
 */
public class Fireball extends Entities {

    /**
     * Damage size of each fireball
     */
    public static final double DAMAGE_SIZE = Double.parseDouble(GameSetting.getProps()
            .getProperty("gameObjects.fireball.damageSize"));
    private int shootingDirection = 0;

    /**
     * The constructor of Fireball
     * @param entity the string that represents Fireball entity
     * @param x X coordinate of Fireball object being referred to
     * @param y Y coordinate of Fireball object being referred to
     */
    public Fireball(String entity, double x, double y) {
        super(entity);
        super.goToLocation(x, y);
    }

    /**
     * Create new fireball and place it in the input list
     * @param allFireballs the list of to store fireball
     * @param entity to get location of the shooter
     * @param direction set the direction of the fireball
     */
    public static void createFireballs(List<Fireball> allFireballs, Entities entity, boolean direction) {
        Fireball newFireBall = new Fireball("fireball", entity.getX(), entity.getY());
        newFireBall.setShootingDirection(direction);
        allFireballs.add(newFireBall);
    }

    /**
     * Change the location and draw all fireballs, check if they collide with the entity
     * @param entity One of these entities: player or enemy boss
     * @param health current health of either player or enemy boss
     * @param fireballList fireball list of either player or enemy boss
     */
    public static void checkFireBallCollision(Entities entity, Health health, List<Fireball> fireballList) {
        for (Fireball fireball: fireballList) {
            fireball.movingFireball();
            entity.collideWith(fireball, null, health, null, null);
            fireball.myDrawImage();
            fireball.disappear();
        }
    }

    /**
     * Remove collided fireball from the screen
     */
    private void disappear() {
        if (super.isVisited()) { super.goToLocation(Window.getWidth() + 1, Window.getHeight() + 1); }
    }

    /**
     * Change location of fireball horizontally
     */
    private void movingFireball() {
        super.goToLocation(super.getX() + super.getSpeed() * shootingDirection, super.getY());
    }

    /**
     * Set the fireball direction based on the locations of player and enemy boss
     * isRight: true if the player is on the right of enemy boss. Otherwise, false
     */
    private void setShootingDirection(boolean isRight) {
        shootingDirection = isRight ? GameSetting.LEFT : GameSetting.RIGHT;
    }
}
