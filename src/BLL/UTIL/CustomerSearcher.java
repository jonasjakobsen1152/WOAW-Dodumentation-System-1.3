package BLL.UTIL;

import BE.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerSearcher {
    public List<Customer> search(ArrayList<Customer> searchBase, String query) {
        List<Customer> searchResult = new ArrayList<>();

        for (Customer customer: searchBase) {
            if(compareToCustomerName(query,customer) || compareToCustomerPhoneNumber(query,customer) || compareToCustomerEmail(query,customer)){
                searchResult.add(customer);
            }
        }

        return searchResult;
    }

    private boolean compareToCustomerName(String query, Customer customer){
        return customer.getName().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToCustomerPhoneNumber(String query, Customer customer){
        return Integer.toString(customer.getPhone()).contains(query);
    }

    private boolean compareToCustomerEmail(String query, Customer customer){
        return customer.getEmail().toLowerCase().contains(query.toLowerCase());
    }
}
