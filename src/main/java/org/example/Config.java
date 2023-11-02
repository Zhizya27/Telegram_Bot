package org.example;

import java.io.IOException;
import java.util.Properties;

/**
 * Класс для чтения из файла config.properties
 */
public class Config {
    private String botName;
    private String botToken;

    /**
     * конструктор класса Config
     */
    public Config() {
        load();
    }

    /**
     * метод для загрузки данных из конфигурационного файла
     */
    public void load() {
        Properties properties = new Properties();
        try {
            properties.load(App.class.getClassLoader().getResourceAsStream("config.properties"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        botName = properties.getProperty("botName", " ");
        botToken = properties.getProperty("botToken", " ");
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
    public String getBotToken(){
        return botToken;
    }
}