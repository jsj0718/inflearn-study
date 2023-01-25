package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterTest {

    @Test
    void stringToInteger() {
        //given
        StringToIntegerConverter converter = new StringToIntegerConverter();

        //when
        Integer result = converter.convert("10");

        //then
        assertThat(result).isInstanceOf(Integer.class);
        assertThat(result).isEqualTo(10);
    }

    @Test
    void integerToString() {
        //given
        IntegerToStringConverter converter = new IntegerToStringConverter();

        //when
        String result = converter.convert(10);
        //then
        assertThat(result).isInstanceOf(String.class);
        assertThat(result).isEqualTo("10");
    }

    @Test
    void stringToIpPort() {
        // given
        StringToIpPortConverter converter = new StringToIpPortConverter();

        // when
        IpPort result = converter.convert("127.0.0.1:8080");

        // then
        assertThat(result).isEqualTo(new IpPort("127.0.0.1", 8080));
    }

    @Test
    void ipPortToString() {
        // given
        IpPortToStringConverter converter = new IpPortToStringConverter();
        IpPort ipPort = new IpPort("127.0.0.1", 8080);

        // when
        String result = converter.convert(ipPort);

        // then
        assertThat(result).isEqualTo("127.0.0.1:8080");
    }
}
