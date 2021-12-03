package hello.typeconverter.formatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class MyNumberFormatterTest {

    MyNumberFormatter formatter = new MyNumberFormatter();

    @Test
    @DisplayName("숫자에서 문자로 변경한다.")
    void parse() {
        String result = formatter.print(1000000, Locale.KOREA);
        assertThat(result).isEqualTo("1,000,000");
    }


    @Test
    @DisplayName("문자에서 숫자로 변경한다.")
    void stringToNumber() throws ParseException {
        Number result = formatter.parse("1,000", Locale.KOREA);
        assertThat(result).isEqualTo(1000L); // Long 타입으로 반환
    }
}