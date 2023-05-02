package GUI.Controller;

import BE.Customer;
import GUI.MODEL.SalesMenModel;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        salesMenModel = SalesMenModel.getInstance();

        showCustomers();
    }

    private void showCustomers(){
        clmName.setCellValueFactory(new PropertyValueFactory<Customer,String>("Name"));
        clmPhone.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("Phone"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<Customer,String>("Email"));
        tblCustomer.setItems(salesMenModel.getCustomerToBeViewed());
    }



}
