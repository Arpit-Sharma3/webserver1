package com.mypackage;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

/**
 * Web server initialise which initialize a single deamon thread to open a webserver instance
 */
public class WebServerInitializer {
    public final static Logger LOG = Logger.getLogger(WebServerInitializer.class.getName());

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOG.debug(e);
            e.printStackTrace();
        }
        PropertyConstants propertyConstants = new PropertyConstants();
        Properties properties = new Properties();
        propertyConstants.loadProperty(properties);
        PropertyConfigurator.configure(properties.getProperty("LOG_PATH"));
        LOG.info("Web Server Initializing...");

        WebServer ws = new WebServer(properties.getProperty("DEFAULT_PORT"));
        ws.server();

    }
}
