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

public class CustomerController implements Initializable {
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnCreate;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtName;
    public CustomerModel customerModel;
    public CustomerController customerController;

    public CustomerController(){
        customerModel = CustomerModel.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleCreateCustomer(ActionEvent actionEvent) throws SQLException {
        String name = txtName.getText();
        int phone = Integer.parseInt(txtPhoneNumber.getText());
        String email = txtEmail.getText();
        try {
            customerModel.createCustomer(name, phone, email);
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e){
            e.printStackTrace();
            alertUser("Could not create Customer");
        }
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public void handleUpdateCustomer(ActionEvent actionEvent) {
        String name = txtName.getText();
        int phone = Integer.parseInt(txtPhoneNumber.getText());
        String email = txtEmail.getText();
        Customer customer = new Customer(customerModel.getSelectedCustomer().getId(),name,phone,email);
        try {
            customerModel.updateCustomer(customer);
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e){
            e.printStackTrace();
            alertUser("Could not update Customer");
        }
    }


    public void setupUpdate(Customer selectedCustomer) {
        btnCreate.setVisible(false);
        txtName.setText(selectedCustomer.getName());
        txtEmail.setText(selectedCustomer.getEmail());
        txtPhoneNumber.setText(selectedCustomer.getPhone()+"");
    }
}
