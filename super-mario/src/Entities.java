import bagel.Image;
import bagel.util.Point;

/**
 * Superclass to all game objects.
 */
public class Entities {

    private boolean visited = false;
    private int speed;
    private double radius;
    private Image entityImage;
    private Point location;
    private Point originalLocation = null;

    /**
     * Assign radius, speed and image of a particular entity
     * @param entities A string that represents the entity
     */
    public Entities(String entities) {
        // For all other entities except platform
        if (!entities.equals("platform") && !entities.equals("flyingPlatform")) {
            radius = Double.parseDouble(GameSetting.getProps().getProperty("gameObjects." + entities + ".radius"));
        }
        // For all other entities except player
        if (!entities.equals("player")) {
            speed = Integer.parseInt(GameSetting.getProps().getProperty("gameObjects." + entities + ".speed"));
            entityImage = new Image(GameSetting.getProps().getProperty("gameObjects." + entities + ".image"));
        }
    }

    /**
     * Update location using input x and y
     * @param x x coordinate of the entity
     * @param y y coordinate of the entity
     */
    public void goToLocation(double x, double y) {
        location = new Point(x, y);
        if (originalLocation == null) { originalLocation = new Point(x, y); }
    }

    /**
     * Check for collision with the entity and update either score or health accordingly
     * entity: one of these entities: coin, double score, invincible power, fireball, enemy
     * score: current score of player (null for enemy boss)
     * health: current health of player or enemy boss
     * doubleScore: global DoubleScore to keep of its activity
     * invinciblePower: global InvinciblePower to keep of its activity
     */
    protected void collideWith(Entities entity, Score score, Health health, InvinciblePower invinciblePower, DoubleScore doubleScore) {
        if (location.distanceTo(new Point(entity.getX(), entity.getY()))
                <= (radius + entity.getRadius()) && !entity.isVisited()) {

            // perform corresponding actions of each entity
            if (entity instanceof Coin) {
                score.updateValue(Coin.COIN_VALUE);
            } else if (entity instanceof DoubleScore) {
                Score.setMultiplier(DoubleScore.DOUBLE);
                doubleScore.setMaxFrame(true, null);
            } else if (entity instanceof Enemy && health.getInflicted()) {
                health.updateValue(Enemy.DAMAGE_SIZE);
            } else if (entity instanceof InvinciblePower) {
                health.setIsInflicted(false);
                invinciblePower.setMaxFrame(false, health);
            } else if (entity instanceof Fireball)  {
                health.updateValue(Fireball.DAMAGE_SIZE);
            }

            // set visited to true if collided
            if (!(entity instanceof Enemy) || health.getInflicted()) { entity.setVisited(); }
        }
    }

    /**
     * Get speed
     */
    protected int getSpeed() { return speed; }

    /**
     * Get radius
     */
    protected double getRadius() { return radius; }

    /**
     * Get X coordinate
     */
    protected double getX() { return location.x; }

    /**
     * Get Y coordinate
     */
    protected double getY() { return location.y; }

    /**
     * Get original X, Y coordinates
     */
    protected Point getOriginalLocation() { return originalLocation; }

    /**
     * Draw entity image
     */
    protected void myDrawImage() { entityImage.draw(location.x, location.y); }

    /**
     * Set visited status to true
     */
    protected void setVisited() { visited = true; }

    /**
     * Return True is the entity has been visited by Player. Otherwise, false
     */
    protected boolean isVisited() { return visited; }
}