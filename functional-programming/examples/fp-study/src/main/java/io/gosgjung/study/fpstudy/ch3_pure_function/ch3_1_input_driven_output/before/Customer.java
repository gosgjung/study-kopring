package io.gosgjung.study.fpstudy.ch3_pure_function.ch3_1_input_driven_output.before;

import java.util.ArrayList;

public class Customer {

    static public ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    public Integer id = 0;
    public Boolean enabled = true;
    public Contract contract;

    public static Customer getCustomerById(Integer customer_id){
        for(Customer customer : Customer.allCustomers){
            if(customer.id == customer_id){
                return customer;
            }
        }
        return null;
    }

}
