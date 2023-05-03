package GUI.Controller;

import BE.Customer;
import BE.Job;
import GUI.MODEL.SalesMenModel;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SalesmenController implements Initializable {
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
    Customer selectedCustomer;



    public SalesmenController(){
        try {
            salesMenModel = SalesMenModel.getInstance();
        }
        catch (SQLException e) {
            alertUser("Experienced a problem collecting customer data from the database");
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showCustomers();
        showJobs();


        txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            Runnable task = () -> salesMenModel.searchCustomers(newValue);

            Thread thread = new Thread(task);
            thread.start();
        });

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedCustomer = newValue;
            try {
                tblJobs.setItems(salesMenModel.getJobsToBeViewed(selectedCustomer));
            } catch (SQLException e) {
                alertUser("There was a problem collecting the jobs");
                e.printStackTrace();
            }
        });
    }


    private void showJobs(){
        clmJobs.setCellValueFactory(new PropertyValueFactory<Job,String>("Title"));
    }

    private void showCustomers(){
        clmName.setCellValueFactory(new PropertyValueFactory<Customer,String>("Name"));
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
