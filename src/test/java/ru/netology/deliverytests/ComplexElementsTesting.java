package ru.netology.deliverytests;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.format.DateTimeFormatter.ofPattern;

public class ComplexElementsTesting {
    LocalDate currentDate = LocalDate.now();
    int days = 7;
    LocalDate dateOfDelivery = LocalDate.now().plusDays(days);

    @BeforeEach
    void SetUpAll() {
        open("http://localhost:9999");
    }

    @Test
    void shouldChooseCityFromMenuAndDateFromCalendar() {

        $("[data-test-id=city] .input__control").setValue("Пе");
        $(".menu").shouldBe(visible);
        $$(".menu-item").find(exactText("Пермь")).click();
        $(".input__icon").click();
        $(".calendar").shouldBe(visible);
        if (currentDate.getMonthValue() != dateOfDelivery.getMonthValue()) {
            $("[data-step='1'").click();
        }
        $$("td.calendar__day").find(text(dateOfDelivery.format(DateTimeFormatter.ofPattern("d")))).click();
        $("[data-test-id=name] .input__control").setValue("Александр Сергеевич");
        $("[data-test-id=phone] .input__control").setValue("+01234567890");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(".button__text").click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] [class='notification__content']")
                .shouldHave(text("Встреча успешно забронирована на " + (dateOfDelivery.format(ofPattern("dd.MM.yyyy")))));
    }


    @Test
    public void shouldCheckTheMenuContent() {
        $("[data-test-id=city] .input__control").setValue("ек");
        $$(".menu-item").shouldHave(CollectionCondition.containExactTextsCaseSensitive("Архангельск",
                "Благовещенск", "Великий Новгород", "Екатеринбург",
                "Ижевск", "Липецк", "Петрозаводск", "Петропавловск-Камчатский",
                "Смоленск", "Чебоксары", "Челябинск", "Черкесск"));
    }

}


