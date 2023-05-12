package GUI.MODEL;

import BE.Documentation;
import BE.Job;
import BLL.DocumentationManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;

public class DocumentationModel {
    private static DocumentationModel instance;
    public DocumentationManager documentationManager;
    public TechnicianJobModel technicianJobModel;
    public Job selectedJob;

    public DocumentationModel() throws SQLException {
        documentationManager = new DocumentationManager();
        technicianJobModel = TechnicianJobModel.getInstance();

    }

    public static DocumentationModel getInstance() throws SQLException {
        if(instance == null){
            instance = new DocumentationModel();
        }
        return instance;
    }


    public void createDocumentation(Documentation documentation) throws SQLException {
        documentationManager.createDocumentation(documentation,selectedJob);
        technicianJobModel.showList();
    }

    public void setSelectedJob(Job selectedDocument) {
        this.selectedJob = selectedDocument;
    }
    public Job getSelectedJob(){
        return selectedJob;
    }
}
