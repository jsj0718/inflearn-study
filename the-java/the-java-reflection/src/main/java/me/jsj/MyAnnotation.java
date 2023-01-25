package me.jsj;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Inherited
public @interface MyAnnotation {

    String value() default "생략 가능";

    String name() default "홍길동";

    int number() default 100;
}
