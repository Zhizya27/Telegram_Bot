
package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import Logic.Logic;
public class Bot extends TelegramLongPollingBot {
    final String botName;
    final String botToken;

    final private Logic botLogic = new Logic();

    public Bot(String botName, String botToken) {
        this.botName = botName;
        this.botToken = botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
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

        @Override
        public String getBotUsername () {
            return "forJavaUrfubot"; // Здесь укажите имя вашего бота
        }


        public String getBotToken () {
            return "6627470072:AAFQjSFg5SrhPPGjnkQGxbYxqeVs5lRnJA8"; // Здесь укажите токен вашего бота
        }
    }
