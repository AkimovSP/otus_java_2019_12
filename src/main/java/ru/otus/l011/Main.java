package ru.otus.l011;


import java.util.ArrayList;

public class Main {
    private static final int MEASURE_COUNT = 1;

    public static void main(String... args) {

        DIYarrayList<Integer> ddd = new DIYarrayList<Integer>(10);
        DIYarrayList<Integer> vvv = new DIYarrayList<Integer>(10);
        DIYarrayList<Integer> yyy = new DIYarrayList<Integer>(10);

        ddd.add(1);
        ddd.add(4);
        ddd.add(3);
        ddd.add(2);

        System.out.println("DDD");
        ddd.print();

        vvv.addAll(ddd);

        System.out.println("VVV");
        vvv.print();
        System.out.println("VVV - end");



            }}
