package logic;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import reminder.CalendarificApi;
import reminder.Reminder;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 *  Покрывает модульными тестами класс Logic
 */
public class LogicTest {
    @Mock
    CalendarificApi calendarificApiMock;

    @InjectMocks
    Logic logic;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест на проверку функции "добавить напоминание"
     */
    @Test
    public void testAddCommand() {
        when(calendarificApiMock.getHolidayInfo(anyString(), anyString(), anyString(), anyString()))
                .thenReturn("{\"response\":{\"holidays\":[]}}");

        Assertions.assertEquals("Какое напоминание вы хотите добавить? Введите в формате <дд.мм.гггг> <чч:мм> <текст напоминания>.",
                logic.commandHandler("добавить напоминание"));

        String result1 = logic.commandHandler("11.11.2023 14:00 купить цветы");
        Assertions.assertEquals("Напоминание установлено!", result1);

        String result2 = logic.commandHandler("список напоминаний");
        Assertions.assertEquals("Список ваших напоминаний:\n" +
        "1. 11.11.2023 14:00   купить цветы\n" +
                "\n" +
                "Сегодня нет праздника.", result2);

    }


    /**
     * Тест для случая, когда пользователь ввел неправильный формат напоминания
     */
    @Test
    public void testIncorrectFormatAddReminder() {

        logic.commandHandler("добавить напоминание");
        Assertions.assertEquals("Введите дату, время и текст напоминания в формате <дд.мм.гггг> <чч:мм> <текст напоминания>.",
                logic.commandHandler("11.2,2023 11:0"));

    }


    /**
     * Тест на проверку функции "список напоминнаий", что происходит при добавлении нескольких напоминаний и вызове функции ""список напоминаний",
     * а также случай, когда в этот день нет праздника
     */

    @Test
    public void testListCommandWithoutHoliday() {
        when(calendarificApiMock.getHolidayInfo(anyString(), anyString(), anyString(), anyString()))
                .thenReturn("{\"response\":{\"holidays\":[]}}");

        logic.commandHandler("добавить напоминание");
        logic.commandHandler("11.11.2023 14:00 купить цветы");

        logic.commandHandler("добавить напоминание");
        logic.commandHandler("12.11.2023 14:00 купить пончики и сделать английский");


        String expec = "Список ваших напоминаний:\n" +
                "1. 11.11.2023 14:00   купить цветы\n" +
                "2. 12.11.2023 14:00   купить пончики и сделать английский\n" +
                "\n" +
                "Сегодня нет праздника.";

        Assertions.assertEquals(expec, logic.commandHandler("список напоминаний"));
    }

    /**
     * Тест на проверку функции "список напоминнаий", что происходит при добавлении нескольких напоминаний и вызове функции ""список напоминаний",
     *      * а также случай, когда в этот день есть праздник (рассматриваем 4 ноября - День Народного Единства)
     */

    @Test
    public void testListCommandWithHoiday(){

        when(calendarificApiMock.getHolidayInfo(anyString(),anyString(), anyString(), anyString()))
                .thenReturn("{\"response\":{\"holidays\":[{\"name\":\"Unity Day\",\"description\":\"Unity Day, which is on November 4, is one of the newest and the most controversial holidays in Russia.\"}]}}");

        logic.commandHandler("добавить напоминание");
        logic.commandHandler("11.11.2023 14:00 купить цветы");


        logic.commandHandler("добавить напоминание");
        logic.commandHandler("12.11.2023 14:00 купить пончики и сделать английский");


        Assertions.assertEquals("Список ваших напоминаний:\n" +
                "1. 11.11.2023 14:00   купить цветы\n" +
                "2. 12.11.2023 14:00   купить пончики и сделать английский\n" +
                "\n" +
                "Сегодня праздник: Unity Day!\nОписание: Unity Day, which is on November 4, is one of the newest and the most controversial holidays in Russia.", logic.commandHandler("список напоминаний"));

    }


    /**
     * Тестирует случай, когда нет добавленных напоминаний, но пользователь вызывает команду "список напоминаний"(пусть это будет 11 ноября - День народного единства)
     */
    @Test
    public void testNotReminderInList(){

        when(calendarificApiMock.getHolidayInfo(anyString(),anyString(), anyString(), anyString()))
                .thenReturn("{\"response\":{\"holidays\":[{\"name\":\"Unity Day\",\"description\":\"Unity Day, which is on November 4, is one of the newest and the most controversial holidays in Russia.\"}]}}");


        Assertions.assertEquals("Сегодня праздник: Unity Day!\nОписание: Unity Day, which is on November 4, is one of the newest and the most controversial holidays in Russia.\nНапоминаний нет! Чтобы задать напоминание, нажмите на кнопку <добавить напоминание>",
                logic.commandHandler("список напоминаний"));

    }

    /**
     * Тест на проверку функции "удалить напоминание", проверяет то, что ф-ия действительно удаляет данные*/
    @Test
    public void testDelCommand() {

        when(calendarificApiMock.getHolidayInfo(anyString(), anyString(), anyString(), anyString()))
                .thenReturn("{\"response\":{\"holidays\":[]}}");


        logic.commandHandler("добавить напоминание");
        logic.commandHandler("11.11.2023 14:00 купить цветы");


        logic.commandHandler("добавить напоминание");
        logic.commandHandler("12.11.2023 14:00 купить пончики и сделать английский");

        String expec = "Список ваших напоминаний:\n" +
                "1. 11.11.2023 14:00   купить цветы\n" +
                "2. 12.11.2023 14:00   купить пончики и сделать английский\n" +
                "\n" +
                "Сегодня нет праздника.";

        Assertions.assertEquals(expec, logic.commandHandler("список напоминаний"));

        Assertions.assertEquals("Какое напоминание вы хотите удалить? Введите номер.\n" +
                        "1. 11.11.2023" + " 14:00   купить цветы\n" +
                        "2. 12.11.2023" + " 14:00   купить пончики и сделать английский\n",
                logic.commandHandler("удалить напоминания"));
        String result3 = logic.commandHandler("1");
        Assertions.assertEquals("Напоминание удалено:\n" +
                "11.11.2023" + " 14:00 купить цветы", result3);
    }


    /**
     * Тестирует случай, когда пользователь вводит номер напоминания, которого нет в списке
     */
    @Test
    public void testIncorrectFormatDeleted(){


        logic.commandHandler("добавить напоминание");
        logic.commandHandler("11.11.2023 14:00 купить цветы");


        Assertions.assertEquals("Какое напоминание вы хотите удалить? Введите номер.\n" +
                        "1. 11.11.2023" + " 14:00   купить цветы\n",
                logic.commandHandler("удалить напоминания"));

        String result = logic.commandHandler("3");
        Assertions.assertEquals("Некорректный номер напоминания. Попробуйте ещё раз.", result);
    }


    /**
     * Тестирует слуяай, когда пользователь хочет удалить напоминание, которое еще не добавил
     */
    @Test
    public void testNoUpdateReminderForDeleted(){
        Assertions.assertEquals("Напоминаний нет! Чтобы задать напоминание, нажмите на кнопку <добавить напоминание>",
                logic.commandHandler("удалить напоминания"));

    }

    /**
     * Тест команды "добавить праздник"
     */
    @Test
    public void addHolidayCommandTest(){

        when(calendarificApiMock.getHolidayInfo(anyString(), anyString(), anyString(), anyString()))
                .thenReturn("{\"response\":{\"holidays\":[]}}");

        String result = logic.commandHandler("добавить праздник");
        Assertions.assertEquals("На какую дату вы хотите добавить праздник? Введите в формате <дд.мм>.", result);
        String result2 = logic.commandHandler("25.11");
        Assertions.assertEquals("Что отмечается в этот день?", result2);
        String result3 = logic.commandHandler("день рождения Артема Ахмадиева");
        Assertions.assertEquals("Хорошо, я напомню вам об этом!", result3);

        String expec = "Список ваших напоминаний:\n" +
                "1. 25.11   день рождения артема ахмадиева\n" +
                "\n" +
                "Сегодня нет праздника.";

        Assertions.assertEquals(expec, logic.commandHandler("список напоминаний"));




    }

    /** * Тестирует случай, когда пользователь вводит неправильный формат даты при добавлении  праздника
     */
    @Test
    public void testIncorrectFormatAddHoliday(){
        String result = logic.commandHandler("добавить праздник");
        Assertions.assertEquals("На какую дату вы хотите добавить праздник? Введите в формате <дд.мм>.", result);
        String result2 = logic.commandHandler("25^11");
        Assertions.assertEquals("Пожалуйста, введите дату в соответствии с нужным форматом!", result2);
    }


    /**
     * Тестирует случай, когда пользователь вводит неверную команду или же просто какое то сообщение, которое бот не понимает
     */
    @Test
    public void testIncorrectedMessage(){
        Assertions.assertEquals("Я вас не понимаю. Пожалуйста, введите команду из списка команд.\n", logic.commandHandler("Ты кто?"));
        Assertions.assertEquals("Я вас не понимаю. Пожалуйста, введите команду из списка команд.\n", logic.commandHandler("добавить уведомление"));
        Assertions.assertEquals("Я вас не понимаю. Пожалуйста, введите команду из списка команд.\n", logic.commandHandler("start"));

    }

    /**
     * тестирует то, что бот ведет разный диалог с пользователями и напоминания сохранятся для каждого пользователя отдельно
     */
    @Test
    public void testManyUsers(){

        long chatId1 = 123456789;
        long chatId2 = 987654321;

        logic.setChatId(chatId1);
        logic.commandHandler("добавить напоминание");
        logic.commandHandler("11.11.2023 13:00 скушать пончики");
        logic.commandHandler("добавить напоминание");
        logic.commandHandler("12.11.2023 13:00 скушать яблоко");
        logic.commandHandler("добавить напоминание");
        logic.commandHandler("13.11.2023 13:00 скушать банан");

        logic.setChatId(chatId2);
        logic.commandHandler("добавить напоминание");
        logic.commandHandler("11.11.2023 14:00 скушать блины");


        List<Reminder> remindersUser1 = logic.getRemindersForUser(chatId1);
        List<Reminder> remindersUser2 = logic.getRemindersForUser(chatId2);

        Assertions.assertFalse(remindersUser1.isEmpty());
        Assertions.assertFalse(remindersUser2.isEmpty());

        // Проверим, что каждый список содержит только напоминания соответствующего пользователя
        for (Reminder reminder : remindersUser1) {
            Assertions.assertEquals(chatId1, reminder.getChatId());
        }

        for (Reminder reminder : remindersUser2) {
            Assertions.assertEquals(chatId2, reminder.getChatId());
        }

        // Второй способ:
        // Assertions.assertNotEquals(remindersUser1.size(), remindersUser2.size());
    }

}
