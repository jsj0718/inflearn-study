package hello.itemservice.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MessageSourceTest {

    @Autowired
    MessageSource messageSource;

    @Test
    @DisplayName("locale이 null인 경우 messages.properties 내용으로 메세지 처리가 진행된다.")
    void helloMessage() {
        String result = messageSource.getMessage("hello", null, null);

        assertThat(result).isEqualTo("안녕");
    }

    @Test
    @DisplayName("messages.properties에 없는 내용인 경우 NoSuchMessageException이 발생한다.")
    void notFoundMessageCode() {
        assertThatThrownBy(() -> messageSource.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    @DisplayName("messages.properties에 없는 내용이지만 default값 설정을 하면 그 값이 나온다.")
    void notFoundMessageCodeDefaultMessage() {
        String result = messageSource.getMessage("no_code", null, "기본 메세지", null);
        assertThat(result).isEqualTo("기본 메세지");
    }

    @Test
    @DisplayName("argument 테스트 (타입은 Object[]로 넘겨야 한다)")
    void argumentMessage() {
        String result = messageSource.getMessage("hello.name", new Object[]{"Spring!"}, null);
        assertThat(result).isEqualTo("안녕 Spring!");
    }

    @Test
    void defaultLanguage() {
        assertThat(messageSource.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(messageSource.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    void enLanguage() {
        assertThat(messageSource.getMessage("hello", null, Locale.US)).isEqualTo("hello");
        assertThat(messageSource.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }
}
