import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * EnemyBoss implements Attackable<Player> as it can attack Player.
 */
public class EnemyBoss extends Entities implements Attackable<Player> {

    private final int ACTIVATION_RADIUS;
    private static final int MAX_SHOOTING_FRAME = 100;
    private static int currFrame = 0;
    private final List<Fireball> ENEMY_FIREBALLS = new ArrayList<>();

    /**
     * The constructor of EnemyBoss
     * @param entity the string that represents EnemyBoss entity
     */
    public EnemyBoss(String entity) {
        super(entity);
        ACTIVATION_RADIUS = Integer.parseInt(GameSetting.getProps().
                getProperty("gameObjects.enemyBoss.activationRadius"));
    }

    /**
     * Return true if player is within range of enemy boss. Otherwise, false
     * @param x x coordinate of player
     * @param y y coordinate of player
     * @return true or false
     */
    public boolean isWithinRange(double x, double y) {
        return (new Point(x, y).distanceTo(new Point(super.getX(), super.getY())) < ACTIVATION_RADIUS);
    }

    /**
     * Create new fireballs based on current frame and randomness and place them into a list.
     * @param player To check where player is located in relation to enemy boss
     */
    @Override
    public void shootFireballs(Player player) {
        if ((new Random().nextBoolean()) && currFrame == 0) {
            currFrame = MAX_SHOOTING_FRAME;
            Fireball.createFireballs(ENEMY_FIREBALLS, this, (player.getX() > super.getX()));
        }
        if (currFrame > 0) { currFrame--; }
    }

    /**
     * Get the list of fireballs of the enemy boss
     * @return List of Fireball
     */
    public List<Fireball> getEnemyFireBalls() { return ENEMY_FIREBALLS; }
}
