/**
 * Method to update score or health value
 */
public interface Updatable {
    /**
     * Update either health or score of player.
     * @param value Either damage size or coin value
     */
    void updateValue(double value);
}
