package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgsTest {

    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    private AspectJExpressionPointcut pointcut(String execution) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(execution); //pointcut의 expression은 한 번 설정된 후에는 변경할 수 없다.
        return pointcut;
    }

    @Test
    void args() {
        assertThat(pointcut("args(String)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(Object)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args()")
                .matches(helloMethod, MemberServiceImpl.class)).isFalse();
        assertThat(pointcut("args(..)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(*)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(String, ..)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * execution(* *(java.io.Serializable)) : 메서드의 시그니처로 판단 (정적)
     * args(java.io.Serializable) : 런타임에 전달된 인수로 판단 (동적)
     */
    void argsVsExecution() {
        //Args (객체 인스턴스가 넘어올 때 판단 후 부모 클래스, 인터페이스까지 허용)
        assertThat(pointcut("args(String)")
                        .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(java.io.Serializable)")
                        .matches(helloMethod, MemberServiceImpl.class)).isTrue(); //부모 타입 허용
        assertThat(pointcut("args(Object)")
                        .matches(helloMethod, MemberServiceImpl.class)).isTrue(); //최상위 타입 허용

        //Execution (정적인 정보를 통해 정확히 일치할 때 허용)
        assertThat(pointcut("execution(* *(String)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("execution(* *(java.io.Serializable)")
                .matches(helloMethod, MemberServiceImpl.class)).isFalse(); //매칭 X
        assertThat(pointcut("execution(* *(Object)")
                .matches(helloMethod, MemberServiceImpl.class)).isFalse(); //매칭 X

    }

}
