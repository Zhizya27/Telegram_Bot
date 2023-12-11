package org.example;


import java.io.IOException;
import java.util.Properties;


/**
 * Класс для чтения из файла config.properties
 */
public class Config {
    private String botName;
    private String botToken;
    private String API_URL;
    private String API_KEY;


    /**
     * Конструктор класса Config
     */
    public Config() {
        load();
    }


    /**
     * Метод для загрузки данных из конфигурационного файла
     */
    public void load() {
        Properties properties = new Properties();

        try {
            properties.load(App.class.getClassLoader().getResourceAsStream(""));
        } catch (IOException e) {
            e.printStackTrace();
        }

        botName = properties.getProperty("botName", " ");
        botToken = properties.getProperty("botToken", " ");
        API_KEY = properties.getProperty("API_KEY", " ");
        API_URL = properties.getProperty("API_URL", " ");
    }


    /**
     * Метод, который возвращает имя бота
     * @return botName
     */
    public String getBotUserName() {
        return botName;
    }


    /**
     * Метод, который возвращает токен бота
     * @return bottoken
     */
    public String getBotToken() {
        return botToken;
    }


    /**
     * Возвращает ключ API
     * @return API_KEY
     */
    public String getAPI_KEY() {
        return API_KEY;
    }

    /**
     * Возвращает URL API
     * @return URL_API
     */
    public String getAPI_URL() {
        return API_URL;
    }
}