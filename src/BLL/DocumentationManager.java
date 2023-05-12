package BLL;

import BE.Documentation;
import BE.Job;
import DAL.DB.DocumentationDAO_DB;
import DAL.IDocumentationDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;

public class DocumentationManager {

    public IDocumentationDAO documentationDAO;
    public DocumentationManager(){
        documentationDAO = new DocumentationDAO_DB();
    }
    public void createDocumentation(Documentation documentation, Job selectedJob) throws SQLException {
        documentationDAO.createDocumentation(documentation, selectedJob);
    }

    public void updateDocumentation(Documentation documentation) throws SQLException {
        documentationDAO.updateDocumentation(documentation);
    }
}
