package io.gosgjung.study.fpstudy.ch3_pure_function.ch3_3_side_effect.after;

import java.util.Calendar;
import java.util.List;

public class Contract {
    public Calendar begin_date;
    public Calendar end_date;
    public Boolean enabled = true;

    public Contract(Calendar begin_date) {
        this.begin_date = begin_date;
        this.end_date = this.begin_date.getInstance();
        this.end_date.setTimeInMillis(this.begin_date.getTimeInMillis());
        this.end_date.add(Calendar.YEAR, 2);
    }

    public Contract setBeginDate(Calendar begin_date) {
        this.begin_date = begin_date;
        return this;
    }

    public Contract setEndDate(Calendar end_date) {
        this.end_date = end_date;
        return this;
    }

    public Contract setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public static List<Contract> setContractEnabledForCustomer(Integer customer_id, Boolean status) {
        return Customer.mapInNewList(
                Customer.getCustomerById(Customer.allCustomers, customer_id),
                new Function1<Customer, Contract>() {
                    @Override
                    public Contract call(Customer customer) {
                        return customer.contract.setEnabled(status);
                    }
                }
        );
    }
}
