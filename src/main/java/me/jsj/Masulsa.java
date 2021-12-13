package me.jsj;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.pool.TypePool;

import java.io.File;
import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class Masulsa {
/*
    //V1 -> Moja.class를 클래스 로더에서 먼저 읽기 때문에 실행된 이후 바이트 코드가 조작된다.
    public static void main(String[] args) {
        try {
            new ByteBuddy().redefine(Moja.class)
                    .method(named("pullOut")).intercept(FixedValue.value("Rabbit!"))
                    .make().saveIn(new File("C:\\devtool\\spring-boot initializer\\thejava\\target\\classes"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(new Moja().pullOut());
    }
*/

/*
    //V2 (문자열에서 참조하도록 변경) -> sout 실행 전까지 Moja.class를 참조하지 않기 때문에 바이트 조작 이후 실행된다.
    //그러나 다른 파일에서 먼저 Moja.class를 읽었으면 적용되지 않는다.
    public static void main(String[] args) {
        ClassLoader classLoader = Masulsa.class.getClassLoader();
        TypePool typePool = TypePool.Default.of(classLoader);

        try {
            new ByteBuddy().redefine(
                    typePool.describe("me.jsj.Moja").resolve(),
                            ClassFileLocator.ForClassLoader.of(classLoader))
                    .method(named("pullOut")).intercept(FixedValue.value("Rabbit!"))
                    .make().saveIn(new File("C:\\devtool\\spring-boot initializer\\thejava\\target\\classes"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(new Moja().pullOut());
    }
*/
    public static void main(String[] args) {
        System.out.println(new Moja().pullOut());
    }
}
