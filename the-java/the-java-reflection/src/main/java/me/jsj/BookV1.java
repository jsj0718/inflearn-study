package me.jsj;

import java.lang.annotation.Target;

//@MyAnnotation(name = "jsj", number = 27)
@MyAnnotation(value = "타입", name = "Book", number = 1)
public class BookV1 {

    private static String A = "a";

    private static final String B = "b";

    private String c = "c";

    @MyAnnotation(value = "필드", name = "Book", number = 2)
    public String d = "d";

    protected String e = "e";

    public BookV1() {
    }

    public BookV1(String c, String d, String e) {
        this.c = c;
        this.d = d;
        this.e = e;
    }

    @AnotherAnnotation
    private void f() {
        System.out.println("f");
    }

    public void g() {
        System.out.println("g");
    }

    public int h() {
        return 100;
    }
}
