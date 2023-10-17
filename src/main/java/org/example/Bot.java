package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import Logic.Logic;

public class Bot extends TelegramLongPollingBot {

    Logic botLogic;
    public Bot(){
        botLogic = new Logic();
    }

    @Override
    public String getBotUsername () {
        return Config.botName;
    }

    @Override
    public String getBotToken () {
        return Config.botToken;
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


}