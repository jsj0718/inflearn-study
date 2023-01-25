package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheSubject;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest() {
        ProxyPatternClient client = new ProxyPatternClient(new CacheSubject(new RealSubject()));
        client.execute();
        client.execute();
        client.execute();
    }
}
