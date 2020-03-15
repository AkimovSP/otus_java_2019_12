package ru.otus.l015;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Gson gson = new Gson();
        DiyGson gson2 = new DiyGsonImpl();
        String objJson1 = "";
        String objJson2 = "";

        BagOfPrimitives obj = new BagOfPrimitives(22, "test", true, (short) 12, (byte) 15, 1.0F, 1.0D, 1L, '!');
        BagOfPrimitives obj2 = new BagOfPrimitives(22, "", true, (short) 12, (byte) 15, 1.0F, 1.0D, 1L, ' ');
        obj2.clearArrays();

        System.out.println("Full object - DIYJson / Gson");
        objJson2 = gson2.toJson(obj);
        System.out.println(objJson2);
        objJson1 = gson.toJson(obj);
        System.out.println(objJson1);
        System.out.println(objJson1.equals(objJson2));

        System.out.println("-----");

        System.out.println("Short object - DIYJson / Gson");
        objJson2 = gson2.toJson(obj2);
        System.out.println(objJson2);
        objJson1 = gson.toJson(obj2);
        System.out.println(objJson1);
        System.out.println(objJson1.equals(objJson2));

        System.out.println("-----");
        System.out.println("Null -  Gson / DIYJson");
        objJson1 = gson.toJson(null);
        System.out.println(objJson1);
        objJson2 = gson2.toJson(null);
        System.out.println(objJson2);
        System.out.println(objJson1.equals(objJson2));

        System.out.println("-----");
        System.out.println("(byte) 1 -  Gson / DIYJson");
        objJson1 = gson.toJson((byte) 1);
        System.out.println(objJson1);
        objJson2 = gson2.toJson((byte) 1);
        System.out.println(objJson2);
        System.out.println(objJson1.equals(objJson2));

        System.out.println("-----");
        System.out.println("(short) 2 -  Gson / DIYJson");
        objJson1 = gson.toJson((short) 2f);
        System.out.println(objJson1);
        objJson2 = gson2.toJson((short) 2f);
        System.out.println(objJson2);
        System.out.println(objJson1.equals(objJson2));

        System.out.println("-----");
        System.out.println("3 -  Gson / DIYJson");
        objJson1 = gson.toJson(3);
        System.out.println(objJson1);
        objJson2 = gson2.toJson(3);
        System.out.println(objJson2);
        System.out.println(objJson1.equals(objJson2));

        System.out.println("-----");
        System.out.println("Long 4 -  Gson / DIYJson");
        objJson1 = gson.toJson(4L);
        System.out.println(objJson1);
        objJson2 = gson2.toJson(4L);
        System.out.println(objJson2);
        System.out.println(objJson1.equals(objJson2));

        System.out.println("-----");
        System.out.println("Float 5 -  Gson / DIYJson");
        objJson1 = gson.toJson(5f);
        System.out.println(objJson1);
        objJson2 = gson2.toJson(5f);
        System.out.println(objJson2);
        System.out.println(objJson1.equals(objJson2));

        System.out.println("-----");
        System.out.println("Double 6 -  Gson / DIYJson");
        objJson1 = gson.toJson(6d);
        System.out.println(objJson1);
        objJson2 = gson2.toJson(6d);
        System.out.println(objJson2);
        System.out.println(objJson1.equals(objJson2));

        System.out.println("-----");
        System.out.println("String aaa -  Gson / DIYJson");
        objJson1 = gson.toJson("aaa");
        System.out.println(objJson1);
        objJson2 = gson2.toJson("aaa");
        System.out.println(objJson2);
        System.out.println(objJson1.equals(objJson2));

        System.out.println("-----");
        System.out.println("Char b -  Gson / DIYJson");
        objJson1 = gson.toJson('b');
        System.out.println(objJson1);
        objJson2 = gson2.toJson('b');
        System.out.println(objJson2);
        System.out.println(objJson1.equals(objJson2));

        System.out.println("-----");
        System.out.println("Array int  -  Gson / DIYJson");
        objJson1 = gson.toJson(new int[]{7, 8, 9});
        System.out.println(objJson1);
        objJson2 = gson2.toJson(new int[]{7, 8, 9});
        System.out.println(objJson2);
        System.out.println(objJson1.equals(objJson2));

        System.out.println("-----");
        System.out.println("List int  -  Gson / DIYJson");
        objJson1 = gson.toJson(List.of(10, 11, 12));
        System.out.println(objJson1);
        objJson2 = gson2.toJson(List.of(10, 11, 12));
        System.out.println(objJson2);
        System.out.println(objJson1.equals(objJson2));

        System.out.println("-----");
        System.out.println("Singleton List -  Gson / DIYJson");
        objJson1 = gson.toJson(Collections.singletonList(13));
        System.out.println(objJson1);
        objJson2 = gson2.toJson(Collections.singletonList(13));
        System.out.println(objJson2);
        System.out.println(objJson1.equals(objJson2));
    }
}
