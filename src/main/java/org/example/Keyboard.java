package org.example;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {

    public static ReplyKeyboardMarkup MenuKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        // Создаем кнопки
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("добавить напоминание"));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("список напоминаний"));

        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("удалить напоминания"));

        KeyboardRow row4 = new KeyboardRow();
        row4.add(new KeyboardButton("добавить праздник"));


        // Добавляем кнопки в клавиатуру
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);

        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }
}
