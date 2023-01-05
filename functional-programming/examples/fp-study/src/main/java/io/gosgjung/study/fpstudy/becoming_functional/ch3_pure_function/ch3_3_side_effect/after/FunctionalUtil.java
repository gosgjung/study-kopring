package io.gosgjung.study.fpstudy.becoming_functional.ch3_pure_function.ch3_3_side_effect.after;

import java.util.ArrayList;
import java.util.List;

public class FunctionalUtil {
    public FunctionalUtil(){}

    public static <A1, B> List<B> mapInNewList(List<A1> inList, Function1<A1, B> func){
        ArrayList<B> outList = new ArrayList<>();

        for(A1 obj : inList){
            outList.add(func.call(obj));
        }

        return outList;
    }

    public static ArrayList<Customer> filter(
            ArrayList<Customer> inList,
            Function1<Customer, Boolean> test){
        ArrayList<Customer> outList = new ArrayList<>();

        for(Customer customer: inList){
            if(test.call(customer)){
                outList.add(customer);
            }
        }

        return outList;
    }

    public static void foreach(
            ArrayList<Customer> inList,
            ForeachCallee<Customer> callee
    ){
        for(Customer customer : inList){
            callee.call(customer);
        }
    }
}
