package com.mypackage;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;


public class DBManager {

    static final Logger LOGGER = Logger.getLogger(DBManager.class.getName());

    public void clientRecord(String inetAddress, int portNumber) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Properties properties = new Properties();
        PropertyConstants propertyConstants = new PropertyConstants();
        propertyConstants.loadProperty(properties);
        try {
            LOGGER.info("Connecting to database...");
            conn = DriverManager.getConnection(properties.getProperty("URL"), properties.getProperty("USER"), properties.getProperty("PASSWORD"));

            LOGGER.info("Creating statement...");
            String sqlQuery = "INSERT INTO clientinfo VALUES(?,?)";
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, inetAddress);
            preparedStatement.setInt(2, portNumber);

            LOGGER.info("Query : " + sqlQuery);

            Integer updateNoOfReCords = preparedStatement.executeUpdate();
            LOGGER.info(updateNoOfReCords + " record inserted ");
        } catch (SQLException se) {
            LOGGER.debug(se);
            se.printStackTrace();
        } catch (Exception e) {
            LOGGER.debug(e);
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                conn.close();
            } catch (NullPointerException npe) {
                LOGGER.debug(npe);
                npe.printStackTrace();
            } catch (SQLException e) {
                LOGGER.debug(e);
                e.printStackTrace();
            }
        }
    }
}