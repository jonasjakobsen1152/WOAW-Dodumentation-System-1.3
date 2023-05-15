package DAL.DB;

import BE.JobImage;
import DAL.IAddImageDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddImageDAO_DB implements IAddImageDAO {
    MyDatabaseConnector myDatabaseConnector;
    public AddImageDAO_DB() {
        myDatabaseConnector = new MyDatabaseConnector();
    }

    /**
     * Called when the user is done creating an image.
     * Adds a new image to the database in the Image table.
     * Uses SQL
     * @param jobImage
     * @throws SQLException
     */
    @Override
    public void addImage(JobImage jobImage) throws SQLException {
        try(Connection conn = myDatabaseConnector.getConnection()){
            String sql = "INSERT INTO Image (Title, ImageData, JobID, Private) VALUES (?,?,?,?)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, jobImage.getTitle());
            stmt.setBytes(2, jobImage.getData());
            stmt.setInt(3, jobImage.getJobId());
            stmt.setString(4, jobImage.getPrivacy());

            stmt.executeUpdate();
        }catch (SQLException e){
            throw new SQLException(e);
        }
    }
}
