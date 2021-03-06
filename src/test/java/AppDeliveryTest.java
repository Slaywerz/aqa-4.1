import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AppDeliveryTest {

    //    This is date logic
    public String date(int days, String format) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(format));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");

    }

    @Test
    @DisplayName("Meet reserved through 3 days")
    void shouldReservedDate() {

//      Test configuration
        String meetDate = date(3, "dd.MM.yyyy");
//      Test data
        $("[data-test-id='city'] .input__box .input__control[placeholder='Город']").setValue("Уфа");
        $("[data-test-id='date'] .input__box .input__control[placeholder='Дата встречи']")
                .doubleClick().sendKeys(Keys.chord(Keys.BACK_SPACE));
        $("[data-test-id='date'] .input__box .input__control[placeholder='Дата встречи']")
                .setValue(String.valueOf(meetDate));
        $("[data-test-id='name'] .input__box .input__control[name='name']").setValue("Семенов Андрей");
        $("[data-test-id='phone'] .input__box .input__control[name='phone']").setValue("+12345678901");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(byText("Успешно!")).shouldBe(Condition.visible);
        $("[data-test-id='notification'] .notification__content").shouldHave
                (Condition.text("Встреча успешно забронирована на " + meetDate));
    }

    @Test
    @DisplayName("Meet reserved through 7 days")
    void shouldReservedMeetThroughSevenDays() {

//        Test config
        String meetDate = date(7, "d");
        String fullMeetDate = date(7, "dd.MM.yyyy");

//        Test data
        $("[data-test-id='city'] .input__box .input__control[placeholder='Город']").setValue("Кр");
        $(byText("Красноярск")).click();
        $("[data-test-id='date'] .input__box .input__control[placeholder='Дата встречи']")
                .doubleClick().sendKeys(Keys.chord(Keys.BACK_SPACE));
        $("[role ='button'] .icon-button__content .icon-button__text").click();
        if(!date(3, "MM").equals(date(7, "MM")))$("[data-step='1']").click();
        $("table[class='calendar__layout']").find(byText(meetDate)).click();
        $("[data-test-id='name'] .input__box .input__control[name='name']").setValue("Семенов Андрей");
        $("[data-test-id='phone'] .input__box .input__control[name='phone']").setValue("+12345678901");
        $("[data-test-id='agreement'] .checkbox__box").click();

        $(byText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(byText("Успешно!")).shouldBe(Condition.visible);
        $("[data-test-id='notification'] .notification__content").shouldHave
                (Condition.text("Встреча успешно забронирована на " + fullMeetDate));
    }
}
