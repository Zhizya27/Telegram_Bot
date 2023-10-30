package reminder;

/**
* Класс Reminder для представления напоминания
 */
public class Reminder {
    private String dateTime; // Дата напоминания
    private String text; // Текст напоминания

    /**
    *Конструктор класса Reminder
    * @param dateTime дата и время напоминания
    * @param text Текст напоминания
     */
    public Reminder(String dateTime, String text) {
        this.dateTime = dateTime;
        this.text = text;
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

}

