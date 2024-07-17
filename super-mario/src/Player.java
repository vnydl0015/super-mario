import bagel.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Player can shoot fireball just like EnemyBoss.
 */
public class Player extends Entities implements Attackable<EnemyBoss> {

    private int verticalSpeed = 0;
    private static final int FIRST_JUMP_SPEED = -20;
    private final Image PLAYER_RIGHT;
    private final Image PLAYER_LEFT;
    private static boolean isOnPlatform = false;
    private final List<Fireball> PLAYER_FIREBALLS = new ArrayList<>();

    /**
     * CConstructor of Player
     * @param entity String that represents Player
     */
    public Player(String entity) {
        super(entity);
        PLAYER_RIGHT = new Image(GameSetting.getProps().getProperty("gameObjects.player.imageRight"));
        PLAYER_LEFT = new Image(GameSetting.getProps().getProperty("gameObjects.player.imageLeft"));
    }

    /**
     * Draw player based on the direction (LEFT or RIGHT key is pressed)
     */
    public void myDrawPlayer(GameSetting gameSetting) {
        if (gameSetting.getIsRight()) {
            PLAYER_RIGHT.draw(super.getX(), super.getY());
        } else {
            PLAYER_LEFT.draw(super.getX(), super.getY());
        }
    }

    /**
     * Increment vertical speed and change player's Y accordingly. This method is adapted from project 1 solution
     */
    public void jumping(Input input) {
        // set vertical speed to -10 when UP is pressed
        if (input.wasPressed(Keys.UP) && (super.getY() == super.getOriginalLocation().y || isOnPlatform)) {
            verticalSpeed = FIRST_JUMP_SPEED;
        }
        // land back on ground
        if (verticalSpeed > 0 && super.getY() >= super.getOriginalLocation().y) {
            verticalSpeed = 0;
            super.goToLocation(super.getX(), super.getOriginalLocation().y);
        }
        // is jumping...
        isOnPlatform = false;
        super.goToLocation(super.getX(), super.getY() + (verticalSpeed++));
    }

    /**
     * If all 3 conditions are satisfied, set player's vertical speed to 0
     * @param platform to get platform's location
     */
    public void landOnFlyingPlatform(FlyingPlatform platform) {
        if (platform.satisfyFlyingPlatformCondition(this)) {
            verticalSpeed = 0;
            isOnPlatform = true;
        }
    }

    /**
     * Create new fireball and place them in the list
     */
    @Override
    public void shootFireballs(EnemyBoss enemyBoss) {
        Fireball.createFireballs(PLAYER_FIREBALLS, this, (enemyBoss.getX() > super.getX()));
    }

    /**
     * Get player's list of fireball
     * @return list of fireball
     */
    public List<Fireball> getPlayerFireBalls() { return PLAYER_FIREBALLS; }
}