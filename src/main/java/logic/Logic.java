package logic;
import reminder.Reminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
*Класс, который отвечает за логику бота
 */
public class Logic {
    private List<Reminder> reminders = new ArrayList<>();
    private boolean isDeleteMode = false;
    private long chatId;
    private List<Reminder> remindersToDelete;

    /**
     * Функция setChatId устанавливает идентификатор чата для бота
     *
     * @param chatId идентификатор чата
     */
    public void setChatId(long chatId) {
        this.chatId = chatId;
    }


    /**
     * Функция - обработчик команд
     *
     * @param message сообщение/функция пользователя
     * @return ответ на сообщение/функцию
     */
    public String commandHandler(String message) {
        message = message.toLowerCase();

        if (message.equals("/start")) {
            isDeleteMode = false;
            remindersToDelete = null;
            return """
                    Привет, мой друг! Я помогу тебе не забывать о твоих важных событиях!
                    Чтобы установить напоминание, введите команду /add
                    Чтобы разобраться, как работает бот, введите команду /help
                    """;
        } else if (message.equals("/help")) {
            return """
                    Я - бот-Напоминания. Чтобы задать напоминания, воспользуйтесь командой /add
                    Чтобы посмотреть весь список напоминаний, воспрльзуйтесь командой /list 
                    Чтобы удалить ненужное напоминание, воспользуйтесь командой /del
                    """;
        } else if (message.startsWith("/add")) {
            String[] parts = message.split(" ", 3);
            if (parts.length < 3) {
                return "Некорректный формат команды. Используйте /add <дата и время> <текст напоминания>";
            }

            String dateTime = parts[1];
            String reminderText = parts[2];

            reminders.add(new Reminder(dateTime, reminderText));

            return "Напоминание установлено!";

        } else if (message.equals("/del")) {
            if (reminders.isEmpty()) {
                return "Напоминаний нет! Чтобы задать напоминание, выберите функцию /add";
            }
            isDeleteMode = true; // Входим в режим удаления
            remindersToDelete = new ArrayList<>(reminders); // Копируем текущий список напоминаний
            StringBuilder reminderList = new StringBuilder("Какое напоминание вы хотите удалить? Введите номер.\n");
            for (int i = 0; i < reminders.size(); i++) {
                Reminder reminder = reminders.get(i);
                reminderList.append(i + 1).append(". ").append(reminder.getDateTime()).append("\n")
                        .append("   ").append(reminder.getText()).append("\n");
            }

            return reminderList.toString();

        } else if (isDeleteMode) {
            int indexToDelete;
            try {
                indexToDelete = Integer.parseInt(message);
                if (indexToDelete < 1 || indexToDelete > remindersToDelete.size()) {
                    return "Некорректный номер напоминания. Попробуйте ещё раз.";
                }
            } catch (NumberFormatException e) {
                return "Некорректный номер напоминания. Попробуйте ещё раз.";
            }

            Reminder deletedReminder = remindersToDelete.remove(indexToDelete - 1);
            reminders = new ArrayList<>(remindersToDelete); // Обновляем список напоминаний после удаления
            isDeleteMode = false; // Выходим из режима удаления
            return "Напоминание удалено:\n" + deletedReminder.getDateTime() + "\n" + deletedReminder.getText();
        } else if (message.startsWith("/list")) {
            if (reminders.isEmpty()) {
                return "Напоминаний нет! Чтобы задать напоминание, выберите функцию /add";
            }

            StringBuilder reminderList = new StringBuilder("Список ваших напоминаний:\n");
            for (int i = 0; i < reminders.size(); i++) {
                Reminder reminder = reminders.get(i);
                reminderList.append(i + 1).append(". ").append(reminder.getDateTime())
                        .append("   ").append(reminder.getText()).append("\n");
            }

            return reminderList.toString();
        }

        return """
                Я вас не понимаю. Пожалуйста, введите команду из списка команд.
                """;
    }
}