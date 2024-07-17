import bagel.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2024
 * Vannyda Long
 */
public class ShadowMario extends AbstractGame {

    private final Image BACKGROUND_IMAGE;
    private final Message GAME_TITLE;
    private final Message INSTRUCTION;
    // player's health is created outside Player object to ease with updating any collisions
    private Health playerHealth;
   // enemy boss' health is created outside EnemyBoss object to ease with updating any fireball collisions
    private Health enemyHealth;
    private Score score;
    private Player player;
    private EnemyBoss enemyBoss;
    private Entities platform;
    private Entities endFlag;
    private InvinciblePower invinciblePower;
    private DoubleScore doubleScore;
    private List<String[]> originalCsv;
    private List<Entities> allEntities;
    private final GameSetting gameSetting;

    /**
     * The constructor
     */
    public ShadowMario() {
        super(Integer.parseInt(GameSetting.getProps().getProperty("windowWidth")),
                Integer.parseInt(GameSetting.getProps().getProperty("windowHeight")),
                GameSetting.getMessages().getProperty("title"));

        // get constant or final properties of game
        BACKGROUND_IMAGE = new Image(GameSetting.getProps().getProperty("backgroundImage"));
        GAME_TITLE = new Message("title");
        INSTRUCTION = new Message("instruction");
        gameSetting = new GameSetting();
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowMario game = new ShadowMario();
        game.run();
    }

    /**
     * Performs a state update of the selected level.
     * Allows the game to exit when the escape key is pressed.
     * Handle screen navigation between levels and instruction pages here.
     */
    @Override
    protected void update(Input input) {
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        // close window
        if (input.wasPressed(Keys.ESCAPE)) { Window.close(); }

        // before game starts, read input level's csv
        if (!gameSetting.getGameEnd() && gameSetting.getLevelFile() == null) {
            GAME_TITLE.myDrawString();
            INSTRUCTION.myDrawString();
            originalCsv = gameSetting.getInputLevel(input);
            // after game ends, reset all game setting status
        } else if (gameSetting.getGameEnd() && input.wasPressed(Keys.SPACE)) {
            gameSetting.setStartGame(false);
            gameSetting.setGameEnd(false);
        }

        // right after reading csv, set up opening screen and set all values to new
        if (!gameSetting.getStartGame() && !gameSetting.getGameEnd() && gameSetting.getLevelFile() != null) {
            resetAllEntitiesData();
            allEntities = populateGameObjects(originalCsv);
            gameSetting.setCanMove(true);
            gameSetting.setStartGame(true);
        }

        // after game ends, print ending message according to health status (either win or lose)
        if (gameSetting.getGameEnd()) {
            (new EndingMessage("message", playerHealth.getHealthScore())).myDrawString();
        }

        // during game play...
        if (gameSetting.getStartGame()) {
            if (gameSetting.getCanMove()) {
                performMovementActivities(input);
                checkAllCollisions();
                if (gameSetting.getLevel() != GameSetting.Level.ONE) { performPowerActivities(); }
                if (gameSetting.getLevel() == GameSetting.Level.THREE) { performFireballActivities(input); }
            }
            checkIfGameEnds();
            drawAllEntities();
        }
    }

    /**
     * Perform right and left, jumping movements based on key pressed.
     */
    private void performMovementActivities(Input input) {
        // move all entities except player to the left when RIGHT is pressed
        if (input.isDown(Keys.RIGHT)) {
            updateAllEntitiesLocation(GameSetting.RIGHT);
            gameSetting.setIsRight(true);
        }
        // move all entities except player to the right when LEFT is pressed
        if (input.isDown(Keys.LEFT)) {
            updateAllEntitiesLocation(GameSetting.LEFT);
            gameSetting.setIsRight(false);
        }
        player.jumping(input);
    }

    /**
     * Check for collision between player and all other entities.
     */
    private void checkAllCollisions() {
        for (Entities obj: allEntities) {
            // check for collision with all entities
            player.collideWith(obj, score, playerHealth, invinciblePower, doubleScore);
            // float certain collided entities
            if (obj instanceof Coin || obj instanceof DoubleScore || obj instanceof InvinciblePower) {
                ((FloatingObject) obj).floatUp();
            }
            // player lands on flying platforms
            if (obj instanceof FlyingPlatform) {
                player.landOnFlyingPlatform((FlyingPlatform) obj);
            }
            // perform random horizontal movement
            if (obj instanceof RandomMoveObject) {
                ((RandomMoveObject) obj).randomMove();
            }
        }
    }

    /**
     * Reduce current frame of double score and invincible power if activated
     */
    private void performPowerActivities() {
        doubleScore.reduceCurrFrame(true, null);
        invinciblePower.reduceCurrFrame(false, playerHealth);
    }

    /**
     * Shoot fireballs by player and enemy boss and check for collisions
     */
    private void performFireballActivities(Input input) {
        // shoot fireballs if only they are within each other's range
        if (enemyBoss.isWithinRange(player.getX(), player.getY())) {
            if (input.wasPressed(Keys.S)) { player.shootFireballs(enemyBoss); }
            if (enemyHealth.getHealthScore() > 0) { enemyBoss.shootFireballs(player); }
        }
        // check if fireballs hit either player or enemy boss
        Fireball.checkFireBallCollision(enemyBoss, enemyHealth, player.getPlayerFireBalls());
        Fireball.checkFireBallCollision(player, playerHealth, enemyBoss.getEnemyFireBalls());
    }

    /**
     * Reset all entities to initial values
     */
    private void resetAllEntitiesData() {
        playerHealth = new Health("player");
        playerHealth.setIsInflicted(true);
        score = new Score("score");
        Score.setMultiplier(DoubleScore.SINGLE);
        player = new Player("player");
        platform = new Entities("platform");
        endFlag = new Entities("endFlag");
        invinciblePower = new InvinciblePower("invinciblePower", 0, 0);
        doubleScore = new DoubleScore("doubleScore", 0, 0);
        // extra condition for level three
        if (gameSetting.getLevel() == GameSetting.Level.THREE) {
            enemyBoss = new EnemyBoss("enemyBoss");
            enemyHealth = new Health("enemyBoss");
        }
    }

    /**
     * Update location of all the entities in either right or left direction.
     * direction: integer for right or left
     */
    private void updateAllEntitiesLocation(int direction) {
        // handle left cliff
        if (direction == GameSetting.LEFT && (platform.getX() < platform.getOriginalLocation().x) ||
                direction == GameSetting.RIGHT) {
            platform.goToLocation(platform.getX() + platform.getSpeed() * direction, platform.getY());
        }
        // increment or decrement X coordinate of end flag
        endFlag.goToLocation(endFlag.getX() + endFlag.getSpeed() * direction, endFlag.getY());
        // increment or decrement X coordinate of enemy boss
        if (gameSetting.getLevel() == GameSetting.Level.THREE) {
            enemyBoss.goToLocation(enemyBoss.getX() + enemyBoss.getSpeed() * direction, enemyBoss.getY());
        }
        // increment or decrement X coordinate of all other entities
        for (Entities obj : allEntities) { obj.goToLocation(obj.getX() + obj.getSpeed() * direction, obj.getY()); }
    }

    /**
     * Draw all entities
     */
    private void drawAllEntities() {
        for (Entities obj: allEntities) { obj.myDrawImage(); }
        platform.myDrawImage();
        endFlag.myDrawImage();
        score.myDrawString();
        playerHealth.myDrawString();
        player.myDrawPlayer(gameSetting);
        // extra condition for level three
        if (gameSetting.getLevel() == GameSetting.Level.THREE) {
            enemyBoss.myDrawImage();
            enemyHealth.myDrawString();
        }
    }

    /**
     * Check if the player (or enemy boss) is dead or reaches the end flag
     */
    private void checkIfGameEnds() {
        // if game's winning or losing conditions are satisfied, switch off the game
        if (playerHealth.isDying(player, gameSetting)|| gameSetting.getLevel() != GameSetting.Level.THREE ||
                enemyHealth.isDying(enemyBoss, gameSetting)) {
            // for level three, the enemy boss must be dead first before end flag can be marked visited
            player.collideWith(endFlag, score, playerHealth, null, null);
            if (endFlag.isVisited() || playerHealth.getHealthScore() <= 0) {
                gameSetting.setGameEnd(true);
                gameSetting.setStartGame(false);
            }
        }
    }

    /**
     *  Extract all data (entity type, x, y coordinates) from csv into an Entities array
     */
    private List<Entities> populateGameObjects(List<String[]> csv) {
        List<Entities> allEntities = new ArrayList<>();
        for (String[] row: csv) {
            // Get the 3 data of each row in csv
            double x = Double.parseDouble(row[1]);
            double y = Double.parseDouble(row[2]);
            String entity = row[0];

            switch (entity) {
                case "COIN":
                    allEntities.add(new Coin("coin", x, y));
                    break;
                case "ENEMY":
                    allEntities.add(new Enemy("enemy", x, y));
                    break;
                case "PLATFORM":
                    platform.goToLocation(x, y);
                    break;
                case "END_FLAG":
                    endFlag.goToLocation(x, y);
                    break;
                case "PLAYER":
                    player.goToLocation(x, y);
                    break;
                case "FLYING_PLATFORM":
                    allEntities.add(new FlyingPlatform("flyingPlatform", x, y));
                    break;
                case "INVINCIBLE_POWER":
                    allEntities.add(new InvinciblePower("invinciblePower", x, y));
                    break;
                case "DOUBLE_SCORE":
                    allEntities.add(new DoubleScore("doubleScore", x, y));
                    break;
                case "ENEMY_BOSS":
                    enemyBoss.goToLocation(x, y);
                    break;
            }
        }
        return allEntities;
    }
}