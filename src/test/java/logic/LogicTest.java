package logic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import reminder.Reminder;
import static org.junit.Assert.*;

/**
 * Класс с тестами для всех функций бота*/

public class LogicTest {
    private Logic logic;
    private Reminder reminder;


    @Before
    public void setUp() {
        logic = new Logic();
        reminder = new Reminder("", "");
    }



    /**
     * Тест на проверку функции /add, что она действительно добавляет новые напоминания*/
    @Test
    public void testAddCommand() {
        String result = logic.commandHandler("/add 12.12.2023 18:00 Купить цветы");
        Assert.assertEquals("Напоминание установлено!", result);

        Assert.assertEquals("Список ваших напоминаний:\n" +
                "1. 12.12.2023   18:00 купить цветы\n", logic.commandHandler("/list"));
    }




    /**
     * Тест на проверку функции /list, что происходит при добавлении нескольких напоминаний и вызове функции /list*/
    @Test
    public void testListCommand() {

        String result1 = logic.commandHandler("/add 12.12.2023 18:00 Купить цветы");
        String result2 = logic.commandHandler("/add 10.12.2023 18:00 Купить клюшку");
        assertEquals("Список ваших напоминаний:\n" +
                "1. 12.12.2023   18:00 купить цветы\n" +
                "2. 10.12.2023   18:00 купить клюшку\n", logic.commandHandler("/list"));
    }

    /**
     * Тест на проверку функции /del, проверяет то, что ф-ия действительно удаляет данные*/
    @Test
    public void testDelCommand() {
        // Добавим напоминание
        String result1 = logic.commandHandler("/add 12.12.2023 18:00 Купить цветы");
        String result2 = logic.commandHandler("/add 10.12.2023 18:00 Купить клюшку");

        assertEquals("Какое напоминание вы хотите удалить? Введите номер.\n" +
                "1. 12.12.2023\n" +
                "   18:00 купить цветы\n" +
                "2. 10.12.2023\n" +
                "   18:00 купить клюшку\n", logic.commandHandler("/del"));
        String result3 = logic.commandHandler("1");
        assertEquals("Напоминание удалено:\n" +
                "12.12.2023\n" +
                "18:00 купить цветы", result3);
    }
}