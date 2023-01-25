package me.jsj;

import java.time.format.SignStyle;

public class BookV2 {

    public static String A = "A";

    private String b = "B";

    public BookV2() {
    }

    public BookV2(String b) {
        this.b = b;
    }

    private void c() {
        System.out.println("C");
    }

    public int sum(int left, int right) {
        return left + right;
    }
}
