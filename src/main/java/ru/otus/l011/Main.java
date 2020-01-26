package ru.otus.l011;


public class Main {
    private static final int MEASURE_COUNT = 1;

    public static void main(String... args) {

        DIYarrayList<Integer> ddd = new DIYarrayList<>();
        DIYarrayList<Integer> vvv = new DIYarrayList<>();

        ddd.add(1);
        ddd.add(2);
        ddd.add(3);

        vvv.addAll(ddd);


        ddd.sort();

        System.out.println(ddd.toString());
    }}
