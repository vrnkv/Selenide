import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
public class RegistrationCardTest {

    String generateDate(int daysToAdd, String pattern) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }
    @Test
    void FirstTest() {
        String date = generateDate(5, "dd.MM.yyyy");
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");

       $(byText("Москва")).shouldBe(Condition.visible).click();

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Виктор Плюшкин");
        $("[data-test-id=phone] input").setValue("+79211234567");
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(Condition.visible, Duration.ofMillis(15000));
        $x("//*[@class='notification__content']").shouldHave(Condition.exactText("Встреча успешно забронирована на " + date), Duration.ofMillis(15000));
    }

}
