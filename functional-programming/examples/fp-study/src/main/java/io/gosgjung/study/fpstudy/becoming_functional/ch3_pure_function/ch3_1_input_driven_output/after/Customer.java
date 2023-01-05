package io.gosgjung.study.fpstudy.becoming_functional.ch3_pure_function.ch3_1_input_driven_output.after;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    static public ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    public Integer id = 0;
    public Boolean enabled = true;
    public Contract contract;

    public static ArrayList<Customer> getCustomerById(Integer customer_id){
        return Customer.filter(new Function1<Customer, Boolean>(){
            @Override
            public Boolean call(Customer customer) {
                return customer.id == customer_id;
            }
        });
    }
    
    public static ArrayList<Customer> filter(Function1<Customer, Boolean> test){
        ArrayList<Customer> outList = new ArrayList<>();
        for(Customer customer: Customer.allCustomers){
            if(test.call(customer)){
                outList.add(customer);
            }
        }
        return outList;
    }

    public static <B> List<B> getField(
            Function1<Customer, Boolean> test,
            Function1<Customer, B> func
    ){
        ArrayList<B> outList = new ArrayList<>();
        for(Customer customer: Customer.filter(test)){
            outList.add(func.call(customer));
        }
        return outList;
    }
}
