package Logic;
/** a class that implements message logic when calling system functions */
public class Logic {

    public String commandHandler(String message) {
        message = message.toLowerCase();

        if (message.equals("/start")) {
            return """
                        Привет, мой друг! Я помогу тебе не забывать о твоих важных событиях!
                        Пока я умею только отзеркаливать твои сообщения, но вскоре ты забудешь про приложение Напоминание и будешь польховаться только мной\s
                        """;
        }
        else if (message.equals("/help")) {
            return """ 
                    Я - бот-Напоминания и освещатель ежедневынх праздников, я помогу тебе всегда знать, какой сегодня праздник! А еще, с помощью меня ты никогда не забудешь купить цветы своей маме, ведь можешь задать мне время и дату и я напишу тебе именно во столько
                    """;
        }
        return message;
    }
}