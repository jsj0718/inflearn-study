package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl memberService = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(memberService);
        proxyFactory.setProxyTargetClass(false); //JDK 동적 프록시

        //프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
        assertThat(memberServiceProxy).isInstanceOf(MemberService.class);

        log.info("proxy class={}", memberServiceProxy.getClass());

        //JDK 동적 프록시를 구현 클래스로 캐스팅 시도 시 실패, ClassCastException 발생
        assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl memberServiceImplProxy = (MemberServiceImpl) proxyFactory.getProxy();
        });
    }

    @Test
    void cglibProxy() {
        MemberServiceImpl memberService = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(memberService);
        proxyFactory.setProxyTargetClass(true); //CGLIB 프록시

        //프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
        assertThat(memberServiceProxy).isInstanceOf(MemberService.class);

        log.info("proxy class={}", memberServiceProxy.getClass());

        //CGLIB 프록시를 구현 클래스로 캐스팅 시도 성공
        MemberServiceImpl memberServiceImplProxy = (MemberServiceImpl) proxyFactory.getProxy();
        assertThat(memberServiceImplProxy).isInstanceOf(MemberServiceImpl.class);

        log.info("proxy class={}", memberServiceImplProxy.getClass());
    }
}
