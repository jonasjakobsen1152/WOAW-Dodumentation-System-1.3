package DAL.DB;

import BE.Documentation;
import BE.Job;
import BE.User;
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
        try (Connection conn = databaseConnector.getConnection()){
            String sql = "SElECT * FROM Document WHERE JobID = (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1,selectedJob.getId());

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
}
