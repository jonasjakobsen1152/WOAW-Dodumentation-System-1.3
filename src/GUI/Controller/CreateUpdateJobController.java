package GUI.Controller;

import BE.Customer;
import BE.User;
import GUI.MODEL.CreateUpdateJobModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateUpdateJobController implements Initializable {

    public TextField txtTitle;
    public Label lblUser;
    public CreateUpdateJobModel createUpdateJobModel;
    public TableColumn clmName;
    public TableColumn clmPhone;
    public TableColumn clmEmail;
    public TableView<Customer> tblCustomers;
    public Customer selectedCustomer;

    public CreateUpdateJobController() throws SQLException {
        createUpdateJobModel = CreateUpdateJobModel.getInstance();
        //lblUser.setText(createUpdateJobModel.getSelectedTechnician().getUsername());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showCustomer();
        tblCustomers.setOnMouseClicked(event -> {
            selectedCustomer = tblCustomers.getSelectionModel().getSelectedItem();

        });
    }
    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public void handleCreateJob(ActionEvent actionEvent) throws SQLException {
        String title = txtTitle.getText();
        User selectedTechnician = createUpdateJobModel.getSelectedTechnician();
        if (selectedCustomer == null){
            alertUser("Select a customer from the list");
        }else {
            try {
                createUpdateJobModel.createJob(title, selectedTechnician, selectedCustomer);
            } catch (SQLException e) {
                e.printStackTrace();
                alertUser("Cant create job");
            }
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    private void showCustomer() {
        clmName.setCellValueFactory(new PropertyValueFactory<Customer, String>("Name"));
        clmPhone.setCellValueFactory(new PropertyValueFactory<Customer,String>("Phone"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<Customer,String>("Email"));
        tblCustomers.setItems(createUpdateJobModel.getCustomerToBeViewed());
    }




}
