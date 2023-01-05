package io.gosgjung.study.fpstudy.becoming_functional.ch3_pure_function.ch3_3_side_effect.before;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    static public ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    public Integer id = 0;
    public Boolean enabled = true;
    public Contract contract;

    public String name = "";
    public String address = "";
    public String state = "";
    public String primaryContact = "";
    public String domain = "";

    public Customer(){}

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

    public static <B> List<B> getField(
            Function1<Customer, Boolean> test,
            Function1<Customer, B> func
    ){
        ArrayList<B> outList = new ArrayList<B>();
        for(Customer customer: Customer.filter(Customer.allCustomers, test)){
            outList.add(func.call(customer));
        }
        return outList;
    }

    public static ArrayList<Customer> getCustomerById(
            ArrayList<Customer> inList,
            final Integer customer_id
    ){
        return Customer.filter(
                inList,
                new Function1<Customer, Boolean>(){
                    // 람다로 바꿀수도 있지만,
                    // 예제를 설명하기 위해서는 함수의 원형이 모두 보이는 익명 객체를 인라인으로 생성하는 방식을 선택.
                    @Override
                    public Boolean call(Customer customer) {
                        return customer.id == customer_id;
                    }
                }
        );
    }

    public static void foreach(
            ArrayList<Customer> inList,
            ForeachCallee<Customer> callee
    ){
        for(Customer customer : inList){
            callee.call(customer);
        }
    }

    public Customer setCustomerId(Integer customer_id) {
        this.id = customer_id;
        return this;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public Customer setState(String state) {
        this.state = state;
        return this;
    }

    public Customer setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public Customer setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Customer setContract(Contract contract) {
        this.contract = contract;
        return this;
    }
    
}
