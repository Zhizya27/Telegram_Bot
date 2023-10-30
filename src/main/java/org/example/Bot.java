package org.example;

import org.quartz.JobExecutionContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import logic.Logic;

/**
 * Класс для реализации телеграмм бота
 */
public class Bot extends TelegramLongPollingBot {

    Logic botLogic;

    /**
     * Конструктор класса Bot
     */
    public Bot() {
        botLogic = new Logic();
    }

    /**
     * Метод, который предоставляет имя бота из конфигурационного файла
     * @return botName
     */
    @Override
    public String getBotUsername() {
        return Config.botName;
    }

    /**
     * Метод, который предоставляет токен бота из конфигурационного файла
     * @return botToken
     */
    @Override
    public String getBotToken() {
        return Config.botToken;
    }

    /**
     * Это метод telegram-бота, который реагирует на новые сообщения
     *@param update Это объект API telegram, который помогает нам взаимодействовать с событиями чата
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            botLogic.setChatId(chatId);
            String answer = botLogic.commandHandler(messageText);
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(answer);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


}