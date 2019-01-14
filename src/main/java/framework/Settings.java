package framework;

import org.json.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static framework.Log.logger;


public class Settings {
    private Properties properties;
    private static final String PROPERTIES_FILE = "api.properties";

    public Settings() {
        properties = loadPropertiesFile();
    }

    public String getProperty(String name) {
        properties = loadPropertiesFile();

        String result = System.getProperty(name, null);
        if ((result) != null && result.length() > 0) {
            return result;
        }
        result = properties.getProperty(name);
        if ((result) != null && result.length() > 0) {
            return result;
        }
        return result;
    }

    public JSONArray getExpectedTestData(String dataName) {
        String fileOutput = null;
        try {
            fileOutput = new String(Files.readAllBytes(Paths.get("src/main/java/rest/testData/" + dataName + ".json")), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray(fileOutput);

        return jsonArray;
    }

    public String getRestServerUrl() {
        return getProperty("rest.server");
    }

    private Properties loadPropertiesFile() {
        try {
            String filename = PROPERTIES_FILE;
            InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);

            if (stream == null) {
                stream = new FileInputStream(new File(filename));
            }
            Properties result = new Properties();
            result.load(stream);
            return result;
        } catch (IOException e) {
            logger.info("Properties file was not found. Check path.");
            return null;
        }
    }
}
