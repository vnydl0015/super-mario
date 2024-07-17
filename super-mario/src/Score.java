/**
 * To keep track of player's score.
 */
public class Score extends Message implements Updatable {

    private int score = 0;
    private static int multiplier = 1;

    /**
     * Constructor of Score
     * @param entity String that represents Score
     */
    public Score(String entity) { super(entity); }

    /**
     * Increment payer's current score by value
     * @param value coin value
     */
    public void updateValue(double value) { score += ((int) value * multiplier); }

    /**
     * Draw score
     */
    @Override
    public void myDrawString() { getWordProps().drawString(getMessageString() + score, getX(), getY()); }

    /**
     * Set the score's multiplier
     * @param step A multiplier, either 1 or 2
     */
    public static void setMultiplier(int step) { multiplier = step; }
}
