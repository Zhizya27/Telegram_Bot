package reminder;

import logic.Logic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Класс для тестирования работы с Api
 */

@ExtendWith(MockitoExtension.class)
class CalendarificApiTest {


    @Mock
    private CalendarificApi calendarificApiMock;

    @InjectMocks
    private Logic logic;

    /**
     * Тестирует случай, когда в данный день праздник есть(тут рассматривается дата 11 нояюря - День Народного Единства)
     */

    @Test
    void testGetHolidayInfoForTodayWithHoliday() {

        when(calendarificApiMock.getHolidayInfo(anyString(), anyString(), anyString(), anyString()))
                .thenReturn("{\"response\":{\"holidays\":[{\"name\":\"Unity Day\",\"description\":\"Unity Day, which is on November 4, is one of the newest and the most controversial holidays in Russia.\"}]}}");


        logic.setCalendarificApi(calendarificApiMock);

        String result = logic.getHolidayInfoForToday();
        Assertions.assertEquals("Сегодня праздник: Unity Day!\nОписание: Unity Day, which is on November 4, is one of the newest and the most controversial holidays in Russia.", result);
    }

    /**
     * Тестирует случай, когда в данный день праздника нет
     */

    @Test
    void testGetHolidayInfoForTodayWithoutHoliday() {

        when(calendarificApiMock.getHolidayInfo(anyString(), anyString(), anyString(), anyString()))
                .thenReturn("{\"response\":{\"holidays\":[]}}");

        logic.setCalendarificApi(calendarificApiMock);

        String result = logic.getHolidayInfoForToday();

        Assertions.assertEquals("Сегодня нет праздника.", result);
    }
}
