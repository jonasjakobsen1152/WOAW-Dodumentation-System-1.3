package GUI.Controller;

import BE.User;
import GUI.MODEL.ProjectManagerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectManagerController implements Initializable {
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView tblShowTechnicians;
    @FXML
    private TableColumn clmShowTechnicians;
    @FXML
    private TableView tblShowSalesmen;
    @FXML
    private TableColumn clmShowSalesmen;
    @FXML
    private TableView tblShowCustomers;
    @FXML
    private TableColumn clmShowCustomers;
    @FXML
    private TableView tblShowDocument;
    @FXML
    private TableColumn clmShowDocument;
    public ProjectManagerModel projectManagerModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTechnician();
    }

    public void handleOpenCreateUser(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/CreateUpdateUser.fxml"));
        try {
            AnchorPane pane = loader.load();

            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.show();
        }
        catch (IOException e) {
            alertUser("Error: Could not open the create user window");
        }
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public void handleAddWork(ActionEvent actionEvent) {
    }

    public void handleDeleteWork(ActionEvent actionEvent) {
    }

    public void handleDeleteTechnician(ActionEvent actionEvent) {
    }

    public void handleDeleteCustomer(ActionEvent actionEvent) {
    }

    public void handleDeleteSalesmen(ActionEvent actionEvent) {
    }

    public void handleReadJob(ActionEvent actionEvent) {
    }

    private void showTechnician(){
        clmShowTechnicians.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        tblShowTechnicians.setItems(projectManagerModel.getTechnicianToBeViewed());
    }
}
