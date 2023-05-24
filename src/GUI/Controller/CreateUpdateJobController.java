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

/**
 * This class is used to control the create update job window.
 */
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

    /**
     * This method is used to create a new job.
     * @param actionEvent
     */
    public void handleCreateJob(ActionEvent actionEvent) {
        if (txtTitle.getText().isEmpty()){
            alertUser("Write something in the text field to create a job");
        }else {
            String title = txtTitle.getText();
            User selectedTechnician = createUpdateJobModel.getSelectedTechnician();
            if (selectedCustomer == null) {
                alertUser("Select a customer from the list");
            } else {
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
    }

    /**
     * This method is used to set the values of the table view.
     */
    private void showCustomer() {
        clmName.setCellValueFactory(new PropertyValueFactory<Customer, String>("Name"));
        clmPhone.setCellValueFactory(new PropertyValueFactory<Customer,String>("Phone"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<Customer,String>("Email"));
        tblCustomers.setItems(createUpdateJobModel.getCustomerToBeViewed());
    }




}
