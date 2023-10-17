package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import Logic.Logic;

/*
* bot constructor class */
public class Bot extends TelegramLongPollingBot {

    Logic botLogic;
    public Bot(){
        botLogic = new Logic();
    }
/*
* method of getting the bot name from the configuration file*/
    @Override
    public String getBotUsername () {
        return Config.botName;
    }
/*
* method of getting the bot token from the configuration file */
    @Override
    public String getBotToken () {
        return Config.botToken;
    }
/*
* a class that implements sending the same messages
 * that the user sends */
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