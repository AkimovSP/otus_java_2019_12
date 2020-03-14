package ru.otus.l009;

public class TestClass {
    @Before
    public void before1() {
        System.out.println("before1 running");
    }

    @Before
    public void before2() {
        System.out.println("before2 running");
    }

    @After
    public void after1() {
        System.out.println("after1 running");
    }

    @After
    public void after2() {
        System.out.println("after2 running");
    }

    @Test
    public void test1() {
        System.out.println("test1 running");
    }

    @Test
    public void test2() {
        System.out.println("test2 running");
    }

    @Test
    public void test3() throws Exception {
        System.out.println("test3 running");
        throw new Exception();
    }
}
