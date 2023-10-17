package Logic;
import org.junit.Assert;
import org.junit.Test;

public class LogicTest {

    @Test
    public void commandHandlerTest() {
        Logic logic = new Logic();

        // Тестовые сообщения для проверки "отзеркаливания"
        String message1 = "привет, бот!";
        String message2 = "1917389344848?";
        String message3 = "погода сегодня";

        // Ожидаемые результаты
        String expected1 = "привет, бот!";
        String expected2 = "1917389344848?";
        String expected3 = "погода сегодня";

        // Проверка, что сообщения "отзеркаливаются" правильно
        Assert.assertEquals(expected1, logic.commandHandler(message1));
        Assert.assertEquals(expected2, logic.commandHandler(message2));
        Assert.assertEquals(expected3, logic.commandHandler(message3));
    }

    @Test
    public void CommandHandlerStartCommandTest() {

        Logic logic = new Logic();

        // Сообщение с командой /start
        String startCommand = "/start";

        // Ожидаемый результат для команды /start
        String expected = """
                Привет, мой друг! Я помогу тебе не забывать о твоих важных событиях!
                Пока я умею только отзеркаливать твои сообщения, но вскоре ты забудешь про приложение Напоминание и будешь польховаться только мной\s
                """;

        // Проверка, что команда /start правильно обрабатывается
        Assert.assertEquals(expected, logic.commandHandler(startCommand));
    }

    @Test
    public void CommandHandlerHelpCommandTest() {

        Logic logic = new Logic();

        // Сообщение с командой /help
        String helpCommand = "/help";

        // Ожидаемый результат для команды /help
        String expected = """
                Я - бот-Напоминания и освещатель ежедневынх праздников, я помогу тебе всегда знать, какой сегодня праздник! А еще, с помощью меня ты никогда не забудешь купить цветы своей маме, ведь можешь задать мне время и дату и я напишу тебе именно во столько
                """;

        // Проверка, что команда /help правильно обрабатывается
        Assert.assertEquals(expected, logic.commandHandler(helpCommand));
    }
}
