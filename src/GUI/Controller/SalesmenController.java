package GUI.Controller;

import BE.Customer;
import GUI.MODEL.SalesMenModel;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class SalesmenController {
    @FXML
    private TableView tblJobs;
    @FXML
    private TableColumn clmJobs;
    @FXML
    private TableView<Customer> tblCustomer;
    @FXML
    private TableColumn clmPhone;
    @FXML
    private TableColumn clmEmail;
    @FXML
    private TableColumn clmName;
    @FXML
    private MFXTextField txtFilter;
    SalesMenModel salesMenModel;

    public SalesmenController(){
        try {
            salesMenModel = SalesMenModel.getInstance();
        }
        catch (SQLException e) {
            alertUser("Experienced a problem collecting customer data from the database");
        }

        showCustomers();
    }

    private void showCustomers(){
        clmName.setCellValueFactory(new PropertyValueFactory<Customer,String>("name"));
        clmPhone.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("Phone"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<Customer,String>("Email"));
        tblCustomer.setItems(salesMenModel.getCustomerToBeViewed());
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

}
