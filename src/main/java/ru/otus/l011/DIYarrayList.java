package ru.otus.l011;

import java.util.*;

public class DIYarrayList<T> implements List<T> {

    private int currentSize;
    private int maximumSize;
    Object[] body;



    public DIYarrayList(int maxSize) {
        this.body = new Object[maxSize];
        maximumSize = maxSize;

        currentSize = 0;
    }

    @Override
    public T get(int i) {
        if (i > currentSize)
        {
            return null;
        }
else
        {
            return (T) body[i];
        }

    }

    @Override
    public boolean add(T t) {
                if( currentSize >= maximumSize)
                {throw new IllegalArgumentException("Array overflow");}
                body[currentSize] = t;
        currentSize++;
        return true;
    }

    public void print() {
        for (int i = 0; i < size(); i++) {
            System.out.println(get(i).toString());
        }
    }

    @Override
    public int size() {
        return currentSize;
    }

    public boolean setSize(int newSize) {
        if (newSize > maximumSize)
        {throw new IllegalArgumentException("Array overflow");}
        else
            currentSize = newSize;
        return true;
    }


    @Override
    public Object[] toArray() {

        return body;
    }



    @Override
    public boolean addAll(Collection<? extends T> collection) {

        for(int i = 0; i < collection.size(); i++) {
            add((T) collection.toArray()[i]);
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return (currentSize == 0);
    }



    @Override
    public T set(int i, T t) {
//        System.out.println("i="+i+"; currentsiz="+currentSize );
        if (i > currentSize)
        {throw new IllegalArgumentException("Array overflow");}
        else
        {body[i-1] = t;}
        return t;
    }

    @Override
    public Iterator<T> iterator() {
         return new DIYListItr(0);
    }

    public ListIterator<T> listIterator() {
        return  new DIYListItr(0);
    }

    private class DIYListItr  implements ListIterator<T> {
        int cursor;

        DIYListItr(int index) {
            super();
            this.cursor = index;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        public T next() {
            int i = this.cursor;
            if (i >= currentSize) {
                throw new NoSuchElementException();
            } else {
                this.cursor = i + 1;
                return (T) body[i];
            }
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public T previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(T t) {
                    DIYarrayList.this.set(this.cursor, t);
        }

        @Override
        public void add(T t) {
                            int i = this.cursor;
                DIYarrayList.this.set(i, t);
                this.cursor = i + 1;

            }
    }
            //--------------

    @Override
    public void add(int i, T t) {

    }

    @Override
    public <T1> T1[] toArray(T1[] t1s) {
        return null;
    }



    @Override
    public boolean contains(Object o) {
        return false;
    }





    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }



    @Override
    public boolean addAll(int i, Collection<? extends T> collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }



    @Override
    public void clear() {

    }





    @Override
    public T remove(int i) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        return null;
    }

    @Override
    public List<T> subList(int i, int i1) {
        return null;
    }

    public void sort() {
     System.out.println("HI");
    }
}
