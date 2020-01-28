package ru.otus.l011;

import java.util.Collections;

public class Main {
    private static final int MEASURE_COUNT = 1;

    public static void main(String... args) {

        DIYarrayList<Integer> ddd = new DIYarrayList<Integer>(20);
        DIYarrayList<Integer> vvv = new DIYarrayList<Integer>(20);
        DIYarrayList<Integer> yyy = new DIYarrayList<Integer>(20);

        ddd.add(1);
        ddd.add(4);
        ddd.add(3);
        ddd.add(2);
        ddd.add(5);
        ddd.add(6);
        ddd.add(8);
        ddd.add(7);
        ddd.add(10);
        ddd.add(9);
        ddd.add(12);
        ddd.add(11);
        ddd.add(14);
        ddd.add(13);
        ddd.add(16);
        ddd.add(15);
        ddd.add(20);
        ddd.add(19);
        ddd.add(18);
        ddd.add(17);

        System.out.println("Initial array - DDD");
        ddd.print();
        System.out.println("DDD - end");

//проверяем Collections.addAll(Collection<? super T> c, T... elements)
        System.out.println("проверяем Collections.addAll(Collection<? super T> c, T... elements)");
        vvv.addAll(ddd);

        System.out.println("VVV - массив, созданный методом AddAll");
        vvv.print();
        System.out.println("VVV - end");

        //проверяем Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
        System.out.println("проверяем Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)");
        yyy.setSize(vvv.size());
        Collections.copy(yyy, vvv);

        System.out.println("YYY - массив, созданный Collections.copy");
        yyy.print();
        System.out.println("YYY - end");

        //проверяем Collections.static <T> void sort(List<T> list, Comparator<? super T> c)
        System.out.println("проверяем Collections.static <T> void sort(List<T> list, Comparator<? super T> c)");
        Collections.sort(yyy);

        System.out.println("YYY - массив после сортировки");
        yyy.print();
        System.out.println("YYY after sort-  end");

        //Если метод не имплементирован, то он должен выбрасывать исключение UnsupportedOperationException.
        try {
            yyy.clear();
        } catch (UnsupportedOperationException e) {
            System.out.println("Поймали исключение UnsupportedOperationException");
        }
    }
}
