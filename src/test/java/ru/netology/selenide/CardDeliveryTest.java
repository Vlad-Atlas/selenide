package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;

import static com.codeborne.selenide.Selenide.*;


class CardDeliveryTest {

    private String generateDate(int addDays, String s) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldCardDeliveryTest() {
        open("http://localhost:9999");

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").setValue(generateDate(4, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.hidden, Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована")).shouldBe(Condition.hidden, Duration.ofSeconds(15));
        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=notification]").shouldHave(Condition.text("Успешно!\n" +
                "Встреча успешно забронирована на " + generateDate(4, "dd.MM.yyyy"))).shouldBe(Condition.visible);

    }
}