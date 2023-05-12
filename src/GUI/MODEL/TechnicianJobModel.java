package GUI.MODEL;

import BE.Documentation;
import BE.Job;
import BLL.TechnicianJobManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class TechnicianJobModel {
    private static TechnicianJobModel instance;
    public ObservableList<Documentation> documentationsToBeViewed;
    public TechnicianJobManager technicianJobManager;
    public LoginModel loginModel;
    public Job selectedJob;
    public TechnicianJobModel() {
        documentationsToBeViewed = FXCollections.observableArrayList();
        loginModel = LoginModel.getInstance();
        technicianJobManager = new TechnicianJobManager();

        
    }

    public static TechnicianJobModel getInstance() throws SQLException {
        if(instance == null){
            instance = new TechnicianJobModel();
        }
        return instance;
    }

    public void showList() throws SQLException {
        documentationsToBeViewed.clear();
        documentationsToBeViewed.addAll(technicianJobManager.getDocumentation(getSelectedJob()));
    }
    public void setSelectedJob(Job selectedDocument) {
        this.selectedJob = selectedDocument;
    }
    public Job getSelectedJob(){
        return selectedJob;
    }
    public ObservableList<Documentation> getDocumentationsToBeViewed(){return documentationsToBeViewed;}

    public void deleteDocumentation(Documentation selectedDocumentation) throws SQLException {
        technicianJobManager.deleteDocumentation(selectedDocumentation);
        showList();
    }
}
