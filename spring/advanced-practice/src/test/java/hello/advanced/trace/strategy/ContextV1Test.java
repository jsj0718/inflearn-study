package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    @Test
    void strategyV0() {
        logic1();
        logic2();
    }

    @Test
    void logic1() {
        long startTimeMs = System.currentTimeMillis();
        // 비즈니스 로직 실행
        log.info("비즈니스 로직1");
        // 비즈니스 로직 종료
        long endTimeMs = System.currentTimeMillis();
        long resultTimeMs = endTimeMs - startTimeMs;
        log.info("resultTimeMs={}", resultTimeMs);
    }

    @Test
    void logic2() {
        long startTimeMs = System.currentTimeMillis();
        // 비즈니스 로직 실행
        log.info("비즈니스 로직2");
        // 비즈니스 로직 종료
        long endTimeMs = System.currentTimeMillis();
        long resultTimeMs = endTimeMs - startTimeMs;
        log.info("resultTimeMs={}", resultTimeMs);
    }

    /**
     * 전략 패턴 사용 (선 조립, 후 실행)
     */
    @Test
    void strategyV1() {
        //조립
        Strategy strategy1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategy1);
        //실행
        context1.execute();

        //조립
        Strategy strategy2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategy2);
        //실행
        context2.execute();
    }

    /**
     * 익명 클래스 사용
     */
    @Test
    void strategyV2() {
        Strategy strategy1 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1");
            }
        };
        ContextV1 context1 = new ContextV1(strategy1);
        log.info("strategyLogic1={}", strategy1.getClass());
        context1.execute();

        Strategy strategy2 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2");
            }
        };
        ContextV1 context2 = new ContextV1(strategy2);
        log.info("strategyLogic2={}", strategy2.getClass());
        context2.execute();
    }

    /**
     * 인라인을 활용한 익명 클래스 활용
     */
    @Test
    void strategyV3() {
        ContextV1 context1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1");
            }
        });
        context1.execute();

        ContextV1 context2 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2");
            }
        });
        context2.execute();
    }

    /**
     * 람다식 활용 (인터페이스의 메서드가 한 개만 있어야 한다.)
     */
    @Test
    void strategyV4() {

        ContextV1 context1 = new ContextV1(() -> log.info("비즈니스 로직1"));
        context1.execute();

        ContextV1 context2 = new ContextV1(() -> log.info("비즈니스 로직2"));
        context2.execute();
    }
}
