package com.mypackage;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * HTTPHandle class handling request and response from client browser
 */
public class HTTPHandle extends Thread {
    public Socket connection;
    public Integer count;
    final static Logger LOGGER = Logger.getLogger(HTTPHandle.class.getName());

    public HTTPHandle(Socket requestConn, int count) throws Exception {
        this.connection = requestConn;
        this.count = count;
    }

    /**
     * processing request and response
     */
    public void run() {
        DBManager jdbcManager = new DBManager();
        Properties properties = new Properties();
        PropertyConstants propertyConstants = new PropertyConstants();
        propertyConstants.loadProperty(properties);
        try {
            System.out.println("request count ID:  " + PropertyConstants.countId);
            jdbcManager.clientRecord(connection.getInetAddress().toString(), connection.getLocalPort());

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//          PropertyConfigurator.configure(properties.getProperty("LOG_PATH"));
            String string = "";
            String str = br.readLine();
            do {
                string = string + str + "\n";
                str = br.readLine();
            } while (!("".equals(str)));
            System.out.println("string:  " + string);
            StringTokenizer stringTokenizer = new StringTokenizer(string);
            String header = stringTokenizer.nextToken();
            if (header.equals("GET")) {
                String fileName = stringTokenizer.nextToken();
                HtmlFileReader fileReader = new HtmlFileReader();
                String htmlFile = properties.getProperty("WEB_PAGE") + fileName;
                String htmlContent = null;
                htmlContent = fileReader.readFile(htmlFile);
                if (htmlContent.equals("")) {
                    LOGGER.fatal("html file not found");
                    htmlContent = "<html>" +
                            "<head><title>404 Not Found</title></head>" +
                            "<body bgcolor=\"#4da6ff\">" +
                            "<h1>OOPS!!!!<br>404 Not Found</h1>" +
                            "</body></html>";
                }
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + htmlContent;
                connection.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                LOGGER.info("Connection closed!!!");
            }
        } catch (Exception e) {
            LOGGER.debug(e);
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (IOException io) {
                LOGGER.debug(io);
                io.printStackTrace();
            }
        }
    }
}
