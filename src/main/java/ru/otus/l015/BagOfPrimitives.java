package ru.otus.l015;

import java.util.ArrayList;
import java.util.HashSet;

public class BagOfPrimitives {
    private int valueInt;
    private String valueString;
    private boolean valueBoolean;
    private static final String valueString2 = "aaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private final String valueString3 = "aaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private static  String valueString4 = "aaaaaaaaaaaaaaaaaaaaaaaaaaa";

    private short valueShort;

    private byte valueByte;
    private float valueFloat;
    private double valueDouble;
    private long valueLong;
    private char valueChar;

    private ArrayList<Integer> arrayListInt;
    private ArrayList<String> arrayListString;
    private ArrayList<InternalClass> arrayListInternal;

    private HashSet<Integer> hashSetInt;
    private HashSet<String> hashSetString;
    private HashSet<InternalClass> hashSetInternal;

    private int[] arrayInt;
    private String[] arrayString;
    private InternalClass[] arrayInternal;

    BagOfPrimitives(int valueInt, String valueString, boolean valueBoolean,
                    short valueShort, byte valueByte, float valueFloat, double valueDouble, long valueLong, char valueChar) {
        this.valueInt = valueInt;
        this.valueString = valueString;
        this.valueBoolean = valueBoolean;
        this.valueShort = valueShort;
        this.valueByte = valueByte;
        this.valueFloat = valueFloat;
        this.valueDouble = valueDouble;
        this.valueLong = valueLong;
        this.valueChar = valueChar;

//Arrays
        arrayInt = new int[5];
        for (int i = 0; i < 5; i++) {
            arrayInt[i] = i;
        }

        arrayInternal = new InternalClass[3];
        for (int i = 0; i < 3; i++) {
            arrayInternal[i] = new InternalClass();
        }

        arrayString = new String[5];
        for (int i = 0; i < 5; i++) {
            arrayString[i] = Integer.toString(i);
        }

//ArrayLists
        arrayListInt = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            arrayListInt.add(i);
        }

        arrayListString = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            arrayListString.add(Integer.toString(i));
        }

        arrayListInternal = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            arrayListInternal.add(new InternalClass());
        }

//HashSets
        hashSetInt = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            hashSetInt.add(i);
        }

        hashSetString = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            hashSetString.add(Integer.toString(i));
        }

        hashSetInternal = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            hashSetInternal.add(new InternalClass());
        }
    }

    public void clearArrays() {
        arrayInt = new int[0];
        arrayInternal = new InternalClass[0];
        arrayString = new String[0];
        arrayListInt = new ArrayList<Integer>();
        arrayListString = new ArrayList<String>();
        arrayListInternal = new ArrayList<>();
        hashSetInt = new HashSet<>();
        hashSetString = new HashSet<>();
        hashSetInternal = new HashSet<>();
    }

    private class InternalClass {
        public int internalValueInt = 1;
        public String internalValueString = "2";
    }
}

