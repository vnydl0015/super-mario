import bagel.Font;
import bagel.Window;
import bagel.util.Point;

/**
 * Superclass to all message type.
 */
public class Message {

    private static final String FONT = GameSetting.getProps().getProperty("font");
    private final Font WORD_PROPS;
    private String message;
    private final Point LOCATION;

    /**
     * The constructor of Message. Assign location and message to specific Message type.
     * @param entity String that represents entity
     */
    public Message(String entity) {
        // set location of the message type
        if (entity.equals("instruction")) {
            LOCATION = new Point(Window.getWidth() / 4.0, Double.parseDouble(GameSetting.getProps().
                    getProperty("instruction.y")));
        } else if (entity.equals("message")) {
            LOCATION = new Point(Window.getWidth() / 3.5, Double.parseDouble(GameSetting.getProps().
                    getProperty("message.y")));
        } else {
            LOCATION = new Point(Double.parseDouble(GameSetting.getProps().getProperty(entity + ".x")),
                    Double.parseDouble(GameSetting.getProps().getProperty(entity + ".y")));
        }
        // get drawing properties
        WORD_PROPS = new Font(FONT, Integer.parseInt(GameSetting.getProps().getProperty(entity + ".fontSize")));

        if (entity.contains("Health")) { entity = "health"; }
        // get message
        if (!entity.equals("message")) { message = GameSetting.getMessages().getProperty(entity); }
    }

    /**
     * Draw the message
     */
    protected void myDrawString() { WORD_PROPS.drawString(message, LOCATION.x, LOCATION.y); }

    /**
     * Get X coordinate of string
     */
    protected double getX() { return LOCATION.x; }

    /**
     * Get Y coordinate of string
     */
    protected double getY() { return LOCATION.y; }

    /**
     * Get message
     */
    protected String getMessageString() { return message; }

    /**
     * Get word properties
     */
    protected Font getWordProps() { return WORD_PROPS; }

    /**
     * Set message
     */
    protected void setMessage(String message) { this.message = message; }
}
