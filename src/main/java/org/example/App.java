package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class App {
    public static void main(String[] args) {
        String botName = "forJavaUrfubot";
        String botToken = "6627470072:AAFQjSFg5SrhPPGjnkQGxbYxqeVs5lRnJA8";
        TelegramBotsApi telegramBotsApi = null;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot(botName, botToken));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);


        }
    }
}
