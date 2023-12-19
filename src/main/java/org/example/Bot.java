package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import logic.Logic;
import java.util.ArrayList;
import java.util.List;



/**
 * Класс для реализации телеграмм бота
 */
public class Bot extends TelegramLongPollingBot {
    private Logic botLogic;
    private final Config config;

    Keyboard keyboardUtil = new Keyboard();

    /**
     * Конструктор класса Bot
     */
    public Bot() {
        botLogic = new Logic();
        config = new Config();
        config.load();

        List<BotCommand> listsOfCommands = new ArrayList<>();
        listsOfCommands.add(new BotCommand("/start", "приветствие пользователя"));
        listsOfCommands.add(new BotCommand("/help", "информация как пользоваться ботом"));
        listsOfCommands.add(new BotCommand("/add", "установить напоминание"));
        listsOfCommands.add(new BotCommand("/list", "показать список напоминаний"));
        listsOfCommands.add(new BotCommand("/del", "удаление ненужного напоминания"));

        try{
            this.execute(new SetMyCommands(listsOfCommands, new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Метод, который предоставляет имя бота из конфигурационного файла
     * @return botName
     */
    @Override
    public String getBotUsername() {
        return config.getBotUserName();
    }

    /**
     * Метод, который предоставляет токен бота из конфигурационного файла
     * @return botToken
     */
    @Override
    public String getBotToken() {
        return config.getBotToken();
    }


    /**
     * Это метод telegram-бота, который реагирует на новые сообщения
     *@param update Это объект API telegram, который помогает нам взаимодействовать с событиями чата
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message inMess = update.getMessage();
            long chatId = inMess.getChatId();
            String messageText = update.getMessage().getText();
            botLogic.setChatId(chatId);
            String answer = botLogic.commandHandler(messageText);
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(answer);
            message.setReplyMarkup(keyboardUtil.MenuKeyboard());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


}