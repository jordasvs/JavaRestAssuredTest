package com.restassuredapitemplate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GlobalParameters {
    public static String ENVIROMENT;
    public static String URL_DEFAULT;
    public static String REPORT_NAME;
    public static String REPORT_PATH;
    public static String DB_URL;
    public static String DB_NAME;
    public static String DB_USER;
    public static String DB_PASSWORD;
    public static String URL_TOKEN;
    public static String TOKEN;
    public static String AUTHENTICATOR_USER;
    public static String AUTHENTICATOR_PASSWORD;

    private Properties properties;

    public GlobalParameters() {
        properties = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("globalParameters.properties");
            properties.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        REPORT_NAME = properties.getProperty("report.name");
        REPORT_PATH = properties.getProperty("report.path");
        ENVIROMENT = properties.getProperty("enviroment");

        //Enviroment variables
        DB_URL = properties.getProperty(ENVIROMENT + ".db.url");
        DB_NAME = properties.getProperty(ENVIROMENT + ".db.name");
        DB_USER = properties.getProperty(ENVIROMENT + ".db.user");
        DB_PASSWORD = properties.getProperty(ENVIROMENT + ".db.password");
        URL_DEFAULT = properties.getProperty(ENVIROMENT + ".url.default");
        URL_TOKEN = properties.getProperty(ENVIROMENT + ".url.token");
        TOKEN = properties.getProperty(ENVIROMENT + ".token");
        AUTHENTICATOR_USER = properties.getProperty(ENVIROMENT + ".authenticator.user");
        AUTHENTICATOR_PASSWORD = properties.getProperty(ENVIROMENT + ".authenticator.password");
    }

    public static void setToken(String token) {
        TOKEN = token;
    }
}
