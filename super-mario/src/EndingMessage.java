/**
 * EndingMessage extends from Message. It has 2 strings unlike Instruction and Title.
 */
public class EndingMessage extends Message {

    /**
     * The constructor of EndingMessage.
     * @param entity the string that represents EndingMessage entity
     * @param health player's health to initialise the appropriate message (either winning or losing).
     */
    public EndingMessage(String entity, double health) {
        super(entity);
        if (health <= 0) {
            super.setMessage(GameSetting.getMessages().getProperty("gameOver"));
        } else {
            super.setMessage(GameSetting.getMessages().getProperty("gameWon"));
        }
    }
}

