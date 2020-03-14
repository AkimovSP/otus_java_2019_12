package ru.otus.l015;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Gson gson = new Gson();

        BagOfPrimitives obj = new BagOfPrimitives(22, "test", true, (short) 12, (byte) 15, 1.0F, 1.0D, 1L, '!');
        BagOfPrimitives obj2 = new BagOfPrimitives(22, "", true, (short) 12, (byte) 15, 1.0F, 1.0D, 1L, ' ');
        obj2.clearArrays()
        ;
        DiyGson gson2 = new DiyGsonImpl();

        System.out.println("Full object - DIYJson / Gson");
        System.out.println(gson2.toJson(obj));
        System.out.println(gson.toJson(obj));
        System.out.println("-----");
        System.out.println("Short object - DIYJson / Gson");
        System.out.println(gson2.toJson(obj2));
        System.out.println(gson.toJson(obj2));

//        BagOfPrimitives obj2 = gson.fromJson(json, BagOfPrimitives.class);
//        System.out.println(obj.equals(obj2));
//        System.out.println(obj2);
    }
}
