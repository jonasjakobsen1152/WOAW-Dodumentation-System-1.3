package DAL.DB;

import BE.Customer;
import BE.Job;
import BE.User;
import DAL.ITechnicianDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TechnicianDAO_DB implements ITechnicianDAO {

    private MyDatabaseConnector databaseConnector;
    public TechnicianDAO_DB(){
        databaseConnector = new MyDatabaseConnector();
    }

    /**
     * Gets all the jobs based on the selected user.
     * @param selectedUser
     * @return
     */
    @Override
    public List<Job> getWork(User selectedUser) {
        ArrayList<Job> jobs = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT j.ID, j.Title, j.CustomerID " +
                             "FROM Job j " +
                             "INNER JOIN UserOnJob uoj ON j.ID = uoj.JobID " +
                             "WHERE uoj.UserID = ?")) {

            stmt.setInt(1, selectedUser.getId());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("Title");
                int customerId = rs.getInt("CustomerID");

                Job job = new Job(id, title, customerId);
                jobs.add(job);
            }
        } catch (SQLException ex) {
            throw new RuntimeException();
        }
        return jobs;
    }

    /**
     * This is used to mark a job as finished why removing all the technicians from the job.
     * @param selectedJob
     */
    @Override
    public void finishJob(Job selectedJob) {
        try(Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM UserOnJob WHERE JobID = ?");
            stmt.setInt(1, selectedJob.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

