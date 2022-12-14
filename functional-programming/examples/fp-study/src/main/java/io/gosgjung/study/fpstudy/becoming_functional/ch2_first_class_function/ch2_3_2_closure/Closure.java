package io.gosgjung.study.fpstudy.becoming_functional.ch2_first_class_function.ch2_3_2_closure;

public class Closure {
    public String foo = "";

    public static Closure process(final Closure t){
        System.out.println(t.toString() + " = " + t.foo);
        t.foo = "bar";
        new Runnable(){
            @Override
            public void run() {
                System.out.println(t.toString() + " = " + t.foo);
                t.foo = "baz";
            }
        }.run();
        System.out.println(t.toString() + " = " + t.foo);
        return t;
    }

    public static void main(String [] args){
        process(new Closure());
    }
}
