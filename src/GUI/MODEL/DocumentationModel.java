package GUI.MODEL;

import BE.Documentation;
import BE.Job;
import BLL.DocumentationManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;

public class DocumentationModel {
    private static DocumentationModel instance;
    public DocumentationManager documentationManager;
    public Job selectedJob;

    public DocumentationModel(){
        documentationManager = new DocumentationManager();
    }

    public static DocumentationModel getInstance() {
        if(instance == null){
            instance = new DocumentationModel();
        }
        return instance;
    }

    public void showList() throws SQLException {


    }

    public void createDocumentation(Documentation documentation) throws SQLException {
        documentationManager.createDocumentation(documentation,selectedJob);
    }

    public void setSelectedJob(Job selectedDocument) {
        this.selectedJob = selectedDocument;
    }
    public Job getSelectedJob(){
        return selectedJob;
    }
}
