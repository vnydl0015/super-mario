import bagel.Input;
import bagel.Keys;

import java.util.List;
import java.util.Properties;

/**
 * Control the game functionalities e.g. getting input level
 */
public class GameSetting {

    /**
     * Represents levels 1 to 3
     */
    public enum Level {
        ONE,
        TWO,
        THREE
    }

    /**
     * Integer that represents left direction
     */
    public static final int LEFT = 1;
    /**
     * Integer that represents right direction
     */
    public static final int RIGHT = -1;

    private boolean isRight = true;
    private boolean startGame = false;
    private boolean gameEnd = false;
    private boolean canMove = true;
    private Level level = null;
    private String levelFile = null;

    /**
     * Get game level based on input
     * @param input keyboard's key
     * @return allEntities a list of string[] that contains the entity, its X and Y
     */
    public List<String[]> getInputLevel(Input input) {
        // Get the input key
        if (input.wasPressed(Keys.NUM_1)) {
            levelFile = "level1File";
            level = Level.ONE;
        } else if (input.wasPressed(Keys.NUM_2)) {
            levelFile = "level2File";
            level = Level.TWO;
        } else if (input.wasPressed(Keys.NUM_3)) {
            levelFile = "level3File";
            level = Level.THREE;
        }

        // get the csv corresponding to the input
        List<String[]> allEntities = null;
        if (levelFile != null) {
            allEntities = IOUtils.readCsv(getProps().getProperty(levelFile));
        }
        return allEntities;
    }

    /**
     * Get properties file
     * @return Properties files
     */
    public static Properties getProps() { return IOUtils.readPropertiesFile("res/app.properties"); }

    /**
     * Get message file
     * @return message files
     */
    public static Properties getMessages() { return IOUtils.readPropertiesFile("res/message_en.properties");}

    /**
     * Get current level
     * @return Level
     */
    public Level getLevel() { return level; }

    /**
     * Get level file
     * @return level file
     */
    public String getLevelFile() { return levelFile; }

    /**
     * Get direction of player's movement
     * @return True if player is moving right
     */
    public boolean getIsRight() { return isRight; }

    /**
     * Get game end status
     * @return True if game ends
     */
    public boolean getGameEnd() { return gameEnd; }

    /**
     * Get start game status
     * @return True if game starts
     */
    public boolean getStartGame() { return startGame; }

    /**
     * Get can move status
     * @return True if all entities can move
     */
    public boolean getCanMove() { return canMove; }

    /**
     * Set isRight status based on player's movement
     */
    public void setIsRight(boolean condition) { isRight = condition; }

    /**
     * Set start game status
     */
    public void setStartGame(boolean condition) { startGame = condition; }

    /**
     * Set game end status
     */
    public void setGameEnd(boolean condition) {
        gameEnd = condition;
        // reset to null at the start of the game
        if (!gameEnd && !startGame) {
            level = null;
            levelFile = null;
        }
    }

    /**
     * Set can move status
     */
    public void setCanMove(boolean condition) { canMove = condition; }
}