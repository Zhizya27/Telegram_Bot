package reminder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.Config;
import org.json.JSONObject;

/**
 * Класс для взаимодействия с Calendarific API для получения информации о праздниках.
 */
public class CalendarificApi {

    private final Config config;

    /**
     * Конструктор класса, инициализирующий объект класса Config для получения конфигурационных данных.
     */
    public CalendarificApi() {
        config = new Config();
        config.load();
    }

    /**
     * Получает информацию о празднике по указанным параметрам.
     *
     * @param country страна, для которой запрашивается информация о празднике
     * @param year    год, для которого запрашивается информация о празднике
     * @param month   месяц, для которого запрашивается информация о празднике
     * @param day     день, для которого запрашивается информация о празднике
     * @return строка JSON с информацией о празднике
     * @throws Exception в случае ошибки при выполнении запроса к API Calendarific
     */
    public String getHolidayInfo(String country, String year, String month, String day) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String url = String.format("%s?api_key=%s&country=%s&year=%s&month=%s&day=%s&type=national,religious,local,observance",
                config.getAPI_URL(), config.getAPI_KEY(), country, year, month, day);

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new Exception("Ошибка при выполнении запроса к API Calendarific");
            }
        }
    }
}
