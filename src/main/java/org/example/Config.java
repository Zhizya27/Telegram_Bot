package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/*
 * Класс для чтения из файла config.properties
 */
public class Config {
    public static String botName;
    public static String botToken;

    /*
     * метод для загрузки данных из конфигурационного файла
     */
    public static void loading() {
        Properties properties = new Properties();
        try {
            properties.load(App.class.getClassLoader().getResourceAsStream("config.properties"));

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        botName = properties.getProperty("botName");
        botToken = properties.getProperty("botToken");
    }
}