package logic;

import logic.Logic;
import org.junit.Assert;
import org.junit.Test;

public class TestApi {

    @Test
    public void testApiConnection() {
        try {
            Logic logic = new Logic();
            String response = logic.getHolidayInfoForToday();

            Assert.assertNotNull(response);

            if (response.contains("Сегодня праздник:")) {
                Assert.assertTrue(response.contains("Описание:"));
            } else if (response.contains("Сегодня нет праздника")) {
                Assert.assertFalse(response.contains("Описание:"));
            } else {
                Assert.fail("Неожиданный ответ от API");
            }
        } catch (Exception ex) {
            Assert.fail("Ошибка при выполнении теста: " + ex.getMessage());
        }

    }
}