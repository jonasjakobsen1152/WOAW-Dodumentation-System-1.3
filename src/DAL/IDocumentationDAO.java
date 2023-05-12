package DAL;

import BE.Documentation;
import BE.Job;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;

public interface IDocumentationDAO {
    void createDocumentation(Documentation documentation, Job selectedJob) throws SQLException;

    void updateDocumentation(Documentation documentation) throws SQLException;
}
