package io.gosgjung.study.fpstudy.ch3_pure_function.ch3_2_make_pure.after;

import java.util.Calendar;

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

    public static void setContractEnabledForCustomer(Integer customer_id) {
        Customer.foreach(
            // arg 1)
            Customer.getCustomerById(Customer.allCustomers, customer_id),

            // arg 2)
            new ForeachCallee<Customer>() {
                @Override
                public void call(Customer customer) {
                    customer.contract.enabled = true;
                }
            }
        );
    }
}
