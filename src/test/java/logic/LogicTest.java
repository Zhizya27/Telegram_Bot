package logic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import reminder.Reminder;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 *  Покрывает модульными тестами класс Logic
 */
public class LogicTest {
    private Logic logic;
    private Reminder reminder;

    /**
     * Инициализация перед каждым тестом
     */
    @Before
    public void setUp() {
        logic = new Logic();
    }

    /**
     * Тест на проверку функции "добавить напоминание"
     */
    @Test
    public void testAddCommand() {
        logic.commandHandler("добавить напоминание");
        assertEquals("Какое напоминание вы хотите добавить? Введите в формате <дд.мм.гггг> <чч:мм> <текст напоминания>.",
                logic.commandHandler("добавить напоминание"));
        String result = logic.commandHandler("11.11.2023 14:00 купить цветы");
        Assert.assertEquals("Напоминание установлено!", result);
    }




    /**
     * Тест на проверку функции "список напоминнаий", что происходит при добавлении нескольких напоминаний и вызове функции ""список напоминаний"
     */
    @Test
    public void testListCommand() {

        logic.commandHandler("добавить напоминание");
        assertEquals("Какое напоминание вы хотите добавить? Введите в формате <дд.мм.гггг> <чч:мм> <текст напоминания>.",
                logic.commandHandler("добавить напоминание"));
        logic.commandHandler("11.11.2023 14:00 купить цветы");

        logic.commandHandler("добавить напоминание");
        assertEquals("Какое напоминание вы хотите добавить? Введите в формате <дд.мм.гггг> <чч:мм> <текст напоминания>.",
                logic.commandHandler("добавить напоминание"));
        logic.commandHandler("12.11.2023 14:00 купить пончики и сделать английский");


        assertEquals("Список ваших напоминаний:\n" +
                "1. 11.11.2023 14:00   купить цветы\n" +
                "2. 12.11.2023 14:00   купить пончики и сделать английский\n" +
                 "\n" +
                "Сегодня нет праздника", logic.commandHandler("список напоминаний"));
    }

    /**
     * Тест на проверку функции "удалить напоминание", проверяет то, что ф-ия действительно удаляет данные*/
    @Test
    public void testDelCommand() {
        // Добавим напоминание
        logic.commandHandler("добавить напоминание");
        assertEquals("Какое напоминание вы хотите добавить? Введите в формате <дд.мм.гггг> <чч:мм> <текст напоминания>.",
                logic.commandHandler("добавить напоминание"));
        logic.commandHandler("11.11.2023 14:00 купить цветы");

        logic.commandHandler("добавить напоминание");
        assertEquals("Какое напоминание вы хотите добавить? Введите в формате <дд.мм.гггг> <чч:мм> <текст напоминания>.",
                logic.commandHandler("добавить напоминание"));
        logic.commandHandler("12.11.2023 14:00 купить пончики и сделать английский");

        assertEquals("Какое напоминание вы хотите удалить? Введите номер.\n" +
                        "1. 11.11.2023" + " 14:00   купить цветы\n" +
                        "2. 12.11.2023" + " 14:00   купить пончики и сделать английский\n",
                logic.commandHandler("удалить напоминания"));
        String result3 = logic.commandHandler("1");
        assertEquals("Напоминание удалено:\n" +
                "11.11.2023" + " 14:00 купить цветы", result3);
    }

}