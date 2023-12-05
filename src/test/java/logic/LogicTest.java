package logic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import reminder.CalendarificApi;
import reminder.Reminder;



import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 *  Покрывает модульными тестами класс Logic
 */
public class LogicTest {
    private Logic logic;
    private Reminder reminder;


    @Mock
    private CalendarificApi calendarificApi;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        logic = new Logic();
        logic.setCalendarificApi(calendarificApi);
    }

    @Test
    public void testGetHolidayInfoForToday() throws Exception {
        try{

        when(calendarificApi.getHolidayInfo(any(), any(), any(), any())).thenReturn("{\"response\":{\"holidays\":[{\"name\":\"Unity Day\",\"description\":\"Unity Day, which is on November 4, is one of the newest and the most controversial holidays in Russia.\"}]}}");

        String result = logic.getHolidayInfoForToday();

        // Проверяем результат
        Assert.assertNotNull(result);
        if (result.contains("Сегодня праздник:")) {
            Assert.assertTrue(result.contains("Описание:"));
        } else if (result.contains("Сегодня нет праздника")) {
            Assert.assertFalse(result.contains("Описание:"));
        } else {
            Assert.fail("Неожиданный ответ от API");
        }
    } catch (Exception ex) {
        Assert.fail("Ошибка при выполнении теста: " + ex.getMessage());
    }

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
     * Тест для случая, когда пользователь ввел неправильный формат напоминания
     */
    @Test
    public void testIncorrectFormatAddReminder() {
        logic.commandHandler("добавить напоминание");
        assertEquals("Введите дату, время и текст напоминания в формате <дд.мм.гггг> <чч:мм> <текст напоминания>.", logic.commandHandler("11.2,2023 11:0"));

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
     * Тестирует случай, когда нет добавленных напоминаний, но пользователь вызывает команду "список напоминаний"
     */
    @Test
    public void testNotReminderInList(){

        assertEquals("Напоминаний нет! Чтобы задать напоминание, нажмите на кнопку <добавить напоминание>",logic.commandHandler("список напоминаний"));

    }

    /**
     * Тест на проверку функции "удалить напоминание", проверяет то, что ф-ия действительно удаляет данные*/
    @Test
    public void testDelCommand() {

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


    /**
     * Тестирует случай, когда пользователь вводит номер напоминания, которого нет в списке
     */
    @Test
    public void testIncorrectFormatDeleted(){

        logic.commandHandler("добавить напоминание");
        assertEquals("Какое напоминание вы хотите добавить? Введите в формате <дд.мм.гггг> <чч:мм> <текст напоминания>.",
                logic.commandHandler("добавить напоминание"));
        logic.commandHandler("11.11.2023 14:00 купить цветы");


        assertEquals("Какое напоминание вы хотите удалить? Введите номер.\n" +
                        "1. 11.11.2023" + " 14:00   купить цветы\n",
                logic.commandHandler("удалить напоминания"));

        String result = logic.commandHandler("3");
        assertEquals("Некорректный номер напоминания. Попробуйте ещё раз.", result);
    }


    /**
     * Тестирует слуяай, когда пользователь хочет удалить напоминание, которое еще не добавил
     */
    @Test
     public void testNoUpdateReminderForDeleted(){
        assertEquals("Напоминаний нет! Чтобы задать напоминание, нажмите на кнопку <добавить напоминание>", logic.commandHandler("удалить напоминания"));

     }

}