package DAL.DB;

import BE.Documentation;
import BE.Job;
import DAL.IDocumentationDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class DocumentationDAO_DB implements IDocumentationDAO {
    private MyDatabaseConnector databaseConnector;

    public DocumentationDAO_DB(){
        databaseConnector = new MyDatabaseConnector();
    }
    @Override
    public void createDocumentation(Documentation documentation, Job selectedJob) throws SQLServerException {
        try(Connection conn = databaseConnector.getConnection()){
            String sql = "INSERT INTO Document ";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
