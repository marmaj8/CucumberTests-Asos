package asos;

import asos.utils.LogUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertyManager {
    private static final String DATA_FOLDER = "data/";

    private static final Logger logger = LogManager.getLogger(PropertyManager.class.getSimpleName());
    private static final Properties data = new Properties();

    public PropertyManager(String dataFile) {
        logger.info("Trying to retrieve property file: " + DATA_FOLDER + dataFile);
        String path = DATA_FOLDER + dataFile;
        try {
            InputStream stream = PropertyManager.class
                    .getClassLoader()
                    .getResourceAsStream(path);
            data.load(stream);
        } catch (IOException e) {
            logger.info("File '" + path + "' cannot be reed");
            LogUtil.logStackTrace(e, logger);

            List<Double> d = new ArrayList<>();
            for (Double x:d
                 ) {

            }
        }
    }

    public String getProperty(String key) {
        String property = data.getProperty(key);
        if (property == null) {
            logger.info("Property '" + key + "' cannot be read");
        }
        return data.getProperty(key);
    }

    public int getIntProperty(String key) {
        String textProperty = getProperty(key);

        try {
            return Integer.parseInt(textProperty);
        } catch (Exception ex) {
            logger.info("Property '" + key + "' with value '" + textProperty + "' cannot be parsed to int");
            throw ex;
        }
    }
}
