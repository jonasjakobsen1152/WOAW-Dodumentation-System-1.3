package DAL.DB;

import BE.Documentation;
import BE.Job;
import DAL.IDocumentationDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DocumentationDAO_DB implements IDocumentationDAO {
    private MyDatabaseConnector databaseConnector;

    public DocumentationDAO_DB(){
        databaseConnector = new MyDatabaseConnector();
    }

    /**
     * Creates new notes in the database
     * @param documentation
     * @param selectedJob
     * @throws SQLException
     */
    @Override
    public void createDocumentation(Documentation documentation, Job selectedJob) throws SQLException {
        try(Connection conn = databaseConnector.getConnection()){
            String sql = "INSERT INTO Document (Title, PublicNote, PrivateNote, JobID) VALUES (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, documentation.title);
            stmt.setString(2,documentation.publicText);
            stmt.setString(3,documentation.privateText);
            stmt.setInt(4,selectedJob.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    /**
     * Changes the values of some notes in the database
     * @param documentation
     * @throws SQLException
     */
    @Override
    public void updateDocumentation(Documentation documentation) throws SQLException {
        try(Connection conn = databaseConnector.getConnection()){
            String sql = "UPDATE Document SET Title = ?, PublicNote = ?, PrivateNote = ?, JobID = ? WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1,documentation.getTitle());
            stmt.setString(2,documentation.getPublicText());
            stmt.setString(3,documentation.getPrivateText());
            stmt.setInt(4,documentation.getJobId());
            stmt.setInt(5,documentation.getId());

            stmt.executeUpdate();
        }catch (SQLException e){
            throw new SQLException(e);
        }
    }
}
