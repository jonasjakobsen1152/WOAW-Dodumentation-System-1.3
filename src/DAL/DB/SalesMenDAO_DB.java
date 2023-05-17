package DAL.DB;

import BE.Customer;
import BE.Documentation;
import BE.Job;
import BE.JobImage;
import DAL.ISalesmenDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;

public class SalesMenDAO_DB implements ISalesmenDAO {

    private MyDatabaseConnector databaseConnector;

    public SalesMenDAO_DB(){
        databaseConnector = new MyDatabaseConnector();
    }

    @Override
    public ArrayList<Customer> getAllCustomers() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "Select * from Customer";

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                int phone = rs.getInt("Phone");
                String email = rs.getString("Email");
                String address = rs.getString("Address");

                Customer customer = new Customer(id,name,phone,email,address);
                customers.add(customer);
            }
        }
        return customers;
    }

    @Override
    public ArrayList<Job> getAllJobs(Customer selectedCustomer) throws SQLException {
        ArrayList<Job> jobs = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Job WHERE ID = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1,selectedCustomer.getId());

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("ID");
                String title = rs.getString("Title");
                int customerID = rs.getInt("CustomerID");


                Job job = new Job(id,title,customerID);
                jobs.add(job);
            }
        }
        return jobs;
    }

    @Override
    public ArrayList<JobImage> getAllJobImages(Job selectedJob) throws SQLException {
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
    public ArrayList<Documentation> getallDocuments(Job selectedJob) throws SQLException {
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
}
