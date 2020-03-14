package ru.otus.l015;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Gson gson = new Gson();
        DiyGson gson2 = new DiyGsonImpl();

        BagOfPrimitives obj = new BagOfPrimitives(22, "test", true, (short) 12, (byte) 15, 1.0F, 1.0D, 1L, '!');
        BagOfPrimitives obj2 = new BagOfPrimitives(22, "", true, (short) 12, (byte) 15, 1.0F, 1.0D, 1L, ' ');
        obj2.clearArrays();

        System.out.println("Full object - DIYJson / Gson");
        String objJson2 = gson2.toJson(obj);
        System.out.println(objJson2);
        String objJson1 = gson.toJson(obj);
        System.out.println(objJson1);
        System.out.println(objJson1.equals(objJson2));

        System.out.println("-----");

        System.out.println("Short object - DIYJson / Gson");
        objJson2 = gson2.toJson(obj2);
        System.out.println(objJson2);
        objJson1 = gson.toJson(obj2);
        System.out.println(objJson1);
        System.out.println(objJson1.equals(objJson2));
    }
}
