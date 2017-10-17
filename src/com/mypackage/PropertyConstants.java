package com.mypackage;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class PropertyConstants {

    final static Logger LOGGER = Logger.getLogger(PropertyConstants.class.getName());

    public static Integer countId = 0;

    Properties properties = new Properties();

    public static final String CONFIG_FILE = "C:\\Users\\Arpit\\IdeaProjects\\mywebserver\\resource\\config.properties";

    public void setProperties() {

        properties.setProperty("LOG_PATH", "C:\\Users\\Arpit\\IdeaProjects\\mywebserver\\resource\\log4j.properties");
        properties.setProperty("WEB_PAGE", "C:\\Users\\Arpit\\IdeaProjects\\mywebserver\\src\\com\\mypackage\\");
        properties.setProperty("DEFAULT_PORT", "1111");
        properties.setProperty("URL", "jdbc:mysql://localhost:3306/webserver");
        properties.setProperty("USER", "arpit");
        properties.setProperty("PASSWORD", "arpit");

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("C:\\Users\\Arpit\\IdeaProjects\\mywebserver\\resource\\config.properties");
            properties.store(outputStream, "asd");
        } catch (FileNotFoundException e) {
            LOGGER.debug(e);
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.debug(e);
            e.printStackTrace();
        }
    }

    public void loadProperty(Properties properties) {
        try {
            InputStream input = new FileInputStream(PropertyConstants.CONFIG_FILE);
            properties.load(input);
        } catch (IOException e) {
            LOGGER.debug(e);
            e.printStackTrace();
        }
    }
}
