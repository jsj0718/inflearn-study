package me.jsj.inflearnthejavatest;

import jdk.jfr.Enabled;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/**
 * @DisplayNameGeneration
 * 1. 테스트 이름 표기 방법 설정 (e.x _를 생략 가능)
 * 2. 클래스, 메서드에 적용 가능하며 클래스에 적용하면 모든 메서드에 적용된다.
 */
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ExtendWith(FindSlowTestExtension.class) //확장 방법 1. 선언적 등록 (커스터마이징 불가능)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //@Order으로 순서 지정 가능 (JUnit5)
class StudyTest {

    //확장 방법 2. 프로그래밍 등록 (생성자를 통해 원하는 기준값 설정 가능)
    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(2000L);

    //모든 테스트 실행 이전 딱 1번 실행
    @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
    }

    //모든 테스트 실행 이후 딱 1번 실행
    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    //각 테스트 실행 이전 1번 실행
    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    //각 테스트 실행 이후 1번 실행
    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }

    /**
     * @DisplayName
     * 1. 원하는 문자열을 넣을 수 있다.
     * 2. 이모티콘도 사용 가능
     * 3. 테스트 이름으로 테스트를 표현할 때 권장되는 방법
     *
     * 테스트 작성 Tip
     * 1. 테스트 실패 시 메세지를 남겨두면 시간이 지난 후에 쉽게 디버깅이 가능하다.
     * 2. 첫 번째 파라미터 : expected 값, 두 번째 파라미터 : actual 값
     * 3. 람다식을 사용하면 필요한 순간에만 연산을 진행한다. (문자열로 넣으면 반드시 연산이 진행된다.)
     * 4. assertAll을 통해 여러 개의 테스트 결과를 한 번에 실행하고 볼 수 있다. (아닐 경우 앞에서 테스트가 오류 발생 시 뒤에 있는 테스트는 검증 불가능)
     * 5. 발생한 exception을 받아 예외 메세지 확인 가능
     * 6. assertTimeout은 테스트가 실패해도 해당 코드가 종료될 때까지 대기해야 되지만, asserTimeoutPreemptively는 테스트 실패와 동시에 종료된다.
     * (단, ThreadLocal을 사용하는 코드인 경우 원치 않는 결과가 발생할 수도 있기 떄문에 주의해서 사용해야 한다. -> Thread와 관련 없으면 괜찮다.)
     */
    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE31")
    void create_new_study() {
        Study study = new Study(10);
//        assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 DRAFT 상태다."),
                () -> assertTrue(1 < 2),
                () -> assertTrue(study.getLimit() > 0, () -> "스터디 최대 참석 인원은 0보다 커야 한다.")
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        String message = exception.getMessage();
        assertEquals("limit은 0보다 커야 한다.", message);

        //시간 측정
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            new Study(10);
//            Thread.sleep(1000);
        });

        //assertJ 사용 예시
        assertThat(new Study(10).getLimit()).isGreaterThan(0);
    }

    @Test
//    @Disabled // 테스트 무시
    void create_new_study_again() {
        System.out.println("create1");
    }
    
    @Test
    @DisplayName("환경변수에 따른 테스트 실행 방법")
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL") //assumeTrue 코드 대신 Annotation으로 설정 가능
    void envConditionTest() {
        //TEST_ENV라는 환경변수가 LOCAL인 경우에만 아래 테스트 진행
        String test_env = System.getenv("TEST_ENV");
//        assumeTrue("LOCAL".equalsIgnoreCase(test_env));

        //조건에 따라 어떤 테스트를 진행할 지 정할 수 있다.
        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            Study study = new Study(100);
            assertThat(study.getLimit()).isGreaterThan(0);
        });

        assumingThat("jsj".equalsIgnoreCase(test_env), () -> {
            Study study = new Study(10);
            assertThat(study.getLimit()).isGreaterThan(0);
        });
    }

    @Test
    @EnabledOnOs({OS.MAC, OS.LINUX})
    void macConditionTest() {
        System.out.println("mac os 환경에서 Test");
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    @Disabled
    void windowConditionTest() {
        System.out.println("window os 환경에서 Test");
    }

    @Test
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_11})
    void javaVersionTest1() {
        System.out.println("java 8/9/10/11에서 Test");
    }

    @Test
    @EnabledOnJre(JRE.OTHER)
    void javaVersionTest2() {
        System.out.println("상수로 정의된 버전 이외의 버전에서 Test");
    }

    /**
     * @Tag를 통해서 테스트를 그룹화하고, 원하는 그룹만 테스트가 가능하다.
     * 가정)
     * 1. fast 그룹은 Local에서 테스트하기에 부담이 없다. slow 그룹은 Local에서 테스트 하기에 부담이 된다.
     * 2. 따라서 fast 그룹만 따로 테스트 하기를 원한다.
     */
    @FastTest
    void tagTest1() {
        System.out.println("fast tag test");
    }

    @SlowTest
    void tagTest2() {
        System.out.println("slow tag test");
    }

    @DisplayName("스터디 만들기")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("test " + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName}, message={0} ")
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요"})
//    @EmptySource
//    @NullSource
    @NullAndEmptySource
    void parameterizedTest(String message) {
        System.out.println(message);
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName}, message={0} ")
    @ValueSource(ints = {10, 20, 30})
    void parameterConvertTest(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());
    }

    @ParameterizedTest(name = "{index} {displayName}, message={0} ")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    @Order(4)
    void csvSourceTest1(Integer limit, String name) {
        Study study = new Study(limit, name);
        System.out.println(study);
    }

    @ParameterizedTest(name = "{index} {displayName}, message={0} ")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    @Order(3)
    void csvSourceTest2(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }

    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException {
            assertThat(Study.class).isEqualTo(aClass);
            return new Study(Integer.parseInt(o.toString()));
        }
    }

    //제약조건 : 1. static class로 Aggregator를 선언해야 한다. 2. public class로 만들어진 클래스 타입이여야 한다.
    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        }
    }

    /**
     * JUnit은 테스트 메서드마다 각각의 인스턴스를 생성하기 때문에 test1과 test2의 value값이 모두 1이며, 객체 주소값도 서로 다르다.
     */
    int value = 1;

    @FastTest
    @Order(1)
    void test1() {
        System.out.println(this);
        System.out.println(value++);
    }

    @Test
    @Order(2)
    void test2() throws InterruptedException {
        System.out.println(this);
        System.out.println(value++);
        Thread.sleep(1005);
    }
}