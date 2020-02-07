import com.codeborne.selenide.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CardOrderServiceTest {

    @BeforeEach
    void InitialSetup() {
        open("http://localhost:9999");
    }

    @Test
    void ShouldSubmitForm() {
        SelenideElement form = $("form");
        form.$("input.input__control[name=name").setValue("Петров Иван");
        form.$("input.input__control[name=phone").setValue("+79210001234");
        form.$("span.checkbox__box").click();
        form.$("button").click();
        System.out.println($(".Success_successBlock__2L3Cw span").getText());
        $(".Success_successBlock__2L3Cw p").shouldHave(exactText("Ваша заявка успешно123 отправлена! Наш менеджер свяжется с вами в ближайшее время."));

    }

    @ParameterizedTest
    @CsvFileSource(resources = "testdata.csv",numLinesToSkip = 1,delimiter = ';',encoding = "Windows-1251")
    void ShouldAlertWhenInvalidInput(String name, String phone, String message_name, String message_phone) {
        SelenideElement form = $("form");
        form.$("input.input__control[name=name").setValue(name);
        form.$("input.input__control[name=phone").setValue(phone);

        form.$("span.checkbox__box").click();
        form.$("button").click();

        $("[data-test-id=name] span.input__sub").shouldHave(text(message_name));
        $("[data-test-id=phone] span.input__sub").shouldHave(text(message_phone));
    }
}
