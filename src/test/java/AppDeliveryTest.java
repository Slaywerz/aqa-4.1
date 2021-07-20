import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AppDeliveryTest {

    //  This is logic for the date
    LocalDate date = LocalDate.now().plusDays(4);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String formatted = formatter.format(date);

    //  This is variable for the delete values in input
    String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;


    @AfterEach
    void setUp() {
        open("http://localhost:9999/");

    }

    @Test
    void shouldReservedDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] .input__box .input__control[placeholder='Город']").setValue("Уфа");
        $("[data-test-id='date'] .input__box .input__control[placeholder='Дата встречи']").setValue(deleteString);
        $("[data-test-id='date'] .input__box .input__control[placeholder='Дата встречи']").setValue(String.valueOf(formatted));
        $("[data-test-id='name'] .input__box .input__control[name='name']").setValue("Семенов Андрей");
        $("[data-test-id='phone'] .input__box .input__control[name='phone']").setValue("+12345678901");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(byText("Успешно!")).shouldBe(Condition.visible);
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + formatted));
    }
}
