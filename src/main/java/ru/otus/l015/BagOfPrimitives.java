package ru.otus.l015;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class BagOfPrimitives {
    private final int valueInt;
    private final String valueString;
    private final boolean valueBoolean;

    private final short valueShort;

    private final byte valueByte;
    private final float valueFloat;
    private final double valueDouble;
    private final long valueLong;
    private final char valueChar;

    private final ArrayList<Integer> arrayListInt;
    private final ArrayList<String> arrayListString;
    private final ArrayList<InternalClass> arrayListInternal;

    private final HashSet<Integer> hashSetInt;
    private final HashSet<String> hashSetString;
    private final HashSet<InternalClass> hashSetInternal;

    private final int[] arrayInt;
    private final String[] arrayString;
    private final InternalClass[] arrayInternal;


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

    /*@Override
    public String toString() {
        return "BagOfPrimitives{" +
                "value1=" + value1 +
                ", value2='" + value2 + '\'' +
                ", value3=" + value3 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BagOfPrimitives that = (BagOfPrimitives) o;
        return value1 == that.value1 &&
                value3 == that.value3 &&
                Objects.equals(value2, that.value2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2, value3);
    }
*/
    private class InternalClass {
        public int internalValueInt = 1;
        public String internalValueString = "2";
    }
}

