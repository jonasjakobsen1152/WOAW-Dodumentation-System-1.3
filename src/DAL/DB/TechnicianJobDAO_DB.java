package DAL.DB;

import BE.Documentation;
import BE.JobImage;
import BE.Job;
import DAL.ITechnicianJobDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TechnicianJobDAO_DB implements ITechnicianJobDAO {
    private MyDatabaseConnector databaseConnector;
    public TechnicianJobDAO_DB(){
        databaseConnector = new MyDatabaseConnector();
    }
    @Override
    public List<Documentation> getDocumentation(Job selectedJob) throws SQLException {
        ArrayList<Documentation> documentations = new ArrayList<>();
        try (Connection conn = databaseConnector.getConnection();
            Statement stmt = conn.createStatement()){
            String sql = "SElECT * FROM Document WHERE JobID ="+selectedJob.getId()+";";


            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("ID");
                String title = rs.getString("Title");
                String publicNote = rs.getString("PublicNote");
                String privateNote = rs.getString("PrivateNote");
                int jobId = rs.getInt("JobID");

                Documentation documentation = new Documentation(id,title,publicNote,privateNote,jobId);
                documentations.add(documentation);
            }
            return documentations;
        }catch (SQLException e){
            throw new SQLException(e);
        }

    }

    @Override
    public void deleteDocumentation(Documentation selectedDocumentation) throws SQLException {
        try (Connection conn = databaseConnector.getConnection()){
            String sql = "DELETE FROM Document WHERE ID = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1,selectedDocumentation.getId());
            stmt.executeUpdate();
        }catch (SQLException e){
            throw new SQLException(e);
        }
    }

    @Override
    public List<JobImage> getImages(Job selectedJob) throws SQLException {
        try(Connection conn = databaseConnector.getConnection()){
            ArrayList<JobImage> jobImages = new ArrayList<>();
            String sql = "SELECT * FROM Image WHERE JobID =" +selectedJob.getId()+";";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("ID");
                String title = rs.getString("Title");
                byte[] bytes = rs.getBytes("ImageData");
                int jobId = rs.getInt("JobID");
                String privacy = rs.getString("Private");

                JobImage jobImage = new JobImage(id,title,bytes,jobId,privacy);
                jobImages.add(jobImage);
            }
            return jobImages;
        }catch (SQLException e){
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteImage(JobImage selectedJobImage) throws SQLException {
        try(Connection conn = databaseConnector.getConnection()){
            String sql = "DELETE FROM Image WHERE ID = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, selectedJobImage.getId());
            stmt.executeUpdate();
        }catch (SQLException e){
            throw  new SQLException(e);
        }
    }
}
