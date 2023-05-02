package GUI.MODEL;

import BE.Customer;
import BE.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;

import java.util.ArrayList;

public class SalesMenModel {

    private static SalesMenModel instance;

    private ObservableList<Customer> customerToBeViewed;
    private SalesMenModel(){
        CustomerModel customerModel = CustomerModel.getInstance();

        customerToBeViewed = FXCollections.observableArrayList();
        customerToBeViewed.addAll(customerModel.getCustomerToBeViewed());
    }

    public static SalesMenModel getInstance(){
        if(instance == null){
            instance = new SalesMenModel();
        }
            return instance;
    }

    public ObservableList<Customer> getCustomerToBeViewed() {
        return customerToBeViewed;
    }
}
