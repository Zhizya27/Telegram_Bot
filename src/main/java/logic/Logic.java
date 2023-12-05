package logic;

import org.json.JSONArray;
import org.json.JSONObject;
import reminder.Reminder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import reminder.CalendarificApi;

/**
 *Класс, который отвечает за логику бота
 */
public class Logic {
    private List<Reminder> reminders = new ArrayList<>();
    private boolean isDeleteMode = false;
    private long chatId;
    private List<Reminder> remindersToDelete;
    private CalendarificApi calendarificApi = new CalendarificApi();


    public Logic() {
        calendarificApi = calendarificApi;
    }

    /**
     * Устанавливает экземпляр CalendarificApi для класса Logic
     *
     * @param calendarificApi экземпляр CalendarificApi
     */
    public void setCalendarificApi(CalendarificApi calendarificApi) {
        this.calendarificApi = calendarificApi;
    }


    /**
     * Состояние бота
     */
    private enum BotState {
        NORMAL, AWAITING_REMINDER, AWAITING_HOLIDAY_DATE, AWAITING_HOLIDAY_DESCRIPTION
    }

    private StringBuilder pendingReminderText = new StringBuilder();

    private BotState currentState = BotState.NORMAL;
    private String pendingHolidayDate;

    /**
     * Функция setChatId устанавливает идентификатор чата для бота
     *
     * @param chatId идентификатор чата
     */
    public void setChatId(long chatId) {
        this.chatId = chatId;
    }


    /**
     * Получает информацию о празднике на текущую дату.
     *
     * @return информация о празднике
     */
    public String getHolidayInfoForToday() {
        try {
            // Получаем текущую дату
            LocalDate currentDate = LocalDate.of(2023, 11, 4);

            String formattedMonth = String.valueOf(currentDate.getMonthValue());

            String responseJson = calendarificApi.getHolidayInfo(
                    "RU",
                    String.valueOf(currentDate.getYear()),
                    formattedMonth,
                    String.valueOf(currentDate.getDayOfMonth()));

            // Разбираем JSON
            JSONObject jsonObject = new JSONObject(responseJson);

            if (jsonObject.has("response")) {
                JSONObject response = jsonObject.getJSONObject("response");

                if (response.has("holidays")) {
                    JSONArray holidays = response.getJSONArray("holidays");

                    if (holidays.length() > 0) {
                        JSONObject holiday = holidays.getJSONObject(0);
                        String holidayName = holiday.getString("name");
                        String holidayDescription = holiday.getString("description");

                        return "Сегодня праздник: " + holidayName + "!" + "\nОписание: " + holidayDescription;
                    }
                    else if(response.isEmpty()){

                        return "Сегодня нет праздника.";
                    }
                }
            }
            return "Сегодня нет праздника.";

        } catch (Exception e) {
            e.printStackTrace();

            return "Сегодня нет праздника";
        }
    }


    /**
     * Проверяет, соответствует ли переданная строка формату даты и времени.
     *
     * @param dateTime строка даты и времени
     * @return true, если строка соответствует формату, в противном случае - false
     */
    private boolean isValidDateTimeFormat(String dateTime) {
        String dateTimeRegex = "\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}";
        Pattern dateTimePattern = Pattern.compile(dateTimeRegex);
        Matcher dateTimeMatcher = dateTimePattern.matcher(dateTime);
        return dateTimeMatcher.matches();
    }

    /**
     * Проверяет, соответствует ли переданная строка формату даты.
     *
     * @param date строка даты
     * @return true, если строка соответствует формату, в противном случае - false
     */
    private boolean isValidDateFormat(String date) {
        String dateFormatRegex = "\\d{2}\\.\\d{2}";
        Pattern dateFormatPattern = Pattern.compile(dateFormatRegex);
        Matcher dateFormatMatcher = dateFormatPattern.matcher(date);
        return dateFormatMatcher.matches();
    }

    /**
     * Функция - обработчик команд
     *
     * @param message сообщение/функция пользователя
     * @return ответ на сообщение/функцию
     */
    public String commandHandler(String message) {
        message = message.toLowerCase();
        //TODO
        if (message.equals("/start")) {
            isDeleteMode = false;
            remindersToDelete = null;

            return """
                    Привет, мой друг! Я помогу тебе не забывать о твоих важных событиях!
                    Чтобы установить напоминание, нажмите кнопку "добавить напоминание"
                    Чтобы разобраться, как работает бот, введите команду /help
                    """;

        } else if (message.equals("/help")) {

            return """
                    Я - бот-Напоминания. Чтобы задать напоминания, воспользуйтесь кнопкой "добавить напоминание"
                    Чтобы посмотреть весь список напоминаний и узнать, есть ли сегодня в России какой-лиюо праздник, воспользуйтесь кнопкой "список напоминаний"
                    Чтобы удалить ненужное напоминание, воспользуйтесь кнопкой "удалить напоминание"
                    """;

        } else if (message.equals("добавить напоминание")) {

            currentState = BotState.AWAITING_REMINDER;
            return "Какое напоминание вы хотите добавить? Введите в формате <дд.мм.гггг> <чч:мм> <текст напоминания>.";

        } else if (currentState == BotState.AWAITING_REMINDER) {

            String result = processAddReminder(message);
            currentState = BotState.NORMAL;
            return result;

        } else if (message.equals("удалить напоминания")) {

            if (reminders.isEmpty()) {

                return "Напоминаний нет! Чтобы задать напоминание, нажмите на кнопку <добавить напоминание>";
            }
            isDeleteMode = true; // Входим в режим удаления
            remindersToDelete = new ArrayList<>(reminders); // Копируем текущий список напоминаний
            StringBuilder reminderList = new StringBuilder("Какое напоминание вы хотите удалить? Введите номер.\n");

            for (int i = 0; i < reminders.size(); i++) {
                Reminder reminder = reminders.get(i);
                reminderList.append(i + 1).append(". ").append(reminder.getDateTime()).append("   ").append(reminder.getText()).append("\n");
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

            return "Напоминание удалено:\n" + deletedReminder.getDateTime()+ " " + deletedReminder.getText();


        } else if (message.equals("список напоминаний")) {

            if (reminders.isEmpty()) {
                return "Напоминаний нет! Чтобы задать напоминание, нажмите на кнопку <добавить напоминание>";
            }
            StringBuilder reminderList = new StringBuilder("Список ваших напоминаний:\n");

            for (int i = 0; i < reminders.size(); i++) {
                Reminder reminder = reminders.get(i);
                reminderList.append(i + 1).append(". ").append(reminder.getDateTime())
                        .append("   ").append(reminder.getText()).append("\n");
            }

            try {
                CalendarificApi calendarificApi = new CalendarificApi();
                String holidayInfo = getHolidayInfoForToday();

                // Добавим информацию о празднике в ответ бота
                return reminderList.toString() + "\n" +
                        holidayInfo;

            } catch (Exception e) {
                e.printStackTrace();

                return "Ошибка при получении информации о празднике";
            }
        }
        else if (message.equals("добавить праздник")) {

            currentState = BotState.AWAITING_HOLIDAY_DATE;
            return "На какую дату вы хотите добавить праздник? Введите в формате <дд.мм>.";
        } else if (currentState == BotState.AWAITING_HOLIDAY_DATE) {

            if (isValidDateFormat(message)) {
                pendingHolidayDate = message;
                currentState = BotState.AWAITING_HOLIDAY_DESCRIPTION;

                return "Что отмечается в этот день?";
            }
            else {
                return "Пожалуйста, введите дату в соответствии с нужным форматом!";
            }

        } else if (currentState == BotState.AWAITING_HOLIDAY_DESCRIPTION) {

            String holidayDescription = message;
            reminders.add(new Reminder(pendingHolidayDate, holidayDescription));
            currentState = BotState.NORMAL;

            return "Хорошо, я напомню вам об этом!";
        }

        return """
                Я вас не понимаю. Пожалуйста, введите команду из списка команд.
                """;
    }


    /**
     * Обрабатывает добавление напоминания на основе переданного сообщения пользователя.
     *
     * @param message сообщение пользователя
     * @return результат обработки добавления напоминания
     */
    private String processAddReminder(String message) {
        String[] parts = message.split(" ", -1);

        if (parts.length >= 3 && isValidDateTimeFormat(parts[0] + " " + parts[1])) {

            // Пользователь ввел дату, время и текст напоминания
            String reminderText = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));
            reminders.add(new Reminder(parts[0] + " " + parts[1], reminderText));

            return "Напоминание установлено!";
        }
        return "Введите дату, время и текст напоминания в формате <дд.мм.гггг> <чч:мм> <текст напоминания>.";
    }
}