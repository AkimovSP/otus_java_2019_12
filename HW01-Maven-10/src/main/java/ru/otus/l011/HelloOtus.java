package ru.otus.l011;

import com.google.common.base.CharMatcher;

public class HelloOtus {
    public static void main(String... args) {

        System.out.println("HELLO OTUS");
        System.out.println("HELLO OTUS 2");

        String rock = "1, 2, 3 o'clock, 4 o'clock rock!";

        System.out.println(CharMatcher.digit().retainFrom(rock)); // "1234"
        System.out.println(CharMatcher.digit().removeFrom(rock)); // ", , o'clock, o'clock rock!"
        System.out.println(CharMatcher.digit().negate().removeFrom(rock)); // "1234"

        System.out.println("HELLO OTUS 4");
    }
}
