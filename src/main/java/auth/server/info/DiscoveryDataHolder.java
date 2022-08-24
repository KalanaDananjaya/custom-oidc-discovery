package auth.server.info;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DiscoveryDataHolder {

    private static final Log log = LogFactory.getLog(DiscoveryDataHolder.class);
    private static final String DISCOVERY_FILE_PATH = "repository/conf/identity/openid-configuration.json";
    private static final String KEYSTORE_FILE_PATH = "repository/conf/identity/keystore.json";
    private static volatile DiscoveryDataHolder dataHolder;
    private JSONObject discoveryData;
    private JSONObject keystoreData;

    // Private constructor to prevent instantiation.
    private DiscoveryDataHolder() {

    }

    public static DiscoveryDataHolder getInstance() {

        if (dataHolder == null) {
            synchronized (DiscoveryDataHolder.class) {
                if (dataHolder == null) {
                    dataHolder = new DiscoveryDataHolder();
                    dataHolder.readDiscoveryJSONFile();
                    dataHolder.readKeystoreJSONFile();
                }
            }
        }
        return dataHolder;
    }

    private void readDiscoveryJSONFile() {

        if (Files.exists(Paths.get(DISCOVERY_FILE_PATH))) {

            JSONParser parser = new JSONParser();
            try {
                discoveryData = (JSONObject) parser.parse(new FileReader(DISCOVERY_FILE_PATH));
                log.info("Updated discovery data using file in the path : " + KEYSTORE_FILE_PATH);
            } catch (IOException e) {
                // handle exception.
                log.error("Error occurred while reading the file in the path : " + DISCOVERY_FILE_PATH, e);

            } catch (ParseException e) {
                // handle exception.
                log.error("Error occurred while parsing JSON in the file : " + DISCOVERY_FILE_PATH, e);
            }
        } else {
            // handle file not found case.
            if (log.isDebugEnabled()) {
                log.debug("Could not find config file in the path : " + DISCOVERY_FILE_PATH);
            }
        }
    }

    private void readKeystoreJSONFile() {

        if (Files.exists(Paths.get(KEYSTORE_FILE_PATH))) {

            JSONParser parser = new JSONParser();
            try {
                keystoreData = (JSONObject) parser.parse(new FileReader(KEYSTORE_FILE_PATH));
                log.info("Updated keystore data using file in the path : " + KEYSTORE_FILE_PATH);
            } catch (IOException e) {
                // handle exception.
                log.error("Error occurred while reading the file in the path : " + KEYSTORE_FILE_PATH, e);

            } catch (ParseException e) {
                // handle exception.
                log.error("Error occurred while parsing JSON in the file : " + KEYSTORE_FILE_PATH, e);
            }
        } else {
            // handle file not found case.
            if (log.isDebugEnabled()) {
                log.debug("Could not find config file in the path : " + KEYSTORE_FILE_PATH);
            }
        }
    }

    public JSONObject getDiscoveryData() {
        return discoveryData;
    }

    public JSONObject getKeystoreData() {
        return keystoreData;
    }
}
