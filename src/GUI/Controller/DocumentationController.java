package GUI.Controller;

import BE.Documentation;
import BLL.UTIL.BCrypt;
import GUI.MODEL.DocumentationModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;

public class DocumentationController {
    public TextArea txtaPublicInformation;
    public TextArea txtaPrivateinformation;
    public TextField txtfDocumentationTitle;
    public DocumentationModel documentationModel;
    public MFXButton btnUpdate;
    public MFXButton btnFinish;

    public DocumentationController() throws SQLException {
        documentationModel = DocumentationModel.getInstance();
    }

    public void handleCreateDocumentation(ActionEvent actionEvent) {
        String title = txtfDocumentationTitle.getText();
        String publicText = txtaPublicInformation.getText();
        String privateText = txtaPrivateinformation.getText();
        Documentation documentation = new Documentation(1,title,publicText,privateText,documentationModel.getSelectedJob().getId());
        try{
            documentationModel.createDocumentation(documentation);
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }catch (Exception e){
            alertUser("Could not create documentation");
            e.printStackTrace();
        }

    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public void setupUpdate(Documentation selectedDocumentation) {
        btnFinish.setVisible(false);
        txtaPublicInformation.setText(selectedDocumentation.getPublicText());
        txtfDocumentationTitle.setText(selectedDocumentation.getTitle());
        txtaPrivateinformation.setText(selectedDocumentation.getPrivateText());
    }

    public void handleUpdateDocumentation(ActionEvent actionEvent) {
        String title = txtfDocumentationTitle.getText();
        String publicTxt = txtaPublicInformation.getText();
        String privateTxt = txtaPrivateinformation.getText();
        try {
            documentationModel.updateDocumentation(title, publicTxt, privateTxt);
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }catch (SQLException e){
            alertUser("Could not the update documentation");
            e.printStackTrace();
        }
    }

    public void setupCreate(Documentation selectedDocumentation) {
        btnUpdate.setVisible(false);
    }
}
