import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Class that contains methods to read a CSV file and a properties file.
 * You may edit this as you wish.
 */
public class IOUtils {
    private static final String COMMA = ",";

    /***
     * Method that reads a CSV file and return a 2D String array
     * @param csvFile: the path to the CSV file
     * @return String ArrayList
     */
    public static List<String[]> readCsv(String csvFile) {
        List<String[]> allEntities = new ArrayList<>();
        try (BufferedReader csv =  new BufferedReader(new FileReader(csvFile))) {
            String input;
            while ((input = csv.readLine()) != null) {
                allEntities.add(input.split(COMMA));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        return allEntities;
    }

    /***
     * Method that reads a properties file and return a Properties object
     * @param configFile: the path to the properties file
     * @return Properties object
     */
    public static Properties readPropertiesFile(String configFile) {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(configFile));
        } catch(IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        return appProps;
    }
}
