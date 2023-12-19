package reminder;

/**
 *  Класс для работы с напоминаниями
 */
public class Reminder {

    private String dateTime; // Дата напоминания
    private String text; // Текст напоминания
    private long chatid;

    /**
     *Конструктор класса Reminder
     * @param dateTime дата и время напоминания
     * @param text Текст напоминания
     * @param chatId id users or chat id
     */
    public Reminder(String dateTime, String text, long chatId) {
        this.dateTime = dateTime;
        this.text = text;
        this.chatid = chatId;
    }

    /**
     * Метод, который возвращает время и дату напоминания
     * @return dateTime
     */
    public String getDateTime(){
        return dateTime;
    }

    /**
     * Метод, который возвращает текст напоминания
     * @return text
     */
    public String getText() {
        return text;
    }

    public long getChatId(){
        return chatid;
    }
}
