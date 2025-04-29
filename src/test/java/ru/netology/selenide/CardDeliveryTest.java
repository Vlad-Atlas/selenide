package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;


class CardDeliveryTest {

    private String generateDate(int addDays) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldSubmitCardDeliveryFormSuccessfully() {
        open("http://localhost:9999");


        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").clear();
        $("[data-test-id=date] input").setValue(generateDate(3));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $$("button").findBy(Condition.text("Забронировать")).click();
        $(".loading-indicator").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(15));
        String expectedMessage = "Успешно! Встреча успешно забронирована на " + generateDate(3);
        $("[data-test-id=notification]").shouldHave(Condition.text(expectedMessage));
    }
}