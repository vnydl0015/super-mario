/**
 * Implemented by Player and EnemyBoss. These entities can shoot fireballs but their actions are slightly different.
 * @param <OpponentT> The opponent e.g. Player would have EnemyBoss as their <OpponentT>.
 */
public interface Attackable<OpponentT> {
    /**
     * Create new fireballs based on current frame and randomness and place them into a list.
     */
    void shootFireballs(OpponentT entity);
}
