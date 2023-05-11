package DAL;

import BE.Documentation;
import BE.Job;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public interface IDocumentationDAO {
    void createDocumentation(Documentation documentation, Job selectedJob) throws SQLServerException;
}
