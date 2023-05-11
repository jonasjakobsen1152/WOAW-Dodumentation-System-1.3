package DAL.DB;

import BE.Customer;
import BE.Job;
import BE.User;
import DAL.ITechnicianDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TechnicianDAO_DB implements ITechnicianDAO {

    private MyDatabaseConnector databaseConnector;
    public TechnicianDAO_DB(){
        databaseConnector = new MyDatabaseConnector();
    }
    @Override
    public List<Job> getWork(User selectedUser) {
        ArrayList<Job> jobs = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection();
            Statement stmt = conn.createStatement()){
            int userId = selectedUser.getId();
            String sql =
                    "SELECT j.Title " +
                    "FROM Job j " +
                    "INNER JOIN UserOnJob uoj ON j.ID = uoj.JobID" +
                    "WHERE j.CustomerID = ? AND uoj.UserID = ?;";

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("ID");
                String title = rs.getString("Title");
                int customerId = rs.getInt("CustomerID");

                Job job = new Job(id,title,customerId);
                jobs.add(job);
            }
        }catch (SQLException ex){
            throw new RuntimeException();
        }
        return jobs;
    }
}

