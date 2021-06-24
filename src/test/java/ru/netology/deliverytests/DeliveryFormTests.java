package ru.netology.deliverytests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.*;

public class DeliveryFormTests {

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTestValidInfo() {
        //open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue("Ульяновск");
        $("[data-test-id='date'] [class='input__control']").click();
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().plusDays(8).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [class='input__control']").setValue(date);
        $("[data-test-id='name'] [class='input__control']").setValue("Алексей-Родион Калужский");
        $("[data-test-id='phone'] [class='input__control']").setValue("+09817264867");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] [class='notification__content']").shouldHave(text("Встреча успешно забронирована на " + (date)));
    }

    @Test
    void shouldTestEmptyForm() {
        //open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue("");
        $("[data-test-id='date'] [class='input__control']").click();
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='name'] [class='input__control']").setValue("");
        $("[data-test-id='phone'] [class='input__control']").setValue("");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestInvalidCity() {
        //open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Магнитогорск");
        $("[data-test-id='date'] .input__control").click();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] .input__control").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().plusDays(6).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] .input__control").setValue(date);
        $("[data-test-id='name'] .input__control").setValue("Владимир Владимирович");
        $("[data-test-id='phone'] .input__control").setValue("+87112240089");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestEmptyCity() {
        //open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("");
        $("[data-test-id='date'] .input__control").click();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] .input__control").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().plusDays(3).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] .input__control").setValue(date);
        $("[data-test-id='name'] .input__control").setValue("Олег Тю");
        $("[data-test-id='phone'] .input__control").setValue("+18657400223");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestInvalidDate() {
        //open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Саранск");
        $("[data-test-id='date'] .input__control").click();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] .input__control").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().minusDays(3).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] .input__control").setValue(date);
        $("[data-test-id='name'] .input__control").setValue("Чипсы От-Бингрэ");
        $("[data-test-id='phone'] .input__control").setValue("+64582309871");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(visible);
    }

    @Test
    void shouldFillInDateIncorrectly() {
        //open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Саранск");
        $("[data-test-id='date'] .input__control").click();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] .input__control").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().minusDays(15).format(ofPattern("dd.MM.yy"));
        $("[data-test-id='date'] .input__control").setValue(date);
        $("[data-test-id='name'] .input__control").setValue("Нини Роман");
        $("[data-test-id='phone'] .input__control").setValue("+64582309871");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Неверно введена дата")).shouldBe(visible);
    }

    @Test
    void shouldTestLatinName() {
        //open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue("Ижевск");
        $("[data-test-id='date'] [class='input__control']").click();
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().plusDays(14).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [class='input__control']").setValue(date);
        $("[data-test-id='name'] [class='input__control']").setValue("Fanta Cola");
        $("[data-test-id='phone'] [class='input__control']").setValue("+31094758910");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(("[data-test-id='name'].input_invalid .input__sub")).shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestEmptyName() {
        //open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue("Магадан");
        $("[data-test-id='date'] [class='input__control']").click();
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().plusDays(4).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [class='input__control']").setValue(date);
        $("[data-test-id='name'] [class='input__control']").setValue("");
        $("[data-test-id='phone'] [class='input__control']").setValue("+45678901234");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(("[data-test-id='name'].input_invalid .input__sub")).shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestNameWithNumbers() {
        //open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue("Вологда");
        $("[data-test-id='date'] [class='input__control']").click();
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().plusDays(5).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [class='input__control']").setValue(date);
        $("[data-test-id='name'] [class='input__control']").setValue("838390 28220");
        $("[data-test-id='phone'] [class='input__control']").setValue("+56789012345");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(("[data-test-id='name'].input_invalid .input__sub")).shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestInvalidPhoneLess() {
        //open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue("Магас");
        $("[data-test-id='date'] [class='input__control']").click();
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().plusDays(8).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [class='input__control']").setValue(date);
        $("[data-test-id='name'] [class='input__control']").setValue("Эрнест Потапович");
        $("[data-test-id='phone'] [class='input__control']").setValue("+3431199");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(("[data-test-id='phone'].input_invalid .input__sub")).shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestInvalidPhoneMore() {
        //open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue("Кызыл");
        $("[data-test-id='date'] [class='input__control']").click();
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().plusDays(8).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [class='input__control']").setValue(date);
        $("[data-test-id='name'] [class='input__control']").setValue("Фондю Что-Это");
        $("[data-test-id='phone'] [class='input__control']").setValue("+619079909876543187");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(("[data-test-id='phone'].input_invalid .input__sub")).shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestEmptyPhone() {
        //open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue("Кызыл");
        $("[data-test-id='date'] [class='input__control']").click();
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().plusDays(8).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [class='input__control']").setValue(date);
        $("[data-test-id='name'] [class='input__control']").setValue("Фондю Что-Это");
        $("[data-test-id='phone'] [class='input__control']").setValue("");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(("[data-test-id='phone'].input_invalid .input__sub")).shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWithoutCheckBoxTick() {
        //open("http://localhost:9999");
        $("[data-test-id='city'] [class='input__control']").setValue("Петрозаводск");
        $("[data-test-id='date'] [class='input__control']").click();
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] [class='input__control']").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().plusDays(11).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] [class='input__control']").setValue(date);
        $("[data-test-id='name'] [class='input__control']").setValue("Айось Андройд");
        $("[data-test-id='phone'] [class='input__control']").setValue("+89012345678");
        $$("button").find(exactText("Забронировать")).click();
        String invalidColor = $("[data-test-id='agreement'] span.checkbox__text").getCssValue("color");
        assertEquals("rgba(255, 92, 92, 1)", invalidColor);
    }

    @Test
    void shouldBlinkButtonColorOnClick() {
        //open("http://localhost:9999");
        $$("button").find(exactText("Забронировать")).click();
        $$("button").find(exactText("Забронировать")).hover();
        String changedButtonColor = $("button:focus, button:hover").getCssValue("background-color");
        String changedButtonBorderColor = $("button:focus, button:hover").getCssValue("border-color");
        assertEquals("rgba(240, 50, 38, 1)", changedButtonColor);
        assertEquals("rgb(240, 50, 38)", changedButtonBorderColor);
    }
}
