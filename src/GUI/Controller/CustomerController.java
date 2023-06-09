package GUI.Controller;

import BE.Customer;
import GUI.MODEL.CustomerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class is used to control the customer window
 */
public class CustomerController implements Initializable {
    public TextField txtAddress;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnCreate;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtPhoneNumber;
    @FXML
    public TextField txtName;
    public CustomerModel customerModel;
    public CustomerController customerController;

    public CustomerController(){
        try {
            customerModel = CustomerModel.getInstance();
        }catch (SQLException e){
            alertUser("Could not get the customer list");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * This method is used to create a new customer.
     * @param actionEvent
     */
    public void handleCreateCustomer(ActionEvent actionEvent) {
        //Checks if the text fields are empty, if they are tell the user to write something in them.
        if(txtName.getText().isEmpty() || txtPhoneNumber.getText().isEmpty() || txtEmail.getText().isEmpty()|| txtAddress.getText().isEmpty()) {
            alertUser("Please fill out the information about the customer");
        } else {
            String name = txtName.getText();
            int phone = Integer.parseInt(txtPhoneNumber.getText());
            String email = txtEmail.getText();
            String address = txtAddress.getText();
            try {
                customerModel.createCustomer(name, phone, email, address);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                alertUser("Could not create Customer");
            }
        }
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    /**
     * This method is used to edit/update a customer.
     * @param actionEvent
     */
    public void handleUpdateCustomer(ActionEvent actionEvent) {
        //Checks if the text fields are empty, if they are tell the user to write something in them.
        if(txtName.getText().isEmpty() || txtPhoneNumber.getText().isEmpty() || txtEmail.getText().isEmpty()|| txtAddress.getText().isEmpty()) {
            alertUser("Please fill out the information about the customer");
        } else {
            String name = txtName.getText();
            int phone = Integer.parseInt(txtPhoneNumber.getText());
            String email = txtEmail.getText();
            String address = txtAddress.getText();
            Customer customer = new Customer(customerModel.getSelectedCustomer().getId(), name, phone, email, address);
            try {
                customerModel.updateCustomer(customer);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                alertUser("Could not update Customer");
            }
        }
    }

    /**
     * Sets the text in the text field to the selected customers values.
     * @param selectedCustomer
     */
    public void setupUpdate(Customer selectedCustomer) {
        btnCreate.setVisible(false);
        txtName.setText(selectedCustomer.getName());
        txtEmail.setText(selectedCustomer.getEmail());
        txtPhoneNumber.setText(selectedCustomer.getPhone()+"");
        txtAddress.setText(selectedCustomer.getAddress());
    }

    public void removeUpdate() {
        btnUpdate.setVisible(false);
    }
}
