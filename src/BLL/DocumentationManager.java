package BLL;

import BE.Documentation;
import BE.Job;
import DAL.IDocumentationDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public class DocumentationManager {

    public IDocumentationDAO documentationDAO;
    public void createDocumentation(Documentation documentation, Job selectedJob) throws SQLServerException {
        documentationDAO.createDocumentation(documentation, selectedJob);
    }
}
